package com.lichkin.application.invokers.GetBannerList;

import com.lichkin.framework.defines.beans.LKRequestBean;

import lombok.Getter;
import lombok.ToString;

/**
 * 获取Banner列表
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@ToString(callSuper = true)
public class GetBannerListIn extends LKRequestBean {

    /** 分类编码 */
    private String categoryCode = "";

}
