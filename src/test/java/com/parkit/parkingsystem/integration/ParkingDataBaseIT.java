package com.parkit.parkingsystem.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;

@ExtendWith(MockitoExtension.class)
public class ParkingDataBaseIT {

	private static DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
	private static ParkingSpotDAO parkingSpotDAO;
	private static TicketDAO ticketDAO;
	private static DataBasePrepareService dataBasePrepareService;
	private static ParkingService parkingService;
	private static Ticket ticket;
	private static boolean isAvailable;

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
		dataBasePrepareService.clearDataBaseEntries();

	}

	@AfterEach
	private void unDefPerTest() throws Exception {

		parkingService = null;
	}

	@AfterAll
	private static void tearDown() {

		parkingSpotDAO = null;
		ticketDAO = null;
		dataBasePrepareService = null;

	}

	@Test
	@Disabled
	public void testParkingACar() {

		// GIVEN

		// WHEN
		parkingService.processIncomingVehicle();
		String VehicleRegNumberDAO = ticketDAO.getTicket("ABCDEF").getVehicleRegNumber();

		int availableParkingSlotDAO = parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR);
		int availableParkingSlot = parkingService.getNextParkingNumberIfAvailable().getId();
		Boolean resultAvailability = ticketDAO.getTicket("ABCDEF").getParkingSpot().isAvailable();
		boolean result = ticketDAO.saveTicket(ticketDAO.getTicket("ABCDEF"));
		// THEN

		assertEquals("ABCDEF", VehicleRegNumberDAO);
		assertFalse(result);
		assertFalse(resultAvailability);
		assertEquals(availableParkingSlot, availableParkingSlotDAO);

		// TODO: check that a ticket is actually saved in DB and Parking table is
		// updated
		// with availability
	}

	@Test
	public void testParkingLotExit() throws Exception {
		// GIVEN

		// WHEN
		parkingService.processIncomingVehicle();
		parkingService.processExitingVehicle();

		double farepopulatedInDB = ticketDAO.getTicket("ABCDEF").getPrice();
		Date outTimePopulatedInDB = ticketDAO.getTicket("ABCDEF").getOutTime();
		boolean updateTicketisTrue = ticketDAO.updateTicket(ticketDAO.getTicket("ABCDEF"));

		// testParkingACar();

		// THEN
		assertNotNull(farepopulatedInDB);
		assertNotNull(outTimePopulatedInDB);
		assertTrue(updateTicketisTrue);

		// TODO: check that the fare generated and out time are populated correctly in
		// the database
	}

}
