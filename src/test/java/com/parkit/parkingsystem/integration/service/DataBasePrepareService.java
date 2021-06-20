package com.parkit.parkingsystem.integration.service;

import java.sql.Connection;

import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;

/**
 * Class that prepare the service of the database
 * 
 * @author Christine Duarte
 * 
 */
public class DataBasePrepareService {
	/**
	 * An instance of the class DataBaseTestConfig
	 * 
	 * @see DataBaseTestConfig
	 */
	DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();

	/**
	 * Method that clear the entries of the database of test
	 * 
	 * @exception Exception it's throws if a prepared statement is not execute
	 *                      correctly or if the connection to the database fail
	 */
	public void clearDataBaseEntries() {
		Connection connection = null;
		try {
			connection = dataBaseTestConfig.getConnection();

			// set parking entries to available
			connection.prepareStatement("update parking set available = true").execute();

			// clear ticket entries;
			connection.prepareStatement("truncate table ticket").execute();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dataBaseTestConfig.closeConnection(connection);
		}
	}

}
