package com.parkit.parkingsystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.parkit.parkingsystem.config.DataBaseConfig;
import com.parkit.parkingsystem.constants.DBConstants;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;

/**
 * Class that manage the ticket in the database
 * 
 * @author Christine Duarte
 *
 */
public class TicketDAO {
	/**
	 * Creation of a logger instance to manage the logs of the application
	 * 
	 * @see Logger
	 */
	private static final Logger logger = LogManager.getLogger("TicketDAO");
	/**
	 * An instance of DataBaseConfig
	 * 
	 * @see DataBaseConfig
	 */
	public DataBaseConfig dataBaseConfig = new DataBaseConfig();

	/**
	 * Method that save a ticket in the database with columns :
	 * 
	 * @param ticket an instance of ticket that contain : ID, PARKINGSPOT,
	 *               VEHICLE_REG_NUMBER, PRICE, IN_TIME, OUT_TIME
	 * 
	 * @return false if the ticket cannot be saved with success
	 * 
	 * @exception if the ticket cannot be saved, display the exception with a logger
	 */
	public boolean saveTicket(Ticket ticket) {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = dataBaseConfig.getConnection();
			ps = con.prepareStatement(DBConstants.SAVE_TICKET);
			// ID, PARKING_NUMBER, VEHICLE_REG_NUMBER, PRICE, IN_TIME, OUT_TIME)
			// ps.setInt(1,ticket.getId());
			ps.setInt(1, ticket.getParkingSpot().getId());
			ps.setString(2, ticket.getVehicleRegNumber());
			ps.setDouble(3, ticket.getPrice());
			ps.setTimestamp(4, new Timestamp(ticket.getInTime().getTime()));
			ps.setTimestamp(5, (ticket.getOutTime() == null) ? null : (new Timestamp(ticket.getOutTime().getTime())));
			ps.execute();
			return true;
		} catch (Exception ex) {
			logger.error("Error save ticket info", ex);
		} finally {
			dataBaseConfig.closePreparedStatement(ps);
			dataBaseConfig.closeConnection(con);
		}
		return false;
	}

	/**
	 * Method that get a ticket saved in the database
	 * 
	 * @param vehicleRegNumber A String with the Vehicle Registration Number
	 * 
	 * @return ticket With values of the columns :ID, PARKING_NUMBER,
	 *         VEHICLE_REG_NUMBER, PRICE, IN_TIME, OUT_TIME saved the in database
	 * 
	 * @exception if the ticket cannot be obtained, display the exception with a
	 *               logger
	 * 
	 */
	public Ticket getTicket(String vehicleRegNumber) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Ticket ticket = null;
		try {
			con = dataBaseConfig.getConnection();
			ps = con.prepareStatement(DBConstants.GET_TICKET);
			ps.setString(1, vehicleRegNumber);
			rs = ps.executeQuery();
			if (rs.next()) {
				ticket = new Ticket();
				ParkingSpot parkingSpot = new ParkingSpot(rs.getInt(1), ParkingType.valueOf(rs.getString(6)), false);
				ticket.setParkingSpot(parkingSpot);
				ticket.setId(rs.getInt(2));
				ticket.setVehicleRegNumber(vehicleRegNumber);
				ticket.setPrice(rs.getDouble(3));
				ticket.setInTime(rs.getTimestamp(4));
				ticket.setOutTime(rs.getTimestamp(5));
			}
		} catch (Exception ex) {
			logger.error("Error fetching the ticket", ex);
		} finally {
			dataBaseConfig.closeResultSet(rs);
			dataBaseConfig.closePreparedStatement(ps);
			dataBaseConfig.closeConnection(con);
		}
		return ticket;
	}

	/**
	 * Method that update the ticket saved in database with the PRICE and the
	 * OUT_TIME
	 * 
	 * @param ticket an instance of ticket that contain : ID, PARKINGSPOT,
	 *               VEHICLE_REG_NUMBER, PRICE, IN_TIME, OUT_TIME
	 * 
	 * @return true if the ticket is updated with success
	 * 
	 * @exception if the ticket cannot be updated, display the exception with a
	 *               logger
	 */
	public boolean updateTicket(Ticket ticket) {
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = dataBaseConfig.getConnection();
			ps = con.prepareStatement(DBConstants.UPDATE_TICKET);
			ps.setDouble(1, ticket.getPrice());
			ps.setTimestamp(2, new Timestamp(ticket.getOutTime().getTime()));
			ps.setInt(3, ticket.getId());
			ps.execute();
			return true;
		} catch (Exception ex) {
			logger.error("Error upadate ticket info", ex);
		} finally {
			dataBaseConfig.closePreparedStatement(ps);
			dataBaseConfig.closeConnection(con);
		}
		return false;
	}

	/**
	 * Method that count the occurrences of VEHICLE_REG_NUMBER in the database
	 * 
	 * @param vehicleRegNumber A string that contain the vehicle registration number
	 * 
	 * @return occurrences A integer of occurrences find in database
	 * 
	 * @exception if the ticket cannot be fetching the occurrences of the
	 *               VEHICLE_REG_NUMBER ,display the exception with a logger
	 */
	public int getOccurrencesTicket(String vehicleRegNumber) {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int occurrences = 0;
		try {
			con = dataBaseConfig.getConnection();
			ps = con.prepareStatement(DBConstants.GET_OCCURRENCES_TICKET);

			ps.setString(1, vehicleRegNumber);
			rs = ps.executeQuery();
			if (rs.next()) {
				occurrences = rs.getInt(2);
			}
		} catch (Exception ex) {
			logger.error("Error fetching the occurrences", ex);
		} finally {
			dataBaseConfig.closeResultSet(rs);
			dataBaseConfig.closePreparedStatement(ps);
			dataBaseConfig.closeConnection(con);
		}
		return occurrences;
	}
}
