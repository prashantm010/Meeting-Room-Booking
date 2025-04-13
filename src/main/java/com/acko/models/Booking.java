package com.acko.models;

import java.util.HashSet;
import java.util.Set;

public class Booking {
    private final TimeSlot timeSlot;
    private final Set<String> guests;

    public Booking(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
        this.guests = new HashSet<>();
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    public boolean addGuest(String guestName, int roomCapacity) {
        if (guests.size() >= roomCapacity) return false;
        guests.add(guestName);
        System.out.println("Guest added: " + guestName);
        System.out.println("📧 Notification sent to " + guestName);
        return true;
    }

    public Set<String> getGuests() {
        return guests;
    }
}
