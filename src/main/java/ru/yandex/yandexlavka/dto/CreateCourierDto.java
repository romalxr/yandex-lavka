package ru.yandex.yandexlavka.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import ru.yandex.yandexlavka.entity.CourierType;
import ru.yandex.yandexlavka.entity.TimeInterval;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateCourierDto {

  @JsonProperty("courier_type")
  @NotNull(message = "Courier type must be specified!")
  private CourierType courierType;
  @NotNull(message = "Regions must be specified!")
  @NotEmpty(message = "Regions must be specified!")
  private List<@Min(value = 0) Integer> regions;
  @JsonProperty("working_hours")
  @NotNull(message = "Working hours must be specified!")
  @NotEmpty(message = "Working hours must be specified!")
  private List<TimeInterval> workingHours;

}

