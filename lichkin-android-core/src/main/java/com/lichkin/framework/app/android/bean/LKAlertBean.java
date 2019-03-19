package com.lichkin.framework.app.android.bean;

import com.lichkin.framework.app.android.callback.LKBtnCallback;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * 提示信息对象
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class LKAlertBean {

    /** 提示内容 */
    @NonNull
    private String msg;

    /** 按钮点击回调方法 */
    private LKBtnCallback dialogCallback;

}
