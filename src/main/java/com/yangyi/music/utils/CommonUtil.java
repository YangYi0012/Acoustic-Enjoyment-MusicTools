package com.yangyi.music.utils;

import com.alibaba.fastjson.JSONArray;
import com.yangyi.music.entity.Lyric;
import org.jsoup.Connection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description: TODO
 * @Author YangYi
 * @Date 2022/8/13 15:23
 */
public class CommonUtil {

    /**
     * 歌词文件转换
     * @param lyric
     * @return
     */

    public static List<Lyric> lyricHandler(String lyric) {
        String regex = "\\[(\\d{1,2}):(\\d{1,2}).(\\d{1,2})\\]";
        Pattern pattern = Pattern.compile(regex); // 创建 Pattern 对象
        String[] lines = lyric.split("\r\n|\r|\n");
        List<Lyric> list = new ArrayList<>();
        for (String line : lines) {
            Lyric item = new Lyric();
            Matcher matcher = pattern.matcher(line);
            while (matcher.find()) {
                String min = matcher.group(1); // 分钟
                String sec = matcher.group(2); // 秒
                String mill = matcher.group(3);
                String time = getLongTime(min, sec, mill);
                String str = line.substring(matcher.end());
                item.setTime(time);
                item.setLineLyric(str);
                list.add(item);
            }
        }
        return list;
    }

    private static String getLongTime(String min, String sec, String mill) {
        // 转成整型
        int m = Integer.parseInt(min);
        int s = Integer.parseInt(sec);
        // 组合成一个长整型表示的以秒为单位的时间
        String time = m*60+s+"."+mill;
        return time.toString();
    }

    public static String getArtist(JSONArray singers,String keyword){
        String artist = null;
        for (int j = 0; j < singers.size(); j++) {
            String name = singers.getJSONObject(j).getString(keyword);
            artist = j == 0? name : artist+"&"+name;
        }
        return artist;
    }

    /**
     * 发送请求
     * @param connection
     * @return
     * @throws IOException
     */
    public static String getData(Connection connection) throws IOException {
        String body = connection.timeout(6000)
                .maxBodySize(0)
                .ignoreContentType(true)
                .execute()
                .body();
        return body;
    }
}
