package com.pathania.gcmnotification;

import android.util.Log;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import java.io.IOException;

import cz.msebera.android.httpclient.Header;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Malhotra G on 6/25/2016.
 */


public class FirebaseInstanceIDService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {

        String token = FirebaseInstanceId.getInstance().getToken();
        //Log.e("token" , token);
        
        registerToken(token);
    }

    private void registerToken(String token) {

        //Log.e("token" , token);

        try {
            SyncHttpClient client = new SyncHttpClient();
            /*
            * Bind parameters here
            * */
            RequestParams params = new RequestParams();
            try {
                params.put("device_token", token);
                Log.e("token" , token);

            } catch (Exception e) {
                Log.e("catch1" , e.toString());
            }
            client.post(this, "http://indianroute.roms4all.com/register_fcm.php", params, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    Log.e("success", responseString);
                }
            });
        } catch (Exception e) {
            Log.e("catch2" , e.toString());
        }

        /*OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("Token",token)
                .build();

        Request request = new Request.Builder()
                .url("http://wepool.roms4all.com/register_fcm.php")
                .post(body)
                .build();

        try {
            client.newCall(request).execute();
        } catch (IOException e) {
            Log.e("catch" , e.toString());
        }*/
    }
}
