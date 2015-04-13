package com.dct.db;

import com.dct.model.DocumentItem;

import java.util.List;

/**
 * Created by Stukolov on 11.04.2015.
 */
public interface IDatabaseHandler {
    public void addDocumentItem(DocumentItem item);
    public DocumentItem getDocumentItem(int id);
    public List<DocumentItem> getAllDocumentItems();
    public int getDocumentItemCount();
    public int updateDocumentItem(DocumentItem item);
    public void deleteDocumentItem(DocumentItem item);
    public void deleteAll();
}
