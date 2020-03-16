package com.leeyom.weread.controller;

import com.leeyom.weread.domain.Consts;
import com.leeyom.weread.domain.WereadNoteDTO;
import com.leeyom.weread.exception.BizException;
import com.leeyom.weread.utils.WereadNoteUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequestMapping("/weread")
public class WereadController {

    /**
     * 将微信读书笔记转为markdown格式
     *
     * @param wereadNoteDTO 微信读书笔记
     * @return markdown格式
     */
    @PostMapping(value = "/convertMarkdown")
    public void convertToMarkdown(WereadNoteDTO wereadNoteDTO, HttpServletResponse response) {
        if (StringUtils.isBlank(wereadNoteDTO.getWereadNote())) {
            throw new BizException(Consts.ErrCode.ERROR, "读书笔记不能为空哦！");
        }
        WereadNoteUtil.convertWereadNote2Markdown(wereadNoteDTO.getWereadNote(), response);
    }
}
