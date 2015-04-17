package com.dct.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.dct.model.DocumentItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stukolov on 11.04.2015.
 */
public class DbOpenHelper extends SQLiteOpenHelper implements IDatabaseHandler{
    final String LOG_TAG = "myLogs";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "dctDB";
    private static final String TABLE_DOCUMENTS = "doctable";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_BARCODE = "barcode";
    private static final String KEY_QTY = "qty";


    public DbOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_DOCUMENT_TABLE = "CREATE TABLE " + TABLE_DOCUMENTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_BARCODE + " TEXT, " + KEY_QTY + " TEXT" + ")";

        db.execSQL(CREATE_DOCUMENT_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DOCUMENTS);

        onCreate(db);
    }
    @Override
    public void addDocumentItem(DocumentItem item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, item.getScu());
        values.put(KEY_NAME, item.getScu());
        values.put(KEY_BARCODE, item.getBarcode());
        values.put(KEY_QTY, item.getQty());

        db.insert(TABLE_DOCUMENTS, null, values);
        db.close();
    }

    @Override
    public DocumentItem getDocumentItem(int id) {
        return null;
    }

    @Override
    public List<DocumentItem> getAllDocumentItems() {
        List<DocumentItem> docList = new ArrayList<DocumentItem>();
        String selectQuery = "SELECT  * FROM " + TABLE_DOCUMENTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                DocumentItem item = new DocumentItem();
                item.setScu(cursor.getString(0));
                item.setScu(cursor.getString(1));
                item.setBarcode(cursor.getString(2));
                item.setQty(cursor.getString(2));

                docList.add(item);
            } while (cursor.moveToNext());
        }

        return docList;
    }

    @Override
    public int getDocumentItemCount() {
        String countQuery = "SELECT  * FROM " + TABLE_DOCUMENTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }

    @Override
    public int updateDocumentItem(DocumentItem item) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, item.getScu());
        values.put(KEY_BARCODE, item.getBarcode());

        return db.update(TABLE_DOCUMENTS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(item.getScu()) });

    }

    @Override
    public void deleteDocumentItem(DocumentItem item) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DOCUMENTS, KEY_ID + " = ?", new String[] { String.valueOf(item.getScu()) });
        db.close();
    }

    @Override
    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DOCUMENTS, null, null);
        db.close();
    }


}

