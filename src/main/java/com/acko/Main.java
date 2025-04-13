package com.acko;

import com.acko.services.Scheduler;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scheduler scheduler = new Scheduler();
        try {
            // Add custom room (optional)
            scheduler.addMeetingRoom("GalaxyRoom", 15);

            // Valid booking
            scheduler.bookRoom("09:30", "10:00", 4); // Should go to FerozShahKotla
            scheduler.bookRoom("10:00", "10:30", 2); // Should go to Wimbledon
            scheduler.bookRoom("10:00", "10:30", 3); // Should go to FerozShahKotla

            // Maintenance time - should fail
            try {
                scheduler.bookRoom("09:00", "09:30", 2); // Maintenance time
            } catch (RuntimeException e) {
                System.out.println("❌ " + e.getMessage());
            }

            // Invalid time format - should fail
            try {
                scheduler.bookRoom("10:15", "10:45", 5); // Not 30 min interval
            } catch (RuntimeException e) {
                System.out.println("❌ " + e.getMessage());
            }

            // Invalid capacity - should fail
            try {
                scheduler.bookRoom("11:00", "11:30", 1); // Capacity too low
            } catch (RuntimeException e) {
                System.out.println("❌ " + e.getMessage());
            }

            // Add guests
            scheduler.addGuestToMeeting("FerozShahKotla", "09:30", "alice@example.com");
            scheduler.addGuestToMeeting("FerozShahKotla", "09:30", "bob@example.com");

            // View available rooms
            System.out.println("\nAvailable rooms between 10:00 - 10:30:");
            scheduler.viewRooms("10:00", "10:30");

            // View full calendar
            System.out.println("\nFull Calendar:");
            scheduler.viewCalendar();

        } catch (RuntimeException e) {
            System.out.println("❌ " + e.getMessage());
        }
    }
}
