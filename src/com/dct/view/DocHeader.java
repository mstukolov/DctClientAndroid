package com.dct.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.dct.core.GlobalApplication;
import com.dct.db.DbOpenHelper;
import com.example.TestAndroid.R;

/**
 * Created by Stukolov on 19.04.2015.
 */
public class DocHeader extends Activity implements View.OnClickListener{

    public EditText docnum;
    public DbOpenHelper dbHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.docheader);

        docnum = (EditText) findViewById(R.id.docnum);

        Button create_btn = (Button) findViewById(R.id.create_document);
        create_btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.create_document:
                createDocument(docnum.getText().toString());
                Intent  intent = new Intent(this, ArrivalActivity.class);
                intent.putExtra("docnum", docnum.getText().toString());
                startActivity(intent);
        }
    }

    public void createDocument(String docnum){
        GlobalApplication.getInstance().dbHelper.addDocumentHeader(docnum, "arrival");
    }
    public void toastMsg(String msg) {

        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        toast.show();

    }

}