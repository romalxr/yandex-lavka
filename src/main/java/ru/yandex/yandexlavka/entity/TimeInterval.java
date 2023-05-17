package ru.yandex.yandexlavka.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TimeInterval {
    private LocalTime startTime;
    private LocalTime endTime;

    @JsonValue
    public void setValue(String value) {}

    @JsonValue
    public String getValue() {
        return toString();
    }

    @Override
    public String toString() {
        String first = startTime.format(DateTimeFormatter.ofPattern("HH:mm"));
        String second = endTime.format(DateTimeFormatter.ofPattern("HH:mm"));
        return first + "-" + second;
    }

    @JsonCreator
    public static TimeInterval fromValue(String value) {
        try {
            String[] tokens = value.split("-");
            TimeInterval ti = new TimeInterval();
            ti.startTime = LocalTime.parse(tokens[0]);
            ti.endTime = LocalTime.parse(tokens[1]);
            if (!ti.startTime.isBefore(ti.endTime)) throw new IllegalArgumentException();
            return ti;
        } catch (Exception e) {
            throw new IllegalArgumentException("Unexpected value '" + value + "'");
        }
    }
}