package com.dct.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.android.volley.*;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dct.connection.RequestTask;
import com.dct.connection.parse.JsonPostArrayRequest;
import com.dct.db.DbOpenHelper;
import com.dct.model.DocumentItem;
import com.example.TestAndroid.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.lang.reflect.Array;
import java.util.List;

/**
 * Created by Stukolov on 11.04.2015.
 */
public class ExportDataActivity extends Activity implements View.OnClickListener{

    ProgressBar progressBar;
    TextView mTextView;
    EditText mEditText;

    JSONObject jsonObject;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.export);

        Button export_btn = (Button) findViewById(R.id.export_run);
        export_btn.setOnClickListener(this);

        Button send_json = (Button) findViewById(R.id.send_json);
        send_json.setOnClickListener(this);
        Button send_arr_json = (Button) findViewById(R.id.send_arr_json);
        send_arr_json.setOnClickListener(this);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);





    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.export_run:

                sendMessageToServer();
                toastMsg("Run started");

            case R.id.send_json:
                try {
                    sendJSON();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            case R.id.send_arr_json:
                try {
                    sendArrayJSON();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
        }
    }

    public void toastMsg(String msg) {

        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        toast.show();

    }

    public void sendArrayJSON() throws JSONException {

        final  String url ="http://192.168.0.114:8080/dct/sendData/";
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonPostArrayRequest req = new JsonPostArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {

            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }, packJSONArray());

        queue.add(req);
    }
    public void sendJSON() throws JSONException {
        final  String url ="http://192.168.0.114:8080/dct/sendData/";

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, packJSON(),
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
                VolleyLog.e("Error: ", error.getMessage());
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