package com.parkit.parkingsystem.constants;

/**
 * Class that enclose constant that contain the SQL statement precompiled
 * 
 * @author Christine Duarte
 *
 */
public class DBConstants {
	/**
	 * This constant get the next parking spot available saved in the table parking
	 * of the database
	 */
	public static final String GET_NEXT_PARKING_SPOT = "select min(PARKING_NUMBER) from parking where AVAILABLE = true and TYPE = ?";
	/**
	 * This constant update the parking spot saved in the table parking of the
	 * database
	 */
	public static final String UPDATE_PARKING_SPOT = "update parking set available = ? where PARKING_NUMBER = ?";
	/**
	 * This constant save the ticket in the table ticket by inserting in columns :
	 * PARKING_NUMBER, VEHICLE_REG_NUMBER, PRICE, IN_TIME, OUT_TIME of the database
	 */
	public static final String SAVE_TICKET = "insert into ticket(PARKING_NUMBER, VEHICLE_REG_NUMBER, PRICE, IN_TIME, OUT_TIME) values(?,?,?,?,?)";
	/**
	 * This constant update the ticket in the table ticket by inserting in columns :
	 * PRICE and OUT_TIME for corresponding ID
	 */
	public static final String UPDATE_TICKET = "update ticket set PRICE=?, OUT_TIME=? where OUT_TIME IS null and ID=? ";
	/**
	 * This constant give the ticket saved in the table ticket in order by the most
	 * recent date
	 */
	public static final String GET_TICKET = "select t.PARKING_NUMBER, t.ID, t.PRICE, t.IN_TIME, t.OUT_TIME, p.TYPE from ticket t,parking p where p.parking_number = t.parking_number and t.VEHICLE_REG_NUMBER=? order by t.IN_TIME DESC  limit 1";
	/**
	 * This constant count the occurrences saved in the table ticket group by
	 * VEHICLE_REG_NUMBER
	 */
	public static final String GET_OCCURENCES_TICKET = "select t.VEHICLE_REG_NUMBER, COUNT(*) AS occurences from ticket t where t.VEHICLE_REG_NUMBER=? GROUP BY t.VEHICLE_REG_NUMBER ";
}
