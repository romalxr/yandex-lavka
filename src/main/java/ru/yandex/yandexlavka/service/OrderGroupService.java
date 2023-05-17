package ru.yandex.yandexlavka.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.yandex.yandexlavka.dto.CouriersGroupOrders;
import ru.yandex.yandexlavka.dto.OrderAssignResponse;
import ru.yandex.yandexlavka.entity.Courier;
import ru.yandex.yandexlavka.entity.Group;
import ru.yandex.yandexlavka.mapper.GroupMapper;
import ru.yandex.yandexlavka.repository.OrderGroupsRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

@Service
@Validated
@Transactional
public class OrderGroupService {

    private final OrderGroupsRepository orderGroupsRepository;

    public OrderGroupService(OrderGroupsRepository orderGroupsRepository) {
        this.orderGroupsRepository = orderGroupsRepository;
    }

    public List<OrderAssignResponse> getAssignedOrdersByCourier(LocalDate date, Courier courier) {
        List<Group> groups = (List<Group>) orderGroupsRepository.findAllByDateAndCourier(date, courier);
        return mapGroupsToOrderAssign(date, groups);
    }

    public List<OrderAssignResponse> getAssignedOrders(LocalDate date) {
        List<Group> groups = getAllGroups(date);
        return mapGroupsToOrderAssign(date, groups);
    }

    public List<OrderAssignResponse> mapGroupsToOrderAssign(LocalDate date, List<Group> groups) {
        List<OrderAssignResponse> result = new ArrayList<>();
        Map<Courier, List<Group>> map = groups.stream().collect(groupingBy(Group::getCourier));

        List<CouriersGroupOrders> courierGroups = new ArrayList<>();
        for (Map.Entry<Courier, List<Group>> entry : map.entrySet()) {
            courierGroups.add(CouriersGroupOrders.builder()
                .courierId(entry.getKey().getCourierId())
                .orders(entry.getValue().stream()
                        .map(GroupMapper::toDto)
                        .toList())
                .build());
        }

        courierGroups = courierGroups.stream()
                .sorted(Comparator.comparing(CouriersGroupOrders::getCourierId))
                .toList();

        OrderAssignResponse assignResponse = OrderAssignResponse.builder()
                .date(date)
                .couriers(courierGroups)
                .build();

        result.add(assignResponse);
        return result;
    }

    public List<Group> getAllGroups(LocalDate date) {
        return (List<Group>) orderGroupsRepository.findAllByDate(date);
    }

    public void saveAll(List<Group> groups) {
        orderGroupsRepository.saveAll(groups);
    }
}
