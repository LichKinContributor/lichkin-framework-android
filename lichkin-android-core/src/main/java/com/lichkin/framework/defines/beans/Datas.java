package com.lichkin.framework.defines.beans;

import com.lichkin.framework.app.android.LKAndroidStatics;
import com.lichkin.framework.app.android.utils.LKPropertiesLoader;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class Datas {

    /** 客户端唯一标识 */
    private String appKey = LKAndroidStatics.appKey();

    /** 客户端类型 */
    private String clientType = "ANDROID";

    /** 客户端版本号（大版本号） */
    private Byte versionX = LKAndroidStatics.versionX();

    /** 客户端版本号（中版本号） */
    private Byte versionY = LKAndroidStatics.versionY();

    /** 客户端版本号（小版本号） */
    private Short versionZ = LKAndroidStatics.versionZ();

    /** 登录后获取得 */
    private String token = LKAndroidStatics.token();

    /** 公司令牌 */
    private String compToken = LKPropertiesLoader.appToken;

}
