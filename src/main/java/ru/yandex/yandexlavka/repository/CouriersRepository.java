package ru.yandex.yandexlavka.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.yandex.yandexlavka.entity.Courier;

public interface CouriersRepository extends CrudRepository<Courier, Long>, PagingAndSortingRepository<Courier, Long> {
    Iterable<Courier> findAllByOrderByCourierTypeDesc();
}
