package com.yangyi.music.service;

import com.yangyi.music.entity.Lyric;
import com.yangyi.music.entity.Music;
import com.yangyi.music.entity.Url;

import java.util.List;

/**
 * @Description: TODO
 * @Author YangYi
 * @Date 2022/8/13 14:03
 */
public interface MiGuService {

    List<Music> getSearchByKeyWord(String keyWord,int page,int pageSize);

    List<Lyric> getLyricById(String copyrightId);
}
