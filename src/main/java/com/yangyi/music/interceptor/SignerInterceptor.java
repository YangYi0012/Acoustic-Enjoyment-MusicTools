package com.yangyi.music.interceptor;

import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.symmetric.AES;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yangyi.music.utils.AESUtils;
import com.yangyi.music.utils.JsonData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Date;

/**
 * @Description: TODO
 * @Author YangYi
 * @Date 2022/8/16 13:10
 */
@Slf4j
public class SignerInterceptor implements HandlerInterceptor {

    private final long TIMEOUT = 60 * 1000L;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String siger = request.getHeader("siger");
        if (siger != null) {
            StringBuffer requestURL = request.getRequestURL();
            String ret = AESUtils.aesDecrypt(siger);
            long fontTime = Long.parseLong(ret);
            long time = Long.parseLong(String.valueOf(new Date().getTime()).substring(3));
            if (time < fontTime + this.TIMEOUT) {
                return true;
            }
        }
        //签名验证失败
        sendJsonMessage(response, JsonData.error("签名无效"));
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

    /**
     * 响应json数据给前端
     * @param response
     * @param obj
     */
    public static void sendJsonMessage(HttpServletResponse response, Object obj){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            response.setContentType("application/json;charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.print(objectMapper.writeValueAsString(obj));
            writer.close();
            response.flushBuffer();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
