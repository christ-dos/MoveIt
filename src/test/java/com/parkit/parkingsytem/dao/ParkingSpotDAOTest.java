package com.parkit.parkingsytem.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.model.ParkingSpot;

/**
 * Class that testing methods getNextAvailableSlot and updateParking of the
 * class ParkingSpotDAO
 * 
 * @author Christine Duarte
 *
 */
public class ParkingSpotDAOTest {

	/**
	 * @see DataBaseTestConfig
	 */
	private static DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();

	/**
	 * @see ParkingSpotDAO
	 */
	private static ParkingSpotDAO parkingSpotDAO;
	/**
	 * @see ParkingSpot
	 */
	private ParkingSpot parkingSpot;

	/**
	 * Method that set the configuration required before anything
	 * 
	 * @throws Exception The exception is not treated it's throws
	 */
	@BeforeAll
	private static void setUp() {
		parkingSpotDAO = new ParkingSpotDAO();
		parkingSpotDAO.dataBaseConfig = dataBaseTestConfig;
	}

	/**
	 * Method that testing if the parkingSpotDAO availability is updated to false
	 * when the boolean isAvailable of parkingSpot is false
	 */
	@Test
	public void TestingIfParkingSpotDAOIsUpdatedWithBikeInSlotFour() {
		// GIVEN
		parkingSpot = new ParkingSpot(4, ParkingType.BIKE, false);
		// WHEN
		boolean resultExpected = parkingSpotDAO.updateParking(parkingSpot);
		// THEN
		assertTrue(resultExpected);
	}

	/**
	 * Method that testing if the slot number four is not available to park a BIKE
	 * then parkingSystem get the next available slot number five
	 */
	@Test
	public void TestingParkingDAOIfgetNextAvailableSlot() {
		// GIVEN
		parkingSpot = new ParkingSpot(4, ParkingType.BIKE, false);
		ParkingType parkingType = ParkingType.BIKE;
		// WHEN
		parkingSpotDAO.updateParking(parkingSpot);
		int Result = parkingSpotDAO.getNextAvailableSlot(parkingType);
		// THEN
		assertEquals(5, Result);
	}

}
