package com.example.adminthinkable.Model;

public class UploadImage {
    private String imageUrl;

    public UploadImage() {
    }

    public UploadImage(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
