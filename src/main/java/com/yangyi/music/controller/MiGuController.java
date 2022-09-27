package com.yangyi.music.controller;

import com.yangyi.music.entity.Lyric;
import com.yangyi.music.entity.Music;
import com.yangyi.music.service.MiGuService;
import com.yangyi.music.utils.JsonData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description: TODO
 * @Author YangYi
 * @Date 2022/8/14 12:40
 */
@Slf4j
@RestController
@RequestMapping("/v3/music")
public class MiGuController {

    @Autowired
    private MiGuService miGuService;

    @GetMapping("/searchByKeyword")
    public JsonData searchByKeyword(String str,int pn,int rn){
        List<Music> musicList = miGuService.getSearchByKeyWord(str, pn, rn);
        if (musicList != null && musicList.size()>0) {
            return JsonData.success(musicList);
        }
        return JsonData.buildError(-1,"暂无更多数据");

    }

    @GetMapping("/getLyric")
    public JsonData getLyric(String copyrightId){
        List<Lyric> lyrics = miGuService.getLyricById(copyrightId);
        if (lyrics != null && lyrics.size() > 0) {
            return JsonData.success(lyrics);
        }
        return JsonData.buildError(-1,"暂无更多数据");
    }


}
