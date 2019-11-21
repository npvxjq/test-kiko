package kiko.homes.pojo;


import com.fasterxml.jackson.databind.util.StdConverter;
import kiko.homes.Config;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


class LocalDateTimeToStringConverter extends StdConverter<LocalDateTime, String> {
    static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(Config.dateFormat);

    @Override
    public String convert(LocalDateTime value) {
        return value.format(DATE_FORMATTER);
    }
}