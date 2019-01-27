package com.example.vishesh.xicomtest.SingleTon;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MySingleton {
    private static MySingleton mInstance;
    private RequestQueue requestQueue;
    private static Context context;

    private MySingleton(Context mCtx)
    {
        context = mCtx;
        requestQueue = getRequestQueue();
    }

    private RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
            return requestQueue;
       }
       return requestQueue;
    }
    public static synchronized MySingleton getInstance(Context mCtx){
        if (mInstance == null){
        mInstance = new MySingleton(mCtx); }
        return mInstance;
    }
    public<T> void addToRequestQue(Request<T> request){
        getRequestQueue().add(request);
    }
    }