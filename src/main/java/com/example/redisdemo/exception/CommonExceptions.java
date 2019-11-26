package com.example.redisdemo.exception;

public enum CommonExceptions implements ExceptionType {
    PARAM_ERROR(400, "参数错误:%s"),
    TOKEN_ERROR(401, "令牌校验失败,请重新登录"),
    PERMISSION_DENY(401, "无权访问"),
    SERVER_ERROR(500, "服务器繁忙，请稍后重试");

    private int code;
    private String description;

    public int getCode() {
        return this.code;
    }

    public String getDescription() {
        return this.description;
    }

    private CommonExceptions(int code, String description) {
        this.code = code;
        this.description = description;
    }
}
