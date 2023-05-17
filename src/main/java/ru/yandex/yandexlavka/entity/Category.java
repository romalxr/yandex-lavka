package ru.yandex.yandexlavka.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    private CourierType courierType;
    int maxWeight;
    int maxCount;
    int maxRegions;
    int timeFirst;
    int timeOthers;

    public int maxRunTime() {
        return timeFirst + timeOthers * (maxCount - 1);
    }

    public int runTime(int size) {
        if (size == 0 || size >= maxCount) {
            return maxRunTime();
        } else {
            return timeFirst + timeOthers * (size - 1);
        }
    }
}
