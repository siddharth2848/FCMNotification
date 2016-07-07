package com.pathania.gcmnotification;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Malhotra G on 7/7/2016.
 */
public class TimeDiff extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timediff);

        findDiff();
    }

    private void findDiff() {
        try {
            RequestParams params = new RequestParams();
            AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);
            client.get("http://indianroute.roms4all.com/time.php", params, new TextHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, String res) {
                            //Toast.makeText(getActivity(), "success" +res, Toast.LENGTH_LONG).show();

                            decodeJson(res);
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                            Toast.makeText(getApplicationContext(), "" + res, Toast.LENGTH_SHORT).show();
                        }
                    }
            );
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void decodeJson(String result) {
        String start = null;
        try {
            JSONArray jArr = new JSONArray(result);
            for (int count = 0; count < jArr.length(); count++) {
                JSONObject obj = jArr.getJSONObject(count);
                start = obj.getString("time");
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        start = "2016-06-03 01:04:50";
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(new Date());
        Log.e("timestamp", timeStamp);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);

        Date d1 = null;
        Date d2 = null;
        try {
            d1 = format.parse(start);
            d2 = format.parse(timeStamp);
        } catch (Exception e) {
            Log.e("date catch", e.toString());
        }
        // Get msec from each, and subtract.
        long diff = 0;
        try {
            diff = d2.getTime() - d1.getTime();
        } catch (Exception e) {
            Log.e("catch", e.toString());
        }
        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000);
        long diffDays = diffHours / 24;
        long diffWeeks = diffDays / 7;
        Log.e("Time in seconds ", diffSeconds + " seconds.");
        Log.e("Time in minutes ", diffMinutes + " minutes.");
        Log.e("Time in hours ", diffHours + " hours.");
        Log.e("Time in days ", diffDays + " days");
        Log.e("Time in weeks ", diffWeeks + " weeks");
    }

}
