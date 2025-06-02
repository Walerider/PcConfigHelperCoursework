package com.example.pcconfighelpercoursework.utils.filters;

import com.example.pcconfighelpercoursework.api.items.ProductAttributeDAO;

public class VideocardFilters {
    public static final ProductAttributeDAO GPU = new ProductAttributeDAO("Графический процессор");
    public static final ProductAttributeDAO MEMORY_BUS = new ProductAttributeDAO("Графический процессор");
    public static final ProductAttributeDAO MEMORY_TYPE = new ProductAttributeDAO("Тип памяти");
    public static final ProductAttributeDAO MEMORY_AMOUNT = new ProductAttributeDAO("Объем видеопамяти");
    public static final ProductAttributeDAO PSU_RECOMMENDED = new ProductAttributeDAO("Рекомендуемый блок питания");
}
