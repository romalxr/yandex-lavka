package ru.yandex.yandexlavka.mapper;

import ru.yandex.yandexlavka.dto.CourierDto;
import ru.yandex.yandexlavka.dto.CreateCourierDto;
import ru.yandex.yandexlavka.dto.GetCourierMetaInfoResponse;
import ru.yandex.yandexlavka.entity.Courier;

public class CourierMapper {

    public static Courier toEntity(CreateCourierDto createCourierDto) {
        return Courier.builder()
                .courierType(createCourierDto.getCourierType())
                .regions(createCourierDto.getRegions())
                .workingHours(createCourierDto.getWorkingHours())
                .build();
    }

    public static CourierDto toDto(Courier courier) {
        return CourierDto.builder()
                .courierId(courier.getCourierId())
                .courierType(courier.getCourierType())
                .regions(courier.getRegions())
                .workingHours(courier.getWorkingHours())
                .build();
    }

    public static GetCourierMetaInfoResponse toMetaInfo(Courier courier) {
        return GetCourierMetaInfoResponse.builder()
                .courierId(courier.getCourierId())
                .courierType(courier.getCourierType())
                .regions(courier.getRegions())
                .workingHours(courier.getWorkingHours())
                .rating(courier.getRating())
                .earnings(courier.getEarnings())
                .build();
    }

}
