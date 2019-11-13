package com.example.worktools.rxjava;



import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by xuhao on 2017/6/12.
 */
public interface RetrofitService {
    @GET()
    Observable<Object> get(@Url String url);
    @GET()
    Observable<Object> get(@Url String url,@QueryMap Map<String, String> map);
    @POST()
    @FormUrlEncoded
    Observable<Object> post(@Url String url,@FieldMap Map<String, String> map);
    @POST
    Observable<Object> uploadFile(@Url String url,@Body MultipartBody body);
}