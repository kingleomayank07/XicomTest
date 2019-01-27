package com.example.vishesh.xicomtest.Interface;

import com.example.vishesh.xicomtest.model.Images1;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Xicominterface{
    @FormUrlEncoded
    @POST("getdata.php")
    Call<List<Images1>>getdata(
            @Field("user_id")int id,
            @Field("offset")int offset,
            @Field("type")String popular
    );

}

