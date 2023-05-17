package ru.yandex.yandexlavka.service;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.yandexlavka.dto.*;
import ru.yandex.yandexlavka.entity.Courier;
import ru.yandex.yandexlavka.entity.Order;
import ru.yandex.yandexlavka.mapper.CourierMapper;
import ru.yandex.yandexlavka.repository.CouriersRepository;
import ru.yandex.yandexlavka.util.DateTimeUtil;

import java.time.LocalDate;
import java.util.List;

@Service
@Validated
@Transactional
public class CouriersService {

    private final CouriersRepository couriersRepository;
    private final OrderService orderService;
    private final OrderGroupService orderGroupService;

    public CouriersService(CouriersRepository couriersRepository, OrderService orderService, OrderGroupService orderGroupService) {
        this.couriersRepository = couriersRepository;
        this.orderService = orderService;
        this.orderGroupService = orderGroupService;
    }

    public CreateCouriersResponse createCourier(@Valid CreateCourierRequest createCourierRequest) {
        List<Courier> couriers = createCourierRequest.getCouriers().stream().map(CourierMapper::toEntity).toList();
        couriersRepository.saveAll(couriers);
        return CreateCouriersResponse.builder()
                .couriers(couriers.stream()
                        .map(CourierMapper::toDto)
                        .toList())
                .build();
    }

    public CourierDto findById(@Valid Long courierId) {
        Courier courier = couriersRepository.findById(courierId).orElse(null);
        if (courier == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Courier not found!");
        }
        return CourierMapper.toDto(courier);
    }

    public GetCouriersResponse findAll(@Valid Integer limit, @Valid Integer offset) {
        if (limit < 1 || offset < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad limit / offset values!");
        }
        Page<Courier> page = couriersRepository.findAll(PageRequest.of(
                offset / limit,
                limit,
                Sort.Direction.ASC,
                "courierId"));
        return GetCouriersResponse.builder()
                .couriers(page.map(CourierMapper::toDto).toList())
                .limit(limit)
                .offset(offset)
                .build();
    }

    public GetCourierMetaInfoResponse getCourierMetaInfo(@Valid Long courierId, @Valid LocalDate startDate, @Valid LocalDate endDate) {
        if (!startDate.isBefore(endDate)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad startDate / endDate values!");
        }
        Courier courier = couriersRepository.findById(courierId).orElse(null);
        if (courier == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Courier not found!");
        }
        List<Order> orders = orderService.findAllByCourierAndPeriod(courier, startDate, endDate);
        if (orders.size() > 0) {
            int earnings = 0;
            for (Order order : orders) {
                earnings += order.getCost() * (courier.getCourierType().ordinal() + 2);
            }
            courier.setEarnings(earnings);

            int days = DateTimeUtil.daysBetween(startDate, endDate);
            int hours = courier.getTotalHours() * days;
            int rating = (int) (1.0 * orders.size() / hours * (3 - courier.getCourierType().ordinal()));
            courier.setRating(rating);
        }
        return CourierMapper.toMetaInfo(courier);
    }

    public List<OrderAssignResponse> couriersAssignments(LocalDate date, Long courierId) {
        Courier courier = couriersRepository.findById(courierId).orElse(null);
        if (courier == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Courier not found!");
        }
        return orderGroupService.getAssignedOrdersByCourier(date, courier);
    }
}
