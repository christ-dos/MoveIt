package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

public class FareCalculatorService {

	public void calculateFare(Ticket ticket) {
		if ((ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime()))) {
			throw new IllegalArgumentException("Out time provided is incorrect:" + ticket.getOutTime().toString());
		}

		long inTime = ticket.getInTime().getTime();
		long outTime = ticket.getOutTime().getTime();

		// TODO: Some tests are failing here. Need to check if this logic is correct
		double durationMs = ((outTime - inTime) / (60 * 1000));
		double duration = durationMs / 60;
		// duration is less that 1/2 hours
		if (duration < 0.5) {
			duration = 0;
		}

		switch (ticket.getParkingSpot().getParkingType()) {
		case CAR: {
			ticket.setPrice(duration * Fare.CAR_RATE_PER_HOUR);
			break;
		}
		case BIKE: {
			ticket.setPrice(duration * Fare.BIKE_RATE_PER_HOUR);
			break;
		}
		default:
			throw new IllegalArgumentException("Unkown Parking Type");
		}

	}
}