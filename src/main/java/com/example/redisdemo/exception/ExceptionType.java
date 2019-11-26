package com.example.redisdemo.exception;

import org.apache.commons.lang3.StringUtils;

public interface ExceptionType {
    int getCode();

    String getDescription();

    default String descriptionWithArgs(Object[] args) {
        return StringUtils.isNotBlank(this.getDescription()) ? String.format(this.getDescription(), args) : this.getDescription();
    }
}

