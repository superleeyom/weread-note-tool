package com.leeyom.weread.exception;

public abstract class BaseException extends RuntimeException {

    private long errorCode = -1L;

    public BaseException() {

    }

    public BaseException(long code, String msg) {
        super(msg);
        this.errorCode = code;
    }

    public BaseException(Throwable e, String msg) {
        super(msg, e);
    }

    public BaseException(String msg) {
        super(msg);
    }

    public long getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(long errorCode) {
        this.errorCode = errorCode;
    }
}
