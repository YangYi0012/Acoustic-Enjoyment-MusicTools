package com.yangyi.music.config;

import com.yangyi.music.interceptor.CorsInterceptor;
import com.yangyi.music.interceptor.SignerInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Description: 拦截器配置
 *      public权限    /api/v1/public
 *      private权限   /api/v1/pri
 * @Author YangYi
 * @Date 2022/6/24 19:41
 */
@Slf4j
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        log.info("跨域请求配置");
//        //跨域请求拦截器,拦截所有请求，跨域放在最上面
//        CorsInterceptor corsInterceptor = new CorsInterceptor();
//        registry.addInterceptor(corsInterceptor).addPathPatterns("/**");

        log.info("签名验证配置");
        //创建签名验证拦截器
        HandlerInterceptor singerInterceptor = new SignerInterceptor();
        //拦截路径
        String[] path = {"/v1/**","/v2/**","/v3/**","/v4/**"};
        //排除路径
//        String[] excludePath={"/api/v1/pri/user/login","/api/v1/pri/user/register"};
        registry.addInterceptor(singerInterceptor).addPathPatterns(path);
//                .excludePathPatterns(excludePath);


//        //创建登陆拦截器对象
//        HandlerInterceptor loginInterceptor = new LoginInterceptor();
//        //拦截路径
//        String[] path = {"/api/v1/pri/**"};
//        //排除路径
//        String[] excludePath={"/api/v1/pri/user/login","/api/v1/pri/user/register"};
//        registry.addInterceptor(loginInterceptor).addPathPatterns(path).excludePathPatterns(excludePath);
    }
}
