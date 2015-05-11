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

import java.util.Objects;

/**
 *
 * @author Max Dowkes
 */
public class Room
{

    private String name;
    private int id;
    private int capacity;

    public Room(int aRoomID, String aName, int aCapacity)
    {
        this.name = aName;
        this.id = aRoomID;
        this.capacity = aCapacity;
    }

    public Room()
    {

    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getRoomID()
    {
        return id;
    }

    public void setID(int id)
    {
        this.id = id;
    }

    public int getCapacity()
    {
        return capacity;
    }

    public void setCapacity(int capacity)
    {
        this.capacity = capacity;
    }

    @Override
    public String toString()
    {
        return this.getName();
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final Room other = (Room) obj;
        if (!Objects.equals(this.name, other.name))
        {
            return false;
        }
        return true;
    }
}
