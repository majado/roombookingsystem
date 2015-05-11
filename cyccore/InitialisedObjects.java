/*
 * Copyright (C) 2015 Max Dowkes
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>. 
 */
package cyccore;

import cycpersistence.DatabaseConnector;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Max Dowkes
 */
public class InitialisedObjects
{

    private static Map<Integer, Room> ROOM_MAP = new HashMap<Integer, Room>();
    private static Map<Integer, StartTime> STARTTIME_MAP = new HashMap<Integer, StartTime>();
    private static Map<Integer, EndTime> ENDTIME_MAP = new HashMap<Integer, EndTime>();
    private static Map<Integer, Booking> bookingMap = new HashMap<Integer, Booking>();
    private static Map<Integer, Customer> customerMap = new HashMap<Integer, Customer>();
    private static Map<Integer, CYCDate> dateMap = new HashMap<Integer, CYCDate>();
    private ResultSet rows;
    private Booking aBooking;

    /**
     * Constructor uses the InitialiseObjects class to initialise Maps for
     * rooms, start times and end times.
     */
    public InitialisedObjects()
    {
        InitialiseObjects initO = new InitialiseObjects();
        initO.setUpObjects();
        ROOM_MAP = initO.getRoomMap();
        STARTTIME_MAP = initO.getStartTimeMap();
        ENDTIME_MAP = initO.getEndTimeMap();
    }

    /**
     * Method used to get dates from the database.
     */
    public void getDates()
    {
        rows = DatabaseConnector.getDateRows();
        try
        {
            while (rows.next())
            {
                //check for existing date
                CYCDate cycDate;
                int dateID = rows.getInt("dates_id");
                if (!dateMap.containsKey(dateID))
                {
                    java.sql.Date sqlDate = rows.getDate("date");
                    cycDate = new CYCDate(sqlDate.getTime(), dateID);
                    dateMap.put(dateID, cycDate);
                }
                else
                {
                    cycDate = dateMap.get(rows.getInt("dates_id"));
                }

                System.out.println(
                        "Date is " + cycDate + " Date id is " + cycDate.getDateID() + " and map is " + dateMap);
            }
        }
        catch (SQLException ex)
        {
            System.out.println(ex);
        }
        DatabaseConnector.closeConnection();
    }

    /**
     * Method to update the map of dates when new dates are created.
     * May be redundant due to CYCDate now being able to link itself
     * to bookings
     */
    public void updateDateMap()
    {
        rows = DatabaseConnector.getDateRows();
        try
        {
            while (rows.next())
            {
                //check for existing date
                CYCDate cycDate;
                int dateID = rows.getInt("dates_id");
                if (!dateMap.containsKey(dateID))
                {
                    java.sql.Date sqlDate = rows.getDate("date");
                    cycDate = new CYCDate(sqlDate.getTime(), dateID);
                    dateMap.put(dateID, cycDate);
                }
                else
                {
                    cycDate = dateMap.get(rows.getInt("dates_id"));
                }

                System.out.println(
                        "Date is " + cycDate + " Date id is " + cycDate.getDateID() + " and map is " + dateMap);

            }
        }
        catch (SQLException ex)
        {
            System.out.println(ex);
        }
        DatabaseConnector.closeConnection();

    }

    /**
     * Method to get customers from database.
     */
    public void getCustomers()
    {
        rows = DatabaseConnector.getCustomerRows();
        try
        {
            while (rows.next())
            {
                //check for existing customers
                Customer aCustomer;
                int customerID = rows.getInt("customer_id");
                if (!customerMap.containsKey(customerID))
                {
                    aCustomer = new Customer(rows.getInt("customer_id"),
                            rows.getString("customer_name"));
                    customerMap.put(customerID, aCustomer);
                }
                else
                {
                    aCustomer = customerMap.get(rows.getInt("customer_id"));
                }
                System.out.println(
                        "Customer is " + aCustomer + " and map is " + customerMap);
            }
        }
        catch (SQLException ex)
        {
            System.out.println(ex);
        }
        DatabaseConnector.closeConnection();
    }

