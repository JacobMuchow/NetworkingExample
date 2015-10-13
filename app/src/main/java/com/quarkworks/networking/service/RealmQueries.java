package com.quarkworks.networking.service;

import com.quarkworks.networking.service.models.RSong;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * @author jacobamuchow@gmail.com (Jacob Muchow)
 */
public class RealmQueries {

    private Realm realm;

    public RealmQueries(Realm realm) {
        this.realm = realm;
    }

    public static RealmQueries withDefaultDefaultRealm() {
        return new RealmQueries(DataStore.getInstance().getRealm());
    }

    public RealmResults<RSong> getSongs() {
        return realm.where(RSong.class).findAll();
    }
}
