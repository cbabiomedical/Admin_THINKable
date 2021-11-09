package com.example.adminthinkable.Model;

public class UploadMeditate {
    public String Mp3Title;
    public String meditateImage;
    public String mediateId;
    public String meditateName;




    public UploadMeditate(String Mp3Title,String meditateImage,String mediateId,String meditateName) {
//        if (songTitle.trim().equals("")){
//            songTitle= "No Title";
//        }

        this.Mp3Title = Mp3Title;
        this.meditateImage = meditateImage;
        this.mediateId =mediateId;
        this.meditateName=meditateName;




    }

    public String getMp3Title() {
        return Mp3Title;
    }

    public void setMp3Title(String mp3Title) {
        Mp3Title = mp3Title;
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



}
