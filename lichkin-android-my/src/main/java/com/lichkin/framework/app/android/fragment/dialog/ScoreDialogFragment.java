package com.lichkin.framework.app.android.fragment.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.lichkin.android.my.R;
import com.lichkin.application.invokers.Score.ScoreIn;
import com.lichkin.application.invokers.Score.ScoreInvoker;
import com.lichkin.application.invokers.Score.ScoreOut;
import com.lichkin.application.invokers.Score.ScoreTester;
import com.lichkin.framework.app.android.bean.LKScreenBean;
import com.lichkin.framework.app.android.callback.impl.LKBaseInvokeCallback;
import com.lichkin.framework.app.android.utils.LKAndroidUtils;
import com.lichkin.framework.app.android.utils.LKRetrofit;
import com.lichkin.framework.app.android.utils.LKToast;
import com.lichkin.framework.defines.beans.LKErrorMessageBean;
import com.wang.avi.AVLoadingIndicatorView;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * 评分
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public class ScoreDialogFragment extends DialogFragment {

    /** 评分 */
    MaterialRatingBar scoreView;
    /** 标题 */
    EditText titleView;
    /** 内容 */
    EditText contentView;
    /** 加载 */
    AVLoadingIndicatorView loadingView;
    /** 按钮 */
    Button buttonView;

    @Override
    public void onStart() {
        super.onStart();
        //点击外部不消失
        getDialog().setCanceledOnTouchOutside(false);
        //设置宽高
        Window window = getDialog().getWindow();
        if (window == null) {
            return;
        }
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        LKScreenBean screen = LKAndroidUtils.getScreenDispaly();
        layoutParams.width = screen.getWidth() * 85 / 100;
        layoutParams.height = LKAndroidUtils.getPxValueByDpValue(330);
        window.setAttributes(layoutParams);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_score, container);

        scoreView = view.findViewById(R.id.score);
        titleView = view.findViewById(R.id.title);
        contentView = view.findViewById(R.id.content);
        loadingView = view.findViewById(R.id.loading);
        buttonView = view.findViewById(R.id.btn);
        buttonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invokeScore();
            }
        });

        return view;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        scoreView.setRating(5.0f);
        titleView.setText("");
        contentView.setText("");
        buttonView.setEnabled(true);
    }

    /**
     * 请求评分
     */
    private void invokeScore() {
        beforeInvokeScore();

        //请求参数
        ScoreIn in = new ScoreIn((byte) scoreView.getRating(), titleView.getText().toString(), contentView.getText().toString());

        //创建请求对象
        final LKRetrofit<ScoreIn, ScoreOut> retrofit = new LKRetrofit<>(this.getActivity(), ScoreInvoker.class);

        //测试代码
        ScoreTester.test(retrofit);

        //执行请求
        retrofit.callAsync(in, new LKBaseInvokeCallback<ScoreIn, ScoreOut>() {

            @Override
            protected void success(Context context, ScoreIn scoreIn, ScoreOut responseDatas) {
                LKToast.showTip(ScoreDialogFragment.this.getContext(), R.string.dialog_fragment_score__invoke_success_result);
                afterInvokeScore();
                ScoreDialogFragment.this.dismiss();
            }

            @Override
            protected void busError(Context context, ScoreIn scoreIn, int errorCode, LKErrorMessageBean.TYPE errorType, LKErrorMessageBean errorBean) {
                super.busError(context, scoreIn, errorCode, errorType, errorBean);
                afterInvokeScore();
            }

            @Override
            public void connectError(Context context, String requestId, ScoreIn scoreIn, DialogInterface dialog) {
                super.connectError(context, requestId, scoreIn, dialog);
                afterInvokeScore();
            }

            @Override
            public void timeoutError(Context context, String requestId, ScoreIn scoreIn, DialogInterface dialog) {
                super.timeoutError(context, requestId, scoreIn, dialog);
                afterInvokeScore();
            }

        });
    }

    /**
     * 开始请求评分
     */
    private void beforeInvokeScore() {
        buttonView.setVisibility(View.GONE);
        loadingView.setVisibility(View.VISIBLE);
    }

    /**
     * 结束请求评分
     */
    private void afterInvokeScore() {
        buttonView.setVisibility(View.VISIBLE);
        loadingView.setVisibility(View.GONE);
    }

}
