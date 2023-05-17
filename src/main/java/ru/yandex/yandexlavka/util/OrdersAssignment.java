package ru.yandex.yandexlavka.util;

import ru.yandex.yandexlavka.entity.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class OrdersAssignment {

    // When processing the next item, scan the previous bins in order and place the item in the first bin that fits.
    // Start a new bin only if it does not fit in any of the existing bins.
    public static List<Group> packBinsFirstFit(LocalDate date,
                                               Map<CourierType,
                                               Category> categories,
                                               List<Courier> couriers,
                                               List<Group> existGroups,
                                               List<Order> orders) {

        List<Group> groups = splitByGroups(couriers, categories, date, existGroups);
        Set<Group> usedGroups = new HashSet<>();

        for (Order order : orders) {
            boolean groupFound = false;
            for (Group group : usedGroups) {
                if (addToGroup(group, order)) {
                    groupFound = true;
                    break;
                }
            }
            if (!groupFound) {
                for (Group group : groups) {
                    if (usedGroups.contains(group)) {
                        continue;
                    }
                    if (addToGroup(group, order)) {
                        usedGroups.add(group);
                        break;
                    }
                }
            }
        }

        List<Group> result = new ArrayList<>(usedGroups);
        repackByCost(result);
        return result;
    }

    private static void repackByCost(List<Group> groups) {
        for (Group group : groups) {
            List<Order> orders = group.getOrders();
            int minCost = Integer.MAX_VALUE;
            int indMin = 0;
            for (int i = 0; i < orders.size(); i++) {
                int cost = orders.get(i).getCost();
                if (cost < minCost) {
                    minCost = cost;
                    indMin = i;
                }
            }
            if (indMin > 0) {
                Order buff = orders.get(0);
                orders.set(0, orders.get(indMin));
                orders.set(indMin, buff);
            }
        }
    }

    private static List<Group> splitByGroups(List<Courier> couriers, Map<CourierType, Category> categories, LocalDate date, List<Group> existGroups) {
        List<Group> groups = new ArrayList<>();
        for (Courier courier : couriers) {
            List<Group> existByCourier = existGroups.stream()
                    .filter(g -> courier.equals(g.getCourier()))
                    .toList();
            Category category = categories.get(courier.getCourierType());
            int maxRunTime = category.maxRunTime();
            for (TimeInterval timeInterval : courier.getWorkingHours()) {
                LocalTime start = timeInterval.getStartTime();
                LocalTime end = start.plusMinutes(maxRunTime);
                while (end.isBefore(timeInterval.getEndTime())) {

                    final LocalTime finalStart = start;
                    final LocalTime finalEnd = end;
                    if (existByCourier.stream().noneMatch(g ->
                            g.getWorkingTime().getStartTime().isBefore(finalEnd) &&
                            g.getWorkingTime().getEndTime().isAfter(finalStart))) {

                        groups.add(Group.builder()
                                .date(date)
                                .courier(courier)
                                .maxRegions(category.getMaxRegions())
                                .maxOrdersSize(category.getMaxCount())
                                .maxWeight(category.getMaxWeight())
                                .workingTime(new TimeInterval(start, end))
                                .orders(new ArrayList<>())
                                .regions(new HashSet<>())
                                .build());
                    }

                    start = end;
                    end = start.plusMinutes(maxRunTime);
                }
            }
        }

        return groups;
    }

    private static boolean addToGroup(Group group, Order order) {

        if (group.getOrders().size() == group.getMaxOrdersSize()) {
            return false;
        }
        if (group.getTotalWeight() + order.getWeight() > group.getMaxWeight()) {
            return false;
        }
        //if (!checkDeliveryHours(group, order)) return false;

        Integer orderRegion = order.getRegion();
        Set<Integer> groupRegions = group.getRegions();
        List<Integer> courierRegions = group.getCourier().getRegions();
        if (!courierRegions.contains(orderRegion)) {
            return false;
        }
        if (groupRegions.size() == group.getMaxRegions()
            && !groupRegions.contains(orderRegion)) {
            return false;
        }

        group.getRegions().add(orderRegion);
        group.setTotalWeight(group.getTotalWeight() + order.getWeight());
        group.getOrders().add(order);
        order.setCourier(group.getCourier());
        return true;
    }

    private static boolean checkDeliveryHours(Group group, Order order) {
        TimeInterval wt = group.getWorkingTime();
        return order.getDeliveryHours().stream()
                .anyMatch(dh -> wt.getStartTime().plusSeconds(1).isAfter(dh.getStartTime())
                        && wt.getEndTime().minusSeconds(1).isBefore(dh.getEndTime()));
    }

}
