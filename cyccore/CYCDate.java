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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Max Dowkes
 */
public class CYCDate extends java.sql.Date
{

    private Map<Integer, Booking> bookings = new HashMap<Integer, Booking>();
    private int dateID = 1;
    static Date todaysDate;
    private static List dateIDs = new ArrayList();

    /**
     * CYCDate constructor used to create a date with an id
     * when the date and id are known ie when constructing
     * an object from persistent data
     * @param date a long used to represent the date
     * @param dateID a unique id used to identify the CYCDate object
     */
    public CYCDate(long date, int dateID)
    {
        super(date);
        this.dateID = dateID;
        dateIDs.add(dateID);
    }

    /**
     * Constructor to create CYCDate object with a known date.
     * The object is given a unique id. Used when creating a new
     * CYCDate object
     * @param date a long used to represent the date
     */
    public CYCDate(long date)
    {
        super(date);
        while (dateIDs.contains(dateID))
        {
            dateID++;
        }
        dateIDs.add(dateID);
        System.out.println("CYCDate has incremented and dateID is: " + dateID);
    }

    /**
     * Getter for the CYCDate id
     * @return an int representing this date's id
     */
    public int getDateID()
    {
        return dateID;
    }

    /**
     * Setter for the CYCDate id
     * @param aDateID an int used to represent this date's id
     */
    public void setDateID(int aDateID)
    {
        while (dateIDs.contains(aDateID))
        {
            this.dateID = dateID++;
        }
    }

    /**
     * Method to get a map of bookings to which this date is linked
     * @return a map of bookings
     */
    public Map getBookings()
    {
        return bookings;
    }

    /**
     * Method used to link this date to a booking
     * @param aID an int used to represent the id of a booking
     * @param aBooking a Booking object
     */
    public void addBooking(int aID, Booking aBooking)
    {
        bookings.put(aID, aBooking);
        System.out.println("CYCDate added to booking map via CYCDate class");
    }

    /**
     * Helper method to get today's date
     * @return a String representing today's date in the
     * dd/MM/yy format
     */
    public static String getTodaysDate()
    {
        todaysDate = new Date();
        String todaysStringDate;
        todaysStringDate = cycConvertDate(todaysDate, "dd/MM/yy");
        return todaysStringDate;
    }

    /**
     * Method to convert a string representing a date in dd-MM-yyyy
     * format to a string representing a date in the format yyyy-MM-dd
     * Possibly will be unused and may be removed at a later date
     * @param sDate a String representing a date, must be in 
     * the format dd-MM-yyyy
     */
    static void convertDateToSQL(String sDate)
    {

        String start_dt = sDate;
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        java.util.Date date = null;
        try
        {
            date = (java.util.Date) formatter.parse(start_dt);
        }
        catch (ParseException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd");
        String finalString = newFormat.format(date);
        System.out.println(finalString);
    }

    /**
     * Method used to convert a date to a string using the format
     * submitted as the dateFormat parameter. Possibly will be unused and may
     * be removed at a later date
     * @param aDate a Date object to be converted
     * @param dateFormat a String to represent the format required
     * @return a String representing a date
     */
    public static String cycConvertDate(Date aDate, String dateFormat)
    {
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat();
        DATE_FORMAT.applyPattern(dateFormat);
        String date = DATE_FORMAT.format(aDate.getTime());
        return date;
    }

    @Override
    /**
     * Overridden hashCode() method
     */
    public int hashCode()
    {
        int hash;
        String s;
        s = String.valueOf(this.getTime());
        hash = Integer.parseInt(s);
        return hash;
    }

    @Override
    /**
     * Overridden equals() method. CYCDates are equal if
     * their getTime() methods return the same result or
     * if their toString() method returns the same result
     */
    public boolean equals(Object obj)
    {

        final CYCDate other = (CYCDate) obj;
        boolean equal = false;

        if (this.getTime() == other.getTime())
        {
            equal = true;
        }
        if (this.toString().equals(other.toString()))
        {
            equal = true;
        }
        return equal;

    }

    @Override
    /**
     * Overridden toString() method. This method outputs a
     * String representing this date's date in the dd/MM/yyyy format
     */
    public String toString()
    {
        String toString;
        SimpleDateFormat newFormat = new SimpleDateFormat("dd/MM/yyyy");
        toString = newFormat.format(this);
        return toString;
    }
}
