package ru.yandex.yandexlavka.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.yandexlavka.dto.CompleteOrderRequestDto;
import ru.yandex.yandexlavka.dto.CreateOrderRequest;
import ru.yandex.yandexlavka.dto.OrderAssignResponse;
import ru.yandex.yandexlavka.dto.OrderDto;
import ru.yandex.yandexlavka.service.OrderService;
import ru.yandex.yandexlavka.util.RequestBucket;

import java.time.LocalDate;
import java.util.List;

@RestController
public class OrdersApiController implements OrdersApi {

    private final RequestBucket requestBucket;
    private final OrderService orderService;

    public OrdersApiController(OrderService orderService, RequestBucket requestBucket) {
        this.orderService = orderService;
        this.requestBucket = requestBucket;
    }

    @Override
    public List<OrderDto> completeOrder(CompleteOrderRequestDto completeOrderRequestDto) {
        requestBucket.consume();
        return orderService.completeOrder(completeOrderRequestDto);
    }

    @Override
    public List<OrderDto> createOrder(CreateOrderRequest createOrderRequest) {
        requestBucket.consume();
        return orderService.createOrder(createOrderRequest);
    }

    @Override
    public OrderDto getOrder(Long orderId) {
        requestBucket.consume();
        return orderService.findById(orderId);
    }

    @Override
    public List<OrderDto> getOrders(Integer limit, Integer offset) {
        requestBucket.consume();
        return orderService.findAll(limit, offset);
    }

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    public List<OrderAssignResponse> ordersAssign(LocalDate date) {
        requestBucket.consume();
        if (date == null) {
            date = LocalDate.now();
        }
        return orderService.ordersAssign(date);
    }

}
