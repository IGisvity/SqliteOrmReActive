package com.igisvity.sqliteormreactive;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.igisvity.orm.InseartHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        JSONArray jsonArray = new JSONArray();
        try {
            for (int i = 0; i < 1000; i++) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("userName", "userName" + i);
                jsonObject.put("weight", i + 22);
                jsonObject.put("userid","ID"+i);
                jsonArray.put(jsonObject);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        SimpleDateFormat formatter   =   new   SimpleDateFormat   ("yyyy年MM月dd日   HH:mm:ss");
        Date curDate =  new Date(System.currentTimeMillis());

        Log.d("timeStart",curDate.toString());

        InseartHelper.Insert(jsonArray, User.class, new InseartHelper.CompleteListener() {
            @Override
            public void success(String s) {
                Date curDate =  new Date(System.currentTimeMillis());
                Log.d("timeEnd",curDate.toString());
            }
        });

    }
}
