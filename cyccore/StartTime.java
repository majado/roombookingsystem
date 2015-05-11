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
public class StartTime
{

    private String startTime;
    private int id;

    /**
     * 
     * @param aID
     * @param aStartTime 
     */
    public StartTime(int aID, String aStartTime)
    {
        this.startTime = aStartTime;
        this.id = aID;
    }

    /**
     * 
     */
    public StartTime()
    {

    }

    /**
     * 
     * @return 
     */
    public String getStartTime()
    {
        return startTime;
    }

    /**
     * 
     * @param startTime 
     */
    public void setStartTime(String startTime)
    {
        this.startTime = startTime;
    }

    /**
     * 
     * @return 
     */
    public int getStartTimeID()
    {
        return id;
    }

    @Override
    /**
     * 
     */
    public String toString()
    {
        String time = (this.getStartTime());
        return time;
    }

    @Override
    /**
     * 
     */
    public int hashCode()
    {
        int hash = 5;
        hash = 13 * hash + Objects.hashCode(this.startTime);
        hash = 13 * hash + this.id;
        return hash;
    }

    @Override
    /**
     * 
     */
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
        final StartTime other = (StartTime) obj;
        if (!Objects.equals(this.startTime, other.startTime))
        {
            return false;
        }
        if (this.id != other.id)
        {
            return false;
        }
        return true;
    }
}
