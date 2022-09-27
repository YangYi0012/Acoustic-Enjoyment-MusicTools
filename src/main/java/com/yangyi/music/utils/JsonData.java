package com.yangyi.music.utils;

/**
 * @Description: 返回结果类
 * @Author YangYi
 * @Date 2022/8/4 15:19
 */
public class JsonData<T> {

    private int code; // 200 表示成功， 400 表示失败

    private String message;

    private T data; //数据

    public static <T> JsonData<T> success(T object) {
        JsonData<T> jsonData = new JsonData<T>();
        jsonData.data = object;
        jsonData.code = 200;
        return jsonData;
    }

    public static JsonData success() {
        JsonData jsonData = new JsonData();
        jsonData.code = 200;
        return jsonData;
    }

    public static <T> JsonData<T> error(String msg) {
        JsonData jsonData = new JsonData();
        jsonData.message = msg;
        jsonData.code = 400;
        return jsonData;
    }

    public static JsonData error() {
        JsonData jsonData = new JsonData();
        jsonData.code = 400;
        jsonData.message = "网络问题，请稍后重试";
        return jsonData;
    }

    public static JsonData buildError(int code,String message){
        JsonData jsonData = new JsonData();
        jsonData.message = message;
        jsonData.code = code;
        return jsonData;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
