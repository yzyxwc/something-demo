package com.example.redisdemo.exception;

public interface BusinessException {
    int getCode();

    String getDescription();
}
