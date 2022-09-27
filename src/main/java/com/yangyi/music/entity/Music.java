package com.yangyi.music.entity;

import java.util.Objects;

public class Music {
//    private String musicRid;

    private String artist;

    private String pic;

//    private Integer isStar;

    private String rid;

//    private Integer duration;

//    private Boolean hasLossless;

//    private Integer hasMv;

//    private String album;

//    private String albumId;

//    private Integer artistId;

    private String albumPic;

//    private String songTimeMinutes;

    private Boolean isListenFee;

//    private String pic120;

    private String name;

//    private Integer online;

//    private Integer num;

    private String copyrightId;

    @Override
    public String toString() {
        return "Music{" +
                "artist='" + artist + '\'' +
                ", pic='" + pic + '\'' +
                ", rid='" + rid + '\'' +
                ", albumPic='" + albumPic + '\'' +
                ", isListenFee=" + isListenFee +
                ", name='" + name + '\'' +
                ", copyrightId='" + copyrightId + '\'' +
                '}';
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getAlbumPic() {
        return albumPic;
    }

    public void setAlbumPic(String albumPic) {
        this.albumPic = albumPic;
    }

    public Boolean getListenFee() {
        return isListenFee;
    }

    public void setListenFee(Boolean listenFee) {
        isListenFee = listenFee;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCopyrightId() {
        return copyrightId;
    }

    public void setCopyrightId(String copyrightId) {
        this.copyrightId = copyrightId;
    }
}