package com.parkit.parkingsystem.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;

/**
 * Class Test that testing if the ParkingService
 * 
 * @author Christine Duarte
 *
 */
@ExtendWith(MockitoExtension.class)
public class ParkingDataBaseIT {

	private static DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
	private static ParkingSpotDAO parkingSpotDAO;
	private static TicketDAO ticketDAO;
	private static DataBasePrepareService dataBasePrepareService;
	private static ParkingService parkingService;

	@Mock
	private static InputReaderUtil inputReaderUtil;

	@BeforeAll
	private static void setUp() throws Exception {
		parkingSpotDAO = new ParkingSpotDAO();
		parkingSpotDAO.dataBaseConfig = dataBaseTestConfig;
		ticketDAO = new TicketDAO();
		ticketDAO.dataBaseConfig = dataBaseTestConfig;
		dataBasePrepareService = new DataBasePrepareService();
		dataBasePrepareService.clearDataBaseEntries();
	}

	@BeforeEach
	private void setUpPerTest() throws Exception {
		parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
		when(inputReaderUtil.readSelection()).thenReturn(1);
		when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");
		System.out.println("executer avec before each");
		dataBasePrepareService.clearDataBaseEntries();
		parkingService.processIncomingVehicle();
	}

	@AfterEach
	private void unDefPerTest() throws Exception {

	}

	@Test
	public void testParkingACar() {

		// GIVEN
		String vehicleRegNumber = "ABCDEF";
		Ticket ticket = new Ticket();
		// WHEN
		ticket = ticketDAO.getTicket(vehicleRegNumber);
		// THEN
		assertEquals(vehicleRegNumber, ticket.getVehicleRegNumber());
		assertNotNull(ticket);
		assertFalse(ticket.getParkingSpot().isAvailable());
		// TODO: check that a ticket is actually saved in DB and Parking table is
		// updated
		// with availability
	}

	@Test
	public void testParkingLotExit() throws Exception {
		// GIVEN
		dataBasePrepareService.clearDataBaseEntries();
		String vehicleRegNumber = "ABCDEF";
		Ticket ticket = new Ticket();
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis() - (60 * 60 * 1000));
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
		// WHEN
		ticket.setInTime(inTime);
		ticket.setVehicleRegNumber(vehicleRegNumber);
		ticket.setParkingSpot(parkingSpot);
		ticketDAO.saveTicket(ticket);
		parkingService.processExitingVehicle();
		double price = ticketDAO.getTicket(vehicleRegNumber).getPrice();
		Date outTimePopulatedInDB = ticketDAO.getTicket(vehicleRegNumber).getOutTime();
		// THEN
		assertNotNull(outTimePopulatedInDB);
		assertEquals(1.50, price);
		// TODO: check that the fare generated and out time are populated correctly in
		// the database
	}
}
