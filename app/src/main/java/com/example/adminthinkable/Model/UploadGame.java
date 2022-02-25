package com.example.adminthinkable.Model;

public class UploadGame {


    public String gameImage;
    public String gameName;
    public String gameId;
    public String songsCategory;


    public UploadGame(String gameImage, String gameName, String gameId, String songsCategory) {
        this.gameImage = gameImage;
        this.gameName = gameName;
        this.gameId = gameId;
        this.songsCategory = songsCategory;
    }

    public String getGameImage() {
        return gameImage;
    }

    public void setGameImage(String gameImage) {
        this.gameImage = gameImage;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getSongsCategory() {
        return songsCategory;
    }

    public void setSongsCategory(String songsCategory) {
        this.songsCategory = songsCategory;
    }

    @Override
    public String toString() {
        return "UploadGame{" +
                "gameImage='" + gameImage + '\'' +
                ", gameName='" + gameName + '\'' +
                ", gameId='" + gameId + '\'' +
                ", songsCategory='" + songsCategory + '\'' +
                '}';
    }
}
