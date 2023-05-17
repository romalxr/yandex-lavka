package ru.yandex.yandexlavka.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.yandex.yandexlavka.util.DateTimeUtil;

import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Courier {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long courierId;
    private CourierType courierType;
    @ElementCollection(fetch = FetchType.LAZY)
    private List<Integer> regions;
    @ElementCollection(fetch = FetchType.LAZY)
    private List<TimeInterval> workingHours;
    @Transient
    private Integer rating;
    @Transient
    private Integer earnings;

    public int getTotalHours() {
        int totalHours = 0;
        for (TimeInterval interval : workingHours) {
            totalHours += DateTimeUtil.hoursBetween(interval.getStartTime(), interval.getEndTime());
        }
        return totalHours;
    }
}
