package com.leeyom.weread.exception;


public class BizException extends BaseException {

    public BizException(long errorCode, String msg) {
        super(errorCode, msg);
    }

    public BizException(String msg) {
        super(msg);
    }
}