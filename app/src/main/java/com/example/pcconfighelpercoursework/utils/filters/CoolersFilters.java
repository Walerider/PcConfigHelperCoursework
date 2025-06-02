package com.example.pcconfighelpercoursework.utils.filters;

import com.example.pcconfighelpercoursework.api.items.ProductAttributeDAO;

public class CoolersFilters {
    public static final ProductAttributeDAO SOCKET = new ProductAttributeDAO("Сокет");
    public static final ProductAttributeDAO DISS_POWER = new ProductAttributeDAO("Рассеиваемая мощность");
    public static final ProductAttributeDAO RADIATOR_MATERIAL = new ProductAttributeDAO("Материал радиатора");
    public static final ProductAttributeDAO NOISE_LEVEL = new ProductAttributeDAO("Максимальный уровень шума");
    public static final ProductAttributeDAO CONNECTOR = new ProductAttributeDAO("Разъем для подключения вентиляторов");
}
