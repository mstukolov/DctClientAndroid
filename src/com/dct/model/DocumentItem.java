package com.dct.model;

/**
 * Created by Stukolov on 11.04.2015.
 */
public class DocumentItem {

    public String scu;
    public String barcode;
    public String qty;

    private String documentType;
    private String documentNumber;

    public DocumentItem() {
    }

    public DocumentItem(String scu, String barcode, String qty) {
        this.scu = scu;
        this.barcode = barcode;
        this.qty = qty;
    }

    public String getScu() {
        return scu;
    }

    public void setScu(String scu) {
        this.scu = scu;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }
}
