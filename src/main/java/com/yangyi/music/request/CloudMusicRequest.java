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
 * @Date 2022/8/20 12:48
 */
@Slf4j
@Component
public class CloudMusicRequest {

    private String searchByKeyWord = "http://music.163.com/api/search/get/web?type=1&";

    public String getSearchByKeyWord(String keyWord,int offset,int limit) throws IOException {
        String url = this.searchByKeyWord+"s="+keyWord+"&offset="+offset+"&limit=" + limit;
        log.info("KuGou请求地址：" + url);
        Connection connection = Jsoup.connect(url);
//                .headers();
        return CommonUtil.getData(connection);
    }

}
