package com.yangyi.music.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yangyi.music.entity.Music;
import com.yangyi.music.exception.MusicException;
import com.yangyi.music.request.CloudMusicRequest;
import com.yangyi.music.service.CloudMusicService;
import com.yangyi.music.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: TODO
 * @Author YangYi
 * @Date 2022/8/20 13:25
 */
@Service
public class CloudMusicServiceImpl implements CloudMusicService {

    @Autowired
    private CloudMusicRequest cloudMusicRequest;

    @Autowired
    private RedisUtil redisUtil;
    @Override
    public List<Music> searchByKeyWord(String keyword, int begin, int end) {
        //从缓存获取数据
        if (redisUtil.hasKey("CM" + keyword + begin + end)) {
            return (List<Music>) redisUtil.get("CM" + keyword + begin + end);
        }
        JSONObject jsonObject = null;
        try {
            // 获取json数据
            String json = cloudMusicRequest.getSearchByKeyWord(keyword, begin, end);
//            jsonObject = JSONObject.parseObject(json);
        } catch (IOException e) {
            throw new MusicException(-1,"数据源异常");
        }
        //如果返回状态码不为200，则获取数据失败，抛出异常
        if (jsonObject == null || jsonObject.getInteger("code") != 200) {
            throw new MusicException(-1,"暂无更多数据");
        }
        //处理业务数据
        JSONArray songs = jsonObject.getJSONObject("result").getJSONArray("songs");
        List<Music> musicList = new ArrayList<>();
        for (int i = 0; i < songs.size(); i++) {
            JSONObject item = songs.getJSONObject(i);
            Music music = new Music();
            JSONArray artists = item.getJSONArray("artists");
            String artist = "";
            String img = "";
            for (int j = 0; j < artists.size(); j++) {
                String name = artists.getJSONObject(j).getString("name");
                artist = j == 0 ? name : artist + "&" + name;
                img = artists.getJSONObject(0).getString("img1v1Url");
            }
            music.setArtist(artist);
            music.setRid(item.getString("id"));
            music.setPic(img);
            music.setName(item.getString("name"));
            musicList.add(music);
        }
        //将数据储存到缓存并返回数据
        redisUtil.setCacheObject("CM" + keyword + begin + end, musicList, 24 * 60);
        return musicList;
    }
}
