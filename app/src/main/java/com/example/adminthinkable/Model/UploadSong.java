package com.example.adminthinkable.Model;

public class UploadSong {



    public String songTitle1;
    public String imageUrl;

    public String name;
    public String songsCategory;
    public String id;


    public UploadSong(String songsCategory, String songTitle1, String imageUrl, String name, String id, String s) {
        this.songTitle1 = songTitle1;
        this.imageUrl = imageUrl;
        this.name = name;
        this.songsCategory = songsCategory;
        this.id = id;
    }


    public String getSongTitle1() {
        return songTitle1;
    }

    public void setSongTitle1(String songTitle1) {
        this.songTitle1 = songTitle1;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSongsCategory() {
        return songsCategory;
    }

    public void setSongsCategory(String songsCategory) {
        this.songsCategory = songsCategory;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "UploadSong{" +
                "songTitle1='" + songTitle1 + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", name='" + name + '\'' +
                ", songsCategory='" + songsCategory + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
