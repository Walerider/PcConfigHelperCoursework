package com.example.pcconfighelpercoursework.utils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.Map;

public class SharedViewModel extends ViewModel {
    private MutableLiveData<List<Map<String, String>>> mapList = new MutableLiveData<>();

    public void setMapList(List<Map<String, String>> list) {
        mapList.setValue(list);
    }

    public LiveData<List<Map<String, String>>> getMapList() {
        return mapList;
    }
}
