package com.dct.model;

/**
 * Created by stukolov_m on 22.04.2015.
 */
public class InventItemBarcode {

    public String barcode;
    public String scu;
    public String size;

    public InventItemBarcode(String barcode, String scu, String size) {
        this.barcode = barcode;
        this.scu = scu;
        this.size = size;
    }

    public InventItemBarcode(String scu, String size) {
        this.scu = scu;
        this.size = size;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getScu() {
        return scu;
    }

    public void setScu(String scu) {
        this.scu = scu;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
