package ru.yandex.yandexlavka.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CompleteOrder {

  @JsonProperty("courier_id")
  @NotNull
  private Long courierId;
  @JsonProperty("order_id")
  @NotNull
  private Long orderId;
  @JsonProperty("complete_time")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  @NotNull
  private LocalDateTime completeTime;

}

