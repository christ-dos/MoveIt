package com.parkit.parkingsystem;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;

/**
 * Class that test the Parking Service with MockitoExtension
 * 
 * @author Christine Duarte
 *
 */
@ExtendWith(MockitoExtension.class)
public class ParkingServiceTest {

	/**
	 * An attribute of the class
	 * 
	 * @see ParkingService
	 */
	private static ParkingService parkingService;

	/**
	 * A mock of the class InputReaderUtil
	 * 
	 * @see InputReaderUtil
	 */
	@Mock
	private static InputReaderUtil inputReaderUtil;

	/**
	 * A mock of the class ParkingSpotDAO
	 * 
	 * @see ParkingSpotDAO
	 */
	@Mock
	private static ParkingSpotDAO parkingSpotDAO;

	/**
	 * A mock of the class TicketDAO
	 * 
	 * @see TicketDAO
	 */
	@Mock
	private static TicketDAO ticketDAO;

	/**
	 * Method that set the configuration required before each test
	 */
	@BeforeEach
	private void setUpPerTest() {
		try {
			parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to set up test mock objects");
		}
	}

	/**
	 * Method that test the process to exiting the parking spot verification if the
	 * methods was called with mocks
	 * 
	 * @throws Exception
	 */
	@Test
	public void processExitingVehicleTest() throws Exception {
		// GIVEN
		when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");
		Ticket ticket = new Ticket();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);

		ticket.setInTime(new Date(System.currentTimeMillis() - (60 * 60 * 1000)));
		ticket.setVehicleRegNumber("ABCDEF");
		ticket.setParkingSpot(parkingSpot);
		ticket.setPrice(0);
		ticket.setOutTime(null);

		when(ticketDAO.updateTicket(any(Ticket.class))).thenReturn(true);
		when(ticketDAO.getTicket(anyString())).thenReturn(ticket);
		when(parkingSpotDAO.updateParking(any(ParkingSpot.class))).thenReturn(true);
		// WHEN
		parkingService.processExitingVehicle();
		// THEN
		verify(parkingSpotDAO, times(1)).updateParking(any(ParkingSpot.class));
		verify(ticketDAO, times(1)).updateTicket(any(Ticket.class));
		verify(ticketDAO, times(1)).getTicket(anyString());
	}

	/**
	 * Method that test the process to incoming the parking spot verification if the
	 * methods was called with mocks
	 * 
	 * @throws Exception
	 */
	@Test
	public void processIncomingVehicleTest() throws Exception {
		// GIVEN
		ParkingType parkingType = ParkingType.CAR;
		when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");
		when(ticketDAO.getOccurrencesTicket(anyString())).thenReturn(2);
		when(inputReaderUtil.readSelection()).thenReturn(1);
		when(parkingSpotDAO.getNextAvailableSlot(parkingType)).thenReturn(1);
		// WHEN
		parkingService.processIncomingVehicle();
		// THEN
		verify(parkingSpotDAO, times(1)).updateParking(any(ParkingSpot.class));
		verify(ticketDAO, times(1)).saveTicket(any(Ticket.class));
		verify(ticketDAO, times(1)).getOccurrencesTicket(anyString());
		verify(parkingSpotDAO, times(1)).getNextAvailableSlot(any());
	}

	/**
	 * Method that test if an exception is thrown when the parkingType is null
	 * 
	 * @throws Exception
	 */
	@Test
	public void getNextParkingNumberIfAvailableTestWhenParkingTypeIsNull() {

		// GIVEN
		// incorrect input in the method getVehicleType()
		when(inputReaderUtil.readSelection()).thenReturn(3);
		// WHEN

		// THEN
		assertThrows(Exception.class, () -> parkingService.getNextParkingNumberIfAvailable().getParkingType());
	}

	/**
	 * Method that verify if getNextAvailableSlot() is called in method
	 * getNextParkingNumberIfAvailable()
	 */
	@Test
	public void getNextParkingNumberIfAvailableTest() {

		// GIVEN
		ParkingType parkingType = ParkingType.CAR;
		when(inputReaderUtil.readSelection()).thenReturn(1);
		when(parkingSpotDAO.getNextAvailableSlot(parkingType)).thenReturn(1);
		// WHEN
		parkingService.getNextParkingNumberIfAvailable();
		// THEN
		verify(parkingSpotDAO, times(1)).getNextAvailableSlot(any());

	}

}
