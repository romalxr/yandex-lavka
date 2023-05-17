package ru.yandex.yandexlavka.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum CourierType {
    FOOT("FOOT"),

    BIKE("BIKE"),

    AUTO("AUTO");

    private final String value;

    CourierType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static CourierType fromValue(String value) {
        for (CourierType b : CourierType.values()) {
            if (b.value.equals(value.toUpperCase())) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

}
