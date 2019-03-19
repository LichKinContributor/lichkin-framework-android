package com.lichkin.framework.app.android.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * 提示窗对象
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class LKToastBean {

    /** 提示内容 */
    @NonNull
    private String msg;

    /** 显示时长 */
    private int timeout;

}
