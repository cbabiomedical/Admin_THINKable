package com.example.adminthinkable.Model;

public class UploadMeditate {
    public String url;
    public String meditateImage;
    public String mediateId;
    public String meditateName;




    public UploadMeditate(String url,String meditateImage,String mediateId,String meditateName) {
//        if (songTitle.trim().equals("")){
//            songTitle= "No Title";
//        }

        this.url = url;
        this.meditateImage = meditateImage;
        this.mediateId =mediateId;
        this.meditateName=meditateName;




    }

    public UploadMeditate() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMeditateImage() {
        return meditateImage;
    }

    public void setMeditateImage(String meditateImage) {
        this.meditateImage = meditateImage;
    }

    public String getMediateId() {
        return mediateId;
    }

    public void setMediateId(String mediateId) {
        this.mediateId = mediateId;
    }

    public String getMeditateName() {
        return meditateName;
    }

    public void setMeditateName(String meditateName) {
        this.meditateName = meditateName;
    }

    //    public UploadSong(String songsCategory, String title1, String toString) {


    @Override
    public String toString() {
        return "UploadMeditate{" +
                "url='" + url + '\'' +
                ", meditateImage='" + meditateImage + '\'' +
                ", mediateId='" + mediateId + '\'' +
                ", meditateName='" + meditateName + '\'' +
                '}';
    }
}
