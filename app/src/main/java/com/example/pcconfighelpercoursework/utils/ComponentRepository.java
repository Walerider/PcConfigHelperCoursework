package com.example.pcconfighelpercoursework.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.pcconfighelpercoursework.api.items.ProductAttributeDAO;
import com.example.pcconfighelpercoursework.api.items.ProductDAO;
import com.example.pcconfighelpercoursework.items.Component;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ComponentRepository {
    private final DatabaseHelper dbHelper;
    Gson gson = new Gson();
    public ComponentRepository(Context context) {
        this.dbHelper = DbSingleton.getInstance(context);
    }
    public boolean hasAnyComponents() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("SELECT 1 FROM components LIMIT 1", null);
            return cursor != null && cursor.getCount() > 0;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
    public void insertComponents(List<ProductDAO> list, int categoryId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.beginTransaction();

            for (ProductDAO p : list) {
                ContentValues values = new ContentValues();
                values.put("id", p.getId());
                values.put("name", p.getName());
                values.put("description", p.getDescription());
                if(p.getPrices().size() != 0){
                    values.put("price", p.getPrices().get(0));
                }else{
                    continue;
                }
                values.put("category_id", categoryId);
                if (p.getAttributes() != null) {
                    values.put("attributes", gson.toJson(p.getAttributes()));
                }
                db.insertWithOnConflict("components", null, values,
                        SQLiteDatabase.CONFLICT_IGNORE);
            }

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            // Не закрывайте db здесь, если будете использовать ее дальше
            // db.close();
        }
    }
    public List<Component> getComponentsByCategory(int categoryId,String componentType) {
        List<Component> components = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try (Cursor cursor = db.query(
                "components",
                null,
                "category_id = ?",
                new String[]{String.valueOf(categoryId)},
                null, null, null
        )) {
            while (cursor.moveToNext()) {
                Component component = new Component();
                component.setId(cursor.getInt(cursor.getColumnIndex("id")));
                component.setName(cursor.getString(cursor.getColumnIndex("name")));
                component.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                component.setPrice(cursor.getInt(cursor.getColumnIndex("price")));
                component.setComponentType(componentType);
                String attributesJson = cursor.getString(cursor.getColumnIndex("attributes"));
                Type attrType = new TypeToken<List<ProductAttributeDAO>>(){}.getType();
                List<ProductAttributeDAO> attributes = gson.fromJson(attributesJson, attrType);
                component.setAttributes(attributes != null ? attributes : new ArrayList<>());
                components.add(component);
            }
        }
        return components;
    }
    public void recreateDatabase(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        dbHelper.onUpgrade(db,1,2);
    }
}

