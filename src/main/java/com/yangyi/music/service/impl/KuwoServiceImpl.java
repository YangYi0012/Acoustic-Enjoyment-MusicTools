package com.yangyi.music.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yangyi.music.entity.*;
import com.yangyi.music.exception.MusicException;
import com.yangyi.music.request.KuwoRequest;
import com.yangyi.music.service.KuwoService;
import com.yangyi.music.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: TODO
 * @Author YangYi
 * @Date 2022/8/6 14:40
 */
@Service
public class KuwoServiceImpl implements KuwoService {
    @Autowired
    private KuwoRequest kuwoRequest;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 获取歌词
     * @param musicId
     * @return
     */
    public List<Lyric> getLyric(String musicId){
        List<Lyric> lyrics = new ArrayList<>();
        try {
            String json = kuwoRequest.getLyric(musicId);
            JSONObject jsonObject = JSONObject.parseObject(json);
            JSONArray list = jsonObject.getJSONObject("data").getJSONArray("lrclist");
            if (list != null || list.size() > 0) {
                list.forEach(item ->{
                    JSONObject obj = (JSONObject) item;
                    Lyric lyric = obj.toJavaObject(Lyric.class);
                    lyrics.add(lyric);
                });
                return lyrics;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    /**
     * 获取推荐歌单
     * @param order
     * @param pn
     * @param rn
     * @return
     */
    @Override
    public List<SongList> getRcmPlayList(String order,int pn,int rn) {
        if (redisUtil.hasKey("W"+order+pn+rn)) {
            List<SongList> list = (List<SongList>) redisUtil.get("W"+order+pn+rn);
            return list;
        }
        try {
            List<SongList> songLists = new ArrayList<>();
            String json = null;
            json = kuwoRequest.getHotList(order,pn,rn+10);
            if (json != null || json != "") {
                JSONObject jsonRet = JSONObject.parseObject(json);
                String code = jsonRet.getString("code");

                if ("200".equals(code)) {
                    //code==200 获取成功
                    JSONObject data = jsonRet.getJSONObject("data");
                    //获取结果集数组

                    JSONArray list = data.getJSONArray("data");
                    a: for (int i = 0; i < list.size(); i++){
                        if (list.get(i) instanceof JSONObject) {
                            SongList songEle = new SongList();
                            JSONObject jsonObject = (JSONObject) list.get(i);
                            songEle.setId(jsonObject.getString("id"));
                            songEle.setName(jsonObject.getString("name"));
                            songEle.setViewCounts(jsonObject.getString("listencnt"));
                            songLists.add(songEle);
                        }
                        if (songLists.size() == rn) {
                            break a;
                        }

                    };
                    redisUtil.setCacheObject("W"+order+pn+rn,songLists,60*24);
                    return songLists;
                }
            }
            return null;
        } catch (IOException e) {
            throw new MusicException(401,"网络异常，请稍后再试");
        }

    }

    /**
     * 获取推荐歌单里面的歌
     * @param pid
     * @param pn
     * @param rn
     * @return
     */

    @Override
    public List<Music> getPlayListInfo(String pid, int pn, int rn) {
        try {
            List<Music> musicList = new ArrayList<>();
            String playListInfo = kuwoRequest.getPlayListInfo(pid,pn,rn+10);
            JSONObject jsonObject = JSONObject.parseObject(playListInfo);
            if ("200".equals(jsonObject.getString("code"))) {
                JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("musicList");
                a:for (int i = 0; i < jsonArray.size(); i++) {
                    Music music = jsonArray.getJSONObject(i).toJavaObject(Music.class);
                    if (!music.getListenFee()) {
                        musicList.add(music);
                    }
                    if (musicList.size() == rn) {
                        break a;
                    }
                }
                return musicList;
            }
            return null;
        } catch (IOException e) {
            throw new MusicException(401,"网络异常，请稍后再试");
        }
    }

    /**
     * 获取首页推荐
     * @param bangId
     * @param pn
     * @param rn
     * @return
     */
    @Override
    public Map getMusicList(String bangId,int pn,int rn) {
        if (redisUtil.hasKey("WB"+bangId+pn+rn)) {
            Map map = (Map) redisUtil.get("W"+bangId + pn + rn);
            return map;
        }
        Map map = new HashMap<>();
        try {
            String json = kuwoRequest.getMusicList(bangId,pn,rn+10);
            if (json != null ||  json != "") {
                JSONObject jsonObject = JSONObject.parseObject(json);
                String code = jsonObject.getString("code");
                if ("200".equals(code)) {
                    JSONObject data  = jsonObject.getJSONObject("data");
                    map.put("img",data.getString("img"));
                    map.put("num",data.getString("num"));
                    List<Music> musicList = new ArrayList<>();
                    JSONArray list = data.getJSONArray("musicList");
                    a: for (int i = 0 ; i < list.size(); i++){
                        if (list.get(i) instanceof JSONObject) {
                            JSONObject music = (JSONObject) list.get(i);
                            if (!music.getBoolean("isListenFee")) {
                                musicList.add(music.toJavaObject(Music.class));
                            }
                        }
                        if (musicList.size() >= rn) {
                            break a;

                        }
                    }
                    map.put("musicList",musicList);
                    redisUtil.setCacheObject("W"+bangId+pn+rn,map,60*24);
                    return map;
                }
            }
            return null;
        } catch (IOException e) {
            throw new MusicException(401,"网络异常，请稍后再试");
        }
    }


    /**
     * 获取歌曲播放链接
     * @param musicId
     * @return
     */
    public Url getPlayUrl(String musicId){
        if (redisUtil.hasKey("W"+"ul"+musicId)) {
            Url url = (Url) redisUtil.get("W"+"ul"+musicId);
            return url;
        }
        String playUrl = null;
        Url url = null;
        try {
            playUrl = kuwoRequest.getPlayUrl(musicId);
            JSONObject jsonObject = JSONObject.parseObject(playUrl);
            String code = jsonObject.getString("code");
            if ("200".equals(code)) {
                url = new Url();
                url.setUrl(jsonObject.getJSONObject("data").getString("url"));
                List<Lyric> lyric = this.getLyric(musicId);
                url.setLyric(lyric);
                redisUtil.setCacheObject("W"+"ul"+musicId,url,10);
                return url;
            }else {
                return null;
            }
        } catch (IOException e) {
            throw new MusicException(401,"网络异常，请稍后再试");
        }
    }

    /**
     * 根据id获取歌手信息
     * @param artistId
     * @return
     */
    @Override
    public Artist getOtherArtistByArtistId(int artistId) {
        Artist artist = null;
        artist = getByArtistIdFromKuWoMusic(artistId);
        if (artist != null) {

        }
        return artist;
    }


    /**
     * 从酷我获取歌手信息
     * @param artistId
     * @return
     */
    @Override
    public Artist getByArtistIdFromKuWoMusic(int artistId) {
        Artist artist = null;
        //尝试从酷我音乐获取数据
        String json = null;
        try {
            //发送请求
            json =kuwoRequest.getArtist(artistId);
            if (json != null || json != "") {
                JSONObject jsonRet = JSONObject.parseObject(json);
                String code = jsonRet.getString("code");

                if ("200".equals(code)) {
                    //code==200 获取成功
                    JSONObject data = jsonRet.getJSONObject("data");
                    artist = JSONObject.toJavaObject(data, Artist.class);
                    if (artist != null) {
                        return artist;
                    }
                }
            }
            return null;
        } catch (IOException e) {
            throw new MusicException(401,"网络异常，请稍后再试");
        }
    }

    /**
     * 酷我音乐模糊搜索
     * @param keyWord
     * @param pageNo
     * @param pageNum
     * @return
     */
    @Override
    public List<Music> getByKeyWordFromKuWoMusic(String keyWord, int pageNo, int pageNum) {
        if (redisUtil.hasKey("W"+keyWord+pageNo+pageNum)) {
             List<Music> list = (List<Music>) redisUtil.get("W"+keyWord+pageNo+pageNum);
            return list;
        }
        try {
            List<Music> musicList = new ArrayList<>();
            String json = null;
            json = kuwoRequest.searchMusicByKeyword(keyWord,pageNo,pageNum+10);
            if (json != null || json != "") {
                JSONObject jsonRet = JSONObject.parseObject(json);
                String code = jsonRet.getString("code");

                if ("200".equals(code)) {
                    //code==200 获取成功
                    JSONObject data = jsonRet.getJSONObject("data");
                    //获取结果集数组
                    JSONArray list = data.getJSONArray("list");
                    for (int i = 0; i < list.size(); i++){
                        if (list.get(i) instanceof JSONObject) {
                            JSONObject jsonObject = (JSONObject) list.get(i);
                            Music music = jsonObject.toJavaObject(Music.class);
                            if (!music.getListenFee()){
                                musicList.add(music);
                            }
                        }
                        if (musicList.size() == (pageNum-10)) {
                            break;
                        }

                    }
                    redisUtil.setCacheObject("W"+keyWord+pageNo+pageNum,musicList,24*60);
                    return musicList;
                }
            }
            return null;
        } catch (IOException e) {
            throw new MusicException(401,"网络异常，请稍后再试");
        }
    }
}
