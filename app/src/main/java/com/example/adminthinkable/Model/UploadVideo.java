package com.example.adminthinkable.Model;

public class UploadVideo {

    public String videoId;
    public String videoName;
    public String videoImage;
    public String videoUrl;
    private String fav;
    private String songsCategory;





    public UploadVideo() {
    }

    public UploadVideo(String songsCategory,String videoId, String videoName, String videoImage, String videoUrl, String fav) {

        this.songsCategory =songsCategory;
        this.videoId = videoId;
        this.videoName = videoName;
        this.videoImage = videoImage;
        this.videoUrl = videoUrl;
        this.fav = fav;

    }

    public UploadVideo(String videoId, String videoName, String videoImage, String videoUrl) {
        this.videoId = videoId;
        this.videoName = videoName;
        this.videoImage = videoImage;
        this.videoUrl = videoUrl;
    }

    //    public UploadSong(String songsCategory, String title1, String toString) {


    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getVideoImage() {
        return videoImage;
    }

    public void setVideoImage(String videoImage) {
        this.videoImage = videoImage;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getFav() {
        return fav;
    }

    public void setFav(String fav) {
        this.fav = fav;
    }

    public String getSongsCategory() {
        return songsCategory;
    }

    public void setSongsCategory(String songsCategory) {
        this.songsCategory = songsCategory;
    }

    @Override
    public String toString() {
        return "UploadVideo{" +
                "videoId='" + videoId + '\'' +
                ", videoName='" + videoName + '\'' +
                ", videoImage='" + videoImage + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                ", fav='" + fav + '\'' +
                ", songsCategory='" + songsCategory + '\'' +
                '}';
    }
}
