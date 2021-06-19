package com.example.soc_macmini_15.musicplayer.Fragments;


import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.soc_macmini_15.musicplayer.Adapter.SongAdapter;
import com.example.soc_macmini_15.musicplayer.Model.SongsList;
import com.example.soc_macmini_15.musicplayer.R;

import java.util.ArrayList;

public class AllSongFragment extends ListFragment {


    private static ContentResolver contentResolver1;

    public ArrayList<SongsList> songsList;
    public ArrayList<SongsList> newList;

    private ListView listView;

    private createDataParse createDataParse;
    private ContentResolver contentResolver;

    public static Fragment getInstance(int position, ContentResolver mcontentResolver) {
        Bundle bundle = new Bundle();
        bundle.putInt("pos", position);
        AllSongFragment tabFragment = new AllSongFragment();
        tabFragment.setArguments(bundle);
        contentResolver1 = mcontentResolver;
        return tabFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        createDataParse = (createDataParse) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab, container, false);
    }

    // Hiển thị nội dung.
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        listView = view.findViewById(R.id.ListViewSong);
        contentResolver = contentResolver1;
        setContent();
    }

    // Đặt nội dung và sự kiện chọn item trên listview.
    public void setContent() {
        boolean searchedList = false;
        songsList = new ArrayList<>();
        newList = new ArrayList<>();
        getMusic();
        SongAdapter adapter = new SongAdapter(getContext(), songsList);
        if (!createDataParse.queryText().equals("")) {
            adapter = onQueryTextChange();
            adapter.notifyDataSetChanged();
            searchedList = true;
        } else {
            searchedList = false;
        }
        createDataParse.getLength(songsList.size());
        listView.setAdapter(adapter);

        final boolean finalSearchedList = searchedList;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!finalSearchedList) {
                    createDataParse.onDataPass(songsList.get(position).getSongsTitle(), songsList.get(position).getPath());
                    createDataParse.fullSongList(songsList, position);
                } else {
                    createDataParse.onDataPass(newList.get(position).getSongsTitle(), newList.get(position).getPath());
                    createDataParse.fullSongList(songsList, position);
                }
            }
        });
    }

    // Nhận nhạc từ bộ nhớ ngoài của thiết bị.
    public void getMusic() {
        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor songCursor = contentResolver.query(songUri, null, null, null, null);
        if (songCursor != null && songCursor.moveToFirst()) {
            int songTitle = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int songArtist = songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int songPath = songCursor.getColumnIndex(MediaStore.Audio.Media.DATA);

            do {
                songsList.add(new SongsList(songCursor.getString(songTitle), songCursor.getString(songArtist), songCursor.getString(songPath)));
            } while (songCursor.moveToNext());
            songCursor.close();
        }
    }


    // Thay đổi văn bản.
    public SongAdapter onQueryTextChange() {
        String text = createDataParse.queryText();
        for (SongsList songs : songsList) {
            String title = songs.getSongsTitle().toLowerCase();
            if (title.contains(text)) {
                newList.add(songs);
            }
        }
        return new SongAdapter(getContext(), newList);

    }

    public interface createDataParse {
        public void onDataPass(String name, String path);

        public void fullSongList(ArrayList<SongsList> songList, int position);

        public String queryText();

        public void currentSong(SongsList songsList);
        public void getLength(int length);
    }

}
