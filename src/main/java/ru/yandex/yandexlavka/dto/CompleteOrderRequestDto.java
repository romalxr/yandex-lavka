package ru.yandex.yandexlavka.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CompleteOrderRequestDto {

  @JsonProperty("complete_info")
  @NotNull
  @NotEmpty
  @Valid
  private List<CompleteOrder> completeInfo;

}

