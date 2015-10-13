package com.quarkworks.networking.service.models;

import android.util.Log;

import com.quarkworks.networking.service.DataStore;
import com.quarkworks.networking.service.NetworkRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.realm.Realm;
import io.realm.RealmObject;

/**
 * @author jacobamuchow@gmail.com (Jacob Muchow)
 */
public class RSong extends RealmObject {
    private static final String TAG = RSong.class.getSimpleName();

    private static final class Urls {
        private static final String TOP_25 = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topsongs/limit=25/json";
    }

    private static final class JsonKeys {
        private static final String FEED = "feed";
        private static final String ENTRY = "entry";
        private static final String LABEL = "label";
        private static final String NAME = "im:name";
        private static final String ARTIST = "im:artist";
    }

    private static final class RealmKeys {
        private static final String NAME = "name";
        private static final String ARTIST = "artist";
    }

    private String name;
    private String artist;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public static void syncSongs() {

        NetworkRequest.Callback callback = new NetworkRequest.Callback() {
            @Override
            public void done(JSONObject jsonObject) {
                if(jsonObject == null) {
                    Log.e(TAG, "Error syncing top 25 songs");
                    return;
                }

                Realm realm = DataStore.getInstance().getRealm();
                realm.beginTransaction();
                realm.clear(RSong.class);

                //TODO: create or update songs, add primary key

                try {
                    JSONObject feedObject = jsonObject.getJSONObject(JsonKeys.FEED);

                    JSONArray entries = feedObject.getJSONArray(JsonKeys.ENTRY);

                    for(int i = 0 ; i < entries.length(); i++) {

                        try {
                            RSong song = realm.createObject(RSong.class);
                            mapJsonToSong(entries.getJSONObject(i), song);

                        } catch (JSONException e) {
                            Log.e(TAG, "Error parsing song object", e);
                        }
                    }

                    realm.commitTransaction();

                } catch (JSONException e) {
                    realm.cancelTransaction();
                    Log.e(TAG, "Error parsing json from url: " + Urls.TOP_25, e);
                }
            }
        };

        new NetworkRequest(Urls.TOP_25, callback).execute();
    }

    public static void mapJsonToSong(JSONObject jsonObject, RSong song) throws JSONException {

        /**
         * Name
         */
        JSONObject nameObject = jsonObject.optJSONObject(JsonKeys.NAME);
        if(nameObject != null) {
            song.setName(nameObject.getString(JsonKeys.LABEL));
        }

        /**
         * Artist
         */
        JSONObject artistObject = jsonObject.optJSONObject(JsonKeys.ARTIST);
        if(artistObject != null) {
            song.setArtist(artistObject.getString(JsonKeys.LABEL));
        }
    }
}
