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
 * @Date 2022/8/3 19:15
 */
@Slf4j
@Component
public class KuwoRequest {

    //请求头
    private Map<String,String> headers = new HashMap<>();

    {
        log.info("Kuwo音乐请求配置");
        headers.put("Referer","http://www.kuwo.cn/");
        headers.put("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:103.0) Gecko/20100101 Firefox/103.0");
        headers.put("csrf","76KSFGV0G63");
        headers.put("Cookie","kw_token=76KSFGV0G63");
    }


    // 根据id获取歌手名称
    private String getArtist = "http://www.kuwo.cn/api/www/artist/artist?";
    // 根据关键词获取歌曲名称
    private String searchByKeyWord = "http://www.kuwo.cn/api/www/search/searchMusicBykeyWord?";
    // 根据musicId获取播放地址
    private String getPlayUrl = "http://www.kuwo.cn/api/v1/www/music/playUrl?";

    //获取首页推荐
    private String musicList = "http://www.kuwo.cn/api/www/bang/bang/musicList?";

    private String lyric = "http://m.kuwo.cn/newh5/singles/songinfoandlrc?";

    //获取最热歌单
    private String hotList = "http://www.kuwo.cn/api/www/classify/playlist/getRcmPlayList?pn=1&rn=30&order=hot";

    private String playListInfo = "http://www.kuwo.cn/api/www/playlist/playListInfo?";

    public String getPlayListInfo(String pid,int pn,int rn) throws IOException {
        String url = this.playListInfo +"pid="+pid+"&pn="+pn+"&rn=" + rn;;
        log.info("KuWo请求地址：" + url);
        Connection connection = Jsoup.connect(url)
                .headers(headers);
        return getData(connection);
    }


    public String getLyric(String musicId) throws IOException {
        String url = lyric +"musicId="+musicId;
        log.info("KuWo请求地址：" + url);
        Connection connection = Jsoup.connect(url)
                .headers(headers);
        return getData(connection);
    }

    public String getMusicList(String bangId,int pn,int rn) throws IOException {
        String url = musicList +"bangId="+bangId+"&pn="+pn+"&rn=" + rn;
        log.info("KuWo请求地址：" + url);
        Connection connection = Jsoup.connect(url)
                .headers(headers);
        return getData(connection);
    }

    public String searchMusicByKeyword(String keyWord,int pageNo,int pageNum) throws IOException {
        String url = searchByKeyWord+"key="+keyWord+"&pn="+pageNo+"&rn=" + pageNum;
        log.info("KuWo请求地址：" + url);
        Connection connection = Jsoup.connect(url)
                .headers(headers);
        return getData(connection);
    }

    public String getPlayUrl(String musicId) throws IOException {
        String url = getPlayUrl +"mid="+musicId;
        log.info("KuWo请求地址：" + url);
        Connection connection = Jsoup.connect(url)
                .headers(headers);
        return getData(connection);
    }

    public String getArtist(int artistId) throws IOException {
        String url = getArtist+ "artistid="+artistId;
        log.info("KuWo请求地址：" + url);
        Connection connection = Jsoup.connect(url)
                .headers(headers);
        return getData(connection);
    }

    public String getHotList(String order,int pn,int rn) throws IOException {
        /*pn=1&rn=30&order=hot*/
        String url = this.hotList+"bangId="+order+"&pn="+pn+"&rn=" + rn;
        log.info("KuWo请求地址：" + hotList);
        Connection connection = Jsoup.connect(hotList)
                .headers(headers);
        return getData(connection);
    }

}
