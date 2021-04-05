package com.parkit.parkingsystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.parkit.parkingsystem.App;
import com.parkit.parkingsystem.config.DataBaseConfig;
import com.parkit.parkingsystem.constants.DBConstants;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.model.ParkingSpot;

/**
 * Class that manage the table Parking in the database
 * 
 * @author Christine Duarte
 *
 */
public class ParkingSpotDAO {
	/**
	 * An instance of Logger
	 * 
	 * @see App
	 */
	private static final Logger logger = LogManager.getLogger("ParkingSpotDAO");
	/**
	 * An instance of DataBaseConfig
	 * 
	 * @see DataBaseConfig
	 */
	public DataBaseConfig dataBaseConfig = new DataBaseConfig();

	/**
	 * Method that permit to obtain the next available slot depending on the type of
	 * vehicle
	 * 
	 * @param parkingType contain the type of vehicle that want to park
	 * 
	 * @see ParkingType
	 * 
	 * @exception Exception display with the logger
	 * 
	 * @return result the number of the spot available
	 */
	public int getNextAvailableSlot(ParkingType parkingType) {
		Connection con = null;
		int result = -1;
		try {
			con = dataBaseConfig.getConnection();
			PreparedStatement ps = con.prepareStatement(DBConstants.GET_NEXT_PARKING_SPOT);
			ps.setString(1, parkingType.toString());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				result = rs.getInt(1);
			}
			dataBaseConfig.closeResultSet(rs);
			dataBaseConfig.closePreparedStatement(ps);
		} catch (Exception ex) {
			logger.error("Error fetching next available slot", ex);
		} finally {
			dataBaseConfig.closeConnection(con);
		}
		return result;
	}

	/**
	 * This method is a boolean that update the availability of the parking spot in
	 * database
	 * 
	 * @param parkingSpot contain attributes of the class :isAvailable, ParkingType
	 *                    and number of spot
	 * @return 1 to passe the row isAvailable in database to 1 if the spot is
	 *         available
	 * 
	 * @return false the method return false if not possible to update parking spot
	 */
	public boolean updateParking(ParkingSpot parkingSpot) {
		Connection con = null;
		try {
			con = dataBaseConfig.getConnection();
			PreparedStatement ps = con.prepareStatement(DBConstants.UPDATE_PARKING_SPOT);
			ps.setBoolean(1, parkingSpot.isAvailable());
			ps.setInt(2, parkingSpot.getId());
			int updateRowCount = ps.executeUpdate();
			dataBaseConfig.closePreparedStatement(ps);
			return (updateRowCount == 1);
		} catch (Exception ex) {
			logger.error("Error updating parking info", ex);
			return false;
		} finally {
			dataBaseConfig.closeConnection(con);
		}
	}

}
