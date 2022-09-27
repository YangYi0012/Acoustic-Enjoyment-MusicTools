package com.yangyi.music.exception;

import com.yangyi.music.utils.JsonData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description: TODO
 * @Author YangYi
 * @Date 2022/8/4 16:54
 */
@Slf4j
@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public JsonData test(Exception e){
        log.error(e.getMessage(),e);
        if (e instanceof MusicException) {
            MusicException exception = (MusicException) e;
            return JsonData.buildError(exception.getCode(),e.getMessage());
        }else {
            return JsonData.buildError(500,"系统繁忙，请稍后再试。。。");
        }

    }
}
