package com.lichkin.application.invokers.Feedback;

import com.lichkin.framework.defines.beans.LKRequestBean;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 反馈
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@RequiredArgsConstructor
public class FeedbackIn extends LKRequestBean {

    /** 反馈内容 */
    private final String content;

    /** 图片（Base64） */
    private final String img;

}
