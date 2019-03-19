package com.lichkin.framework.app.android.bean;

import com.lichkin.framework.defines.LKFrameworkStatics;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 动态TAB页
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LKDynamicTabBean {

    /** TABID */
    private String tabId;

    /** TAB名称 */
    private String tabName;

    /** TAB图片(Base64) */
    private String tabIcon;

    /**
     * 转换为字符串
     * @return 字符串
     */
    public String convertString() {
        StringBuilder sb = new StringBuilder();
        sb.append(tabId).append(LKFrameworkStatics.SPLITOR_FIELDS).append(tabName).append(LKFrameworkStatics.SPLITOR_FIELDS).append(tabIcon).append(LKFrameworkStatics.SPLITOR);
        return sb.toString();
    }

    /**
     * 转换为对象
     * @param compStr 字符串
     * @return 对象
     */
    public static LKDynamicTabBean convertObject(String compStr) {
        String[] compStrArr = compStr.split(LKFrameworkStatics.SPLITOR_FIELDS);
        return new LKDynamicTabBean(compStrArr[0], compStrArr[1], compStrArr[2]);
    }

    /**
     * 转换为列表对象
     * @param listCompStr 字符串
     * @return 列表对象
     */
    public static List<LKDynamicTabBean> convertListObject(String listCompStr) {
        List<LKDynamicTabBean> listComp = new ArrayList<>();
        String[] listCompStrArr = listCompStr.split(LKFrameworkStatics.SPLITOR);
        for (String compStr : listCompStrArr) {
            listComp.add(convertObject(compStr));
        }
        return listComp;
    }

}
