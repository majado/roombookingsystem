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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;


/**
 *
 * @author Max Dowkes
 */
public class Scheduler
{

    private Map<Integer, Booking> bookingMap;
    private static Map<Integer, Room> ROOM_MAP = new HashMap<Integer, Room>();
    private InitialisedObjects ido = new InitialisedObjects();

    /**
     * Constructor gets Map of Rooms from InitialisedObjects class
     * and Map of Bookings from InitialisedObjects
     */
    public Scheduler()
    {
        ROOM_MAP = InitialiseObjects.getRoomMap();
        bookingMap = ido.getBookingMap();
    }

    /**
     * Method to optimise bookings according to the relationship between 
     * room capacity and the number of attendees
     * @param aMap A Map of Bookings to be optimised by capacities
     * @return A Map of Bookings which have been optimised by capacity
     */
    public Map optimiseCapacity(Map aMap)
    {

        //Create tempoary map to hold bookings after room change
        Map<Integer, Booking> ocMap = aMap;
        Map<Integer, Booking> optimisedMap = new HashMap<Integer, Booking>();
        Map<Integer, Integer> tempMap = new TreeMap<Integer, Integer>();

        for (Booking eachBooking : ocMap.values())
        {
            tempMap.put(eachBooking.getID(), eachBooking.getAttendees());
        }

        Map<Integer, Room> tempRoomMap = new HashMap<Integer, Room>();
        tempRoomMap.putAll(ROOM_MAP);
        
        // Rooms with id 6 and 7 i.e. Pitch 1 and Pitch 2 are removed from
        // the temporary Map to prevent a booking for a room being set for
        // a pitch as currently the Room class does not destingush between
        // the two types
        tempRoomMap.remove(6);
        tempRoomMap.remove(7);
        for (Booking eachBooking : ocMap.values())
        {
            if (!tempRoomMap.containsKey(eachBooking.getRoom().getRoomID()))
            {
                eachBooking.setRoomNone();
            }

            Room currentRoom = eachBooking.getRoom();

            for (Room eachRoom : ROOM_MAP.values())
            {

                System.out.println("Eachroom is: " + eachRoom);
                System.out.println("Eachbooking is: " + eachBooking);
                System.out.println(tempRoomMap.containsValue(eachRoom));
                if ((tempRoomMap.containsValue(eachRoom)) && eachRoom.getCapacity() >= eachBooking.getAttendees())
                {
                    System.out.println("Not too many people");
                    if (eachRoom.getCapacity() >= eachBooking.getAttendees()
                            && eachRoom.getCapacity() < currentRoom.getCapacity())
                    {
                        System.out.println(
                                "Current room Capacity is: " + currentRoom.getCapacity());
                        System.out.println(
                                "Each room Capacity is: " + eachRoom.getCapacity());
                        System.out.println(
                                "Attendees are: " + eachBooking.getAttendees());
                        currentRoom = eachRoom;
                    }
                    System.out.println("Temp room map is: " + tempRoomMap);
                    eachBooking.changeRoom(currentRoom);
                    tempRoomMap.remove(currentRoom.getRoomID());
                    optimisedMap.put(eachBooking.getID(), eachBooking);
                }
                else if (eachRoom.getCapacity() < eachBooking.getAttendees())
                {
                    System.out.println("Too many people!");
                }
            }

        }
        return optimisedMap;
    }
    
    /**
     * Method to convert time represented as a String in StartTime
     * or EndTime objects
     * @param aString Representing the time
     * @return An int representing the time
     */
    public int convertTimeToInt(String aString)
    {
        String time = aString;
        // As times in StratTime and EndTime objects are Strings in 
        // the 24 hour format e.g. "13:00" only the first two characters
        // are taken. So the resulting int from the String "13:00" will
        // be 13
        time = time.substring(0, 2);
        int timeInt = Integer.parseInt(time);
        return timeInt;
    }

    /**
     * Method to accept a Booking object and find all bookings
     * which occur on the same date which are returned in a Map
     * @param aBooking A Booking object
     * @return A Map containing all bookings which occur on the
     * same date
     */
    public Map getSameDateBookings(Booking aBooking)
    {
        Map<Integer, Booking> sameDateBookingMap = new HashMap<Integer, Booking>();
        for (Booking eachBooking : InitialisedObjects.getBookingMap().values())
        {
            if (eachBooking.getDate().equals(aBooking.getDate()))
            {
                sameDateBookingMap.put(eachBooking.getID(), eachBooking);
            }
        }
        return sameDateBookingMap;
    }

    /**
     * Method to accept a Booking object and find all bookings
     * which occur at the same time and on the same date which
     * are returned in a Map. This method uses the getSameDateBookings()
     * method to first get bookings from the same date. These bookings are
     * then processed to find bookings which occur at the same time.
     * @param aBooking A Booking object
     * @return A Map containing all bookings which occur at the
     * same time on the same date
     */
    public Map multipleRoomsBookedSimultaneously(Booking aBooking)
    {
        Map<Integer, Booking> sameDateBookingMap;
        sameDateBookingMap = this.getSameDateBookings(aBooking);
        Map<Integer, Booking> multipleSimultaneousBookingMap = new HashMap<Integer, Booking>();

        for (Booking eachBooking : sameDateBookingMap.values())
        {
            if (this.compareTimes(eachBooking.getStartTime().getStartTime(),
                    eachBooking.getEndTime().getEndTime(),
                    aBooking.getStartTime().getStartTime(),
                    aBooking.getEndTime().getEndTime()))
            {
                multipleSimultaneousBookingMap.put(eachBooking.getID(),
                        eachBooking);
            }
        }
        return multipleSimultaneousBookingMap;
    }