    /**
     * Method to get booking data from database.
     */
    public void getDBData()
    {
        int count = 1;
        rows = DatabaseConnector.getRows();

        try
        {
            while (rows.next())
            {
                int bookingID = rows.getInt("booking_id");
                int roomID = rows.getInt("room_id");
                Room room;
                room = getRoomByID(roomID);

                int startTimeID = rows.getInt("starttimes_id");
                StartTime startTime;
                startTime = getStartTimeByID(startTimeID);

                int endTimeID = rows.getInt("endtimes_id");
                EndTime endTime;
                endTime = getEndTimeByID(endTimeID);

                int attendees = rows.getInt("attendees");

                CYCDate cycDate = null;
                cycDate = dateMap.get(rows.getInt("dates_id"));

                Customer aCustomer;
                aCustomer = customerMap.get(rows.getInt("customer_id"));

                System.out.println("Room is " + room);
                System.out.println("Date is " + cycDate);
                System.out.println("Customer is " + aCustomer.getCustomerID());
                System.out.println("Starttime is " + startTime);
                System.out.println("Endtime is " + endTime);

                // Each booking object is created from data held in the database
                aBooking = new Booking(bookingID, aCustomer, startTime, endTime,
                        room, cycDate, attendees);
                bookingMap.put(aBooking.getID(), aBooking);
                System.out.println(aBooking.getID());
                System.out.println(bookingMap);
                System.out.println("Iteration " + count);
                count++;
                System.out.println(aBooking.toString());

                aCustomer.addBooking(aBooking.getID(), aBooking);
                cycDate.addBooking(aBooking.getID(), aBooking);
            }
        }
        catch (SQLException ex)
        {
            System.out.println(ex);
        }
        DatabaseConnector.closeConnection();
    }

    /**
     * Method used to get a Room by using it's id
     * @param aID an int representing a Room id
     * @return A Room object which relates to the id supplied
     */
    public Room getRoomByID(int aID)
    {
        Collection roomCollection = ROOM_MAP.values();
        Room aRoom = null;

        for (Object eachRoom : roomCollection)
        {
            aRoom = (Room) eachRoom;

            if (aRoom.getRoomID() == aID)
            {
                return aRoom;
            }
        }
        return aRoom;
    }

    /**
     * Method used to get a StartTime by using it's id
     * @param aID an int representing a StartTime id
     * @return A StartTime object which relates to the id supplied
     */    
    public StartTime getStartTimeByID(int aID)
    {
        Collection startTimeCollection = STARTTIME_MAP.values();
        StartTime aStartTime = null;

        for (Object eachStartTime : startTimeCollection)
        {
            aStartTime = (StartTime) eachStartTime;

            if (aStartTime.getStartTimeID() == aID)
            {
                return aStartTime;
            }
        }
        return aStartTime;
    }

    /**
     * Method used to get an EndTime by using it's id
     * @param aID an int representing an EndTime id
     * @return An EndTime object which relates to the id supplied
     */    
    public EndTime getEndTimeByID(int aID)
    {
        Collection endTimeCollection = ENDTIME_MAP.values();
        EndTime aEndTime = null;

        for (Object eachEndTime : endTimeCollection)
        {
            aEndTime = (EndTime) eachEndTime;

            if (aEndTime.getEndTimeID() == aID)
            {
                return aEndTime;
            }
        }
        return aEndTime;
    }

    /**
     * Getter method used to return the bookingMap Map
     * @return A Map containing Booking objects
     */
    public static Map<Integer, Booking> getBookingMap()
    {
        return bookingMap;
    }

    /**
     * Getter method used to return the customerMap Map
     * @return A Map containing Customer objects
     */
    public static Map<Integer, Customer> getCustomerMap()
    {
        return customerMap;
    }

    /**
     * Getter method used to return the dateMap Map
     * @return A Map containing CYCDate objects
     */
    public static Map<Integer, CYCDate> getDateMap()
    {
        return dateMap;
    }

    /**
     * Getter method used to return the ROOM_MAP Map
     * @return A Map containing Room objects
     */
    public static Map<Integer, Room> getROOM_MAP()
    {
        return ROOM_MAP;
    }

    /**
     * Getter method used to return the STARTTIME_MAP Map
     * @return A Map containing StartTime objects
     */
    public static Map<Integer, StartTime> getSTARTTIME_MAP()
    {
        return STARTTIME_MAP;
    }

    /**
     * Getter method used to return the ENDTIME_MAP Map
     * @return A Map containing EndTime objects
     */
    public static Map<Integer, EndTime> getENDTIME_MAP()
    {
        return ENDTIME_MAP;
    }
}
