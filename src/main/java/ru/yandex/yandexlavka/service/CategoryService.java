package ru.yandex.yandexlavka.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.yandexlavka.entity.Category;
import ru.yandex.yandexlavka.entity.CourierType;
import ru.yandex.yandexlavka.repository.CategoriesRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class CategoryService {
    private final CategoriesRepository categoriesRepository;

    public CategoryService(CategoriesRepository categoriesRepository) {
        this.categoriesRepository = categoriesRepository;
    }

    public Map<CourierType, Category> findAll() {
        List<Category> list = (List<Category>) categoriesRepository.findAll();
        Map<CourierType, Category> categories = new HashMap<>();
        for (Category category : list) {
            categories.put(category.getCourierType(), category);
        }
        return categories;
    }
}
