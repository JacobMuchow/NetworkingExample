package com.quarkworks.networking.service;

import com.quarkworks.networking.main.NApplication;

import io.realm.Realm;

/**
 * @author jacobamuchow@gmail.com (Jacob Muchow)
 */
public class DataStore {
    private static final String TAG = DataStore.class.getSimpleName();

    private static DataStore dataStore;

    private Realm realm;

    private DataStore() {
        realm = Realm.getInstance(NApplication.getInstance());
    }

    public static DataStore getInstance() {
        if(dataStore == null) {
            dataStore = new DataStore();
        }
        return dataStore;
    }

    public Realm getRealm() {
        return realm;
    }
}
