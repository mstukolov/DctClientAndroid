package com.dct.view;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.*;
import com.dct.db.DbOpenHelper;
import com.example.TestAndroid.R;
import com.test.GenerateTestData;
import com.dct.model.DocumentItem;
import com.dct.model.Goods;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Stukolov on 10.04.2015.
 */
public class ArrivalActivity extends Activity implements View.OnClickListener{

    final String LOG_TAG = "myLogs";

    ScrollView scrollView;
    TableLayout tableLayout;
    EditText input;

    DbOpenHelper dbHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.arrival);

        scrollView = (ScrollView) findViewById(R.id.scrollView);
        tableLayout = (TableLayout) findViewById(R.id.TableLayout1);

        input = (EditText) findViewById(R.id.inputWindow);
        input.setOnClickListener(this);

        Button add_btn = (Button) findViewById(R.id.add_btn);
        add_btn.setOnClickListener(this);

        Button save_btn = (Button) findViewById(R.id.save);
        save_btn.setOnClickListener(this);

        // создаем объект для создания и управления версиями БД
        dbHelper = new DbOpenHelper(this);

    }

    @Override
    public void onClick(View v) {
        int rowNum = tableLayout.getChildCount();
        switch (v.getId()) {
            case R.id.add_btn:
                addRow(input.getText().toString(), rowNum);
                input.getText().clear();
                break;
            case R.id.inputWindow:
                addRow(input.getText().toString(), rowNum);
                input.getText().clear();
                break;
            case R.id.save:
                saveTableLayoutData();
                break;
        }
    }
    public void saveTableLayoutData(){


        List<DocumentItem> document = new ArrayList<DocumentItem>();

        int total = tableLayout.getChildCount();

        DbOpenHelper db = new DbOpenHelper(this);
        System.out.println("Inserting ..");

        for (int i = 1; i < total; i++) {
            TableRow mRow = (TableRow) tableLayout.getChildAt(i);
            TextView id = (TextView) mRow.getChildAt(0);
            TextView scu = (TextView) mRow.getChildAt(1);
            TextView barcode = (TextView) mRow.getChildAt(2);
            TextView qty = (TextView) mRow.getChildAt(3);

           /* document.add(
                    new DocumentItem(id.getText().toString(),
                            scu.getText().toString(),
                            barcode.getText().toString(),
                            qty.getText().toString())
            );
            db.addDocumentItem(
                    new DocumentItem(id.getText().toString(),
                            scu.getText().toString(),
                            barcode.getText().toString(),
                            qty.getText().toString()

                    )
            );*/

        }

        tableLayout.removeViews(1, total - 1);

    }
    public void addRow(String barcode, Integer rowNum){

        final Random random = new Random();
        int id = random.nextInt();


        final TableRow row = new TableRow(this);

        TextView t1, t2, t3, t4;


        //Кнопка удаления строки
        final Button remove_btn = new Button(this);
        remove_btn.setText("X");

        remove_btn.setId(rowNum);
        remove_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                tableLayout.removeView(row);
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

        t3 = new TextView(this);
        t3.setTextColor(Color.BLACK);

        t4 = new TextView(this);
        t4.setTextColor(Color.BLACK);



        t1.setText(String.valueOf(id));
        t2.setText(barcode + "_new");
        t3.setText(barcode);
        t4.setText(String.valueOf(1));


        t1.setTypeface(null, 1);
        t2.setTypeface(null, 1);
        t3.setTypeface(null, 1);
        t4.setTypeface(null, 1);

        t1.setTextSize(15);
        t2.setTextSize(15);
        t3.setTextSize(15);
        t4.setTextSize(15);

        t1.setWidth(100 * dip);
        t2.setWidth(150 * dip);
        t1.setPadding(20 * dip, 0, 20 * dip, 0);
        row.addView(t1);
        row.addView(t2);
        row.addView(t3);
        row.addView(t4);

        row.addView(remove_btn);

        tableLayout.addView(row, new TableLayout.LayoutParams(
                TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));

        scrollView.fullScroll(View.FOCUS_DOWN);

    }

    public void toastMsg(String msg) {

        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        toast.show();

    }

}