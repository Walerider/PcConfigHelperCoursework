package com.example.pcconfighelpercoursework.utils.filters;

import com.example.pcconfighelpercoursework.api.items.ProductAttributeDAO;

public class RamFilters {
    public static final ProductAttributeDAO RANKED = new ProductAttributeDAO("Ранговость");
    public static final ProductAttributeDAO MODULE_SIZE = new ProductAttributeDAO("Объем одного модуля памяти");
    public static final ProductAttributeDAO SUM_MODULE_SIZE = new ProductAttributeDAO("Суммарный объем памяти всего комплекта");
    public static final ProductAttributeDAO MODULE_AMOUNT = new ProductAttributeDAO("Количество модулей в комплекте");
    public static final ProductAttributeDAO FREQUENCY = new ProductAttributeDAO("Тактовая частота");
    public static final ProductAttributeDAO MEMORY_TYPE = new ProductAttributeDAO("Тип памяти");
    public static final ProductAttributeDAO XMP_PROFILE = new ProductAttributeDAO("Профили Intel XMP");
}
