package com.parkit.parkingsytem.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;

/**
 * Class that test methods of the class TicketDAO
 * 
 * @author Christine Duarte
 *
 */
public class TicketDAOTest {

	/**
	 * @see DataBaseTestConfig
	 */
	private static DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();

	/**
	 * @see TicketDAO
	 */
	private static TicketDAO ticketDAO;

	/**
	 * @see DataBasePrepareService
	 */
	private static DataBasePrepareService dataBasePrepareService;

	/**
	 * @see Ticket
	 */
	private static Ticket ticket;

	/**
	 * Method that set the configuration required before anything
	 * 
	 */
	@BeforeAll
	private static void setUp() {
		ticketDAO = new TicketDAO();
		ticketDAO.dataBaseConfig = dataBaseTestConfig;
		dataBasePrepareService = new DataBasePrepareService();
	}

	/**
	 * Method that set the configuration required before each test
	 */
	@BeforeEach
	private void setUpPerTest() {
		dataBasePrepareService.clearDataBaseEntries();
		String vehicleRegNumber = "ABCDEF";
		ticket = new Ticket();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, true);
		ticket.setInTime(new Date());
		ticket.setVehicleRegNumber(vehicleRegNumber);
		ticket.setParkingSpot(parkingSpot);
		ticket.setOutTime(new Date());
		ticket.setPrice(0);
	}

	/**
	 * Method that test the occurrences find in ticketDAO of a Vehicle Registration
	 * Number
	 */
	@Test
	public void TestingGetOccurrencesVehicleRegNumberFindedInTicketDAO() {

		// GIVEN
		String vehicleRegNumber = "ABCDEF";

		// WHEN
		ticketDAO.saveTicket(ticket);
		int result = ticketDAO.getOccurrencesTicket(vehicleRegNumber);
		// THEN
		assertEquals(1, result);
	}

	/**
	 * Method that test if the ticket is saved in ticketDAO
	 */
	@Test
	public void TestingSavedTicketInTicketDAO() {

		// GIVEN

		// WHEN
		boolean resultExpected = ticketDAO.updateTicket(ticket);
		// THEN
		assertTrue(resultExpected);
	}

	/**
	 * Method that test if the ticket is updated in ticketDAO
	 */
	@Test
	public void TestingUpdatedTicketInTicketDAO() {

		// GIVEN

		// WHEN
		boolean resultExpected = ticketDAO.saveTicket(ticket);
		// THEN
		// return false if the first result of the method execute() is an update count
		assertTrue(resultExpected);
	}

	/**
	 * Method that test get a ticket of Bike vehicle in parkingSpot number four
	 */
	@Test
	public void testGetTicketABikeInSlotFour() {

		// GIVEN
		String vehicleRegNumber = "AAAAA";
		ParkingSpot parkingSpot = new ParkingSpot(4, ParkingType.BIKE, false);
		ticket.setInTime(new Date());
		ticket.setOutTime(null);
		ticket.setVehicleRegNumber(vehicleRegNumber);
		ticket.setParkingSpot(parkingSpot);
		// WHEN
		ticketDAO.saveTicket(ticket);
		Ticket ResultTicketDAO = ticketDAO.getTicket(vehicleRegNumber);
		// THEN
		assertEquals(ResultTicketDAO.getVehicleRegNumber(), ticket.getVehicleRegNumber());
	}

}
