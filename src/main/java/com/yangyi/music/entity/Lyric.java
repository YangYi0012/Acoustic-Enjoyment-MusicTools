package com.yangyi.music.entity;

/**
 * @Description: TODO
 * @Author YangYi
 * @Date 2022/8/6 17:37
 */
public class Lyric {
    private String lineLyric;
    private String time;

    public Lyric() {
    }

    @Override
    public String toString() {
        return "Lyric{" +
                "lineLyric='" + lineLyric + '\'' +
                ", time='" + time + '\'' +
                '}';
    }

    public Lyric(String lineLyric, String time) {
        this.lineLyric = lineLyric;
        this.time = time;
    }

    public String getLineLyric() {
        return lineLyric;
    }

    public void setLineLyric(String lineLyric) {
        this.lineLyric = lineLyric;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
