package com.quarkworks.networking.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.quarkworks.networking.R;
import com.quarkworks.networking.service.RealmQueries;
import com.quarkworks.networking.service.models.RSong;

import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    private RealmResults<RSong> songs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        RSong.syncSongs();

        songs = RealmQueries.withDefaultDefaultRealm().getSongs();

        RealmBaseAdapter<RSong> realmBaseAdapter = new RealmBaseAdapter<RSong>(this, songs, true) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                SongCell songCell = convertView != null ?
                        (SongCell) convertView : new SongCell(parent.getContext());

                songCell.setViewData(getItem(position));

                return songCell;
            }
        };

        ListView listView = (ListView) findViewById(R.id.main_list_view_id);
        listView.setAdapter(realmBaseAdapter);
    }

    private BaseAdapter songsAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return songs == null ? 0 : songs.size();
        }

        @Override
        public RSong getItem(int position) {
            return songs.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            SongCell songCell = convertView != null ?
                    (SongCell) convertView : new SongCell(parent.getContext());

            songCell.setViewData(getItem(position));

            return songCell;
        }
    };
}
