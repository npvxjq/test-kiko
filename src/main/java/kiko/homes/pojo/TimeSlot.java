package kiko.homes.pojo;

import kiko.homes.Config;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

class TimeSlot {

    private static final long slotSizeInMinutes=20;
    private static final LocalDateTime initialDate= LocalDateTime.of(2000,1,1,0,0);


    static long getOffset(LocalDateTime dateTime) {
        return ChronoUnit.MINUTES.between(initialDate,dateTime)/slotSizeInMinutes;
    }

    static LocalDateTime getDateTime(long offset) {
        return initialDate.plusMinutes(offset*slotSizeInMinutes);
    }

    static String asString(long timeSlot) {
        return DateTimeFormatter.ofPattern(Config.dateFormat).format(getDateTime(timeSlot));
    }
}
