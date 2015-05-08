package com.dct.db;


import com.dct.model.DocumentLines;

import java.util.List;

/**
 * Created by Stukolov on 11.04.2015.
 */
public interface IDatabaseHandler {
    public void addDocumentLine(DocumentLines line);
    public DocumentLines getDocumentLine(int id);
    public List<DocumentLines> getAllDocumentLines();
    public int getDocumentLinesCount();
    public int updateDocumentLine(DocumentLines line);
    public void deleteDocumentLine(DocumentLines line);
    public void deleteAllLines();
    public void deleteLinesInDocument(String docnum);
}
