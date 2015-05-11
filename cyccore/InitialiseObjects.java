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

import java.util.*;

/**
 *
 * @author Max Dowkes
 */
public class InitialiseObjects
{

    // The variables below are initialised ready to be added
    // to maps by the SetUpObjects() method.
    private final Room ROOM1 = new Room(1, "Room 1", 4);
    private final Room ROOM2 = new Room(2, "Room 2", 5);
    private final Room ROOM3 = new Room(3, "Room 3", 7);
    private final Room ROOM4 = new Room(4, "Room 4", 7);
    private final Room ROOM5 = new Room(5, "Room 5", 9);
    private final Room PITCH1 = new Room(6, "Pitch 1", 5);
    private final Room PITCH2 = new Room(7, "Pitch 2", 7);

    private final StartTime STARTTIME1 = new StartTime(1, "09:00");
    private final StartTime STARTTIME2 = new StartTime(2, "10:00");
    private final StartTime STARTTIME3 = new StartTime(3, "11:00");
    private final StartTime STARTTIME4 = new StartTime(4, "12:00");
    private final StartTime STARTTIME5 = new StartTime(5, "13:00");
    private final StartTime STARTTIME6 = new StartTime(6, "14:00");
    private final StartTime STARTTIME7 = new StartTime(7, "15:00");
    private final StartTime STARTTIME8 = new StartTime(8, "16:00");

    private final EndTime ENDTIME1 = new EndTime(1, "10:00");
    private final EndTime ENDTIME2 = new EndTime(2, "11:00");
    private final EndTime ENDTIME3 = new EndTime(3, "12:00");
    private final EndTime ENDTIME4 = new EndTime(4, "13:00");
    private final EndTime ENDTIME5 = new EndTime(5, "14:00");
    private final EndTime ENDTIME6 = new EndTime(6, "15:00");
    private final EndTime ENDTIME7 = new EndTime(7, "16:00");
    private final EndTime ENDTIME8 = new EndTime(8, "17:00");

    private final static Map<Integer, Room> ROOM_MAP = new HashMap<Integer, Room>();
    private final static Map<Integer, StartTime> STARTTIME_MAP = new HashMap<Integer, StartTime>();
    private final static Map<Integer, EndTime> ENDTIME_MAP = new HashMap<Integer, EndTime>();

    /**
     * Method initialises maps to store the rooms, start times and end times.
     */
    public void setUpObjects()
    {
        ROOM_MAP.put(ROOM1.getRoomID(), ROOM1);
        ROOM_MAP.put(ROOM2.getRoomID(), ROOM2);
        ROOM_MAP.put(ROOM3.getRoomID(), ROOM3);
        ROOM_MAP.put(ROOM4.getRoomID(), ROOM4);
        ROOM_MAP.put(ROOM5.getRoomID(), ROOM5);
        ROOM_MAP.put(PITCH1.getRoomID(), PITCH1);
        ROOM_MAP.put(PITCH2.getRoomID(), PITCH2);

        STARTTIME_MAP.put(STARTTIME1.getStartTimeID(), STARTTIME1);
        STARTTIME_MAP.put(STARTTIME2.getStartTimeID(), STARTTIME2);
        STARTTIME_MAP.put(STARTTIME3.getStartTimeID(), STARTTIME3);
        STARTTIME_MAP.put(STARTTIME4.getStartTimeID(), STARTTIME4);
        STARTTIME_MAP.put(STARTTIME5.getStartTimeID(), STARTTIME5);
        STARTTIME_MAP.put(STARTTIME6.getStartTimeID(), STARTTIME6);
        STARTTIME_MAP.put(STARTTIME7.getStartTimeID(), STARTTIME7);
        STARTTIME_MAP.put(STARTTIME8.getStartTimeID(), STARTTIME8);

        ENDTIME_MAP.put(ENDTIME1.getEndTimeID(), ENDTIME1);
        ENDTIME_MAP.put(ENDTIME2.getEndTimeID(), ENDTIME2);
        ENDTIME_MAP.put(ENDTIME3.getEndTimeID(), ENDTIME3);
        ENDTIME_MAP.put(ENDTIME4.getEndTimeID(), ENDTIME4);
        ENDTIME_MAP.put(ENDTIME5.getEndTimeID(), ENDTIME5);
        ENDTIME_MAP.put(ENDTIME6.getEndTimeID(), ENDTIME6);
        ENDTIME_MAP.put(ENDTIME7.getEndTimeID(), ENDTIME7);
        ENDTIME_MAP.put(ENDTIME8.getEndTimeID(), ENDTIME8);

    }

    /**
     * 
     * @return The Map containing Rooms.
     */
    public static Map<Integer, Room> getRoomMap()
    {
        return ROOM_MAP;
    }

    /**
     * 
     * @return The Map containing StartTimes.
     */
    public static Map<Integer, StartTime> getStartTimeMap()
    {
        return STARTTIME_MAP;
    }

    /**
     * 
     * @return The Map containing EndTimes.
     */
    public static Map<Integer, EndTime> getEndTimeMap()
    {
        return ENDTIME_MAP;
    }
}
