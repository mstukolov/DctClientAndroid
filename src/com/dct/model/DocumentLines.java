package com.dct.model;

/**
 * Created by stukolov_m on 17.04.2015.
 */
public class DocumentLines {
    String id;
    String scu;
    String size;
    String qty;
    String docRef;

    public DocumentLines() {
    }

    public DocumentLines(String scu, String size, String qty, String docRef) {
        this.scu = scu;
        this.size = size;
        this.qty = qty;
        this.docRef = docRef;
    }

    public DocumentLines(String id, String scu, String size, String qty, String docRef) {
        this.id = id;
        this.scu = scu;
        this.size = size;
        this.qty = qty;
        this.docRef = docRef;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DocumentLines that = (DocumentLines) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (scu != null ? !scu.equals(that.scu) : that.scu != null) return false;
        if (size != null ? !size.equals(that.size) : that.size != null) return false;
        if (qty != null ? !qty.equals(that.qty) : that.qty != null) return false;
        return !(docRef != null ? !docRef.equals(that.docRef) : that.docRef != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (scu != null ? scu.hashCode() : 0);
        result = 31 * result + (size != null ? size.hashCode() : 0);
        result = 31 * result + (qty != null ? qty.hashCode() : 0);
        result = 31 * result + (docRef != null ? docRef.hashCode() : 0);
        return result;
    }
}
