package com.example.pcconfighelpercoursework.utils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pcconfighelpercoursework.api.items.ProductAttributeDAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SharedViewModel extends ViewModel {
    // Хранилище для всех списков фильтров
    private final Map<String, MutableLiveData<List<ProductAttributeDAO>>> filterLists = new HashMap<>();

    // Для списка карт (оставляем как было)
    private final MutableLiveData<List<Map<String, String>>> mapList = new MutableLiveData<>();

    // Метод для получения/создания конкретного списка фильтров по тегу
    public MutableLiveData<List<ProductAttributeDAO>> getFiltersList(String tag) {
        if (!filterLists.containsKey(tag)) {
            filterLists.put(tag, new MutableLiveData<>(new ArrayList<>()));
        }
        return filterLists.get(tag);
    }

    // Метод для добавления фильтра в конкретный список
    public void addFilters(String tag, List<ProductAttributeDAO> filters) {
        MutableLiveData<List<ProductAttributeDAO>> liveData = getFiltersList(tag);
        List<ProductAttributeDAO> currentList = liveData.getValue();
        if (currentList != null) {
            liveData.setValue(filters);
        }
    }

    // Метод для очистки конкретного списка
    public void clearFilters(String tag) {
        MutableLiveData<List<ProductAttributeDAO>> liveData = getFiltersList(tag);
        liveData.setValue(new ArrayList<>());
    }

    // Оригинальные методы для mapList
    public void setMapList(List<Map<String, String>> list) {
        mapList.setValue(list);
    }

    public LiveData<List<Map<String, String>>> getMapList() {
        return mapList;
    }
}