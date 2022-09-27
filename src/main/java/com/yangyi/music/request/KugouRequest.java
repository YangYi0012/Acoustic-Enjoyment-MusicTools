package com.yangyi.music.request;

import com.yangyi.music.utils.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Description: TODO
 * @Author YangYi
 * @Date 2022/8/13 13:05
 */
@Slf4j
@Component
public class KugouRequest {


    private String searchByKeyWord = "http://msearchcdn.kugou.com/api/v3/search/song?";

    private String playUrl = "https://wwwapi.kugou.com/yy/index.php?r=play/getdata&hash=0122bff7ebf3f6fa30045fe98f71d0a8&mid=321f21257f8deaaee1846893c845eef3&";

    public String getSearchByKeyWord(String keyWord,int page,int pageSize) throws IOException {
        String url = this.searchByKeyWord+"keyword="+keyWord+"&page="+page+"&pagesize=" + pageSize;
        log.info("KuGou请求地址：" + url);
        Connection connection = Jsoup.connect(url);
//                .headers();
        return CommonUtil.getData(connection);
    }

    public String getPlayUrl(String musicId) throws IOException {
        String url = this.playUrl+"album_audio_id="+musicId;
        log.info("KuGou请求地址：" + url);
        Connection connection = Jsoup.connect(url);
//                .headers();
        return CommonUtil.getData(connection);
    }
}
