package com.chen.cloud.auth;

import lombok.Data;

@Data
public class ResultResponse<T> {
    private static final Integer CODE_200 = 200;
    //未授权返回码
    private static final Integer NO_AUTH_401 = 401;
    /**
     * 成功标志
     */
    private boolean success;
    /**
     * 返回信息
     */
    private String message;
    /**
     * 返回码
     */
    private Integer code;
    /**
     * 返回数据
     */
    private T data;

    public static <T> ResultResponse<T> OK(String msg) {
        ResultResponse<T> r = new ResultResponse<>();
        r.setSuccess(true);
        r.setCode(CODE_200);
        r.setMessage(msg);
        return r;
    }

    public static <T> ResultResponse<T> OK(String msg, T data) {
        ResultResponse<T> r = new ResultResponse<>();
        r.setSuccess(true);
        r.setMessage(msg);
        r.setData(data);
        r.setCode(CODE_200);
        return r;
    }

    public static <T> ResultResponse<T> OK() {
        ResultResponse<T> r = new ResultResponse<>();
        r.setSuccess(true);
        return r;
    }

    public static <T> ResultResponse<T> error(String errorMsg) {
        ResultResponse<T> r = new ResultResponse<>();
        r.setSuccess(false);
        r.setMessage(errorMsg);
        return r;
    }

    public static <T> ResultResponse<T> error(String errorMsg, Integer code) {
        ResultResponse<T> r = new ResultResponse<>();
        r.setSuccess(false);
        r.setCode(code);
        return r;
    }

    public static <T> ResultResponse<T> noAuth(String msg) {
        return error(msg, NO_AUTH_401);
    }
}
