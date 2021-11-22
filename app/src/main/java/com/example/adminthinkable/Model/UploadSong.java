package com.example.adminthinkable.Model;

public class UploadSong {



    public String songTitle1;
    public String imageUrl;
    public String id;
    public String name;



    public UploadSong() {
    }

    public UploadSong(String songTitle1, String imageUrl, String id, String name) {
        this.imageUrl = imageUrl;
        this.id = id;
        this.name = name;
        this.songTitle1 = songTitle1;
    }

    public UploadSong(String songTitle1, String imageUrl, String name) {
        this.songTitle1 = songTitle1;
        this.imageUrl = imageUrl;
        this.name = name;
    }
    //    public UploadSong(String songsCategory, String title1, String toString) {


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSongTitle1() {
        return songTitle1;
    }

    public void setSongTitle1(String songTitle1) {
        this.songTitle1 = songTitle1;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }




    @Override
    public String toString() {
        return "UploadSong{" +
                "songTitle1='" + songTitle1 + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
