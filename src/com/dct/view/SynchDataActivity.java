package com.dct.view;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.android.volley.*;
import com.android.volley.toolbox.*;
import com.dct.core.GlobalApplication;
import com.dct.model.Document;
import com.dct.model.InventItemBarcode;
import com.dct.model.Shop;
import com.example.TestAndroid.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Stukolov on 11.04.2015.
 */
public class SynchDataActivity extends Activity implements View.OnClickListener{

    List<InventItemBarcode> items = new ArrayList<>();
    List<Shop> shops = new ArrayList<>();

    private TextView synchStatus;
    public String serverAddress
            = "http://" + GlobalApplication.getInstance().dbHelper.getSetup().getServerIP() + "/dct/";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.synch);

        Button synchInventBarcode_btn = (Button) findViewById(R.id.synchInventBarcode);
        synchInventBarcode_btn.setOnClickListener(this);

        Button remove_docs = (Button) findViewById(R.id.remove_docs);
        remove_docs.setOnClickListener(this);

        Button synchDocs_btn = (Button) findViewById(R.id.synchDocs);
        synchDocs_btn.setOnClickListener(this);

        Button synchShops_btn = (Button) findViewById(R.id.synchShops);
        synchShops_btn.setOnClickListener(this);

        synchStatus = (TextView) findViewById(R.id.synchStatus);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.synchInventBarcode:
                synchStatus.setText("Start downloading....");
                downloadInventItemBarcodeDataServer();

            case R.id.synchDocs:
                synchStatus.setText("Start uploading....");
                try {
                    uploadDocumentsToServerStringRequest();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            case R.id.remove_docs: removeAllDocuments();
            case R.id.synchShops:  downloadShopsServer();
        }
    }

    private void removeAllDocuments() {
       /* GlobalApplication.getInstance().dbHelper.deleteDocumentsHeader();
        GlobalApplication.getInstance().dbHelper.deleteAllLines();*/
    }

    public void startWriteDB(){
        int i = 0;
        GlobalApplication.getInstance().dbHelper.deleteAllItemBarcodes();
        for(InventItemBarcode itemBarcode : items){
            GlobalApplication.getInstance().dbHelper.addItemBarcode(itemBarcode);
            i++;
            synchStatus.setText("Total rows: " + i);
        }
        synchStatus.setText("Success downloading");
    }


    public void downloadInventItemBarcodeDataServer(){

        final  String url = GlobalApplication.getInstance().serverAddress + "inventItemBarcodeData/";
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("Get data from server: " + response.length());
                        Type type = new TypeToken<List<InventItemBarcode>>(){}.getType();
                        items = new Gson().fromJson(response, type);
                        startWriteDB();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error Volley DCT-SERVER: ", error.getMessage());
                synchStatus.setText("Error uploading");
            }
        });

        queue.add(stringRequest);
    }

    public void downloadShopsServer(){
        final  String url = serverAddress + "downloadShops/";
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("Get data from server: " + response.length());
                        Type type = new TypeToken<List<Shop>>(){}.getType();
                        shops = new Gson().fromJson(response, type);
                        //startWriteDB();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error Volley DCT-SERVER: ", error.getMessage());
                synchStatus.setText("Error uploading");
            }
        });

        queue.add(stringRequest);

    }
    public void uploadDocumentsToServerStringRequest() throws JSONException {

        final  String url = serverAddress + "uploadDocuments/";
        List<Document> documents = GlobalApplication.getInstance().dbHelper.findAllDocumentsHeader();

        for(Document header : documents){
            header.setLines(GlobalApplication.getInstance().dbHelper.findDocumentHeaderLines(header));
        }

        final String jsonRequest = new Gson().toJson(documents);
        final JSONObject obj = new JSONObject();
        obj.put("documents", jsonRequest);
        obj.put("shopindex", "56");

        RequestQueue queue = Volley.newRequestQueue(this);
        Response.Listener<String> jsonListerner = new Response.Listener<String>() {
            @Override
            public void onResponse(String list) {
                VolleyLog.e("MAXIM Volley Success:  ", list);
                synchStatus.setText("Success uploading");
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Volley Error", error.getMessage());
                synchStatus.setText("Error uploading....");
            }
        };

        StringRequest req = new StringRequest(Request.Method.POST, url, jsonListerner,errorListener){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("documents", jsonRequest);
                params.put("shopindex", "56");

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                // do not add anything here
                return headers;
            }
        };
        queue.add(req);
    }

    public void uploadDocumentsToServer() throws JSONException {

        final  String url = GlobalApplication.getInstance().serverAddress + "uploadDocuments/";
        List<Document> documents = GlobalApplication.getInstance().dbHelper.findAllDocumentsHeader();

        for(Document header : documents){
            header.setLines(GlobalApplication.getInstance().dbHelper.findDocumentHeaderLines(header));
        }

        String jsonRequest = new Gson().toJson(documents);
        JSONObject obj = new JSONObject();
        obj.put("documents", jsonRequest);
        obj.put("shopindex", "56");

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, obj,
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
                VolleyLog.e("Error Volley from DCT SERVER: ", error.getMessage());
                synchStatus.setText("Error uploading");     }
            }

        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put( "charset", "utf-8");
                return headers;
            }
          };
        queue.add(req);
    }
}