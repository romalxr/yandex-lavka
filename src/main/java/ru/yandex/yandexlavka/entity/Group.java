package ru.yandex.yandexlavka.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "groups")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long groupId;
    private LocalDate date;
    @ManyToOne
    private Courier courier;
    @OneToMany
    private List<Order> orders;
    private TimeInterval workingTime;
    private Integer maxOrdersSize;
    private float totalWeight;
    private float maxWeight;
    private Integer maxRegions;
    @ElementCollection(fetch = FetchType.LAZY)
    private Set<Integer> regions;

}
