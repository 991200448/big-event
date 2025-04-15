package com.ithema.pojo;


import lombok.Data;


public class Result<T> {
    private Integer code;//业务状态码  0-成功  1-失败
    private String message;//提示信息
    private T data;//响应数据



    // 成功方法，无数据返回
    public static <T> Result<T> success() {
        return new Result<>(0, "操作成功", null);
    }

    // 成功方法，有数据返回
    public static <T> Result<T> success(T data) {
        return new Result<>(0, "操作成功", data);
    }

    // 失败方法，无数据返回
    public static <T> Result<T> error() {
        return new Result<>(1, "操作失败", null);
    }

    // 失败方法，有自定义提示信息，无数据返回
    public static <T> Result<T> error(String message) {
        return new Result<>(1, message, null);
    }

    // 失败方法，有自定义提示信息和数据返回
    public static <T> Result<T> error(String message, T data) {
        return new Result<>(1, message, data);
    }

    public Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
    public Result() {
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
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