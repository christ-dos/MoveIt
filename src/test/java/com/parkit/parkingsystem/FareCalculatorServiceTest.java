package com.parkit.parkingsystem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Date;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.FareCalculatorService;

/**
 * Class that tests the class FareCalculatorService with units tests
 * 
 * @author Christine Duarte
 *
 */
public class FareCalculatorServiceTest {
	/**
	 * @see FareCalculatorService
	 */
	private static FareCalculatorService fareCalculatorService;
	/**
	 * @see Ticket
	 */
	private Ticket ticket;

	/**
	 * Method that set the configuration required before anything
	 */
	@BeforeAll
	private static void setUp() {
		fareCalculatorService = new FareCalculatorService();
	}

	/**
	 * Method that set the configuration required before each test
	 */
	@BeforeEach
	private void setUpPerTest() {
		ticket = new Ticket();
	}

	/**
	 * Method that tests calculation of fare for a car to one hour of parking time
	 */
	@Test
	public void calculateFareCar() {
		// GIVEN
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis() - (60 * 60 * 1000));
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);
		// WHEN
		fareCalculatorService.calculateFare(ticket);
		// THEN
		assertEquals(Fare.CAR_RATE_PER_HOUR, ticket.getPrice());
	}

	/**
	 * Method that tests calculation of fare for a bike to one hour of parking time
	 */
	@Test
	public void calculateFareBike() {
		// GIVEN
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis() - (60 * 60 * 1000));
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE, false);
		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);
		// WHEN
		fareCalculatorService.calculateFare(ticket);
		// THEN
		assertEquals(Fare.BIKE_RATE_PER_HOUR, ticket.getPrice());
	}

	/**
	 * Method that tests calculation of fare when the type is unknown
	 */
	@Test
	public void calculateFareUnkownType() {
		// GIVEN
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis() - (60 * 60 * 1000));
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, null, false);
		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);
		// WHEN

		// THEN
		assertThrows(NullPointerException.class, () -> fareCalculatorService.calculateFare(ticket));
	}

	/**
	 * Method that tests calculation of fare for a bike with a future inTime
	 */
	@Test
	public void calculateFareBikeWithFutureInTime() {
		// GIVEN
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis() + (60 * 60 * 1000));
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE, false);
		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);
		// WHEN

		// THEN
		assertThrows(IllegalArgumentException.class, () -> fareCalculatorService.calculateFare(ticket));
	}

	/**
	 * Method that tests calculation of fare for a bike with less than one hour of
	 * parking time
	 */
	@Test
	public void calculateFareBikeWithLessThanOneHourParkingTime() {
		// GIVEN
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis() - (45 * 60 * 1000));// 45 minutes parking time should give 3/4th
																		// parking fare
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE, false);
		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);
		// WHEN
		// rounded to the nearest tenth
		double priceExpected = fareCalculatorService.getPriceRounded(0.75 * Fare.BIKE_RATE_PER_HOUR);
		fareCalculatorService.calculateFare(ticket);
		// THEN
		assertEquals(priceExpected, ticket.getPrice());
	}

	/**
	 * Method that tests calculation of fare for a car with less than one hour of
	 * parking time
	 */
	@Test
	public void calculateFareCarWithLessThanOneHourParkingTime() {
		// GIVEN
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis() - (45 * 60 * 1000));// 45 minutes parking time should give 3/4th
																		// parking fare
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);
		// WHEN

		// rounded to the nearest tenth
		double priceExpected = fareCalculatorService.getPriceRounded(0.75 * Fare.CAR_RATE_PER_HOUR);
		fareCalculatorService.calculateFare(ticket);
		// THEN
		assertEquals(priceExpected, ticket.getPrice());
	}

	/**
	 * Method that tests calculation of fare for a car with more than a day of
	 * parking time
	 */
	@Test
	public void calculateFareCarWithMoreThanADayParkingTime() {
		// GIVEN
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis() - (24 * 60 * 60 * 1000));// 24 hours parking time should give 24 *
																			// parking fare per hour
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);
		// WHEN
		// rounded to the nearest tenth
		double priceExpected = fareCalculatorService.getPriceRounded(24 * Fare.CAR_RATE_PER_HOUR);
		fareCalculatorService.calculateFare(ticket);
		// THEN
		assertEquals((priceExpected), ticket.getPrice());
	}

	/**
	 * Method that tests calculation of fare for a bike with more than a day of
	 * parking time
	 */
	@Test
	public void calculateFareBikeWithMoreThanADayParkingTime() {
		// GIVEN
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis() - (24 * 60 * 60 * 1000));// 24 hours parking time should give 24 *
																			// parking fare per hour
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE, false);
		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);
		// WHEN
		// rounded to the nearest tenth
		double priceExpected = fareCalculatorService.getPriceRounded(24 * Fare.BIKE_RATE_PER_HOUR);
		fareCalculatorService.calculateFare(ticket);
		// THEN
		assertEquals((priceExpected), ticket.getPrice());
	}

	/**
	 * Method that tests calculation free fare for a car with less than thirty
	 * minutes of parking time
	 */
	@Test
	public void calculateFreeFareCarWithLessThanThirtyMinutesParkingTime() {
		// GIVEN
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis() - (15 * 60 * 1000));// 15 minutes parking time should give
																		// free fare
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);
		// WHEN
		fareCalculatorService.calculateFare(ticket);
		// THEN
		assertEquals(0, ticket.getPrice());
	}

	/**
	 * Method that tests calculation free fare for a bike with less than thirty
	 * minutes of parking time
	 */
	@Test
	public void calculateFreeFareBikeWithLessThanThirtyMinutesParkingTime() {
		// GIVEN
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis() - (15 * 60 * 1000));// 15 minutes parking time should give
																		// free fare
		Date outTime = new Date();
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE, false);
		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);
		// WHEN
		fareCalculatorService.calculateFare(ticket);
		// THEN
		assertEquals(0, ticket.getPrice());
	}

	/**
	 * Method that tests calculation fare for a car with discount of five percent as
	 * recurring users
	 */
	@Test
	public void calculateFareCarWithDiscountFivePercentThanTwoHourParkingTime() {
		// GIVEN
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis() - (120 * 60 * 1000));
		Date outTime = new Date();
		double PriceExpected = 2 * Fare.CAR_RATE_PER_HOUR;
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);
		// WHEN
		// rounded to the nearest tenth
		double PriceExpectedWithDiscount = fareCalculatorService
				.getPriceRounded(PriceExpected - (PriceExpected * 0.05));// 5% gives
																			// the rate 0.05
		fareCalculatorService.calculateFareWithDiscount(ticket);
		// THEN
		assertEquals(PriceExpectedWithDiscount, ticket.getPrice());
	}

	/**
	 * Method that tests calculation fare for a bike with discount of five percent
	 * as recurring users
	 */
	@Test
	public void calculateFareBikeWithDiscountFivePercentThanTwoHourParkingTime() {
		// GIVEN
		Date inTime = new Date();
		inTime.setTime(System.currentTimeMillis() - (120 * 60 * 1000));
		Date outTime = new Date();
		double PriceForTwoHours = 2 * Fare.BIKE_RATE_PER_HOUR;

		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE, false);
		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);
		ticket.setParkingSpot(parkingSpot);
		// WHEN
		// rounded to the nearest tenth
		double PriceExpectedWithDiscount = PriceForTwoHours - (PriceForTwoHours * 0.05);// 5% gives the
																						// the rate 0.05
		fareCalculatorService.calculateFareWithDiscount(ticket);
		// THEN
		assertEquals(PriceExpectedWithDiscount, ticket.getPrice());
	}

}
