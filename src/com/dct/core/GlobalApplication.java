package com.dct.core;

import android.app.Application;
import com.dct.db.DbOpenHelper;

/**
 * Created by Stukolov on 17.04.2015.
 */
public class GlobalApplication extends Application {

    public String helloFromGlobalApplication = "Hello From GlobalApplication";
    public String sendDocuments = "Export Documents";

    //connection setup
    public String serverAddress = "http://192.168.0.114:8080/dct/";

    //database initialization
    public DbOpenHelper dbHelper;

    private static GlobalApplication singleton;

    public static GlobalApplication getInstance() {
        return singleton;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;

        // создаем объект для создания и управления версиями БД
        dbHelper = new DbOpenHelper(this);

    }
}
