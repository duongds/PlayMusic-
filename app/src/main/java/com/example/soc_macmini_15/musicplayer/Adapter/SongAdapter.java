package com.example.soc_macmini_15.musicplayer.Adapter;

import android.content.Context;
import android.graphics.Movie;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.soc_macmini_15.musicplayer.Model.SongsList;
import com.example.soc_macmini_15.musicplayer.R;

import java.util.ArrayList;
import java.util.List;

public class SongAdapter extends ArrayAdapter<SongsList> implements Filterable{

    private Context mContext;
    private ArrayList<SongsList> songList = new ArrayList<>();

    public SongAdapter(Context mContext, ArrayList<SongsList> songList) {
        super(mContext, 0, songList);
        this.mContext = mContext;
        this.songList = songList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view ;
        view = convertView ;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.playlist_items, parent, false);
        }

        SongsList currentSong = songList.get(position);
        TextView tvTitle = view.findViewById(R.id.TextViewSongsTitle);
        TextView tvSubtitle = view.findViewById(R.id.TextViewArtistTitle);
        tvTitle.setText(currentSong.getSongsTitle());
        tvSubtitle.setText(currentSong.getArtistTitle());

        return view;
    }
}
