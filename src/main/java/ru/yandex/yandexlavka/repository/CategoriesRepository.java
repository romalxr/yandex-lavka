package ru.yandex.yandexlavka.repository;

import org.springframework.data.repository.CrudRepository;
import ru.yandex.yandexlavka.entity.Category;
import ru.yandex.yandexlavka.entity.CourierType;

public interface CategoriesRepository extends CrudRepository<Category, CourierType> {
}
