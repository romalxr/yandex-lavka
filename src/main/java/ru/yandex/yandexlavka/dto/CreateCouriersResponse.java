package ru.yandex.yandexlavka.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateCouriersResponse {

    private List<CourierDto> couriers;
}
