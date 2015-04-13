package com.dct.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.dct.connection.RequestTask;
import com.dct.db.DbOpenHelper;
import com.dct.model.DocumentItem;
import com.example.TestAndroid.R;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.CommonsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by Stukolov on 11.04.2015.
 */
public class ExportDataActivity extends Activity implements View.OnClickListener{

    ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.export);

        Button export_btn = (Button) findViewById(R.id.export_run);
        export_btn.setOnClickListener(this);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);


    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.export_run:
                /*DbOpenHelper db = new DbOpenHelper(this);
                List<DocumentItem> items = db.getAllDocumentItems();
                for (DocumentItem cn : items) {
                    String log = "Id: " + cn.getId()+" ,Name: " + cn.getName() + " ,Phone: " + cn.getBarcode();
                    System.out.println(log);
                }*/
                //toastMsg("Export started!");
                progressBar.setVisibility(View.VISIBLE);
                //тут указываем куда будем конектится
                //new RequestTask().execute("http://192.168.1.4:8080/hello");
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.setRequestFactory(new CommonsClientHttpRequestFactory());
                String url = "http://192.168.1.4:8080/hello";

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                /*HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);
                restTemplate.getForObject("http://192.168.1.4:8080/hello");*/

        }
    }

    public void toastMsg(String msg) {

        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        toast.show();

    }
}