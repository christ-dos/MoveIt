package com.parkit.parkingsystem;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.parkit.parkingsystem.service.InteractiveShell;

/**
 * Class that contain the method main that run the application
 * 
 * @author Christine Duarte
 *
 */
public class App {
	/**
	 * Creation of a logger instance to manage the application logs
	 * 
	 * @see Logger
	 */
	private static final Logger logger = LogManager.getLogger("App");

	/**
	 * Method that launches the interface for user interaction
	 * 
	 * @param args is a array of string
	 */
	public static void main(String args[]) {
		logger.info("Initializing Parking System");
		InteractiveShell.loadInterface();
	}
}
