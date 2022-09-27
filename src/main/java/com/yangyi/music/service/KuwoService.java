package com.yangyi.music.service;

import com.alibaba.fastjson.JSONObject;
import com.yangyi.music.entity.*;

import java.util.List;
import java.util.Map;

/**
 * @Description: TODO
 * @Author YangYi
 * @Date 2022/8/4 18:25
 */
public interface KuwoService {

    Artist getByArtistIdFromKuWoMusic(int artistId);

    List<Music> getByKeyWordFromKuWoMusic(String keyWord, int pageNo, int pageNum);

    Artist getOtherArtistByArtistId(int artistId);

    Url getPlayUrl(String musicId);

    Map getMusicList(String bangId,int pn,int rn);

    List<Lyric> getLyric(String musicId);

    List<SongList> getRcmPlayList(String order,int pn,int rn);

    List<Music> getPlayListInfo(String pid,int pn ,int rn);

}
