package com.example.pcconfighelpercoursework.catalog.items;

public class CatalogCPUCooler extends CatalogItem {
    String socket;
    int dissipation;

    public CatalogCPUCooler(int id, String type, String name, String image, String componentType, String description, int price, String socket, int dissipation) {
        super(id, type, name, image, componentType, description, price);
        this.socket = socket;
        this.dissipation = dissipation;
    }

    public String getSocket() {
        return socket;
    }

    public void setSocket(String socket) {
        this.socket = socket;
    }

    public int getDissipation() {
        return dissipation;
    }

    public void setDissipation(int dissipation) {
        this.dissipation = dissipation;
    }
}
