package ru.yandex.yandexlavka.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import ru.yandex.yandexlavka.entity.TimeInterval;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateOrderDto {
  @NotNull
  @Min(value = 0)
  @Max(value = 40)
  private Float weight;
  @NotNull
  @Min(value = 0)
  private Integer regions;
  @JsonProperty("delivery_hours")
  @NotNull
  @NotEmpty
  private List<TimeInterval> deliveryHours;
  @NotNull
  @Min(value = 0)
  private Integer cost;

}

