package com.dct.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.dct.core.GlobalApplication;
import com.example.TestAndroid.R;

public class StartActivity extends Activity implements View.OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Button arrival = (Button) findViewById(R.id.arrival);
        Button ship = (Button) findViewById(R.id.ship);
        Button invent = (Button) findViewById(R.id.invent);
        Button export = (Button) findViewById(R.id.exportData);
        Button setup_btn = (Button) findViewById(R.id.setup_btn);

        arrival.setOnClickListener(this);
        ship.setOnClickListener(this);
        invent.setOnClickListener(this);
        export.setOnClickListener(this);
        setup_btn.setOnClickListener(this);

        Log.d("helloFromGlobalApplication : ", GlobalApplication.getInstance().helloFromGlobalApplication);

    }

    @Override
     public void onClick(View v) {

        switch (v.getId()) {
            case R.id.arrival: goPage("arrival"); break;
            case R.id.ship: goPage("shipment"); break;
            case R.id.invent: goPage("invent"); break;
            case R.id.exportData: goPage("synch"); break;
            case R.id.setup_btn: goPage("setup"); break;


        }

    }

    public void toastMsg(String msg) {

        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        toast.show();

    }

    public void goPage(String _page){
        Intent intent = null;
        intent = new Intent(this, DocHeader.class);

        if(_page.equals("arrival")) intent.putExtra("doctype", "arrival");
        else if(_page.equals("shipment")) intent.putExtra("doctype", "shipment");
        else if(_page.equals("invent")) intent.putExtra("doctype", "invent");

        else if(_page.equals("synch")){intent = new Intent(this, SynchDataActivity.class);}
        else if(_page.equals("setup")){intent = new Intent(this, SetupActivity.class);}

        startActivity(intent);
    }


}
