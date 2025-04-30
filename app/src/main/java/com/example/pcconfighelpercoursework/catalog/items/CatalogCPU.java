package com.example.pcconfighelpercoursework.catalog.items;

public class CatalogCPU extends CatalogItem {
    int cores;
    String socket;

    public CatalogCPU(int id, String type, String name, String image, String componentType, String description, int price, int cores, String socket) {
        super(id, type, name, image, componentType, description, price);
        this.cores = cores;
        this.socket = socket;
    }

    public int getCores() {
        return cores;
    }

    public void setCores(int cores) {
        this.cores = cores;
    }

    public String getSocket() {
        return socket;
    }

    public void setSocket(String socket) {
        this.socket = socket;
    }
}
