package com.acko.models;

import java.util.*;

public class MeetingRoom {
    private final String name;
    private final int capacity;
    private final List<TimeSlot> bookings = new ArrayList<>();
    private final Map<TimeSlot, Booking> bookingMap = new HashMap<>();

    public MeetingRoom(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
    }

    public String getName() {
        return name.toString();
    }

    public int getCapacity() {
        return capacity;
    }

    public List<TimeSlot> getBookings() {
        return bookings;
    }

    public boolean isAvailable(TimeSlot newSlot) {
        for (TimeSlot slot : bookings) {
            if (slot.overlaps(newSlot)) return false;
        }
        return true;
    }

    public void book(TimeSlot slot) {
        bookings.add(slot);
        bookingMap.put(slot, new Booking(slot));
    }

    public boolean addGuest(TimeSlot slot, String guestName) {
        Booking booking = bookingMap.get(slot);
        if (booking == null) return false;
        return booking.addGuest(guestName, capacity);
    }
}
