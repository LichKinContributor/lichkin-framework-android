package com.lichkin.application.invokers.UploadPhoto;

import com.lichkin.framework.defines.beans.LKRequestBean;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 头像上传
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@RequiredArgsConstructor
public class UploadPhotoIn extends LKRequestBean {

    /** 头像（Base64） */
    private final String photo;

}
