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
 * Class that Test the ParkingService and the database with an integration test
 * 
 * @author Christine Duarte
 *
 */
@ExtendWith(MockitoExtension.class)
public class ParkingDataBaseIT {
	/**
	 * @see DataBaseTestConfig
	 */
	private static DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
	/**
	 * @see ParkingSpotDAO
	 */
	private static ParkingSpotDAO parkingSpotDAO;
	/**
	 * @see TicketDAO
	 */
	private static TicketDAO ticketDAO;
	/**
	 * @see DataBasePrepareService
	 */
	private static DataBasePrepareService dataBasePrepareService;
	/**
	 * @see ParkingService
	 */
	private static ParkingService parkingService;
	/**
	 * @see InputReaderUtil
	 */
	@Mock
	private static InputReaderUtil inputReaderUtil;

	/**
	 * Method that set the configuration required before anything
	 * 
	 * @throws Exception The exception is not treated it's throws
	 */
	@BeforeAll
	private static void setUp() throws Exception {
		parkingSpotDAO = new ParkingSpotDAO();
		parkingSpotDAO.dataBaseConfig = dataBaseTestConfig;
		ticketDAO = new TicketDAO();
		ticketDAO.dataBaseConfig = dataBaseTestConfig;
		dataBasePrepareService = new DataBasePrepareService();
		dataBasePrepareService.clearDataBaseEntries();
	}

	/**
	 * Method that set the configuration required before each test
	 * 
	 * @throws Exception The exception is not treated it's throws
	 */
	@BeforeEach
	private void setUpPerTest() throws Exception {
		parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
		when(inputReaderUtil.readSelection()).thenReturn(1);
		when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");
		dataBasePrepareService.clearDataBaseEntries();
		parkingService.processIncomingVehicle();
	}

	/**
	 * Method that set the configuration required after each test
	 * 
	 * @throws Exception The exception is not treated it's throws
	 */
	@AfterEach
	private void unDefPerTest() throws Exception {

	}

	/**
	 * Method that test if the ticket is saved in the database and if the parking
	 * table is is updated with availability
	 */
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

	/**
	 * Method that test if the fare generated and the out time were populated
	 * correctly in database
	 * 
	 * @throws Exception The exception is not treated it's throws
	 */
	@Test
	public void testParkingLotExit() throws Exception {
		// GIVEN
		dataBasePrepareService.clearDataBaseEntries();
		String vehicleRegNumber = "ABCDEF";
		Ticket ticket = new Ticket();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
		// WHEN
		ticket.setInTime(new Date(System.currentTimeMillis() - (60 * 60 * (1000 + 2))));
		ticket.setVehicleRegNumber(vehicleRegNumber);
		ticket.setParkingSpot(parkingSpot);
		// ticket.setOutTime(new Date(ticket.getInTime().getTime() + 2000));
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
