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
public class CouriersGroupOrders {

  @JsonProperty("courier_id")
  @NotNull
  private Long courierId;
  @JsonProperty("orders")
  @NotNull
  @NotEmpty
  private List<GroupOrders> orders;

}

