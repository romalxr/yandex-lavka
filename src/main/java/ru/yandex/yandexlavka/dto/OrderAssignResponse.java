package ru.yandex.yandexlavka.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderAssignResponse {
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate date;
  private List<CouriersGroupOrders> couriers;
}

