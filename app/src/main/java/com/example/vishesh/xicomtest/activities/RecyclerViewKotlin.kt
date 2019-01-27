package com.example.vishesh.xicomtest.activities

import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.example.vishesh.xicomtest.R
import com.example.vishesh.xicomtest.SingleTon.MySingleton
import com.example.vishesh.xicomtest.model.Images
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class RecyclerViewKotlin : AppCompatActivity(){
    val url = "http://dev1.xicom.us/xttest/getdata.php"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        jsonrequest()
    }

    private fun jsonrequest() {
        val request = object : StringRequest(Request.Method.POST, url, Response.Listener<String> { response ->
            try {
                val newobject = JSONObject(response)
                val jsonArray:JSONArray = newobject.getJSONArray("images")
                val nums = 0..jsonArray.length()
            }catch (e:JSONException){
                e.printStackTrace()
            }
        }, object : Response.ErrorListener {
            override fun onErrorResponse(error: VolleyError?) {

            }

        }){
            override fun getParams(): Map<String, String> {
                val params = HashMap<String,String>()
                params.put("user_id","108")
                params.put("offset","0")
                params.put("type","popular")
                return params

            }
        }
        MySingleton.getInstance(this).addToRequestQue(request)

    }

}