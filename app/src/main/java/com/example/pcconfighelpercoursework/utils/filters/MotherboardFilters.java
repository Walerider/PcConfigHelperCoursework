package com.example.pcconfighelpercoursework.utils.filters;

import com.example.pcconfighelpercoursework.api.items.ProductAttributeDAO;

public class MotherboardFilters {
    public static final ProductAttributeDAO CORE = new ProductAttributeDAO("Ядро");
    public static final ProductAttributeDAO MAX_RAM_SIZE = new ProductAttributeDAO("Максимальный объем памяти");
    public static final ProductAttributeDAO MAX_RAM_SPEED = new ProductAttributeDAO("Максимальная частота памяти (JEDEC / без разгона)");
    public static final ProductAttributeDAO RAM_TYPE = new ProductAttributeDAO("Тип поддерживаемой памяти");
    public static final ProductAttributeDAO INTEL_CHIPSET = new ProductAttributeDAO("Чипсет Intel");
    public static final ProductAttributeDAO AMD_CHIPSET = new ProductAttributeDAO("Чипсет AMD");
    public static final ProductAttributeDAO SOCKET = new ProductAttributeDAO("Сокет");
    public static final ProductAttributeDAO POWER_PHASES = new ProductAttributeDAO("Количество фаз питания");
    public static final ProductAttributeDAO FORM_FACTOR = new ProductAttributeDAO("Форм-фактор");
}
