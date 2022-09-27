package com.yangyi.music.entity;

import java.util.Objects;

/**
 * @Description: TODO
 * @Author YangYi
 * @Date 2022/8/11 17:14
 */
public class SongList {
    private String id;
    private String name;
    private String viewCounts;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SongList songList = (SongList) o;
        return Objects.equals(id, songList.id);
    }


    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getViewCounts() {
        return viewCounts;
    }

    public void setViewCounts(String viewCounts) {
        this.viewCounts = viewCounts;
    }
}
