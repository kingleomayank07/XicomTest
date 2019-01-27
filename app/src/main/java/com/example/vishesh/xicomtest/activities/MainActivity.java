package com.example.vishesh.xicomtest.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.example.vishesh.xicomtest.Adapter.CustomVolleyRequestQueue;
import com.example.vishesh.xicomtest.R;
import com.example.vishesh.xicomtest.SingleTon.MySingleton;
import com.example.vishesh.xicomtest.model.Images1;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private TextView edtname,edtLastname,edtemail,edtphone;
    private static final String uploadUrl = "http://dev1.xicom.us/xttest/savedata.php";
    private final int IMAGE_REQUEST = 1000;
    private Bitmap bitmap;
    private NetworkImageView imageView1;
    private ImageLoader mImageLoader;
    Button button,uploadImage;
    private static final int permission_request_code = 1;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.upload);
        edtname = findViewById(R.id.edt1);
        edtLastname = findViewById(R.id.edt2);
        edtemail = findViewById(R.id.edt3);
        edtphone = findViewById(R.id.edt4);
        imageView1 = findViewById(R.id.img1);
        mImageLoader = CustomVolleyRequestQueue.getInstance(this.getApplicationContext())
                .getImageLoader();
        Intent intent = getIntent();
        final String url = intent.getStringExtra("img_id");
        mImageLoader.get(url, ImageLoader.getImageListener(imageView1,
                R.mipmap.ic_launcher, android.R.drawable
                        .ic_dialog_alert));
        imageView1.setImageUrl(url, mImageLoader);
        uploadImage = findViewById(R.id.submit1);
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImageNow();
            }
        });
        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED)
            requestPermissions(new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            },permission_request_code);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });


    }

    private void selectImage() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i,"pick Image"),IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==IMAGE_REQUEST){
            Uri path = null;
            if (data != null) {
                path = data.getData();
            }
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                imageView1.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadImageNow()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, uploadUrl,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(MainActivity.this,response,Toast.LENGTH_SHORT).show();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Toast.makeText(MainActivity.this,response,Toast.LENGTH_SHORT).show();
                    edtname.setText("");
                    edtemail.setText("");
                    edtLastname.setText("");
                    edtphone.setText("");
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                Log.d("TAG7",error.getMessage());
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String,String>params = new HashMap<>();
                String string = getStringImage(bitmap);
                params.put("first_name",edtname.getText().toString().trim());
                params.put("last_name",edtLastname.getText().toString().trim());
                params.put("email",edtemail.getText().toString().trim());
                params.put("user_image", string);
                return params;
            }
        };
        MySingleton.getInstance(MainActivity.this).addToRequestQue(stringRequest);
    }

    public String getStringImage(Bitmap bitmap){
        Log.i("MyHitesh",""+bitmap);
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp=Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }
        @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case permission_request_code:
                {
                    if (grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
                        Toast.makeText(this,"Permission Granted",Toast.LENGTH_SHORT).show();
                        else Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}

