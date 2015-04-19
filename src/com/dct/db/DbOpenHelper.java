package com.dct.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.dct.model.DocumentLines;
import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Stukolov on 11.04.2015.
 */
public class DbOpenHelper extends SQLiteOpenHelper implements IDatabaseHandler{
    final String LOG_TAG = "myLogs";
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "dctDB";

    //Шапка документа
    private static final String TABLE_DOCUMENTS = "doctable";
    private static final String DOC_NUM = "docnum";
    private static final String DOC_DATE = "date";
    private static final String DOC_TYPE = "type";

    //Строки документа
    private static final String TABLE_DOCUMENT_LINES = "doclines";
    private static final String KEY_ID = "id";
    private static final String KEY_SCU = "scu";
    private static final String KEY_BARCODE = "barcode";
    private static final String KEY_QTY_FACT = "factqty";
    private static final String KEY_QTY_PLAN = "planqty";
    private static final String KEY_DOC_REF = "docref";


    //Таблица штрих-кодов
    private static final String TABLE_BARCODE = "barcodetable";
    private static final String KEY_INVENT = "name";
    private static final String KEY_BARCODE_ID = "barcode";


    public DbOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_DOCUMENT_TABLE = "CREATE TABLE " + TABLE_DOCUMENTS + "("
                + DOC_NUM + " INTEGER PRIMARY KEY,"
                + DOC_DATE + " TEXT,"
                + DOC_TYPE + " TEXT" + ")";

        String CREATE_DOCUMENTLINES_TABLE = "CREATE TABLE " + TABLE_DOCUMENT_LINES + "("
                //+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + KEY_SCU + " TEXT,"
                + KEY_BARCODE + " TEXT,"
                + KEY_DOC_REF + " TEXT,"
                + KEY_QTY_FACT + " TEXT,"
                + KEY_QTY_PLAN + " TEXT" + ")";

        String CREATE_BARCODE_TABLE = "CREATE TABLE " + TABLE_BARCODE + "("
                + KEY_BARCODE_ID + " TEXT PRIMARY KEY," + KEY_INVENT +  " TEXT" + ")";

        db.execSQL(CREATE_DOCUMENT_TABLE);
        db.execSQL(CREATE_DOCUMENTLINES_TABLE);
        db.execSQL(CREATE_BARCODE_TABLE);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DOCUMENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DOCUMENT_LINES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BARCODE);

        onCreate(db);
    }

    public void addDocumentHeader(String docnum, String type){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DOC_NUM, docnum);
        values.put(DOC_DATE, new Date().toString());
        values.put(DOC_TYPE, type);

        db.insert(TABLE_DOCUMENTS, null, values);
        db.close();
    }

    @Override
    public void addDocumentLine(DocumentLines line) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, line.hashCode());
        values.put(KEY_SCU, line.getScu());
        values.put(KEY_BARCODE, line.getBarcode());
        values.put(KEY_QTY_FACT, line.getQty());
        values.put(KEY_DOC_REF, line.getDocRef());

        db.insert(TABLE_DOCUMENT_LINES, null, values);
        db.close();
    }

    @Override
    public DocumentLines getDocumentLine(int id) {
        return null;
    }

    @Override
    public List<DocumentLines> getAllDocumentLines() {
        List<DocumentLines> docList = new ArrayList<DocumentLines>();
        String selectQuery = "SELECT  * FROM " + TABLE_DOCUMENT_LINES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                DocumentLines line = new DocumentLines();
                line.setScu(cursor.getString(0));
                line.setScu(cursor.getString(1));
                line.setBarcode(cursor.getString(2));
                line.setQty(cursor.getString(2));

                docList.add(line);
            } while (cursor.moveToNext());
        }

        return docList;
    }

    @Override
    public int getDocumentLinesCount() {
        String countQuery = "SELECT  * FROM " + TABLE_DOCUMENT_LINES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();
    }

    @Override
    public int updateDocumentLine(DocumentLines line) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_SCU, line.getScu());
        values.put(KEY_BARCODE, line.getBarcode());
        return db.update(TABLE_DOCUMENT_LINES, values, KEY_ID + " = ?",
                new String[] { String.valueOf(line.getScu()) });

    }

    @Override
    public void deleteDocumentLine(DocumentLines line) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DOCUMENT_LINES, KEY_ID + " = ?", new String[] { String.valueOf(line.getScu()) });
        db.close();
    }

    @Override
    public void deleteAllLines() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DOCUMENT_LINES, null, null);
        db.close();
    }


}

