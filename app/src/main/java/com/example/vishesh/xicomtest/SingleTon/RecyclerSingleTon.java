package com.example.vishesh.xicomtest.SingleTon;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class RecyclerSingleTon {
    private static RecyclerSingleTon mInstance;
    private RequestQueue requestQueue;
    private static Context mCtx;
    private RecyclerSingleTon(Context context){
        mCtx = context;
        requestQueue = getRequestQueue();
    }

    public RequestQueue getRequestQueue(){
        if (requestQueue==null){
            requestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return requestQueue;
    }
    public static synchronized RecyclerSingleTon getmInstance(Context context){
        if (mInstance == null){
            mInstance = new RecyclerSingleTon(context);
        }
        return mInstance;
    }
    public<T> void addToRequestQue(Request<T> request){
        requestQueue.add(request);
}
}
