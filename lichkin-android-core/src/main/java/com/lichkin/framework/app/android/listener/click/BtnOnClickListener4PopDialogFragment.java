package com.lichkin.framework.app.android.listener.click;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.lichkin.framework.app.android.bean.LKParamsBean;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

/**
 * 按钮点击事件 -> 弹出对话框
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@RequiredArgsConstructor
@AllArgsConstructor
public class BtnOnClickListener4PopDialogFragment implements View.OnClickListener {

    /** 当前Fragment */
    private final Fragment fragment;
    /** 弹出对话框 */
    private final Class<?> popDialogFragment;
    /** 附带参数 */
    private LKParamsBean params;

    @Override
    public void onClick(View v) {
        DialogFragment dialogFragment;
        try {
            dialogFragment = (DialogFragment) popDialogFragment.newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return;
        } catch (InstantiationException e) {
            e.printStackTrace();
            return;
        }
        FragmentManager fragmentManager = this.fragment.getFragmentManager();
        if (fragmentManager == null) {
            return;
        }
        dialogFragment.show(fragmentManager, dialogFragment.getTag());
    }

}
