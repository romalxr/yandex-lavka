package ru.yandex.yandexlavka.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.yandex.yandexlavka.entity.Courier;
import ru.yandex.yandexlavka.entity.Order;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

public interface OrdersRepository extends CrudRepository<Order, Long>, PagingAndSortingRepository<Order, Long> {
    Iterable<Order> findAllByOrderIdIn(Collection<Long> orderId);
    Iterable<Order> findAllByCourierAndCompletedTimeBetween(Courier courier, LocalDateTime startDate, LocalDateTime endDate);
    Iterable<Order> findAllByDateBeforeAndCourierIsNullOrderByWeightDesc(LocalDate date);
}