    /**
     * Method used to compare start times and end times. Used by the
     * multipleRoomsBookedSimultaneously() method to determine which
     * bookings occur at the same time
     * @param starttime1 A String representing a start time
     * @param endtime1 A String representing an end time
     * @param starttime2 A String representing a second start time
     * @param endtime2 A string representing a second end time
     * @return True if the period between starttime1 and endtime1
     * overlaps with the period between starttime2 and endtime2
     */
    public boolean compareTimes(String starttime1, String endtime1,
            String starttime2, String endtime2)
    {
        Boolean overlap = false;

        int start1 = this.convertTimeToInt(starttime1);
        int end1 = this.convertTimeToInt(endtime1);
        int start2 = this.convertTimeToInt(starttime2);
        int end2 = this.convertTimeToInt(endtime2);

        if (start2 < end1 && end2 > start1)
        {
            overlap = true;
        }
        System.out.println(overlap);
        return overlap;
    }

    /**
     * Method to produce a Map of CYCDates used to facilitate 
     * repeat bookings. Creates a new date at weekly intervals. The
     * number of weeks defined by the aRepeat parameter 
     * e.g. if dateString is "25/03/1983" and aRepeat is 3 then 3 dates
     * will be created for 25/03/1983, 01/04/1983 and 08/04/1983.
     * Due to rounding this method will not be infinitely accurate 
     * however it should be accurate for up to 800,000 years
     * 
     * @param dateString A String representing the initial date from which
     * subsequent dates are created 
     * @param aRepeat An int determining the number of weeks to be repeated
     * @return A Map of CYCDates
     */
    public Map repeatBookings(String dateString, int aRepeat)
    {
        String day = dateString;
        int repeat = aRepeat;
        java.util.Date weekDate = null;
        try
        {
            weekDate = new SimpleDateFormat("dd/MM/yy").parse(day);
        }
        catch (ParseException ex)
        {
            System.out.println("A parse exception has occured: " + ex);
        }

        java.util.Date date = new java.util.Date(
                (weekDate.getTime() + (1000 * 60 * 60 * 12)));
        System.out.println("Startdate is: " + date);
        Map<Integer, CYCDate> dateMap = new TreeMap<Integer, CYCDate>();
        CYCDate cycDate = null;
        // if checks if date exists, AND if it does assigns existing date to date variable
        if (dateExists(date))
        {
            cycDate = setExistingDate(date);
            dateMap.put(cycDate.getDateID(), cycDate);
        }
        else
        {
            cycDate = new CYCDate(date.getTime());
            dateMap.put(cycDate.getDateID(), cycDate);
        }

        System.out.println("DATEMAP IS: " + dateMap);

        long week = 604800000;
        long startweek = (weekDate.getTime() + (1000 * 60 * 60 * 12));

        /* 
         Adds 12 hours in milliseconds to set the initial 
         long time value to be 12pm to avoid issues with GMT/BST
         changes
         */
        long nextweek = startweek + week;

        for (int index = 1; index < repeat; index++)
        {
            java.util.Date newDate = new java.util.Date(nextweek);
            nextweek = nextweek + week;
            // if checks if newDate exists, AND if it does assigns existing date to newDate variable
            if (dateExists(newDate))
            {
                cycDate = setExistingDate(newDate);
                dateMap.put(cycDate.getDateID(), cycDate);
            }
            else
            {
                cycDate = new CYCDate(newDate.getTime());
                dateMap.put(cycDate.getDateID(), cycDate);
            }
            System.out.println("DATEMAP 2 IS: " + dateMap);
        }
        return dateMap;
    }

    /**
     * Tests if the Date aDate exists in InitialisedObjects' DateMap
     * @param aDate The Date object passed to see if it already exists
     * @return True if the Date aDate exists in InitialisedObjects' DateMap
     */
    public boolean dateExists(java.util.Date aDate)
    {
        boolean exists = false;

        for (CYCDate eachDate : InitialisedObjects.getDateMap().values())
        {
            CYCDate tempDate = new CYCDate(aDate.getTime());

            if (eachDate.equals(tempDate))
            {
                exists = true;
            }
        }
        return exists;
    }

    /**
     * Method used to set an existing date in order to avoid
     * duplicate dates being created
     * @param aDate A Date object
     * @return A CYCDate object
     */
    public CYCDate setExistingDate(java.util.Date aDate)
    {
        CYCDate tempDate = new CYCDate(aDate.getTime());

        for (CYCDate eachDate : InitialisedObjects.getDateMap().values())
        {
            if (eachDate.equals(tempDate))
            {
                tempDate = eachDate;
            }
        }
        return tempDate;
    }
}
