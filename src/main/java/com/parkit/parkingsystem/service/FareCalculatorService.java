package com.parkit.parkingsystem.service;

import org.apache.commons.math3.util.Precision;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

/**
 * Class that calculate the fees to paid
 * 
 * @author Christine Duarte
 *
 */
public class FareCalculatorService {
	/**
	 * Method that calculate the fare to paid
	 * 
	 * @param ticket An instance of the class Ticket
	 * 
	 * @see Ticket
	 */
	public void calculateFare(Ticket ticket) {
		if ((ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime()))) {
			throw new IllegalArgumentException("Out time provided is incorrect:" + ticket.getOutTime().toString());
		}
		long inTime = ticket.getInTime().getTime();
		long outTime = ticket.getOutTime().getTime();
		// TODO: Some tests are failing here. Need to check if this logic is correct
		double durationMs = ((outTime - inTime) / (double) (60 * 1000));
		double duration = durationMs / (double) 60;

		// if duration is less that 1/2 hours
		if (duration < 0.5) {
			duration = 0;
		}
		// Calculate the fare rate using the getTicketFareRate method
		double fareRate = getTicketFareRate(ticket);

		// ticket.setPrice(duration * fareRate);
		ticket.setPrice(getPriceRounded(duration * fareRate));
	}

	/**
	 * Method of getting the ticket fare rate.
	 * 
	 * @param ticket An instance of the class Ticket
	 * 
	 * @return a double with the fare rate
	 */
	private double getTicketFareRate(Ticket ticket) {
		double fareRate = 0.0;
		switch (ticket.getParkingSpot().getParkingType()) {
		case CAR:
			fareRate = Fare.CAR_RATE_PER_HOUR;
			break;
		case BIKE:
			fareRate = Fare.BIKE_RATE_PER_HOUR;
			break;
		default:
			throw new IllegalArgumentException("Unkown Parking Type");
		}
		return fareRate;
	}

	/**
	 * Method that calculate the fare with discount of 5%
	 * 
	 * @param ticket An instance of the class Ticket
	 */
	public void calculateFareWithDiscount(Ticket ticket) {

		double discount = 0.05;// 5% give coefficient 0.05
		calculateFare(ticket);
		double price = ticket.getPrice();
		double reducing = price * discount;

		System.out.println("The full fees is  :" + getPriceRounded(price) + "$");
		ticket.setPrice(getPriceRounded(price - reducing));
	}

	public double getPriceRounded(double price) {

		return Precision.round(price, 2);

	}

}