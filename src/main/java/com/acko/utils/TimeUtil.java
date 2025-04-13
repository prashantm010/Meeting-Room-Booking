package com.acko.utils;


import com.acko.models.TimeSlot;

import java.time.LocalTime;
import java.util.Set;

public class TimeUtil {
    private static final Set<TimeSlot> MAINTENANCE = Set.of(
        new TimeSlot(LocalTime.of(9, 0), LocalTime.of(9, 30)),
        new TimeSlot(LocalTime.of(13, 0), LocalTime.of(13, 30)),
        new TimeSlot(LocalTime.of(18, 30), LocalTime.of(19, 0))
    );

    private static final LocalTime START_LIMIT = LocalTime.of(9, 0);
    private static final LocalTime END_LIMIT = LocalTime.of(19, 0);

    public static boolean isValidTime(LocalTime time) {
        int minute = time.getMinute();
        return (minute == 0 || minute == 30);
    }

    public static boolean isMaintenance(TimeSlot slot) {
        for (TimeSlot m : MAINTENANCE) {
            if (slot.overlaps(m)) return true;
        }
        return false;
    }

    public static boolean isWithinBusinessHours(TimeSlot slot) {
        return !slot.getStart().isBefore(START_LIMIT) && !slot.getEnd().isAfter(END_LIMIT);
    }
}
