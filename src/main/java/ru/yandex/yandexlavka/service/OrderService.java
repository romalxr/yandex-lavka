package ru.yandex.yandexlavka.service;

import jakarta.validation.Valid;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.yandexlavka.dto.*;
import ru.yandex.yandexlavka.entity.*;
import ru.yandex.yandexlavka.mapper.OrderMapper;
import ru.yandex.yandexlavka.repository.CouriersRepository;
import ru.yandex.yandexlavka.repository.OrdersRepository;
import ru.yandex.yandexlavka.util.OrdersAssignment;

import java.time.LocalDate;
import java.util.*;

@Service
@Validated
@Transactional
public class OrderService {
    private final OrdersRepository ordersRepository;
    private final CouriersRepository couriersRepository;
    private final CategoryService categoryService;
    private final OrderGroupService orderGroupService;
    public OrderService(OrdersRepository ordersRepository, CouriersRepository couriersRepository, CategoryService categoryService, OrderGroupService orderGroupService) {
        this.ordersRepository = ordersRepository;
        this.couriersRepository = couriersRepository;
        this.categoryService = categoryService;
        this.orderGroupService = orderGroupService;
    }

    public List<OrderDto> createOrder(@Valid CreateOrderRequest createOrderRequest) {
        List<Order> orders = createOrderRequest.getOrders().stream().map(OrderMapper::toEntity).toList();
        ordersRepository.saveAll(orders);
        return orders.stream().map(OrderMapper::toDto).toList();
    }

    public OrderDto findById(@Valid Long orderId) {
        Order order = ordersRepository.findById(orderId).orElse(null);
        if (order == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found!");
        }
        return OrderMapper.toDto(order);
    }

    public List<OrderDto> findAll(@Valid Integer limit, @Valid Integer offset) {
        if (limit < 1 || offset < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad limit / offset values!");
        }
        Page<Order> page = ordersRepository.findAll(PageRequest.of(
                offset / limit,
                limit,
                Sort.Direction.ASC,
                "orderId"));
        return page.map(OrderMapper::toDto).toList();
    }

    public List<OrderDto> completeOrder(@Valid CompleteOrderRequestDto completeOrderRequestDto) {
        List<CompleteOrder> orderDtos = completeOrderRequestDto.getCompleteInfo();
        List<Long> orderIds = orderDtos.stream().map(CompleteOrder::getOrderId).toList();
        List<Order> orders = (List<Order>) ordersRepository.findAllByOrderIdIn(orderIds);
        orderDtos.forEach(dto -> {
            Order order = orders.stream()
                    .filter(o -> Objects.equals(dto.getOrderId(), o.getOrderId()))
                    .findFirst()
                    .orElse(null);
            if (order == null || order.getCourier() == null || !Objects.equals(dto.getCourierId(), order.getCourier().getCourierId())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad order id / courier id values!");
            }
            if (order.getCompletedTime() == null)
                order.setCompletedTime(dto.getCompleteTime());
        });
        ordersRepository.saveAll(orders);
        return orders.stream().map(OrderMapper::toDto).toList();
    }

    public List<Order> findAllByCourierAndPeriod(Courier courier, LocalDate startDate, LocalDate endDate) {
        return (List<Order>) ordersRepository.findAllByCourierAndCompletedTimeBetween(courier, startDate.atStartOfDay(), endDate.atStartOfDay());
    }

    public List<OrderAssignResponse> ordersAssign(@Valid LocalDate date) {

        Map<CourierType, Category> categories = categoryService.findAll();

        List<Courier> couriers = (List<Courier>) couriersRepository.findAllByOrderByCourierTypeDesc();
        List<Group> existGroups = orderGroupService.getAllGroups(date);
        List<Order> orders = (List<Order>) ordersRepository.
                findAllByDateBeforeAndCourierIsNullOrderByWeightDesc(date.plusDays(1));

        List<Group> groups = OrdersAssignment.packBinsFirstFit(date, categories, couriers, existGroups, orders);

        List<Order> assignedOrders = orders.stream()
                .filter(o -> o.getCourier() != null)
                .toList();

        ordersRepository.saveAll(assignedOrders);
        orderGroupService.saveAll(groups);

        return orderGroupService.getAssignedOrders(date);
    }
}
