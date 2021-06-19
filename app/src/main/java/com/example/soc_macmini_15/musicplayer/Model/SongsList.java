package com.example.soc_macmini_15.musicplayer.Model;

public class SongsList {

    private String title;
    private String artistTitle;
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public SongsList(String title, String subTitle, String path) {
        this.title = title;
        this.artistTitle = subTitle;
        this.path = path;

    }

    public String getSongsTitle() {
        return title;
    }

    public void setSongsTitle(String title) {
        this.title = title;
    }

    public String getArtistTitle() {
        return artistTitle;
    }

}
