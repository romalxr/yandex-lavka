package ru.yandex.yandexlavka.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderId;
    private Integer cost;
    private Float weight;
    private Integer region;
    private LocalDate date;
    @ElementCollection(fetch = FetchType.LAZY)
    private List<TimeInterval> deliveryHours;
    @ManyToOne(fetch = FetchType.LAZY)
    private Courier courier;
    private LocalDateTime completedTime;

}
