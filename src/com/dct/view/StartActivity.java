package com.dct.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
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

        arrival.setOnClickListener(this);
        ship.setOnClickListener(this);
        invent.setOnClickListener(this);
        export.setOnClickListener(this);

    }

    @Override
     public void onClick(View v) {

        switch (v.getId()) {
            case R.id.arrival: goPage("arrival"); break;
            case R.id.ship: goPage("shipment"); break;
            case R.id.invent: goPage("inventory"); break;
            case R.id.exportData: goPage("export"); break;

        }

    }

    public void toastMsg(String msg) {

        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        toast.show();

    }

    public void goPage(String _page){
        Intent intent = null;

        if(_page.equals("arrival")){intent = new Intent(this, ArrivalActivity.class);}
        else if(_page.equals("shipment")){intent = new Intent(this, ShipmentActivity.class);}
        else if(_page.equals("inventory")){intent = new Intent(this, InventoryActivity.class);}
        else if(_page.equals("export")){intent = new Intent(this, ExportDataActivity.class);}

        startActivity(intent);
    }


}
