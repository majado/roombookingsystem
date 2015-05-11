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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Max Dowkes
 */
public class Booking
{

    private int id = 1;
    private static List bookingIDs = new ArrayList();
    private Customer customer;
    private StartTime startTime;
    private EndTime endTime;
    private Room room;
    private CYCDate date;
    private int numberOfAttendees;

    /**
     * Constructor used when constructing Booking objects and id
     * is already known i.e. when creating objects from persistent data
     * @param id an int used to uniquely identify the booking
     * @param aCustomer a Customer object
     * @param aStartTime a StartTime object
     * @param aEndTime an EndTime object
     * @param aRoom a Room object
     * @param aDate a CYCDate object
     * @param aNumberOfAttendees an int used to represent the number of 
     * attendees associated with this booking
     */
    public Booking(int id, Customer aCustomer, StartTime aStartTime,
            EndTime aEndTime, Room aRoom, CYCDate aDate, int aNumberOfAttendees)
    {

        this.customer = aCustomer;
        this.startTime = aStartTime;
        this.endTime = aEndTime;
        this.room = aRoom;
        this.date = aDate;
        this.id = id;
        bookingIDs.add(id);
        this.numberOfAttendees = aNumberOfAttendees;
        updateMaps();
    }

    /**
     * Constructor used when constructing Booking objects when the id
     * is not known i.e. when creating a booking to insert into a database
     * @param aCustomer a Customer object
     * @param aStartTime a StartTime object
     * @param aEndTime an EndTime object
     * @param aRoom a Room object
     * @param aDate a CYCDate object
     * @param aNumberOfAttendees an int used to represent the number of 
     * attendees associated with this booking
     */
    public Booking(Customer aCustomer, StartTime aStartTime, EndTime aEndTime,
            Room aRoom, CYCDate aDate, int aNumberOfAttendees)
    {

        this.customer = aCustomer;
        this.startTime = aStartTime;
        this.endTime = aEndTime;
        this.room = aRoom;
        this.date = aDate;
        this.numberOfAttendees = aNumberOfAttendees;
        while (bookingIDs.contains(id))
        {
            id++;
        }
        bookingIDs.add(id);
        updateMaps();

    }

    /**
     * Method to update maps held in Customer and CYCDate objects
     * to add the relationship from them to this Booking object
     */
    public final void updateMaps()
    {
        this.getCustomer().addBooking(id, this);
        System.out.println("Customer added to booking map via Booking class");
        this.getDate().addBooking(id, this);
        System.out.println("CYCDate added to booking map via Booking class");
        System.out.println(
                "Customers booking map is: " + this.getCustomer().getBookings());
        System.out.println(
                "CYCDate booking map is: " + this.getDate().getBookings());
    }

    /**
     * Setter for the booking id
     * @param id the booking id
     */
    public void setID(int id)
    {
        this.id = id;
    }

    /**
     * Getter for the booking id
     * @return an int representing the booking id
     */
    public int getID()
    {
        return id;
    }

    /**
     * Getter for the EndTime
     * @return the EndTime object associated with this booking
     */
    public EndTime getEndTime()
    {
        return endTime;
    }

    /**
     * Getter for the StartTime
     * @return the StartTime object associated with this booking
     */
    public StartTime getStartTime()
    {
        return startTime;
    }

    /**
     * Getter for the Customer
     * @return the customer object associated with this booking
     */
    public Customer getCustomer()
    {
        return customer;
    }

    /**
     * Getter for the Room
     * @return the Room object associated with this booking
     */
    public Room getRoom()
    {
        return room;
    }

    /**
     * Getter for the CYCDate
     * @return CYCDate object associated with this booking
     */
    public CYCDate getDate()
    {
        return date;
    }

    /**
     * Method to change the StartTime associated with this booking
     * @param aStartTime a StartTime object
     */
    public void changeStartTime(StartTime aStartTime)
    {
        this.startTime = aStartTime;
    }

    /**
     * Method to change the EndTime associated with this booking
     * @param aEndTime an EndTime object
     */
    public void changeEndTime(EndTime aEndTime)
    {

        this.endTime = aEndTime;
    }

    /**
     * Method to change the Room associated with this booking
     * @param aRoom a Room object
     */
    public void changeRoom(Room aRoom)
    {
        this.room = aRoom;
    }

    /**
     * Method to set this booking's room to "None"
     * Used during scheduling optimisation
     */
    public void setRoomNone()
    {
        this.room = new Room(8, "None", 10);
    }

    /**
     * Method to change the CYCDate associated with this booking
     * @param aCYCDate a CYCDate object
     */
    public void changeDate(CYCDate aCYCDate)
    {

        this.date = aCYCDate;
    }

    /**
     * Getter for the number of attendees
     * @return an int representing the number of attendees
     */
    public int getAttendees()
    {
        return numberOfAttendees;
    }

    /**
     * Setter for the number of attendees
     * @param numberOfAttendees an int representing the number of attendees
     */
    public void setAttendees(int numberOfAttendees)
    {
        this.numberOfAttendees = numberOfAttendees;
    }

    @Override
    /**
     * Overridden toString() method. Returns a String containing
     * the booking id, customer, room, timeslot, date and 
     * number of attendees for this booking
     */
    public String toString()
    {
        String toString = "Booking id is: "
                + this.getID()
                + " Customer is: "
                + this.getCustomer().toString()
                + " Room is: "
                + this.getRoom().toString()
                + " Timeslot is: "
                + this.startTime.toString() + " " + this.endTime.toString()
                + " Date is : "
                + this.getDate().toString()
                + " Attendees are: "
                + this.getAttendees();

        return toString;
    }

    @Override
    /**
     * Overridden hashcode method
     */
    public int hashCode()
    {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.customer);
        hash = 79 * hash + Objects.hashCode(this.startTime);
        hash = 79 * hash + Objects.hashCode(this.endTime);
        hash = 79 * hash + Objects.hashCode(this.room);
        hash = 79 * hash + Objects.hashCode(this.date);
        return hash;
    }

    @Override
    /**
     * Overridden equals() method. Compares the customer,
     * starttime, endtime and date of this booking with the 
     * booking supplied as an argument. Returns true if these
     * elements are all equal.
     */
    public boolean equals(Object obj)
    {
        boolean equal = false;

        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final Booking other = (Booking) obj;

        if (this.customer.equals(other.getCustomer())
                && this.room.equals(other.getRoom())
                && this.startTime.equals(other.getStartTime())
                && this.endTime.equals(other.getEndTime())
                && this.date.equals(other.getDate()))
        {
            equal = true;
        }
        return equal;
    }

}
