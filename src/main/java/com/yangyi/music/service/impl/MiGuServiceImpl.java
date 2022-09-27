package com.yangyi.music.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yangyi.music.entity.Lyric;
import com.yangyi.music.entity.Music;
import com.yangyi.music.request.MiGuRequest;
import com.yangyi.music.service.MiGuService;
import com.yangyi.music.utils.CommonUtil;
import com.yangyi.music.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: TODO
 * @Author YangYi
 * @Date 2022/8/14 12:36
 */
@Service
public class MiGuServiceImpl implements MiGuService {

    @Autowired
    private MiGuRequest miGuRequest;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public List<Music> getSearchByKeyWord(String keyWord, int page, int pageSize) {
        if (redisUtil.hasKey("MK"+keyWord+page+pageSize)) {
            return (List<Music>) redisUtil.get("MK"+keyWord+page+pageSize);
        }
        try {
            String json = miGuRequest.getPlayListInfo(keyWord, page, pageSize);
            JSONObject jsonObject = JSONObject.parseObject(json);
            if ("成功".equals(jsonObject.getString("info"))) {
                JSONArray jsonArray = jsonObject.getJSONObject("songResultData").getJSONArray("result");
                List<Music> musicList = new ArrayList<>();
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject ele = jsonArray.getJSONObject(i);
                    Music music = new Music();
                    music.setName(ele.getString("name"));
                    music.setRid(ele.getString("contentId"));
                    music.setCopyrightId(ele.getString("copyrightId"));
                    JSONArray singers = ele.getJSONArray("singers");
                    music.setArtist(CommonUtil.getArtist(singers,"name"));
                    music.setAlbumPic(ele.getJSONArray("imgItems").getJSONObject(1).getString("img"));
                    musicList.add(music);
                }
                redisUtil.setCacheObject("MK"+keyWord+page+pageSize,musicList,60*24);
                return musicList;
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<Lyric> getLyricById(String copyrightId) {
        if (redisUtil.hasKey("MC"+copyrightId)) {
            return (List<Lyric>) redisUtil.get("MC"+copyrightId);
        }
        try {
            String json = miGuRequest.getLyric(copyrightId);
            JSONObject jsonObject = JSONObject.parseObject(json);
            if ("成功".equals(jsonObject.getString("msg"))) {
                String lyric = jsonObject.getString("lyric");
                List<Lyric> lyrics = CommonUtil.lyricHandler(lyric);
                redisUtil.setCacheObject("MC"+copyrightId,lyrics,60*24);
                return lyrics;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
