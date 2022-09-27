package com.yangyi.music.controller;

import com.yangyi.music.entity.*;
import com.yangyi.music.utils.JsonData;
import com.yangyi.music.service.KuwoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author YangYi
 * @Date 2022/8/4 16:50
 */
@RestController
@RequestMapping("/v1/music")
public class KuwoController {
    @Autowired
    private KuwoService kuwoService;

    /**
     * 根据id获取歌手信息
     * @param artistId
     * @return
     */
    @GetMapping("/getOtherArtistByArtistId")
    public JsonData getOtherArtistByArtistId(int artistId){

        Artist artist = kuwoService.getOtherArtistByArtistId(artistId);
        if (artist != null) {
            return JsonData.success(artist);
        }
        return JsonData.error();

    }

    /**
     * kuwo模糊搜索
     * @param str
     * @param pn
     * @param rn
     * @return
     */
    @GetMapping("/searchMusicByKeyWord")
    public JsonData searchMusicByKeyWord(String str, int pn, int rn){
        List<Music> musicList = kuwoService.getByKeyWordFromKuWoMusic(str, pn, rn);
        if (musicList != null && musicList.size() > 0) {
            return JsonData.success(musicList);
        }
        return JsonData.buildError(-1,"暂无更多数据");
    }

    @GetMapping("/getPlayUrl")
    public JsonData getPlayUrl(String musicId){
        Url playUrl = kuwoService.getPlayUrl(musicId);
        if (playUrl != null) {
            return JsonData.success(playUrl);
        }
        return JsonData.buildError(-1,"暂无更多数据");
    }

    @GetMapping("/getMusicList")
    public JsonData getMusicList(String str, int pn, int rn){
        Map musicList = kuwoService.getMusicList(str, pn, rn);
        if (musicList != null && musicList.size() > 0) {
            return JsonData.success(musicList);

        }
        return JsonData.buildError(-1,"暂无更多数据");
    }

    @GetMapping("/getMusicDetail")
    public JsonData getMusicDetail(String musicId){
        List<Lyric> lyrics = kuwoService.getLyric(musicId);
        if (lyrics != null && lyrics.size() > 0) {
            return JsonData.success(lyrics);
        }
        return JsonData.buildError(-1,"暂无更多数据");
    }

    @GetMapping("/getRcmPlayList")
    public JsonData getRcmPlayList(String str, int pn, int rn){
        List<SongList> rcmPlayList = kuwoService.getRcmPlayList(str,pn,rn);
        if (rcmPlayList != null && rcmPlayList.size()>0) {
            return JsonData.success(rcmPlayList);
        }
        return JsonData.buildError(-1,"暂无更多数据");
    }

    @GetMapping("/getPlayListInfo")
    public JsonData getPlayListInfo(String str, int pn, int rn){
        List<Music> playListInfo = kuwoService.getPlayListInfo(str, pn, rn);
        if (playListInfo != null && playListInfo.size()>0) {
            return JsonData.success(playListInfo);
        }
        return JsonData.buildError(-1,"暂无更多数据");

    }
}
