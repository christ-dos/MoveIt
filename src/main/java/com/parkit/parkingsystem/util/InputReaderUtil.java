package com.parkit.parkingsystem.util;

import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class that read the inputs of the user in the console
 * 
 * @author Christine Duarte
 *
 */
public class InputReaderUtil {
	/**
	 * An instance of the class Scanner to scan the input in the console
	 * 
	 * @see Scanner
	 */
	private static Scanner scan = new Scanner(System.in);
	/**
	 * An instance of Logger
	 * 
	 * @see Logger
	 */
	private static final Logger logger = LogManager.getLogger("InputReaderUtil");

	/**
	 * Method that read the selection input by the user
	 * 
	 * @return input The character read in the console and cast in integer
	 * 
	 * @exception Exception if the input is invalid
	 */
	public int readSelection() {
		try {
			int input = Integer.parseInt(scan.nextLine());
			return input;
		} catch (Exception e) {
			logger.error("Error while reading user input from Shell", e);
			System.out.println("Error reading input. Please enter valid number for proceeding further");
			return -1;
		}
	}

	/**
	 * Method that read the vehicle registration number input in the console
	 * 
	 * @return vehicleRegNumber A String with the vehicleRegNumber
	 * 
	 * @throws Exception if the input is invalid
	 */
	public String readVehicleRegistrationNumber() throws Exception {
		try {
			String vehicleRegNumber = scan.nextLine();
			if (vehicleRegNumber == null || vehicleRegNumber.trim().length() == 0) {
				throw new IllegalArgumentException("Invalid input provided");
			}
			return vehicleRegNumber;
		} catch (Exception e) {
			logger.error("Error while reading user input from Shell", e);
			System.out.println("Error reading input. Please enter a valid string for vehicle registration number");
			throw e;
		}
	}

}
