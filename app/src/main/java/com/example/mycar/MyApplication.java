package com.example.mycar;

import android.app.Application;
import com.example.mycar.Model.RealmMigrationManager;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        //Realm.deleteRealm(Realm.getDefaultConfiguration());

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .schemaVersion(1)
                .migration( new RealmMigrationManager() )
                .build();

        Realm.setDefaultConfiguration( realmConfiguration );
        Realm.getInstance( realmConfiguration );

    }
}
