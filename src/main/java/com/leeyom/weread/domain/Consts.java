package com.leeyom.weread.domain;

public interface Consts {

    /**
     * 错误码
     */
    interface ErrCode {
        /**
         * 请求成功
         */
        int SUCCESS = 0;
        /**
         * 服务异常
         */
        int ERROR = -1;
        /**
         * 参数非法
         */
        int ILL_PARAMS = -3;
    }
}
