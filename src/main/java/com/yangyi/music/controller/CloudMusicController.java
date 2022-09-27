package com.yangyi.music.controller;

import com.yangyi.music.entity.Music;
import com.yangyi.music.service.CloudMusicService;
import com.yangyi.music.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description: 网易云音乐请求处理
 * @Author YangYi
 * @Date 2022/8/20 13:29
 */
@RestController
@RequestMapping("/v4/music")
public class CloudMusicController {
    @Autowired
    private CloudMusicService cloudMusicService;

    /**
     * 网易云音乐模糊搜索
     * @param str
     * @param pn
     * @param rn
     * @return
     */
    @GetMapping("/searchByKeyword")
    public JsonData searchByKeyword(String str,int pn,int rn){
        int begin = (rn - 1) * pn;
        List<Music> musicList = cloudMusicService.searchByKeyWord(str, begin, rn);
        return JsonData.success(musicList);
    }
}
