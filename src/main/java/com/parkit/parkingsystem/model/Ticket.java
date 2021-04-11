package com.parkit.parkingsystem.model;

import java.util.Date;

/**
 * Class model of a Ticket
 * 
 * The attributes of Ticket :id, parkingSpot, vehicleRegNumber, price, inTime,
 * outTime
 * 
 * @author Christine Duarte
 *
 */
public class Ticket {
	/**
	 * An attribute of the class that contain the id of the ticket
	 */
	private int id;
	/**
	 * An attribute of the class that contain the parkingSpot of the ticket
	 */
	private ParkingSpot parkingSpot;
	/**
	 * An attribute of the class that contain the vehicleRegNumber of the ticket
	 */
	private String vehicleRegNumber;
	/**
	 * An attribute of the class that contain the price of the ticket
	 */
	private double price;
	/**
	 * An attribute of the class that contain the inTime : the time of entry of the
	 * vehicle
	 */
	private Date inTime;
	/**
	 * An attribute of the class that contain the outTime : time of exit of the
	 * vehicle
	 */
	private Date outTime;

	/**
	 * Method get to obtain the id of the ticket
	 * 
	 * @return id An integer
	 */
	public int getId() {
		return id;
	}

	/**
	 * Method to set the id in the ticket
	 * 
	 * @param id An integer
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Method get to obtain the parkingSpot of the ticket
	 * 
	 * @return parkingSpot An instance of the class ParkingSpot
	 * 
	 * @see ParkingSpot
	 */
	public ParkingSpot getParkingSpot() {
		return parkingSpot;
	}

	/**
	 * Method to set the parkingSpot of the ticket
	 * 
	 * @param parkingSpot parkingSpot An instance of the class ParkingSpot
	 * 
	 * @see ParkingSpot
	 */
	public void setParkingSpot(ParkingSpot parkingSpot) {
		this.parkingSpot = parkingSpot;
	}

	/**
	 * Method get to obtain the vehicleRegNumber of the ticket
	 * 
	 * @return vehicleRegNumber A String with the Vehicle Registration Number
	 */
	public String getVehicleRegNumber() {
		return vehicleRegNumber;
	}

	/**
	 * Method to set the vehicleRegNumber of the ticket
	 * 
	 * @param vehicleRegNumber A String with the Vehicle Registration Number
	 */
	public void setVehicleRegNumber(String vehicleRegNumber) {
		this.vehicleRegNumber = vehicleRegNumber;
	}

	/**
	 * Method get to obtain the price of the ticket
	 * 
	 * @return price A double number
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * Method to set the price of the ticket
	 * 
	 * @param price A double number
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * Method get to obtain the inTime of the ticket
	 * 
	 * @return inTime The time of entry of the vehicle
	 */
	public Date getInTime() {
		return new Date(inTime.getTime());
	}

	/**
	 * Method to set the inTime of the ticket
	 * 
	 * @param inTime the Time of entry of the vehicle
	 */
	public void setInTime(Date inTime) {
		this.inTime = new Date(inTime.getTime());
	}

	/**
	 * Method get to obtain the outTime of the ticket
	 * 
	 * @return outTime The time of exit
	 */
	public Date getOutTime() {
		return outTime != null ? new Date(outTime.getTime()) : null;
	}

	/**
	 * Method to set the outTime of the ticket
	 * 
	 * @param outTime The time of exit
	 */
	public void setOutTime(Date outTime) {
		this.outTime = outTime != null ? new Date(outTime.getTime()) : null;
	}
}
