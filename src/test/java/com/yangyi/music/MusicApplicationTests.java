package com.yangyi.music;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yangyi.music.entity.Lyric;
import com.yangyi.music.entity.Music;
import com.yangyi.music.entity.Url;
import com.yangyi.music.request.*;
import com.yangyi.music.utils.AESUtils;
import com.yangyi.music.utils.CommonUtil;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.*;

class MusicApplicationTests {

    @Test
    void contextLoads() {
        KuwoRequest kuwoRequest = new KuwoRequest();
        Map map = new HashMap<>();
        try {
            String json = kuwoRequest.getMusicList("93", 1, 20);
            if (json != null) {
                JSONObject jsonObject = JSONObject.parseObject(json);
                String code = jsonObject.getString("code");
                if ("200".equals(code)) {
                    JSONObject data = jsonObject.getJSONObject("data");
                    map.put("img", data.getString("img"));
                    map.put("num", data.getString("num"));
                    List<Music> musicList = new ArrayList<>();
                    JSONArray list = data.getJSONArray("musicList");
                    a:
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i) instanceof JSONObject) {
                            JSONObject music = (JSONObject) list.get(i);
                            if (!music.getBoolean("isListenFee")) {
                                musicList.add(music.toJavaObject(Music.class));
                            }
                        }
                        System.out.println(i);
                        if (musicList.size() >= 10) {
                            System.out.println("结束");
                            break a;
                        }
                    }
                    map.put("musicList", musicList);
//                    redisUtil.setCacheObject(bangId+pn+rn,map,60*24);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void kugouGetSearchByKeyWord() {
        KugouRequest kugouRequest = new KugouRequest();
        String searchByKeyWord = null;
        try {
            searchByKeyWord = kugouRequest.getSearchByKeyWord("许嵩", 1, 10);
            JSONObject jsonObject = JSONObject.parseObject(searchByKeyWord);
            JSONObject data = jsonObject.getJSONObject("data");
            JSONArray jsonArray = data.getJSONArray("info");
            List<Music> musicList = new ArrayList<>();
            a:
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                Music music = new Music();
                music.setArtist(obj.getString("singername"));
                music.setRid(obj.getString("album_audio_id"));
                music.setName(obj.getString("songname"));
                musicList.add(music);
            }
            musicList.forEach(item -> {
                System.out.println(item);
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    void kugouGetPlayUrl() {
        KugouRequest kugouRequest = new KugouRequest();
        try {
            Url url = new Url();
            String playUrl = kugouRequest.getPlayUrl("53768672");
            JSONObject jsonObject = JSONObject.parseObject(playUrl);
            if (jsonObject.getInteger("status") == 1) {
                JSONObject data = jsonObject.getJSONObject("data");
                List<Lyric> lyrics = CommonUtil.lyricHandler(data.getString("lyrics"));
                for (Lyric lyric : lyrics) {
                    System.out.println(lyric);
                }
                url.setUrl(data.getString("play_url"));
                url.setImg("img");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    @Test
    void miguGetByKeyword() {
        MiGuRequest miGuRequest = new MiGuRequest();
        try {
            String json = miGuRequest.getPlayListInfo("素颜", 1, 10);
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
                    String artist = "";
                    for (int j = 0; j < singers.size(); j++) {
                        String name = singers.getJSONObject(j).getString("name");
                        artist = j == 0? name : artist+"&"+name;
                    }
                    music.setArtist(artist);
                    music.setAlbumPic(ele.getJSONArray("imgItems").getJSONObject(1).getString("img"));
                    musicList.add(music);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void migGetByKeyword(){
        MiGuRequest miGuRequest = new MiGuRequest();
        try {
            String json = miGuRequest.getLyric("60058623010");
//            System.out.println(json);
            JSONObject jsonObject = JSONObject.parseObject(json);
            String lyric = jsonObject.getString("lyric");
            List<Lyric> lyrics = CommonUtil.lyricHandler(lyric);
            lyrics.forEach(item->{
                System.out.println(item);
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void test() throws Exception {
        String password = AESUtils.aesDecrypt("Cd8U/YN3hsmaJhKuqdKilw==");
        System.out.println(password);
    }

    @Test
    void keywordCloudMusic() throws IOException {
        CloudMusicRequest cloudMusicRequest = new CloudMusicRequest();
        String json = cloudMusicRequest.getSearchByKeyWord("许嵩", 1, 2);
        JSONObject jsonObject = JSONObject.parseObject(json);
        if (jsonObject.getInteger("code") == 200) {
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
                    artist = j == 0? name : artist+"&"+name;
                    img = artists.getJSONObject(0).getString("img1v1Url");
                }
                music.setArtist(artist);
                music.setRid(item.getString("TSID"));
                music.setPic(img);
                music.setName(item.getString("name"));
                musicList.add(music);
            }
            System.out.println(musicList);
        }
    }


}
