package com.dct.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.dct.model.*;


import java.text.SimpleDateFormat;
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
    private static final String DOC_EXPORTED = "exported";


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
    private static final String KEY_INVENT = "scu";
    private static final String KEY_BARCODE_ID = "barcode";
    private static final String KEY_SIZE = "size";

    //Таблица настроек
    private static final String TABLE_SETUP = "setuptable";
    private static final String KEY_SHOPINDEX = "shopindex";
    private static final String KEY_SERVERIP = "serverip";
    private static final String KEY_SHOPDIR = "director";
    //Таблица магазинов
    private static final String TABLE_SHOP = "shoptable";
    private static final String KEY_SHOPNAME = "shopname";

    private static DbOpenHelper instance;


    public static synchronized DbOpenHelper getInstance(Context context)
    {
        if (instance == null)
            instance = new DbOpenHelper(context);

        return instance;
    }
    public DbOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_DOCUMENT_TABLE = "CREATE TABLE " + TABLE_DOCUMENTS + "("
                + DOC_NUM + " TEXT PRIMARY KEY,"
                + DOC_DATE + " TEXT,"
                + DOC_TYPE + " TEXT"
                + DOC_EXPORTED + " Boolean"
                + ")";

        String CREATE_DOCUMENTLINES_TABLE = "CREATE TABLE " + TABLE_DOCUMENT_LINES + "("
                //+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + KEY_SCU + " TEXT,"
                + KEY_SIZE + " TEXT,"
                + KEY_DOC_REF + " TEXT,"
                + KEY_QTY_FACT + " TEXT,"
                + KEY_QTY_PLAN + " TEXT" + ")";

        String CREATE_BARCODE_TABLE = "CREATE TABLE " + TABLE_BARCODE + "("
                + KEY_BARCODE_ID + " TEXT PRIMARY KEY,"
                + KEY_INVENT +  " TEXT,"
                + KEY_SIZE +  " TEXT"
                + ")";
        String CREATE_SETUP_TABLE = "CREATE TABLE " + TABLE_SETUP + "("
                + KEY_SHOPINDEX + " TEXT PRIMARY KEY,"
                + KEY_SERVERIP +  " TEXT,"
                + KEY_SHOPDIR +  " TEXT"
                + ")";

        String CREATE_SHOP_TABLE = "CREATE TABLE " + TABLE_SHOP + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + KEY_SHOPINDEX + " TEXT,"
                + KEY_SHOPNAME+ " TEXT" + ")";

        db.execSQL(CREATE_DOCUMENT_TABLE);
        db.execSQL(CREATE_DOCUMENTLINES_TABLE);
        db.execSQL(CREATE_BARCODE_TABLE);
        db.execSQL(CREATE_SETUP_TABLE);
        db.execSQL(CREATE_SHOP_TABLE);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DOCUMENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DOCUMENT_LINES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BARCODE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SETUP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHOP);

        onCreate(db);
    }

    public void addDocumentHeader(String docnum, String type){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DOC_NUM, docnum);
        values.put(DOC_DATE, new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()).toString());
        values.put(DOC_TYPE, type);

        db.insert(TABLE_DOCUMENTS, null, values);
        db.close();
    }
    public Boolean findDocumentHeader(String _docnum){
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT  *  FROM " + TABLE_DOCUMENTS + " where docnum = '" + _docnum + "'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            return true;
        }
        return false;
    }

    public List<Document> findAllDocumentsHeader() {
        List<Document> documentList = new ArrayList<Document>();
        String selectQuery = "SELECT  * FROM " + TABLE_DOCUMENTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Document document = new Document();
                document.setDocNum(cursor.getString(0));
                document.setDocDate(cursor.getString(1));
                document.setDocType(cursor.getString(2));

                documentList.add(document);
            } while (cursor.moveToNext());
        }

        return documentList;
    }
    public void deleteDocumentsHeader() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DOCUMENTS, null, null);
        db.close();
    }

    @Override
    public void addDocumentLine(DocumentLines line) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put(KEY_ID, line.hashCode());
        values.put(KEY_SCU, line.getScu());
        values.put(KEY_SIZE, line.getSize());
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
        List<DocumentLines> linesList = new ArrayList<DocumentLines>();
        String selectQuery = "SELECT  * FROM " + TABLE_DOCUMENT_LINES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                DocumentLines line = new DocumentLines();

                line.setScu(cursor.getString(1));
                line.setSize(cursor.getString(2));
                line.setDocRef(cursor.getString(3));
                line.setQty(cursor.getString(4));

                linesList.add(line);
            } while (cursor.moveToNext());
        }

        return linesList;
    }
    public synchronized List<DocumentLines> findDocumentHeaderLines(Document document) {
        List<DocumentLines> linesList = new ArrayList<DocumentLines>();
        String selectQuery = "SELECT  * FROM " + TABLE_DOCUMENT_LINES + " WHERE " + KEY_DOC_REF +"='" + document.getDocNum() + "'";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                DocumentLines line = new DocumentLines();

                line.setScu(cursor.getString(1));
                line.setSize(cursor.getString(2));
                line.setDocRef(cursor.getString(3));
                line.setQty(cursor.getString(4));

                linesList.add(line);
            } while (cursor.moveToNext());
        }

        return linesList;
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
        values.put(KEY_SIZE, line.getSize());
        return db.update(TABLE_DOCUMENT_LINES, values, KEY_ID + " = ?",
                new String[]{String.valueOf(line.getScu())});

    }

    @Override
    public void deleteDocumentLine(DocumentLines line) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DOCUMENT_LINES, KEY_ID + " = ?", new String[]{String.valueOf(line.getScu())});
        db.close();
    }

    @Override
    public void deleteLinesInDocument(String _docnum) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DOCUMENT_LINES, KEY_DOC_REF + " = ?", new String[]{String.valueOf(_docnum)});
        db.close();
    }

    @Override
    public void deleteAllLines() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DOCUMENT_LINES, null, null);
        db.close();
    }
    public void addItemBarcode(InventItemBarcode itemBarcode){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_BARCODE_ID, itemBarcode.getBarcode());
        values.put(KEY_INVENT, itemBarcode.getScu());
        values.put(KEY_SIZE, itemBarcode.getSize());
        db.insert(TABLE_BARCODE, null, values);
        db.close();
    }

    public void deleteAllItemBarcodes() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_BARCODE, null, null);
        db.close();
    }

    public InventItemBarcode findItemBarcode(String itemBarcode){
        String scu = null;
        String size = null;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            String selectQuery = "SELECT  *  FROM " + TABLE_BARCODE + " where barcode = '" + itemBarcode + "'";
            Cursor cursor = db.rawQuery(selectQuery, null);
            cursor.moveToFirst();
            scu = cursor.getString(cursor.getColumnIndex(KEY_INVENT));
            size = cursor.getString(cursor.getColumnIndex(KEY_SIZE));
            cursor.close();
        } catch(Exception e)
        {
            e.printStackTrace();
        }
        return new InventItemBarcode(scu, size);
    }

    public void addSetup(Setup setup) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_SHOPINDEX, setup.getShopIndex());
        values.put(KEY_SERVERIP, setup.getServerIP());
        values.put(KEY_SHOPDIR, setup.getShopDirector());
        db.insert(TABLE_SETUP, null, values);
        db.close();
    }

    public void deleteSetup() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SETUP, null, null);
        db.close();
    }

    public Setup getSetup() {
        Setup setup = new Setup();

        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_SETUP;
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
            setup.setShopIndex(cursor.getString(cursor.getColumnIndex(KEY_SHOPINDEX)));
            setup.setServerIP(cursor.getString(cursor.getColumnIndex(KEY_SERVERIP)));
            setup.setShopDirector(cursor.getString(cursor.getColumnIndex(KEY_SHOPDIR)));
        cursor.close();
        return setup;
    }

    public void addShop(Shop shop) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_SHOPINDEX, shop.getShopindex());
        values.put(KEY_SHOPNAME, shop.getShopname());

        db.insert(TABLE_SHOP, null, values);
        db.close();
    }

    public List<Shop> findAllShops() {
        List<Shop> shops = new ArrayList<Shop>();
        String selectQuery = "SELECT  * FROM " + TABLE_SHOP;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                String shopindex = cursor.getString(1);
                String shopname = cursor.getString(2);
                shops.add(new Shop(shopindex, shopname));

            } while (cursor.moveToNext());
        }

        return shops;
    }
    public void deleteShops() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SHOP, null, null);
        db.close();
    }

}

