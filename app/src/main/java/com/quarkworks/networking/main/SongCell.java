package com.quarkworks.networking.main;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.quarkworks.networking.R;
import com.quarkworks.networking.service.models.RSong;

/**
 * @author jacobamuchow@gmail.com (Jacob Muchow)
 */
public class SongCell extends RelativeLayout {
    private static final String TAG = SongCell.class.getSimpleName();

    /*
        References
     */
    private TextView nameTextView;
    private TextView artistTextView;

    public SongCell(Context context) {
        super(context);
        initialize();
    }

    public SongCell(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public SongCell(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    private void initialize() {
        LayoutInflater.from(getContext()).inflate(R.layout.song_cell, this);

        /**
         * Get view references
         */
        nameTextView = (TextView) findViewById(R.id.song_cell_name_id);
        artistTextView = (TextView) findViewById(R.id.song_cell_artist_id);
    }

    public void setViewData(RSong song) {

        nameTextView.setText(song.getName());
        artistTextView.setText(song.getArtist());
    }
}
