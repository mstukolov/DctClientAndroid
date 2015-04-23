package com.dct.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.dct.core.GlobalApplication;
import com.dct.model.Setup;
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


    public Setup baseSetup(){
        Setup setup = new Setup("56", "Люсиновская", "Волкова А.", "192.168.0.114");

        return setup;
    }
    public String[] allShops(){
        List<String> shops = new ArrayList<>();
        shops.add("12 - HHHHH");
        shops.add("13 - AAAAA");
        shops.add("14 - ADFFF");
        shops.add("15 - ADFSFF");
        shops.add("16 - REWUWOI");

        String[] shopsArr = new String[shops.size()];
        shopsArr = shops.toArray(shopsArr);
        return shopsArr;

    }
}

