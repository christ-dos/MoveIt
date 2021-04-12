package com.parkit.parkingsystem.integration.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.parkit.parkingsystem.config.DataBaseConfig;

/**
 * Class that configure the database for the tests and that extend the class
 * DataBaseConfig
 * 
 * @author Christine Duarte
 *
 */
public class DataBaseTestConfig extends DataBaseConfig {
	/**
	 * An instance of Logger
	 * 
	 * @see Logger
	 */
	private static final Logger logger = LogManager.getLogger("DataBaseTestConfig");

	/**
	 * Method that get connection to the database test
	 * 
	 * @exception ClassNotFoundException The exception is not treated it's throws
	 * 
	 * @exception SQLException           The exception is not treated it's throws
	 * 
	 */
	public Connection getConnection() throws ClassNotFoundException, SQLException {
		logger.info("Create DB connection");
		Class.forName("com.mysql.cj.jdbc.Driver");
		return DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/test?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Europe/Paris",
				"root", "rootroot");
	}

	/**
	 * Method that close connection to the database test
	 * 
	 * @exception SQLException is throws if occur an error while closing the
	 *                         connection of database
	 */
	public void closeConnection(Connection con) {
		if (con != null) {
			try {
				con.close();
				logger.info("Closing DB connection");
			} catch (SQLException e) {
				logger.error("Error while closing connection", e);
			}
		}
	}

	/**
	 * Method that close the prepared statement to the database test
	 * 
	 * @exception SQLException is throws if occur an error while closing prepared
	 *                         statement
	 */
	public void closePreparedStatement(PreparedStatement ps) {
		if (ps != null) {
			try {
				ps.close();
				logger.info("Closing Prepared Statement");
			} catch (SQLException e) {
				logger.error("Error while closing prepared statement", e);
			}
		}
	}

	/**
	 * Method that close the set of results of the database test
	 * 
	 * @exception SQLException is throws if occur an error while closing the result
	 *                         set
	 */
	public void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
				logger.info("Closing Result Set");
			} catch (SQLException e) {
				logger.error("Error while closing result set", e);
			}
		}
	}
}
