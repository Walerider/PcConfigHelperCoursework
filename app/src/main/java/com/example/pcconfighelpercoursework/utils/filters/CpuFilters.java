package com.example.pcconfighelpercoursework.utils.filters;

import com.example.pcconfighelpercoursework.api.items.ProductAttributeDAO;

public class CpuFilters {
    public static final ProductAttributeDAO CORE = new ProductAttributeDAO("Ядро");
    public static final ProductAttributeDAO CORE_AMOUNT = new ProductAttributeDAO("Общее количество ядер");
    public static final ProductAttributeDAO THREAD_AMOUNT = new ProductAttributeDAO("Максимальное число потоков");
    public static final ProductAttributeDAO SOCKET = new ProductAttributeDAO("Сокет");
}
