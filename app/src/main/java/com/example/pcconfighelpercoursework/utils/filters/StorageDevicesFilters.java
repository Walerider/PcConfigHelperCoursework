package com.example.pcconfighelpercoursework.utils.filters;

import com.example.pcconfighelpercoursework.api.items.ProductAttributeDAO;

public class StorageDevicesFilters {
    public static final ProductAttributeDAO STORAGE_SIZE = new ProductAttributeDAO("Объем накопителя");
    public static final ProductAttributeDAO MAX_WRITE_SPEED = new ProductAttributeDAO("Максимальная скорость последовательной записи");
    public static final ProductAttributeDAO MAX_READ_SPEED = new ProductAttributeDAO("Максимальная скорость последовательного чтения");
    public static final ProductAttributeDAO MAX_WRITE_RESOURCE = new ProductAttributeDAO("Максимальный ресурс записи");
    public static final ProductAttributeDAO MODEL = new ProductAttributeDAO("Модель");
}
