package com.yangyi.music.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yangyi.music.entity.Lyric;
import com.yangyi.music.entity.Music;
import com.yangyi.music.entity.Url;
import com.yangyi.music.exception.MusicException;
import com.yangyi.music.request.KugouRequest;
import com.yangyi.music.service.KuGouService;
import com.yangyi.music.utils.CommonUtil;
import com.yangyi.music.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: TODO
 * @Author YangYi
 * @Date 2022/8/13 14:09
 */
@Service
public class KuGouServiceImpl implements KuGouService {

    @Autowired
    private KugouRequest kugouRequest;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 根据关键词获取歌单
     * @param keyWord
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public List<Music> getSearchByKeyWord(String keyWord, int page, int pageSize) {
        if (redisUtil.hasKey("G"+keyWord+page+pageSize)) {
            List<Music> list = (List<Music>) redisUtil.get("G" + keyWord + page + pageSize);
        }
        try {
            String searchByKeyWord = kugouRequest.getSearchByKeyWord(keyWord,1,10);
            JSONObject jsonObject = JSONObject.parseObject(searchByKeyWord);
            JSONObject data = jsonObject.getJSONObject("data");
            if (1 == jsonObject.getInteger("status")) {
                JSONArray jsonArray = data.getJSONArray("info");
                List<Music> musicList = new ArrayList<>();
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    Music music = new Music();
                    music.setArtist(obj.getString("singername"));
                    music.setRid(obj.getString("album_audio_id"));
                    music.setName(obj.getString("songname"));
                    musicList.add(music);
                }
                redisUtil.setCacheObject("G"+keyWord+page+pageSize,musicList,60*24);
                return musicList;
            }else {
                return null;
            }
        } catch (IOException e) {
            throw new MusicException(401,"网络异常，请稍后再试");
        }
    }

    /**
     * 获取歌曲的url
     * @param musicId
     * @return
     */
    @Override
    public Url getPlayUrl(String musicId) {
        if (redisUtil.hasKey("G"+musicId)) {
            Url url = (Url) redisUtil.get("G" + musicId);
        }
        try {
            Url url = new Url();
            String playUrl = kugouRequest.getPlayUrl(musicId);
            JSONObject jsonObject = JSONObject.parseObject(playUrl);
            if (jsonObject.getInteger("status") == 1) {
                JSONObject data = jsonObject.getJSONObject("data");
                List<Lyric> lyrics = CommonUtil.lyricHandler(data.getString("lyrics"));
                url.setLyric(lyrics);
                url.setUrl(data.getString("play_url"));
                url.setImg(data.getString("img"));
                redisUtil.setCacheObject("G" + musicId,url,30);
                return url;
            }else {
                return null;
            }
        } catch (IOException e) {
            throw new MusicException(401,"网络异常，请稍后再试");
        }
    }
}
