package ru.yandex.yandexlavka.mapper;

import ru.yandex.yandexlavka.dto.GroupOrders;
import ru.yandex.yandexlavka.entity.Group;

public class GroupMapper {
    public static GroupOrders toDto(Group group) {
        return GroupOrders.builder()
                .groupOrderId(group.getGroupId())
                .orders(group.getOrders().stream()
                        .map(OrderMapper::toDto)
                        .toList())
                .build();
    }
}
