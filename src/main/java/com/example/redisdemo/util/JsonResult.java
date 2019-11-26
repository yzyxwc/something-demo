package com.example.redisdemo.util;

import com.example.redisdemo.exception.BusinessException;
import com.example.redisdemo.exception.CommonExceptions;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.example.redisdemo.exception.ExceptionType;
import io.swagger.annotations.ApiModelProperty;

public class JsonResult<T> {
    @ApiModelProperty(
            value = "服务器RPC响应code",
            example = "200"
    )
    private int code;
    @ApiModelProperty(
            value = "执行结果描述",
            example = "success"
    )
    private String msg;
    @ApiModelProperty(
            value = "服务器结果返回时的 Unix timestamp,单位毫秒",
            example = "1356019200000"
    )
    private long ts;
    @ApiModelProperty(
            name = "data",
            value = "响应结果",
            example = "{}"
    )
    private T data;

    public JsonResult(int code, String msg) {
        this.code = 200;
        this.msg = "success";
        this.ts = System.currentTimeMillis();
        this.code = code;
        this.msg = msg;
    }

    public JsonResult(int code, String msg, T data) {
        this.code = 200;
        this.msg = "success";
        this.ts = System.currentTimeMillis();
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    @JsonIgnore
    public boolean isOK() {
        return this.code == 200 || this.code == 0;
    }

    public JsonResult(T data) {
        this.code = 200;
        this.msg = "success";
        this.ts = System.currentTimeMillis();
        this.data = data;
    }

    public JsonResult(ExceptionType exceptionType) {
        this(exceptionType.getCode(), exceptionType.getDescription());
    }

    public JsonResult(BusinessException bisinessException) {
        this(bisinessException.getCode(), bisinessException.getDescription());
    }

    public static <T> JsonResult<T> of(T data) {
        return new JsonResult(data);
    }

    public static JsonResult of(Exception ex) {
        return ex instanceof BusinessException ? new JsonResult(((BusinessException)ex).getCode(), ((BusinessException)ex).getDescription(), ex.getMessage()) : new JsonResult(CommonExceptions.SERVER_ERROR.getCode(), ex.getMessage());
    }

    public int getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    public long getTs() {
        return this.ts;
    }

    public T getData() {
        return this.data;
    }

    public JsonResult() {
        this.code = 200;
        this.msg = "success";
        this.ts = System.currentTimeMillis();
    }
}
