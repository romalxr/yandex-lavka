package ru.yandex.yandexlavka.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ru.yandex.yandexlavka.entity.CourierType;
import ru.yandex.yandexlavka.entity.TimeInterval;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CourierDto {
    @JsonProperty("courier_id")
    private Long courierId;
    @JsonProperty("courier_type")
    private CourierType courierType;
    private List<Integer> regions;
    @JsonProperty("working_hours")
    private List<TimeInterval> workingHours;
}
