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

    List<Shop> shops;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setup);
        context = this;
        initDatabase();

        director = (TextView) findViewById(R.id.editDirector);
        serverIP = (TextView) findViewById(R.id.editServerIP);
        spinner = (Spinner) findViewById(R.id.shops_spinner);
        save_btn = (Button) findViewById(R.id.save_setup_btn);

        director.setText(GlobalApplication.getInstance().dbHelper.getSetup().getShopDirector());
        serverIP.setText(GlobalApplication.getInstance().dbHelper.getSetup().getServerIP());

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, allShops());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setSelection(getIndex(spinner, GlobalApplication.getInstance().dbHelper.getSetup().getShopIndex()));

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

    public String[] allShops(){
        shops = GlobalApplication.getInstance().dbHelper.findAllShops();
        List<String> nameList = new ArrayList<>();
        for(Shop shop : shops){
            nameList.add(shop.getShopname());
        }
        String[] shopsArr = new String[nameList.size()];
        shopsArr = nameList.toArray(shopsArr);
        return shopsArr;

    }

    //private method of your class
    private int getIndex(Spinner spinner, String myString)
    {
        int index = 0;

        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                index = i;
                break;
            }
        }
        return index;
    }
    public void initDatabase(){
        Setup setup = new Setup();
        setup.setServerIP("192.168.0.114:8080");
        GlobalApplication.getInstance().dbHelper.addSetup(setup);
    }
}

