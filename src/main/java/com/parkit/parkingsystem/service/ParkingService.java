package com.parkit.parkingsystem.service;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.util.InputReaderUtil;

/**
 * Class of service of the parking spot
 * 
 * @author Christine Duarte
 *
 */
public class ParkingService {
	/**
	 * @see Logger
	 */
	private static final Logger logger = LogManager.getLogger("ParkingService");
	/**
	 * @see FareCalculatorService
	 */
	private static FareCalculatorService fareCalculatorService = new FareCalculatorService();
	/**
	 * @see InputReaderUtil
	 */
	private InputReaderUtil inputReaderUtil;
	/**
	 * @see ParkingSpotDAO
	 */
	private ParkingSpotDAO parkingSpotDAO;
	/**
	 * @see TicketDAO
	 */
	private TicketDAO ticketDAO;
	/**
	 * An integer that contain the occurrences of vehicleRegNumber
	 */
	private int occurrences;

	/**
	 * The constructor of the class ParkingService
	 * 
	 * @param inputReaderUtil An instance of InputReaderUtil that read the inputs to
	 *                        the console
	 * @param parkingSpotDAO  An instance of ParkingSpotDAO
	 * @see ParkingSpotDAO
	 * 
	 * @param ticketDAO An instance of TicketDAO
	 * @see TicketDAO
	 */
	public ParkingService(InputReaderUtil inputReaderUtil, ParkingSpotDAO parkingSpotDAO, TicketDAO ticketDAO) {
		this.inputReaderUtil = inputReaderUtil;
		this.parkingSpotDAO = parkingSpotDAO;
		this.ticketDAO = ticketDAO;
	}

	/**
	 * Method that process to the entry of a vehicle
	 * 
	 * @exception is throw if it Unable to process incoming vehicle
	 */
	public void processIncomingVehicle() {
		try {
			ParkingSpot parkingSpot = getNextParkingNumberIfAvailable();
			if (parkingSpot != null && parkingSpot.getId() > 0) {
				String vehicleRegNumber = getVehichleRegNumber();

				occurrences = ticketDAO.getOccurrencesTicket(vehicleRegNumber);
				// check if it's a recurring user of the parking lot
				if (occurrences >= 1) {
					System.out.println(
							"Welcome back!As a recurring user of our parking lot, you'll benefit from a 5% discount.");
				}
				parkingSpot.setAvailable(false);
				parkingSpotDAO.updateParking(parkingSpot);// allot this parking space and mark it's availability as
				Date inTime = new Date();
				Ticket ticket = new Ticket();
				// ID, PARKING_NUMBER, VEHICLE_REG_NUMBER, PRICE, IN_TIME, OUT_TIME)
				// ticket.setId(ticketID);
				ticket.setParkingSpot(parkingSpot);
				ticket.setVehicleRegNumber(vehicleRegNumber);
				ticket.setPrice(0);
				ticket.setInTime(inTime);
				ticket.setOutTime(null);
				ticketDAO.saveTicket(ticket);

				System.out.println("Generated Ticket and saved in DB");
				System.out.println("Please park your vehicle in spot number:" + parkingSpot.getId());
				System.out.println("Recorded in-time for vehicle number:" + vehicleRegNumber + " is:" + inTime);
			}
		} catch (Exception e) {
			logger.error("Unable to process incoming vehicle", e);
		}
	}

	/**
	 * Method that get the Vehicle Registration Number
	 * 
	 * @return the input read in the console
	 * @throws Exception
	 */
	private String getVehichleRegNumber() throws Exception {
		System.out.println("Please type the vehicle registration number and press enter key");
		return inputReaderUtil.readVehicleRegistrationNumber();
	}

	/**
	 * Method that get the next parking number if available
	 * 
	 * @return parkingSpot An instance of ParkingSpot
	 * @see ParkingSpot
	 * 
	 * @throws Exception
	 * @throws IllegalArgumentException if the input for the type of vehicle is
	 *                                  wrong
	 */
	public ParkingSpot getNextParkingNumberIfAvailable() {
		int parkingNumber = 0;
		ParkingSpot parkingSpot = null;
		try {
			ParkingType parkingType = getVehichleType();
			parkingNumber = parkingSpotDAO.getNextAvailableSlot(parkingType);
			if (parkingNumber > 0) {
				parkingSpot = new ParkingSpot(parkingNumber, parkingType, true);
			} else {
				throw new Exception("Error fetching parking number from DB. Parking slots might be full");
			}
		} catch (IllegalArgumentException ie) {
			logger.error("Error parsing user input for type of vehicle", ie);
		} catch (Exception e) {
			logger.error("Error fetching next available parking slot", e);
		}
		return parkingSpot;
	}

	/**
	 * Method that get the type of the vehicle input in the console by the user
	 * 
	 * @return depends of the result of the switch
	 * 
	 * @throws IllegalArgumentException if the input is invalid
	 * 
	 */
	private ParkingType getVehichleType() {
		System.out.println("Please select vehicle type from menu");
		System.out.println("1 CAR");
		System.out.println("2 BIKE");
		int input = inputReaderUtil.readSelection();
		switch (input) {
		case 1: {
			return ParkingType.CAR;
		}
		case 2: {
			return ParkingType.BIKE;
		}
		default: {
			System.out.println("Incorrect input provided");
			throw new IllegalArgumentException("Entered input is invalid");
		}
		}
	}

	/**
	 * Method that process to exiting if the vehicle
	 * 
	 * @exception Exception if it's Unable to process exiting vehicle
	 */
	public void processExitingVehicle() {
		try {
			String vehicleRegNumber = getVehichleRegNumber();
			Ticket ticket = ticketDAO.getTicket(vehicleRegNumber);
			Date outTime = new Date();
			ticket.setOutTime(outTime);
			// if not a recurring user paid full fare
			if (occurrences < 2) {
				fareCalculatorService.calculateFare(ticket);
				System.out.println("Please pay the parking fare:" + ticket.getPrice() + "$");
				// fare calculate with discount
			} else {
				fareCalculatorService.calculateFareWithDiscount(ticket);
				System.out.println("Please pay the parking fare (you get 5% discount): " + ticket.getPrice() + "$");
			}
			if (ticketDAO.updateTicket(ticket)) {
				ParkingSpot parkingSpot = ticket.getParkingSpot();
				parkingSpot.setAvailable(true);
				parkingSpotDAO.updateParking(parkingSpot);

				System.out.println(
						"Recorded out-time for vehicle number:" + ticket.getVehicleRegNumber() + " is:" + outTime);
			} else {
				System.out.println("Unable to update ticket information. Error occurred");
			}
		} catch (Exception e) {
			logger.error("Unable to process exiting vehicle", e);
		}
	}
}
