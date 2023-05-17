package ru.yandex.yandexlavka.handler;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.yandex.yandexlavka.entity.Category;
import ru.yandex.yandexlavka.entity.CourierType;
import ru.yandex.yandexlavka.repository.CategoriesRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class OnApplicationStartUp {
    private final CategoriesRepository repository;

    public OnApplicationStartUp(CategoriesRepository repository) {
        this.repository = repository;
    }

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (((List<Category>) repository.findAll()).size() == 0) {
            preloadData();
        }
    }

    private void preloadData() {

        List<Category> services = new ArrayList<>();

        Category foot = Category.builder()
                .courierType(CourierType.FOOT)
                .maxWeight(10)
                .maxCount(2)
                .maxRegions(1)
                .timeFirst(25)
                .timeOthers(10)
                .build();

        Category bike = Category.builder()
                .courierType(CourierType.BIKE)
                .maxWeight(20)
                .maxCount(4)
                .maxRegions(2)
                .timeFirst(12)
                .timeOthers(8)
                .build();

        Category auto = Category.builder()
                .courierType(CourierType.AUTO)
                .maxWeight(40)
                .maxCount(7)
                .maxRegions(3)
                .timeFirst(8)
                .timeOthers(4)
                .build();

        services.add(foot);
        services.add(bike);
        services.add(auto);

        repository.saveAll(services);
    }
}
