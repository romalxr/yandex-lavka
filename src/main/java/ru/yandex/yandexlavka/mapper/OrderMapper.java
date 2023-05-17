package ru.yandex.yandexlavka.mapper;

import ru.yandex.yandexlavka.dto.CreateOrderDto;
import ru.yandex.yandexlavka.dto.OrderDto;
import ru.yandex.yandexlavka.entity.Order;

import java.time.LocalDate;

public class OrderMapper {

    public static Order toEntity(CreateOrderDto orderDto) {
        return Order.builder()
                .region(orderDto.getRegions())
                .cost(orderDto.getCost())
                .weight(orderDto.getWeight())
                .deliveryHours(orderDto.getDeliveryHours())
                .date(LocalDate.now())
                .build();
    }
    public static OrderDto toDto(Order order) {
        return OrderDto.builder()
                .orderId(order.getOrderId())
                .weight(order.getWeight())
                .cost(order.getCost())
                .regions(order.getRegion())
                .deliveryHours(order.getDeliveryHours())
                .completedTime(order.getCompletedTime())
                .build();
    }
}
