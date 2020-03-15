package com.leeyom.weread.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResultDto implements Serializable {

    private long code = 0L;

    private String msg;

    private Object data;

    public ResultDto() {
    }

    public ResultDto(Object data) {
        this.data = data;
    }

    public ResultDto(long code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResultDto(long code, Object data) {
        this.code = code;
        this.data = data;
    }

    public ResultDto(long code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

}
