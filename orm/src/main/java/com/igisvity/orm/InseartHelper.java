package com.igisvity.orm;

import android.util.Log;
import android.widget.Toast;

import com.reactiveandroid.annotation.Column;
import com.reactiveandroid.query.Insert;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/12/25.
 */

public class InseartHelper {

    public interface CompleteListener {
        public void success(String s);
    }

    /**
     * 异步插入
     * @param jsonArray
     * @param T
     */
    public static void Insert(JSONArray jsonArray, final Class T) {
        Insert(jsonArray, T, null);
    }

    /**
     * 带返回的异步插入
     * @param jsonArray
     * @param T
     * @param completeListener
     */
    public static void Insert(JSONArray jsonArray, final Class T, final CompleteListener completeListener) {
        Flowable.just(jsonArray)
                .subscribeOn(Schedulers.newThread())
                .map(new Function<JSONArray, String>() {
                    @Override
                    public String apply(JSONArray jsonArray) throws Exception {
                        InsertData(jsonArray, T);
                        return "插入完毕";
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.d("database", s);
                        if (completeListener != null) {
                            completeListener.success(s);
                        }
                    }
                });
    }

    /**
     * 主线程插入
     * @param jsonArray
     * @param T
     */
    public static void InsertData(JSONArray jsonArray, Class T) {
        Field[] fields = T.getDeclaredFields();
        List<String> keys = new ArrayList<>();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            Column column = field.getAnnotation(Column.class);
            if (column != null && column.name() != null) {
                keys.add(column.name());
            }
        }
        String[] keyStrs = new String[keys.size()];
        keys.toArray(keyStrs);
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                List<String> itemKeys = new ArrayList<>();
                List<Object> itemDatas = new ArrayList<>();
                final JSONObject jsonObject = jsonArray.getJSONObject(i);
                for (int i1 = 0; i1 < keyStrs.length; i1++) {
                    String key1 = keyStrs[i1];
                    if (jsonObject.has(key1)) {
                        itemKeys.add(key1);
                        itemDatas.add(jsonObject.get(key1));
                    }
                }
                Insert.into(T)
                        .columns(itemKeys.toArray(new String[]{}))
                        .values(itemDatas.toArray())
                        .executeAsync()
                        .doOnError(new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Log.d("DatabaseFaild", throwable.getLocalizedMessage().toLowerCase().toString());
                            }
                        })
                        .doOnComplete(new Action() {
                            @Override
                            public void run() throws Exception {
                                Log.d("DatabaseSuccess", jsonObject.toString() + " 插入成功！");
                            }
                        }).subscribe();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
