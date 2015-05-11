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
package cycpersistence;

import cyccore.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Max Dowkes
 */
public class DatabaseConnector
{

    private static Connection con;
    private static ResultSet rows;
    private Map<Integer, Customer> customerMap = new HashMap<Integer, Customer>();
    private Map<Integer, CYCDate> dateMap = new HashMap<Integer, CYCDate>();
    private Map<Integer, Booking> bookingMap = new HashMap<Integer, Booking>();
    InitialisedObjects io;

    /**
     * Constructor gets gets booking, customer and date
     * Maps from InitialisedObjects
     */
    public DatabaseConnector()
    {
        bookingMap = io.getBookingMap();
        customerMap = io.getCustomerMap();
        dateMap = io.getDateMap();
    }

    /**
     * Method used to connect to a database using JDBC driver
     * @return A Connection representing a connection to a database
     */
    public static Connection getDBConnection()
    {
        con = null;
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "Obsfuscated";
            String user = "Obsfuscated";
            String pw = "Obsfuscated";
            con = DriverManager.getConnection(url, user, pw);
            System.out.println("Connected to the database getcon");
        }
        catch (ClassNotFoundException ex)
        {
            System.out.println("An error has occured: " + ex);
        }
        catch (SQLException ex)
        {
            System.out.println("An SQL error has occured: " + ex.getMessage());
        }
        return con;
    }

    /*    public static Connection getConnection()
    {
    return DatabaseConnector.con;
    }*/

    /**
     * Method to get data from a database
     * @return A ResultSet containing data from a database
     */
    public static ResultSet getRows()
    {
        try
        {
            getDBConnection();
            Statement s = con.createStatement();
            String select = "SELECT booking.booking_id, booking.attendees, customer.customer_id, dates.date, dates.dates_id, starttimes.starttime, starttimes.starttimes_id, endtimes.endtime, endtimes.endtimes_id, rooms.room, rooms.room_id FROM booking, customer, dates, starttimes, endtimes, rooms "
                    + "WHERE booking.customer_id = customer.customer_id "
                    + "AND booking.dates_id = dates.dates_id "
                    + "AND booking.starttimes_id = starttimes.starttimes_id "
                    + "AND booking.endtimes_id = endtimes.endtimes_id "
                    + "AND booking.room_id = rooms.room_id";

            rows = s.executeQuery(select);
        }
        catch (SQLException ex)
        {
            System.out.println("An SQL error has occured: " + ex.getMessage());
        }
        return rows;
    }

    /**
     * Method to get dates from a database
     * @return A ResultSet containing dates from a database
     */
    public static ResultSet getDateRows()
    {
        try
        {
            getDBConnection();
            Statement s = con.createStatement();
            String select = "SELECT  dates.date, dates.dates_id FROM dates ";

            rows = s.executeQuery(select);

        }
        catch (SQLException ex)
        {
            System.out.println("An SQL error has occured: " + ex.getMessage());
        }
        return rows;
    }

    /**
     * Method to get customers from a database
     * @return A ResultSet containing customers from a database
     */
    public static ResultSet getCustomerRows()
    {
        try
        {
            getDBConnection();
            Statement s = con.createStatement();
            String select = "SELECT  customer.customer_id, customer.customer_name FROM customer ";

            rows = s.executeQuery(select);

        }
        catch (SQLException ex)
        {
            System.out.println("An SQL error has occured: " + ex.getMessage());
        }
        return rows;
    }

    /**
     * Method to check if a Customer object exists
     * @param aCustomer A Customer object
     * @return True if aCustomer is contained in the
     * customerMap, false otherwise
     */
    public boolean customerExists(Customer aCustomer)
    {
        boolean exists = false;
        for (Customer eachCustomer : customerMap.values())
        {
            if (eachCustomer.equals(aCustomer))
            {
                exists = true;
            }
        }
        return exists;
    }

    /**
     * Method to check if a CYCDate object exists
     * @param aDate A CYCDate object
     * @return True if aDate is contained in the
     * dateMap, false otherwise 
     */
    public boolean dateExists(CYCDate aDate)
    {
        System.out.println("Check if date exists");
        boolean dateExists = false;

        for (CYCDate eachDate : dateMap.values())
        {
            if (eachDate.equals(aDate))
            {
                dateExists = true;
            }
        }
        return dateExists;
    }

    /**
     * Method to check if a Booking object exists
     * @param aBooking A Booking object
     * @return True if aBooking is contained in the
     * bookingMap, false otherwise 
     */
    public boolean bookingExists(Booking aBooking)
    {
        boolean exists = false;
        for (Booking eachBooking : bookingMap.values())
        {
            if (eachBooking.equals(aBooking))
            {
                exists = true;
            }
        }
        return exists;
    }
   
    /**
     * Method to insert new bookings to a database
     * @param newBooking A Booking object to be inserted into a database
     */
    public void newInsertData(Booking newBooking)
    {
        Booking aBooking = newBooking;
        insertCustomerData(aBooking);
        insertDateData(aBooking);
        getDBConnection();

        try
        {
            PreparedStatement insertData = null;

            if (bookingExists(aBooking))
            {
                System.out.println("Booking already exists");
            }
            else
            {
                String statement;
                statement = "INSERT INTO booking "
                        + "VALUES ("
                        + aBooking.getID() + ", "
                        + aBooking.getCustomer().getCustomerID() + ", "
                        + aBooking.getRoom().getRoomID() + ", "
                        + aBooking.getStartTime().getStartTimeID() + ", "
                        + aBooking.getEndTime().getEndTimeID() + ", "
                        + aBooking.getDate().getDateID() + ", "
                        + aBooking.getAttendees() + ");";

                System.out.println(statement);
                con.setAutoCommit(false);
                insertData = con.prepareStatement(statement);
                insertData.executeUpdate();
                con.commit();
                bookingMap.put(newBooking.getID(), newBooking);
            }
        }
        catch (SQLException ex)
        {
            System.out.println(
                    "An SQL error has occured here: " + ex.getMessage());
        }

        DatabaseConnector.closeConnection();
    }

    /**
     * Method to insert new customers to a database
     * @param newBooking A Booking object containing customer data
     */
    public void insertCustomerData(Booking newBooking)
    {
        getDBConnection();
        Booking aBooking = newBooking;

        try
        {
            PreparedStatement insertData = null;
            Customer aCustomer = aBooking.getCustomer();
            System.out.println("Check if customer exists");

            if (!(customerExists(aCustomer)))
            {
                customerMap.put(aCustomer.getCustomerID(), aCustomer);
                String statement;
                statement = "INSERT INTO customer "
                        + "VALUES (" + aCustomer.getCustomerID() + "); ";
                System.out.println(statement);
                con.setAutoCommit(false);
                insertData = con.prepareStatement(statement);
                insertData.executeUpdate();
                con.commit();
            }
            else
            {
                System.out.println("Customer already exists");
            }

        }
        catch (SQLException ex)
        {
            System.out.println(
                    "An SQL error has occured here: " + ex.getMessage());
        }
        DatabaseConnector.closeConnection();
    }

    /**
     * Method to insert new dates to a database
     * @param newBooking A Booking object containing CYCDate data
     */
    public void insertDateData(Booking newBooking)
    {
        getDBConnection();
        Booking aBooking = newBooking;

        try
        {
            PreparedStatement insertData = null;
            CYCDate aCYCDate = aBooking.getDate();
            if (!dateExists(aCYCDate))
            {
                PreparedStatement pstmt = con.prepareStatement(
                        "INSERT INTO dates (dates_id, date) VALUES (?,  ? );");
                int id = aCYCDate.getDateID();

                pstmt.setInt(1, id);
                pstmt.setDate(2, aCYCDate);

                System.out.println(pstmt);
                con.setAutoCommit(false);
                insertData = pstmt;
                insertData.executeUpdate();
                con.commit();

                dateMap.put(id, aCYCDate);

            }
            else
            {
                for (CYCDate eachDate : dateMap.values())
                {
                    if (eachDate.equals(aBooking.getDate()))
                    {
                        aBooking.changeDate(eachDate);
                    }
                }
                aCYCDate = dateMap.get(aCYCDate.getDateID());
                System.out.println("Date already exists");
            }

        }
        catch (SQLException ex)
        {
            System.out.println(
                    "An SQL error has occured here: " + ex.getMessage());
        }
        closeConnection();
    }

    /**
     * Method used to change data in database
     * @param aBooking A Booking object
     */
    public void changeData(Booking aBooking)
    {
        Booking booking;
        booking = aBooking;
        int bookingID = booking.getID();
        int customerID = booking.getCustomer().getCustomerID();
        int roomID = booking.getRoom().getRoomID();
        int starttimeID = booking.getStartTime().getStartTimeID();
        int endtimeID = booking.getEndTime().getEndTimeID();
        int dateID = booking.getDate().getDateID();
        int attendees = booking.getAttendees();

        try
        {
            PreparedStatement changeData = null;
            String statement;

            statement = "UPDATE booking SET customer_id = " + customerID
                    + ", room_id = " + roomID
                    + ", starttimes_id = " + starttimeID
                    + ", endtimes_id = " + endtimeID
                    + ", dates_id = " + dateID
                    + ", attendees = " + attendees
                    + " WHERE booking_id = " + bookingID + ";";

            System.out.println(statement);

            con.setAutoCommit(false);
            changeData = con.prepareStatement(statement);
            changeData.executeUpdate();
            con.commit();

            System.out.println(statement);
            System.out.println("Booking " + bookingID + " changed");

        }
        catch (SQLException ex)
        {
            System.out.println("An SQL error has occured: " + ex.getMessage());
        }
    }

    /**
     * Method to delete bookings from database. NOTE: this method also 
     * checks customers and dates, if a customer or date is not linked to
     * any bookings after a booking is deleted by this method then they
     * too are deleted. This is done at this time to keep the database
     * tidy an ensures redundant data is not held.
     * @param aBookingID An int representing a unique booking id
     */
    public void deleteData(int aBookingID)
    {
        getDBConnection();

        try
        {
            PreparedStatement insertData = null;
            Customer customer = bookingMap.get(aBookingID).getCustomer();
            CYCDate date = bookingMap.get(aBookingID).getDate();

            if (bookingMap.containsKey(aBookingID))
            {
                String statement;

                statement = "DELETE FROM booking WHERE booking_id = "
                        + aBookingID + ";";

                con.setAutoCommit(false);
                insertData = con.prepareStatement(statement);
                insertData.executeUpdate();
                con.commit();

                System.out.println(statement);
                System.out.println("Booking " + aBookingID + " deleted");

                System.out.println("Date bookings 0 is " + date.getBookings());

                customer.getBookings().remove(aBookingID);
                System.out.println(
                        "Customer bookings 0 is " + customer.getBookings());

                if (customer.getBookings().isEmpty())
                {
                    String customerStatement;
                    customerStatement = "DELETE FROM customer WHERE customer_id = "
                            + customer.getCustomerID() + ";";
                    con.setAutoCommit(false);
                    insertData = con.prepareStatement(customerStatement);
                    insertData.executeUpdate();
                    con.commit();

                    System.out.println(customerStatement);
                    System.out.println("Customer " + customer + " deleted");
                }

                date.getBookings().remove(aBookingID);

                if (date.getBookings().isEmpty())
                {
                    String dateStatement;
                    dateStatement = "DELETE FROM dates WHERE dates_id = "
                            + date.getDateID() + ";";
                    con.setAutoCommit(false);
                    insertData = con.prepareStatement(dateStatement);
                    insertData.executeUpdate();
                    con.commit();

                    System.out.println(dateStatement);
                    System.out.println("Date " + date + " deleted");
                }
            }
            else
            {
                System.out.println("Booking does not exist");
            }

        }
        catch (SQLException ex)
        {
            System.out.println("An SQL error has occured: " + ex.getMessage());
        }
        DatabaseConnector.closeConnection();
    }

    /**
     * Method used to close connection to a database
     * @return A Connection object
     */
    public static Connection closeConnection()
    {
        try
        {
            con.close();
        }
        catch (SQLException ex)
        {
            System.out.println("An SQL error has occured: " + ex.getMessage());
        }
        System.out.println("Disconnected from database");
        return con;
    }

    public void insertOnlyCustomerData(String aName)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
