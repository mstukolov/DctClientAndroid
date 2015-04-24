package com.dct.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.dct.core.GlobalApplication;
import com.dct.model.Setup;
import com.dct.model.Shop;
import com.example.TestAndroid.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stukolov on 10.04.2015.
 */
public class SetupActivity extends Activity {


    Context context;
    TextView director, serverIP;
    Spinner spinner;
    Button save_btn;

    List<String> shops;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setup);
        context = this;

        director = (TextView) findViewById(R.id.editDirector);
        serverIP = (TextView) findViewById(R.id.editServerIP);
        spinner = (Spinner) findViewById(R.id.shops_spinner);
        save_btn = (Button) findViewById(R.id.save_setup_btn);

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                 allShops());
        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {

                AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                alertDialog.setTitle("Сообщение");
                alertDialog.setMessage(allShops()[selectedItemPosition]);
                //alertDialog.show();
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GlobalApplication.getInstance().dbHelper.deleteShops();
                generateShops();

                Setup setup = new Setup(spinner.getSelectedItem().toString(),
                                        spinner.getSelectedItem().toString(),
                                        director.getText().toString(),
                                        serverIP.getText().toString()
                        );

                GlobalApplication.getInstance().dbHelper.deleteSetup();
                GlobalApplication.getInstance().dbHelper.addSetup(setup);

                AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                alertDialog.setTitle("Сообщение");
                alertDialog.setMessage("Настройки сохранены");
                alertDialog.show();
            }
        });

    }

    public String[] allShops(){
        shops = GlobalApplication.getInstance().dbHelper.findAllShops();

        String[] shopsArr = new String[shops.size()];
        shopsArr = shops.toArray(shopsArr);
        return shopsArr;

    }
    public void generateShops(){
        shops = new ArrayList<>();
        shops.add("Авиамоторная №47");
        shops.add("Магазин Академ-Парк СПб №67");
        shops.add("Магазин Бабушкинская №48\n");
        shops.add("Магазин Белорусская №23\n");
        shops.add("Магазин Василеостровский СПб №61\n");
        shops.add("Магазин Владимирский СПб №64\n");
        shops.add("Магазин Г. Кузнецова №10\n");
        shops.add("Магазин Гринвич Екат №65\n");
        shops.add("Магазин Золотой Вавилон №49\n");
        shops.add("Магазин Июнь СПб №60\n");
        shops.add("Магазин Королев №45\n");
        shops.add("Магазин Маросейка №57\n");

        for(String shop : shops){
            GlobalApplication.getInstance().dbHelper.addShop(shop);
        }
    }
}

