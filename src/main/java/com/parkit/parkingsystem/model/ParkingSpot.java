package com.parkit.parkingsystem.model;

import com.parkit.parkingsystem.constants.ParkingType;

/**
 * Class model of a Parking Spot
 * 
 * The attributes of ParkingSpot : number, parkingType, isAvailable
 * 
 * @author Christine Duarte
 *
 */
public class ParkingSpot {
	/**
	 * An attribute of the class that contain the number of the parking slot
	 */
	private int number;
	/**
	 * An attribute of the class that contain the type of vehicle
	 */
	private ParkingType parkingType;
	/**
	 * A boolean that that contain true if the parking slot is available
	 */
	private boolean isAvailable;

	/**
	 * The constructor of the class ParkingSpot
	 * 
	 * @param number      The number of the parking slot
	 * 
	 * @param parkingType The type of vehicle that want to park in parking spot
	 * 
	 * @param isAvailable A boolean that return true if the parkingSlot is available
	 */
	public ParkingSpot(int number, ParkingType parkingType, boolean isAvailable) {
		this.number = number;
		this.parkingType = parkingType;
		this.isAvailable = isAvailable;
	}

	/**
	 * Method get to obtain the id of the parkingSpot
	 * 
	 * @return number an integer with the number of the ParkingSpot
	 */
	public int getId() {
		return number;
	}

	/**
	 * Method to set the value of the id
	 * 
	 * @param number an integer with the number of the ParkingSpot
	 */
	public void setId(int number) {
		this.number = number;
	}

	/**
	 * Method to obtain the type of the vehicle that want to park in the ParkingSpot
	 * 
	 * @return parkingType the type of vehicle that want to park in parking spot
	 */
	public ParkingType getParkingType() {
		return parkingType;
	}

	/**
	 * Method to set the value of the parkingType
	 * 
	 * @param parkingType the type of vehicle that want to park in parking spot
	 */
	public void setParkingType(ParkingType parkingType) {
		this.parkingType = parkingType;
	}

	/**
	 * Method that obtain the value of the boolean isAvailable
	 * 
	 * @return isAvailable A boolean that return true if the parkingSlot is
	 *         available
	 */
	public boolean isAvailable() {
		return isAvailable;
	}

	/**
	 * Method that set the value of the boolean isAvailable
	 * 
	 * @param available A boolean that return true if the parkingSlot is available
	 */
	public void setAvailable(boolean available) {
		isAvailable = available;
	}

	/**
	 * Method equal overrided
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		ParkingSpot that = (ParkingSpot) o;
		return number == that.number;
	}

	/**
	 * Method hashCode overrided
	 */
	@Override
	public int hashCode() {
		return number;
	}
}
