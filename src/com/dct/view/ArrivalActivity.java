package com.dct.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.widget.*;
import com.dct.core.GlobalApplication;
import com.dct.db.DbOpenHelper;
import com.dct.model.DocumentLines;
import com.dct.model.InventItemBarcode;
import com.dct.zxing.IntentIntegrator;
import com.dct.zxing.IntentResult;
import com.example.TestAndroid.R;

/**
 * Created by Stukolov on 10.04.2015.
 */
public class ArrivalActivity extends Activity implements View.OnClickListener{

    final String LOG_TAG = "myLogs";

    ScrollView scrollView;
    TableLayout tableLayout;
    EditText input;

    DbOpenHelper dbHelper;

    String docnum;

    Integer itemsCount = 0;

    TextView qtyField;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.arrival);

        Intent intent = getIntent();
        docnum = intent.getStringExtra("docnum");


        scrollView = (ScrollView) findViewById(R.id.scrollView);
        tableLayout = (TableLayout) findViewById(R.id.TableLayout1);

        input = (EditText) findViewById(R.id.inputWindow);
        input.requestFocus();
        input.setOnKeyListener(new View.OnKeyListener() {

                                   @Override
                                   public boolean onKey(View v, int keyCode, KeyEvent event) {
                                       addRowByScan(tableLayout.getChildCount());
                                       return false;
                                   }
                               }
            );
        //input.setOnClickListener(this);

//        Button add_btn = (Button) findViewById(R.id.add_btn);
//        add_btn.setOnClickListener(this);

        Button save_btn = (Button) findViewById(R.id.save);
        save_btn.setOnClickListener(this);

        qtyField = (TextView) findViewById(R.id.qtyText);
        qtyField.setText("0");

        // создаем объект для создания и управления версиями БД
        dbHelper = new DbOpenHelper(this);

    }

    @Override
    public void onClick(View v) {
        int rowNum = tableLayout.getChildCount();
        switch (v.getId()) {
//            case R.id.add_btn:
//                if(checkControlSumEAN13(input.getText().toString())) {
//                    InventItemBarcode searchResult = GlobalApplication.getInstance().dbHelper.findItemBarcode(input.getText().toString());
//                    if(searchResult.getScu() != null)addRow(input.getText().toString(), rowNum);
//                    else
//                    {
//                        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
//                        alertDialog.setTitle("Ошибка");
//                        alertDialog.setMessage("Не известный штрих-код");
//                        alertDialog.show();
//
//                    }
//
//                }
//                else{toastMsg("Не правильный штрих-код");}
//                input.getText().clear();
//                break;
            //Работа с мобильного телефона
           /* case R.id.inputWindow:
                *//*if(checkControlSumEAN13(input.getText().toString())) {addRow(input.getText().toString(), rowNum);}
                else{toastMsg("Не правильный штрих-код");}
                *//*
                IntentIntegrator scanIntegrator = new IntentIntegrator(this);
                scanIntegrator.initiateScan();
                //input.getText().clear();
                break;*/
            case R.id.save:
                saveTableLayoutData();
                break;
        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();
            input.setText(scanContent);

        }else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }

    }

    public void saveTableLayoutData(){

        int total = tableLayout.getChildCount();

        System.out.println("Inserting ..");

        for (int i = 1; i < total; i++) {
            TableRow mRow = (TableRow) tableLayout.getChildAt(i);
            TextView scu = (TextView) mRow.getChildAt(0);
            TextView size = (TextView) mRow.getChildAt(1);

            TextView qty = (TextView) mRow.getChildAt(2);

            dbHelper.addDocumentLine(
                    new DocumentLines(
                            scu.getText().toString(),
                            size.getText().toString(),
                            "1",
                            docnum
                    )
            );

        }

        tableLayout.removeViews(1, total - 1);

        Intent  intent = new Intent(this, StartActivity.class);
        startActivity(intent);
    }
    public void addRow(String barcode, Integer rowNum){

        final TableRow row = new TableRow(this);

        TextView t1, t2;

        //Кнопка удаления строки
        final Button remove_btn = new Button(this);
        remove_btn.setText("X");

        remove_btn.setId(rowNum);
        remove_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                tableLayout.removeView(row);
                itemsCount--;
                qtyField.setText(itemsCount.toString());

            }
        });

        //Converting to dip unit
        int dip = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                (float) 1, getResources().getDisplayMetrics());
        //row = new TableRow(this);

        t1 = new TextView(this);
        t1.setTextColor(Color.BLACK);

        t2 = new TextView(this);
        t2.setTextColor(Color.BLACK);


        t1.setText(GlobalApplication.getInstance().dbHelper.findItemBarcode(barcode).getScu());
        t2.setText(GlobalApplication.getInstance().dbHelper.findItemBarcode(barcode).getSize());


        t1.setTypeface(null, 1);
        t2.setTypeface(null, 1);


        t1.setTextSize(15);
        t2.setTextSize(15);

        t1.setWidth(180 * dip);
        t2.setWidth(50 * dip);
        t1.setPadding(10 * dip, 0, 20 * dip, 0);
        row.addView(t1);
        row.addView(t2);

        row.addView(remove_btn);

        tableLayout.addView(row, new TableLayout.LayoutParams(
                TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));

        scrollView.fullScroll(View.FOCUS_DOWN);
        itemsCount++;
        qtyField.setText(itemsCount.toString());

    }
    public Boolean checkControlSumEAN13(String barcode){
        if (barcode.length() < 13) {return false;}

        else {
                char[] char12digits = barcode.toCharArray();
                int[] ean13 = {1,3};
                int sum = 0;
                for(int i = 0 ; i<char12digits.length-1; i++){
                    sum += Character.getNumericValue(char12digits[i]) * ean13[i%2];
                }

                int checksum = 10 - sum%10;
                if(checksum == 10){ checksum = 0; }
            int controlCount = Character.getNumericValue(char12digits[12]);

            if(checksum == controlCount){return true;}

            return false;
        }
    }

    public void addRowByScan(Integer _rowNum){
        if(checkControlSumEAN13(input.getText().toString())) {
            InventItemBarcode searchResult = GlobalApplication.getInstance().dbHelper.findItemBarcode(input.getText().toString());
            if(searchResult.getScu() != null) {
                addRow(input.getText().toString(), _rowNum);
                input.getText().clear();
                getScanSoundOK();
            }
                else
            {
                AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setTitle("Ошибка");
                alertDialog.setMessage("Не известный штрих-код");
                alertDialog.show();
                input.getText().clear();
                getScanSoundError();
            }
        }

    }
    public void toastMsg(String msg) {

        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        toast.show();

    }

    public void getScanSoundOK(){
        MediaPlayer player= MediaPlayer.create(ArrivalActivity.this, R.drawable.beep);
        player.start();
    }
    public void getScanSoundError(){
        MediaPlayer player= MediaPlayer.create(ArrivalActivity.this, R.drawable.oshibka);
        player.start();
    }

}