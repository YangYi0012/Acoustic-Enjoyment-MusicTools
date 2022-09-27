package com.yangyi.music.exception;

/**
 * @Description: TODO
 * @Author YangYi
 * @Date 2022/8/4 17:00
 */
public class MusicException extends RuntimeException {
    private int code;
    private String message;

    public MusicException() {

    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MusicException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

}
