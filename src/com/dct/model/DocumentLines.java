package com.dct.model;

/**
 * Created by stukolov_m on 17.04.2015.
 */
public class DocumentLines {
    String scu;
    String barcode;
    String qty;
    String docRef;

    public DocumentLines() {
    }

    public DocumentLines(String scu, String barcode, String qty, String docRef) {
        this.scu = scu;
        this.barcode = barcode;
        this.qty = qty;
        this.docRef = docRef;
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

    public String getDocRef() {
        return docRef;
    }

    public void setDocRef(String docRef) {
        this.docRef = docRef;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DocumentLines that = (DocumentLines) o;

        if (scu != null ? !scu.equals(that.scu) : that.scu != null) return false;
        if (barcode != null ? !barcode.equals(that.barcode) : that.barcode != null) return false;
        if (qty != null ? !qty.equals(that.qty) : that.qty != null) return false;
        return !(docRef != null ? !docRef.equals(that.docRef) : that.docRef != null);

    }

    @Override
    public int hashCode() {
        int result = scu != null ? scu.hashCode() : 0;
        result = 31 * result + (barcode != null ? barcode.hashCode() : 0);
        result = 31 * result + (qty != null ? qty.hashCode() : 0);
        result = 31 * result + (docRef != null ? docRef.hashCode() : 0);
        return result;
    }
}
