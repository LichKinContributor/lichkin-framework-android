package com.lichkin.application.invokers.Score;

import com.lichkin.framework.defines.beans.LKRequestBean;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 评分
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@RequiredArgsConstructor
public class ScoreIn extends LKRequestBean {

    /** 评分（按照星级评分1~5分 ） */
    private final Byte score;

    /** 标题 */
    private final String title;

    /** 评分内容 */
    private final String content;

}
