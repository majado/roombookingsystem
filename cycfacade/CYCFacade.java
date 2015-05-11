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
package cycfacade;

import cyccore.*;
import cyccore.InitialisedObjects;
import cycpersistence.DatabaseConnector;
import java.util.Map;

/**
 *
 * @author Max Dowkes
 */
public class CYCFacade
{

    DatabaseConnector dbc;
    InitialisedObjects io;

    public CYCFacade()
    {
        dbc = new DatabaseConnector();
        io = new InitialisedObjects();
    }

    public void cancelBooking(int aBookingID)
    {
        dbc.deleteData(aBookingID);
    }

    public Map getBookingMap()
    {
        return io.getBookingMap();
    }

    public Map getCustomerMap()
    {
        return io.getCustomerMap();
    }

    public Map getRoomMap()
    {
        return io.getROOM_MAP();
    }

    public Map getStartTimeMap()
    {
        return io.getSTARTTIME_MAP();
    }

    public Map getEndTimeMap()
    {
        return io.getENDTIME_MAP();
    }

    public void newInsertData(Booking booking)
    {
        dbc.newInsertData(booking);
        io.updateDateMap();
    }

    public Map getDateMap()
    {
        return io.getDateMap();
    }

    public void addCustomer(String aName)
    {
        dbc.insertOnlyCustomerData(aName);
    }
}
