package com.example.hoctienganh;

public class VideoYoutube {
    private String title;
    private String idVideo;
    private int idThumbnail;

    public VideoYoutube(String title, String idVideo, int idThumbnail) {
        this.title = title;
        this.idVideo = idVideo;
        this.idThumbnail = idThumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIdVideo() {
        return idVideo;
    }

    public void setIdVideo(String idVideo) {
        this.idVideo = idVideo;
    }

    public int getIdThumbnail() {
        return idThumbnail;
    }

    public void setIdThumbnail(int idThumbnail) {
        this.idThumbnail = idThumbnail;
    }
}

