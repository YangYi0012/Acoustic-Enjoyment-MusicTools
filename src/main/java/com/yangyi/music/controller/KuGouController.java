package com.yangyi.music.controller;

import com.yangyi.music.entity.Music;
import com.yangyi.music.entity.Url;
import com.yangyi.music.service.KuGouService;
import com.yangyi.music.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description: 酷狗音乐请求处理
 * @Author YangYi
 * @Date 2022/8/13 14:13
 */
@RestController
@RequestMapping("/v2/music")
public class KuGouController {

    @Autowired
    private KuGouService kuGouService;

    /**
     * 模糊搜搜
     * @param str
     * @param rn
     * @param pn
     * @return
     */
    @GetMapping("/searchByKeyWord")
    public JsonData searchByKeyWord(String str,int rn,int pn){
        List<Music> musicList = kuGouService.getSearchByKeyWord(str, rn, pn);
        if (musicList != null && musicList.size()>0) {
            return JsonData.success(musicList);
        }
        return JsonData.buildError(-1,"暂无更多数据");
    }

    /**
     * 获取播放链接
     * @param musicId
     * @return
     */
    @GetMapping("/getPlayUrl")
    public JsonData getPlayUrl(String musicId){
        Url playUrl = kuGouService.getPlayUrl(musicId);
        if (playUrl != null) {
            return JsonData.success(playUrl);
        }
        return JsonData.buildError(-1,"暂无更多数据");
    }
}
