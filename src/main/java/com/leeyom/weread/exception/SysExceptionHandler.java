package com.leeyom.weread.exception;


import cn.hutool.core.util.StrUtil;
import com.leeyom.weread.domain.Consts;
import com.leeyom.weread.domain.ResultDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

/**
 * 全局异常处理
 */
@ControllerAdvice
@RestController
@Slf4j
public class SysExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResultDto runOperateExp(RuntimeException ex) {
        log.error("异常日志：", ex);
        return new ResultDto(Consts.ErrCode.ERROR, "转换失败，请确认笔记格式是否正确！");
    }

    @ExceptionHandler(BizException.class)
    public ResultDto handleBizException(BizException ex) {
        log.error("异常日志：", ex);
        int code = Consts.ErrCode.ERROR;
        String message = ex.getMessage();
        return new ResultDto(code, StrUtil.isBlank(message) ? "转换失败，请确认笔记格式是否正确！" : message);
    }

}
