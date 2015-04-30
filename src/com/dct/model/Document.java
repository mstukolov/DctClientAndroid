package com.dct.model;

import java.util.List;

/**
 * Created by stukolov_m on 17.04.2015.
 */
public class Document {
    String docType;
    String docNum;
    String docDate;
    List<DocumentLines> lines;

    public Document() {
    }

    public Document(String docType, String docNum, String docDate) {
        this.docType = docType;
        this.docNum = docNum;
        this.docDate = docDate;
    }

    public Document(String docType, String docNum, String docDate, List<DocumentLines> lines) {
        this.docType = docType;
        this.docNum = docNum;
        this.docDate = docDate;
        this.lines = lines;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getDocNum() {
        return docNum;
    }

    public void setDocNum(String docNum) {
        this.docNum = docNum;
    }

    public String getDocDate() {
        return docDate;
    }

    public void setDocDate(String docDate) {
        this.docDate = docDate;
    }

    public List<DocumentLines> getLines() {
        return lines;
    }

    public void setLines(List<DocumentLines> lines) {
        this.lines = lines;
    }

    @Override
    public String toString() {
        return "Document{" +
                "docType='" + docType + '\'' +
                ", docNum='" + docNum + '\'' +
                ", docDate='" + docDate + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Document documnent = (Document) o;

        if (docType != null ? !docType.equals(documnent.docType) : documnent.docType != null) return false;
        if (docNum != null ? !docNum.equals(documnent.docNum) : documnent.docNum != null) return false;
        return !(docDate != null ? !docDate.equals(documnent.docDate) : documnent.docDate != null);

    }

    @Override
    public int hashCode() {
        int result = docType != null ? docType.hashCode() : 0;
        result = 31 * result + (docNum != null ? docNum.hashCode() : 0);
        result = 31 * result + (docDate != null ? docDate.hashCode() : 0);
        return result;
    }
}
