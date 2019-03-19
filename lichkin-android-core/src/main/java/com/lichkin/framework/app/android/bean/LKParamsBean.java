package com.lichkin.framework.app.android.bean;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

/**
 * 参数对象
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
public class LKParamsBean {

    /**
     * 创建参数对象
     * @return 参数对象
     */
    public static LKParamsBean i() {
        return new LKParamsBean();
    }

    /** 附带参数 */
    private final Map<String, Object> params = new HashMap<>();

    /**
     * 添加参数
     * @param key 键
     * @param value 值
     * @return 本对象
     */
    public LKParamsBean a(String key, Object value) {
        params.put(key, value);
        return this;
    }

}
