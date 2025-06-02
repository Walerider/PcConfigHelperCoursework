package com.example.pcconfighelpercoursework.utils.filters;

import com.example.pcconfighelpercoursework.api.items.ProductAttributeDAO;

public class CaseFilters {
    public static final ProductAttributeDAO MB_FORM_FACTOR = new ProductAttributeDAO("Форм-фактор совместимых плат");
    public static final ProductAttributeDAO CASE_SIZE = new ProductAttributeDAO("Типоразмер корпуса");
    public static final ProductAttributeDAO CASE_MATERIAL = new ProductAttributeDAO("Материал корпуса");
    public static final ProductAttributeDAO PSU_PLACEMENT = new ProductAttributeDAO("Размещение блока питания");
}
