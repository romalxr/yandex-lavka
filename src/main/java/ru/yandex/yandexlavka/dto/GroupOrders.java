package ru.yandex.yandexlavka.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GroupOrders {

  @JsonProperty("group_order_id")
  @NotNull
  private Long groupOrderId;

  @JsonProperty("orders")
  @NotNull
  @NotEmpty
  private List<OrderDto> orders;

}

