package com.yangyi.music.entity;

import java.util.List;

/**
 * @Description: TODO
 * @Author YangYi
 * @Date 2022/8/12 13:08
 */
public class Url {
    private String url;

    private String img;

    private List<Lyric> lyric;

    @Override
    public String toString() {
        return "Url{" +
                "url='" + url + '\'' +
                ", img='" + img + '\'' +
                ", lyric=" + lyric +
                '}';
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public List<Lyric> getLyric() {
        return lyric;
    }

    public void setLyric(List<Lyric> lyric) {
        this.lyric = lyric;
    }
}
