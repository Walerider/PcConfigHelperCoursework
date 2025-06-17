package com.example.pcconfighelpercoursework.utils;

import com.example.pcconfighelpercoursework.items.Component;

import java.util.*;

public class ComponentsSorter {
    private static final Map<String, Integer> CATEGORY_ORDER = new HashMap<>();
    static {
        CATEGORY_ORDER.put("Процессор", 1);
        CATEGORY_ORDER.put("Видеокарта", 2);
        CATEGORY_ORDER.put("Материнская плата", 3);
        CATEGORY_ORDER.put("ОЗУ", 4);
        CATEGORY_ORDER.put("Блок питания", 5);
        CATEGORY_ORDER.put("Процессорный кулер", 6);
        CATEGORY_ORDER.put("Корпус", 7);
        CATEGORY_ORDER.put("Накопитель", 8);
    }

    public static List<Component> sortComponents(List<Component> components) {
        Comparator<Component> comparator = (c1, c2) -> {
            Integer order1 = CATEGORY_ORDER.getOrDefault(c1.getCategory(), Integer.MAX_VALUE);
            Integer order2 = CATEGORY_ORDER.getOrDefault(c2.getCategory(), Integer.MAX_VALUE);
            return Integer.compare(order1, order2);
        };

        components.sort(comparator);
        return components;
    }
}