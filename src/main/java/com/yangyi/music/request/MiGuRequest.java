package com.yangyi.music.request;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.yangyi.music.utils.CommonUtil.getData;

/**
 * @Description: TODO
 * @Author YangYi
 * @Date 2022/8/14 11:30
 */
@Slf4j
@Component
public class MiGuRequest {
    private Map<String,String> headers = new HashMap<>();

    {
        log.info("MiGu音乐请求配置");
        headers.put("Referer","http://music.migu.cn/");
    }

    private String keyWord = "http://pd.musicapp.migu.cn/MIGUM2.0/v1.0/content/search_all.do?ua=Android_migu&searchSwitch={\"song\":1,\"album\":0,\"singer\":0,\"tagSong\":0,\"mvSong\":0,\"songlist\":0,\"bestShow\":1}";

    private String lyric= "http://music.migu.cn/v3/api/music/audioPlayer/getLyric?";
    public String getPlayListInfo(String keyWord,int pn,int rn) throws IOException {
        String url = this.keyWord +"&text="+keyWord+"&pageNo="+pn+"&pageSize=" + rn;
        log.info("MiGu请求地址：" + url);
        Connection connection = Jsoup.connect(url)
                .headers(headers);
        return getData(connection);
    }

    public String getLyric(String copyrightId) throws IOException {
        String url = this.lyric +"copyrightId="+copyrightId;
        log.info("MiGu请求地址：" + url);
        Connection connection = Jsoup.connect(url)
                .headers(headers);
        return getData(connection);
    }


}
