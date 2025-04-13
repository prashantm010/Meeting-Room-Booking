package com.acko.services;

import com.acko.enums.RoomName;
import com.acko.exceptions.*;
import com.acko.models.MeetingRoom;
import com.acko.models.TimeSlot;
import com.acko.utils.TimeUtil;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Scheduler {
    private final List<MeetingRoom> rooms = new ArrayList<>();

    public Scheduler() {
        rooms.add(new MeetingRoom(RoomName.WIMBLEDON.name(), RoomName.WIMBLEDON.getCapacity()));
        rooms.add(new MeetingRoom(RoomName.FEROZSHAHKOTLA.name(), RoomName.FEROZSHAHKOTLA.getCapacity()));
        rooms.add(new MeetingRoom(RoomName.EDENGARDEN.name(), RoomName.EDENGARDEN.getCapacity()));
        rooms.add(new MeetingRoom(RoomName.LORDS.name(), RoomName.LORDS.getCapacity()));

    }

    public void addMeetingRoom(String name, int capacity) {
        for (MeetingRoom room : rooms) {
            if (room.getName().equalsIgnoreCase(name)) {
                throw new RoomAlreadyExistsException("Room with name '" + name + "' already exists.");
            }
        }
        rooms.add(new MeetingRoom(name.toUpperCase(), capacity));
        System.out.println("Added room: " + name + " with capacity " + capacity);
    }

    public void bookRoom(String startStr, String endStr, int capacity) {
        LocalTime start = LocalTime.parse(startStr);
        LocalTime end = LocalTime.parse(endStr);

        if (!TimeUtil.isValidTime(start) || !TimeUtil.isValidTime(end)) {
            throw new InvalidTimeException("Time should be in 30-minute slots like HH:00 or HH:30");
        }

        if (capacity < 2 || capacity > 20) {
            throw new InvalidCapacityException("Person count should be between 2 and 20");
        }

        TimeSlot slot = new TimeSlot(start, end);
        if (!TimeUtil.isWithinBusinessHours(slot)) {
            throw new InvalidTimeException("Meetings are allowed only between 09:00 and 19:00");
        }

        if (TimeUtil.isMaintenance(slot)) {
            throw new MaintenanceWindowException("Room unavailable during maintenance");
        }

        MeetingRoom allocated = null;

        for (MeetingRoom room : rooms) {
            if (room.getCapacity() >= capacity && room.isAvailable(slot)) {
                allocated = room;
                break;
            }
        }

        if (allocated == null) {
            System.out.println("NO_ROOM_AVAILABLE");
        } else {
            allocated.book(slot);
            System.out.println(allocated.getName());
        }
    }

    public void viewRooms(String startStr, String endStr) {
        try {
            LocalTime start = LocalTime.parse(startStr);
            LocalTime end = LocalTime.parse(endStr);

            if (!TimeUtil.isValidTime(start) || !TimeUtil.isValidTime(end) || !start.isBefore(end)) {
                System.out.println();
                return;
            }

            TimeSlot slot = new TimeSlot(start, end);
            if (TimeUtil.isMaintenance(slot)) {
                System.out.println();
                return;
            }


            if (!TimeUtil.isWithinBusinessHours(slot)) {
                System.out.println();
                return;
            }

            List<String> available = new ArrayList<>();

            for (MeetingRoom room : rooms) {
                if (room.isAvailable(slot)) {
                    available.add(room.getName());
                }
            }

            System.out.println(String.join(" ", available));

        } catch (Exception e) {
            System.out.println();
        }
    }

    public void viewCalendar() {
        System.out.println("------ Meeting Room Calendar for the Day ------");

        for (MeetingRoom room : rooms) {
            System.out.println(room.getName() + ":");
            List<TimeSlot> sorted = new ArrayList<>(room.getBookings());
            sorted.sort(Comparator.comparing(TimeSlot::getStart));

            if (sorted.isEmpty()) {
                System.out.println("  (empty)");
            } else {
                for (TimeSlot slot : sorted) {
                    System.out.println("  " + slot.getStart() + " - " + slot.getEnd());
                }
            }
            System.out.println();
        }
    }

    public void addGuestToMeeting(String roomName, String timeStr, String guestName) {
        LocalTime start = LocalTime.parse(timeStr);
        TimeSlot targetSlot = null;

        for (MeetingRoom room : rooms) {
            if (room.getName().equalsIgnoreCase(roomName)) {
                for (TimeSlot slot : room.getBookings()) {
                    if (slot.getStart().equals(start)) {
                        targetSlot = slot;
                        break;
                    }
                }

                if (targetSlot == null) {
                    throw new InvalidTimeException("No meeting scheduled at the given time.");
                }
                boolean added = room.addGuest(targetSlot, guestName);
                if (!added) {
                    throw new GuestLimitExceededException("Guest limit exceeded.");
                }
                return;
            }
        }

        System.out.println("Room not found.");
    }
}
