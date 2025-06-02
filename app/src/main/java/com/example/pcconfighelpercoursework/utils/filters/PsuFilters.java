package com.example.pcconfighelpercoursework.utils.filters;

import com.example.pcconfighelpercoursework.api.items.ProductAttributeDAO;

public class PsuFilters {
    public static final ProductAttributeDAO POWER = new ProductAttributeDAO("Мощность (номинал)");
    public static final ProductAttributeDAO POWER_12V = new ProductAttributeDAO("Мощность по линии 12 В");
    public static final ProductAttributeDAO WIRE_BRAID = new ProductAttributeDAO("Оплетка проводов");
    public static final ProductAttributeDAO PCI_E_CONNECTORS = new ProductAttributeDAO("Разъемы для питания видеокарты (PCI-E)");
    public static final ProductAttributeDAO CPU_CONNECTORS = new ProductAttributeDAO("Разъемы для питания процессора (CPU)");
    public static final ProductAttributeDAO CERTIFICATE = new ProductAttributeDAO("Сертификат 80 PLUS");
    public static final ProductAttributeDAO DEFEND_TECH = new ProductAttributeDAO("Технологии защиты");
    public static final ProductAttributeDAO MODEL = new ProductAttributeDAO("Модель");
}
