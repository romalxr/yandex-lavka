package ru.yandex.yandexlavka.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import ru.yandex.yandexlavka.entity.TimeInterval;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderDto {
  @JsonProperty("order_id")
  @NotNull
  private Long orderId;
  @NotNull
  private Float weight;
  @NotNull
  private Integer regions;
  @JsonProperty("delivery_hours")
  @NotNull
  @NotEmpty
  private List<TimeInterval> deliveryHours;
  @NotNull
  private Integer cost;
  @JsonProperty("completed_time")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private LocalDateTime completedTime;
}

