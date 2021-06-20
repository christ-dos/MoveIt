package com.parkit.parkingsystem.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class that manage the configuration of the database
 * 
 * @author Christine Duarte
 *
 */
public class DataBaseConfig {
	/**
	 * Creation of a logger instance to manage the application logs
	 * 
	 * @see Logger class
	 */
	private static final Logger logger = LogManager.getLogger("DataBaseConfig");

	/**
	 * Method that create a connection with the database
	 * 
	 * @return a a driver that get a connection to the database.
	 * 
	 * @see DriverManager
	 * 
	 * @throws ClassNotFoundException Thrown when an application tries to load in a
	 *                                class through its string name using: the
	 *                                forName method in class Class.
	 * 
	 * @throws SQLException           An exception that provides information on a
	 *                                database access error .
	 */
	public Connection getConnection() throws ClassNotFoundException, SQLException {
		logger.info("Create DB connection");
		Class.forName("com.mysql.cj.jdbc.Driver");

		return DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/prod?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Europe/Paris",
				"root", "rootroot");
	}

	/**
	 * Method that permit closure of the connection to the database
	 * 
	 * @param con contain the information of connection to the database
	 * 
	 * @see Logger
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
	 * Method that close a precompiled SQL statement.
	 * 
	 * @param ps is a SQL statement precompiled and stored in a PreparedStatement
	 *           object
	 * @see PreparedStatement
	 * 
	 * @exception SQLException if a database access error occurs or this method is
	 *                         called on a closed connection
	 * 
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
	 * Method that close the set of results obtained with a query
	 * 
	 * @param rs A table of data representing a database result set, which is
	 *           usually generated by executing a statement that queries the
	 *           database.
	 * 
	 * @see SQLException
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