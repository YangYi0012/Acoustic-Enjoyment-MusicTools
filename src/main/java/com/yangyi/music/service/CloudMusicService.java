package com.yangyi.music.service;

import com.yangyi.music.entity.Music;

import java.util.List;

/**
 * @Description: TODO
 * @Author YangYi
 * @Date 2022/8/20 13:24
 */
public interface CloudMusicService {
    List<Music> searchByKeyWord(String keyword,int begin,int end);
}
