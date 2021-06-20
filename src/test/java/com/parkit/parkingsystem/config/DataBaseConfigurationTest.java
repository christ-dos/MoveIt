package com.parkit.parkingsystem.config;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.parkit.parkingsystem.constants.DBConstants;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;

/**
 * Class that test the DataBase configuration
 * 
 * @author Christine Duarte
 *
 */
@ExtendWith(MockitoExtension.class)
public class DataBaseConfigurationTest {
	/**
	 * @see DataBaseTestConfig
	 */
	private static DataBaseConfig dataBaseConfig;

	/**
	 * An attribute that permit A connection (session) with a specific database
	 */
	private static Connection con;

	/**
	 * An object that represents a precompiled SQL statement.
	 * 
	 */
	private static PreparedStatement prepaStatt;

	/**
	 * A table of data representing a database result set,
	 */
	private static ResultSet resultSet;

	/**
	 * Method that set the configuration required before anything
	 */
	@BeforeAll
	public static void setUp() {
		dataBaseConfig = new DataBaseConfig();
	}

	/**
	 * Method that test the connection to the database
	 * 
	 * @throws SQLException           An exception that provides information on a
	 *                                database access error or other errors
	 * 
	 * @throws ClassNotFoundException
	 * 
	 * @see SQLException
	 * @see ClassNotFoundException
	 * @see DataBaseConfig
	 */
	@Test
	public void getConnectionDataBaseTest() throws ClassNotFoundException, SQLException {
		// GIVEN

		// WHEN
		con = dataBaseConfig.getConnection();
		// THEN
		assertNotNull(con);
	}

	/**
	 * Method that test if the connection is closed
	 * 
	 * @throws SQLException
	 * 
	 * @throws ClassNotFoundException
	 * 
	 * @see SQLException
	 * @see ClassNotFoundException
	 * @see DataBaseConfig
	 */
	@Test
	public void closeConnectionDataBaseTest() throws ClassNotFoundException, SQLException {
		// GIVEN
		con = dataBaseConfig.getConnection();
		// WHEN
		dataBaseConfig.closeConnection(con);
		boolean ConIsClosed = con.isClosed();

		// THEN
		assertTrue(ConIsClosed);
	}

	/**
	 * Method that test if the connection will closed if the connection is null
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * 
	 * @see SQLException
	 * @see ClassNotFoundException
	 * @see DataBaseConfig
	 */
	@Test
	public void closeConnectionDataBaseTestWhenConIsNull() throws ClassNotFoundException, SQLException {
		// GIVEN
		con = dataBaseConfig.getConnection();
		con = null;
		// WHEN
		dataBaseConfig.closeConnection(con);
		// THEN
		assertThrows(NullPointerException.class, () -> con.close());
	}

	/**
	 * Method that test if the prepared Statement is closed
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * 
	 * @see SQLException
	 * @see ClassNotFoundException
	 * @see DataBaseConfig
	 */
	@Test
	public void closePreparedStatementTest() throws SQLException, ClassNotFoundException {
		// GIVEN
		con = dataBaseConfig.getConnection();
		prepaStatt = con.prepareStatement(DBConstants.GET_NEXT_PARKING_SPOT);
		// WHEN
		dataBaseConfig.closePreparedStatement(prepaStatt);
		boolean resultPrepaStattIsClosed = prepaStatt.isClosed();

		// THEN
		assertTrue(resultPrepaStattIsClosed);
	}

	/**
	 * Method that test if the resultSet is closed
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * 
	 * @see SQLException
	 * @see ClassNotFoundException
	 * @see DataBaseConfig
	 */
	@Test
	public void closeResultSetTest() throws SQLException, ClassNotFoundException {
		// GIVEN
		String vehicleRegNumber = "ABCDEF";
		con = dataBaseConfig.getConnection();
		prepaStatt = con.prepareStatement(DBConstants.GET_TICKET);
		prepaStatt.setString(1, vehicleRegNumber);
		resultSet = prepaStatt.executeQuery();
		// WHEN
		dataBaseConfig.closeResultSet(resultSet);
		boolean resultSetIsClosed = resultSet.isClosed();

		// THEN
		assertTrue(resultSetIsClosed);
	}

}
