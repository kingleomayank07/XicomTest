package com.example.vishesh.xicomtest.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.vishesh.xicomtest.Adapter.RecyclerImageAdapter;
import com.example.vishesh.xicomtest.R;
import com.example.vishesh.xicomtest.model.Images;
import com.example.vishesh.xicomtest.model.Images1;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class RecyclerVieww extends AppCompatActivity {
private static final String url = "http://dev1.xicom.us/xttest/getdata.php";
    private final String url1 = "https://gist.githubusercontent.com/aws1994/f583d54e5af8e56173492d3f60dd5ebf/raw/c7796ba51d5a0d37fc756cf0fd14e54434c547bc/anime.json";
    private List<Images> List1;
    private RecyclerView.Adapter adapter;
    private RecyclerView userlist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        userlist = findViewById(R.id.recyclerview);
//        List1 = new ArrayList<>();
        jsonrequest();
    }
    private void jsonrequest() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        StringRequest request = new StringRequest(url1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("CODE",response);
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                Images1[] images1s = gson.fromJson(response,Images1[].class);
                SetupRecyclerView(images1s);
//                userlist.setAdapter(new RecyclerImageAdapter(RecyclerVieww.this,images1s));
                progressDialog.dismiss();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
    private void SetupRecyclerView(Images1[] List1) {
        userlist.setLayoutManager(new LinearLayoutManager(this));
       // userlist.setAdapter(new RecyclerImageAdapter(RecyclerVieww.this,List1));
    }
}
