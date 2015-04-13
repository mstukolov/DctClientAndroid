package com.dct.model;

/**
 * Created by Stukolov on 10.04.2015.
 */
public class Goods {
    private int id;
    private String name;
    private int barcode;

    public Goods() {
    }

    public Goods(int id, String name, int barcode) {
        this.id = id;
        this.name = name;
        this.barcode = barcode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBarcode() {
        return barcode;
    }

    public void setBarcode(int barcode) {
        this.barcode = barcode;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}


