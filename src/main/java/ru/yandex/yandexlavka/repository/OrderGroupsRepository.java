package ru.yandex.yandexlavka.repository;

import org.springframework.data.repository.CrudRepository;
import ru.yandex.yandexlavka.entity.Courier;
import ru.yandex.yandexlavka.entity.Group;

import java.time.LocalDate;

public interface OrderGroupsRepository extends CrudRepository<Group, Long> {
    Iterable<Group> findAllByDate(LocalDate date);
    Iterable<Group> findAllByDateAndCourier(LocalDate date, Courier courier);
}
