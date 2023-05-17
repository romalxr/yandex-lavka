package ru.yandex.yandexlavka.controller;

import org.springframework.web.bind.annotation.RestController;
import ru.yandex.yandexlavka.dto.*;
import ru.yandex.yandexlavka.service.CouriersService;
import ru.yandex.yandexlavka.util.RequestBucket;

import java.time.LocalDate;
import java.util.List;

@RestController
public class CouriersApiController implements CouriersApi {

    private final RequestBucket requestBucket;
    private final CouriersService couriersService;

    public CouriersApiController(CouriersService couriersService, RequestBucket requestBucket) {
        this.couriersService = couriersService;
        this.requestBucket = requestBucket;
    }

    @Override
    public List<OrderAssignResponse> couriersAssignments(LocalDate date, Long courierId) {
        requestBucket.consume();
        if (date == null) {
            date = LocalDate.now();
        }
        return couriersService.couriersAssignments(date, courierId);
    }

    @Override
    public CreateCouriersResponse createCourier(CreateCourierRequest createCourierRequest) {
        requestBucket.consume();
        return couriersService.createCourier(createCourierRequest);
    }

    @Override
    public CourierDto getCourierById(Long courierId) {
        requestBucket.consume();
        return couriersService.findById(courierId);
    }

    @Override
    public GetCourierMetaInfoResponse getCourierMetaInfo(Long courierId, LocalDate startDate, LocalDate endDate) {
        requestBucket.consume();
        return couriersService.getCourierMetaInfo(courierId, startDate, endDate);
    }

    @Override
    public GetCouriersResponse getCouriers(Integer limit, Integer offset) {
        requestBucket.consume();
        return couriersService.findAll(limit, offset);
    }

}
