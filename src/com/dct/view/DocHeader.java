package com.dct.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.dct.core.GlobalApplication;
import com.dct.db.DbOpenHelper;
import com.dct.zxing.IntentIntegrator;
import com.dct.zxing.IntentResult;
import com.example.TestAndroid.R;

/**
 * Created by Stukolov on 19.04.2015.
 */
public class DocHeader extends Activity implements View.OnClickListener{

    public EditText docnum;
    public TextView doctypeWidget;
    public DbOpenHelper dbHelper;

    public Intent intent;
    Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.docheader);
        context = this;

        //Тип документа из startactivity
        intent = getIntent();
        doctypeWidget = (TextView) findViewById(R.id.docType);
        doctypeWidget.setText(setDocHeader(intent.getStringExtra("doctype")));

        docnum = (EditText) findViewById(R.id.docnum);
        docnum.setOnClickListener(this);

        Button start_btn = (Button) findViewById(R.id.start_document);
        start_btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_document: {
                if (!docnum.getText().toString().equals("")) {
                    checkDocumentCreation(docnum.getText().toString());
                } else {
                    toastMsg("Set document header number");
                }
            }
            case R.id.docnum: {
                IntentIntegrator scanIntegrator = new IntentIntegrator(this);
                scanIntegrator.initiateScan();
                break;
            }
        }
    }
    public void checkDocumentCreation(final String docnum){

        if(GlobalApplication.getInstance().dbHelper.findDocumentHeader(docnum)){

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Документ существует");
            builder.setMessage("Начать заново?");
            builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() { // Кнопка ОК
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startDeleteDocumentLines(docnum);
                    startCreateDocument(docnum);
                    dialog.dismiss(); // Отпускает диалоговое окно
                }
            });
            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() { // Кнопка ОК
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startCreateDocument(docnum);
                    dialog.dismiss(); // Отпускает диалоговое окно
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }else{
            startCreateDocument(docnum);
        }

    }
    public void startDeleteDocumentLines(String _docnum){
        GlobalApplication.getInstance().dbHelper.deleteLinesInDocument(_docnum);
    }
    public void startCreateDocument(String _number){
        createDocument(_number);
        Intent intent = new Intent(this, ArrivalActivity.class);
        intent.putExtra("docnum", _number);
        startActivity(intent);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            String scanContent = scanningResult.getContents();
            docnum.setText(scanContent);
        }

    }
    public void createDocument(String _docnum){
        Boolean isDocExist = GlobalApplication.getInstance().dbHelper.findDocumentHeader(_docnum);
        if(!isDocExist)
        {
            GlobalApplication.getInstance().dbHelper.addDocumentHeader(_docnum, intent.getStringExtra("doctype"));
        }else {toastMsg("Документ уже существует");}

    }
    public void toastMsg(String msg) {

        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        toast.show();

    }
    public String setDocHeader(String str){
        switch (str){
            case "arrival": return "Приход";
            case "shipment": return "Отгрузка";
            case "setup": return "Инвентаризация";
        }
        return null;
    }

}