package com.lichkin.framework.app.android.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * 日志对象
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class LKLogBean {

    /** 日志内容 */
    @NonNull
    private String msg;

    /** 日志类型 */
    private String type = "debug";

}
