package com.dct.model;

/**
 * Created by Stukolov on 11.04.2015.
 */
public class DocumentItem {

    private String id;
    private String barcode;
    private String name;
    private String qty;

    private String documentType;
    private String documentNumber;

    public DocumentItem() {
    }

    public DocumentItem(String id, String barcode, String name, String qty) {
        this.id = id;
        this.barcode = barcode;
        this.name = name;
        this.qty = qty;
        this.documentType = "arrival";
        this.documentNumber = "AR-555";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }
}
