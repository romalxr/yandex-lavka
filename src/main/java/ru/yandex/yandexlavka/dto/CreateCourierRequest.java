package ru.yandex.yandexlavka.dto;

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
public class CreateCourierRequest {

  @Valid
  @NotNull
  @NotEmpty
  private List<CreateCourierDto> couriers;

}

