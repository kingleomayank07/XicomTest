package com.example.vishesh.xicomtest.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.vishesh.xicomtest.Adapter.RecyclerImageAdapter;
import com.example.vishesh.xicomtest.R;
import com.example.vishesh.xicomtest.model.Images;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.android.volley.Request.Method.POST;

public class XicomTest extends AppCompatActivity {
        private static final String url = "http://dev1.xicom.us/xttest/getdata.php";
        private List<Images> list2;
        private Button button;
        private int offset_value=0;
        private ProgressBar progressBar;
        private RecyclerView recyclerView;
        private String user_id="108";

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_xicom_test);
            button= findViewById(R.id.btnload);
            recyclerView = findViewById(R.id.xicomrecycle);
            progressBar = findViewById(R.id.progress);
            list2 = new ArrayList<>();
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    jsonrequest2();
                }
            });
            jsonrequest1(); }

        private void jsonrequest1() {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();

             StringRequest request = new StringRequest(POST,url,new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    try {
                        JSONObject object = new JSONObject(response);
                        JSONArray jsonArray = object.getJSONArray("images");
                        for (int i = 0; i<jsonArray.length(); i++){
                            JSONObject object1 = jsonArray.getJSONObject(i);
                            Images images = new Images();
                            images.setImg_url(object1.getString("xt_image"));
                            list2.add(images);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    setuprecycler(list2);
        }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("NEW_CODE",error.getMessage());
                    progressDialog.dismiss();
                }
            })
             {
                 @Override
                 protected Map<String, String> getParams() {
                     Map<String,String>params = new HashMap<>();
                     params.put("user_id",user_id);
                     params.put("offset", String.valueOf(offset_value));
                     params.put("type","popular");
                     return params;
                 }
             };
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(request);
        }
        private void jsonrequest2() {
            progressBar.setVisibility(View.VISIBLE);
        StringRequest request1 = new StringRequest(POST,url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("1CODE",response);
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray jsonArray = object.getJSONArray("images");
                    for (int i = 0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Images images = new Images();
                        images.setImg_url(jsonObject.getString("xt_image"));
                        list2.add(images);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                progressBar.setVisibility(View.GONE);
                setuprecycler(list2);
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("NEW_CODE",error.getMessage());
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String,String>params = new HashMap<>();
                params.put("user_id",user_id);
                params.put("offset", String.valueOf(++offset_value));
                params.put("type","popular");
                return params;
            }
        };
        RequestQueue queue1 = Volley.newRequestQueue(XicomTest.this);
        queue1.add(request1);
    }
        private void setuprecycler(List<Images>list2) {
            RecyclerImageAdapter myAdapter = new RecyclerImageAdapter(this,list2);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(myAdapter);
        }
}