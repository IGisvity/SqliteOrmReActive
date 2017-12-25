package com.igisvity.sqliteormreactive;

import android.app.Application;
import com.reactiveandroid.ReActiveAndroid;
import com.reactiveandroid.ReActiveConfig;
import com.reactiveandroid.internal.database.DatabaseConfig;

/**
 * Created by Administrator on 2017/12/25.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        DatabaseConfig appDatabase = new DatabaseConfig.Builder(AppData.class)
                .addModelClasses(User.class)
                .disableMigrationsChecking()
                .build();

        ReActiveAndroid.init(new ReActiveConfig.Builder(this)
                .addDatabaseConfigs(appDatabase)
                .build());
    }
}
