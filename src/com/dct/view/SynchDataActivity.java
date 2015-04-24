package com.dct.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dct.core.GlobalApplication;
import com.dct.model.DocumentLines;
import com.dct.model.Documnent;
import com.dct.model.InventItemBarcode;
import com.example.TestAndroid.R;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stukolov on 11.04.2015.
 */
public class SynchDataActivity extends Activity implements View.OnClickListener{

    EditText mEditText;

    JSONObject jsonObject;

    private ProgressDialog progress;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.synch);

        Button synchInventBarcode_btn = (Button) findViewById(R.id.synchInventBarcode);
        synchInventBarcode_btn.setOnClickListener(this);

        Button synchDocs_btn = (Button) findViewById(R.id.synchDocs);
        synchDocs_btn.setOnClickListener(this);

        Button synchShops_btn = (Button) findViewById(R.id.synchShops);
        synchShops_btn.setOnClickListener(this);

        progress = new ProgressDialog(this);
        progress.setMessage("Downloading Music :) ");
        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progress.setIndeterminate(true);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.synchInventBarcode:

                //sendMessageToServer();
                toastMsg("Run started");

            case R.id.synchDocs: synchItemBarcodes();

            case R.id.synchShops:
                try {
                    sendArrayJSON();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
        }
    }
    public void open(View view){
        progress.setMessage("Downloading Music :) ");
        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progress.setIndeterminate(true);
        progress.show();

        final int totalProgressTime = 100;

        final Thread t = new Thread(){

            @Override
            public void run(){

                int jumpTime = 0;
                while(jumpTime < totalProgressTime){
                    try {
                        sleep(200);
                        jumpTime += 5;
                        progress.setProgress(jumpTime);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }

            }
        };
        t.start();

    }
    public void synchItemBarcodes(){

        GlobalApplication.getInstance().dbHelper.deleteAllItemBarcodes();

        InventItemBarcode inventItemBarcode1 = new InventItemBarcode("2000158074364", "469003507-521", "36");
        InventItemBarcode inventItemBarcode2 = new InventItemBarcode("2000157935376", "469701521-521", "37");
        InventItemBarcode inventItemBarcode3 = new InventItemBarcode("2000157914395", "469601501-521", "39");
        InventItemBarcode inventItemBarcode4 = new InventItemBarcode("2000157935383", "469601501-521", "38");

        GlobalApplication.getInstance().dbHelper.addItemBarcode(inventItemBarcode1);
        GlobalApplication.getInstance().dbHelper.addItemBarcode(inventItemBarcode2);
        GlobalApplication.getInstance().dbHelper.addItemBarcode(inventItemBarcode3);
        GlobalApplication.getInstance().dbHelper.addItemBarcode(inventItemBarcode4);

    }
    public void toastMsg(String msg) {

        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        toast.show();

    }

    public void sendArrayJSON() throws JSONException {

        List<DocumentLines> lines = new ArrayList<DocumentLines>();
        lines.add(new DocumentLines("item1", "2222222222", "3", "3333222"));
        lines.add(new DocumentLines("item2", "111111111", "13", "3333222"));
        lines.add(new DocumentLines("item3", "5555555555", "32", "3333222"));



        List<Documnent> documnents = new ArrayList<Documnent>();
        documnents.add(new Documnent("arrival", "3333222", "12/03/2015", lines));
        documnents.add(new Documnent("shipment", "444", "15/03/2015"));
        documnents.add(new Documnent("invent", "1111", "22/03/2015"));

        String jsonRequest = new Gson().toJson(documnents);

        JSONObject obj = new JSONObject();
        obj.put("documents", jsonRequest);

        final  String urlArray = GlobalApplication.getInstance().serverAddress + "sendData/";

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, urlArray, obj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            VolleyLog.v("Response:%n %s", response.toString(4));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error Volley: ", error.getMessage());
            }
        });
        queue.add(req);
        toastMsg("JSON was sent");
    }
    public void sendJSON() throws JSONException {

        final  String urlItem ="http://192.168.0.114:8080/dct/sendItem/";

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, urlItem, packJSON(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            VolleyLog.v("Response:%n %s", response.toString(4));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error Volley: ", error.getMessage());
            }
        });
        queue.add(req);

        toastMsg("JSON was sent");
    }
    public JSONObject packJSON() throws JSONException {

        jsonObject = new JSONObject();
        jsonObject.put("scu", "SCU_153");
        jsonObject.put("barcode", new Integer(550100));
        jsonObject.put("qty", new Double(5));

        return  jsonObject;
    }

    public JSONArray packJSONArray() throws JSONException {

        JSONArray jsonArray = new JSONArray();

        JSONObject jsonObject1 = new JSONObject();
        jsonObject.put("scu", "SCU_153");
        jsonObject.put("barcode", new Integer(550100));
        jsonObject.put("qty", new Double(5));

        JSONObject jsonObject2 = new JSONObject();
        jsonObject.put("scu", "SCU_155");
        jsonObject.put("barcode", new Integer(120100));
        jsonObject.put("qty", new Double(15));

        JSONObject jsonObject3 = new JSONObject();
        jsonObject.put("scu", "SCU_233");
        jsonObject.put("barcode", new Integer(333100));
        jsonObject.put("qty", new Double(2));


        jsonArray.put(jsonObject1);
        jsonArray.put(jsonObject2);
        jsonArray.put(jsonObject3);

        return  jsonArray;
    }

    public void sendMessageToServer(){
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://192.168.0.114:8080/dct/displayMessage/" + mEditText.getText();

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        // mTextView.setText("Response is: "+ response.substring(0,500));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //mTextView.setText("That didn't work!");
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }
}