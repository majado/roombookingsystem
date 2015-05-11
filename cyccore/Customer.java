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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Max Dowkes
 */
public class Customer
{

    private String name;
    private String telephoneNumber;
    private Map<Integer, Booking> bookings = new HashMap<Integer, Booking>();
    private int customerID = 1;
    private static List customerIDs = new ArrayList();

    public Customer(int customerID, String aName)
    {
        this.customerID = customerID;
        Customer.customerIDs.add(customerID);
        this.name = aName;
    }

    public Customer(int customerID)
    {
        this.customerID = customerID;
        Customer.customerIDs.add(customerID);
    }

    public Customer(String aName)
    {
        this.name = aName;
        while (customerIDs.contains(customerID))
        {
            customerID++;
        }
        customerIDs.add(customerID);
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getTelephoneNumber()
    {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber)
    {
        this.telephoneNumber = telephoneNumber;
    }

    public Map getBookings()
    {
        return bookings;
    }

    public void addBooking(int aID, Booking aBooking)
    {
        bookings.put(aID, aBooking);
        System.out.println("Customer added to booking map via Customer class");
    }

    public int getCustomerID()
    {
        return customerID;
    }

    public void setCustomerID(int customerID)
    {
        this.customerID = customerID;

    }

    public static List getCustomerList()
    {
        return customerIDs;
    }

    @Override
    public String toString()
    {
        return (this.getName());
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 41 * hash + this.customerID;
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        boolean equal = false;
        final Customer other = (Customer) obj;
        if (this.customerID == other.customerID)
        {
            equal = true;
        }
        return equal;
    }

}
