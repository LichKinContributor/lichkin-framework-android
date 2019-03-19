package com.lichkin.application.invokers.GetDynamicTabs;

import com.lichkin.framework.app.android.bean.LKDynamicTabBean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 获取动态TAB页
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@Setter
@AllArgsConstructor
public class GetDynamicTabsOut {

    /** TABID */
    private String tabId;

    /** TAB名称 */
    private String tabName;

    /** TAB图片(Base64) */
    private String tabIcon;

    /**
     * 转换为Bean对象
     * @return Bean对象
     */
    public LKDynamicTabBean toBean() {
        return new LKDynamicTabBean(tabId, tabName, tabIcon);
    }

}
