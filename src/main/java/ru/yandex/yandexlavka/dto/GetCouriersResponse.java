package ru.yandex.yandexlavka.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GetCouriersResponse {
    private List<CourierDto> couriers;
    private Integer limit;
    private Integer offset;
}
