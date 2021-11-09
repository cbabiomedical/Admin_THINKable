package com.example.adminthinkable.Model;

public class UploadGame {


    public String gameImage;
    public String gameName;
    public String gameId;





    public UploadGame(String gameImage, String gameName, String gameId) {
//        if (songTitle.trim().equals("")){
//            songTitle= "No Title";
//        }


        this.gameImage = gameImage;
        this.gameName = gameName;
        this.gameId = gameId;





    }



    public String getGameImage() {
        return gameImage;
    }

    public void setGameImage(String gameImage) {
        this.gameImage = gameImage;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    //    public UploadSong(String songsCategory, String title1, String toString) {




}
