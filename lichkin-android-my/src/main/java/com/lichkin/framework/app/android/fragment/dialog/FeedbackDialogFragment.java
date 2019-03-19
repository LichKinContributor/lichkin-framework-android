package com.lichkin.framework.app.android.fragment.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.lichkin.android.my.R;
import com.lichkin.application.invokers.Feedback.FeedbackIn;
import com.lichkin.application.invokers.Feedback.FeedbackInvoker;
import com.lichkin.application.invokers.Feedback.FeedbackOut;
import com.lichkin.application.invokers.Feedback.FeedbackTester;
import com.lichkin.framework.app.android.bean.LKScreenBean;
import com.lichkin.framework.app.android.callback.LKBtnCallback;
import com.lichkin.framework.app.android.callback.impl.LKBaseInvokeCallback;
import com.lichkin.framework.app.android.utils.LKAndroidUtils;
import com.lichkin.framework.app.android.utils.LKBase64;
import com.lichkin.framework.app.android.utils.LKDialog;
import com.lichkin.framework.app.android.utils.LKImageLoader;
import com.lichkin.framework.app.android.utils.LKRetrofit;
import com.lichkin.framework.app.android.utils.LKToast;
import com.lichkin.framework.defines.beans.LKErrorMessageBean;
import com.wang.avi.AVLoadingIndicatorView;

import org.devio.takephoto.app.TakePhoto;
import org.devio.takephoto.app.TakePhotoImpl;
import org.devio.takephoto.compress.CompressConfig;
import org.devio.takephoto.model.CropOptions;
import org.devio.takephoto.model.InvokeParam;
import org.devio.takephoto.model.TContextWrap;
import org.devio.takephoto.model.TResult;
import org.devio.takephoto.model.TakePhotoOptions;
import org.devio.takephoto.permission.InvokeListener;
import org.devio.takephoto.permission.PermissionManager;
import org.devio.takephoto.permission.TakePhotoInvocationHandler;

import java.io.File;

/**
 * 反馈
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public class FeedbackDialogFragment extends DialogFragment implements TakePhoto.TakeResultListener, InvokeListener {

    /** 内容 */
    EditText contentView;
    /** 加载 */
    AVLoadingIndicatorView loadingView;
    /** 图片 */
    ImageView imageView;
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
        layoutParams.height = LKAndroidUtils.getPxValueByDpValue(475);
        window.setAttributes(layoutParams);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_feedback, container);

        contentView = view.findViewById(R.id.content);
        loadingView = view.findViewById(R.id.loading);
        imageView = view.findViewById(R.id.img);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LKDialog(FeedbackDialogFragment.this.getContext(), true, null, LKAndroidUtils.getString(R.string.photo_choose_title))
                        .setPositiveButton(LKAndroidUtils.getString(R.string.btn_positive_name_gallery), new LKBtnCallback() {
                            @Override
                            public void call(Context context, DialogInterface dialog) {
                                takePhoto(true);
                            }
                        })
                        .setNegativeButton(LKAndroidUtils.getString(R.string.btn_negative_name_camera), new LKBtnCallback() {
                            @Override
                            public void call(Context context, DialogInterface dialog) {
                                takePhoto(false);
                            }
                        })
                        .show();
            }
        });
        buttonView = view.findViewById(R.id.btn);
        buttonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invokeFeedback();
            }
        });

        return view;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        contentView.setText("");
        imgBase64 = null;
    }

    /**
     * 请求反馈
     */
    private void invokeFeedback() {
        beforeInvokeFeedback();

        //校验内容
        String content = contentView.getText().toString().trim();
        if ("".equals(content)) {
            LKToast.showTip(FeedbackDialogFragment.this.getContext(), R.string.not_empty);
            contentView.setFocusable(true);
            afterInvokeFeedback();
            return;
        }

        //请求参数
        FeedbackIn in = new FeedbackIn(content, imgBase64);

        //创建请求对象
        final LKRetrofit<FeedbackIn, FeedbackOut> retrofit = new LKRetrofit<>(this.getActivity(), FeedbackInvoker.class);

        //测试代码
        FeedbackTester.test(retrofit);

        //执行请求
        retrofit.callAsync(in, new LKBaseInvokeCallback<FeedbackIn, FeedbackOut>() {

            @Override
            protected void success(Context context, FeedbackIn FeedbackIn, FeedbackOut responseDatas) {
                LKToast.showTip(FeedbackDialogFragment.this.getContext(), R.string.dialog_fragment_feedback__invoke_success_result);
                afterInvokeFeedback();
                FeedbackDialogFragment.this.dismiss();
            }

            @Override
            protected void busError(Context context, FeedbackIn feedbackIn, int errorCode, LKErrorMessageBean.TYPE errorType, LKErrorMessageBean errorBean) {
                super.busError(context, feedbackIn, errorCode, errorType, errorBean);
                afterInvokeFeedback();
            }

            @Override
            public void connectError(Context context, String requestId, FeedbackIn feedbackIn, DialogInterface dialog) {
                super.connectError(context, requestId, feedbackIn, dialog);
                afterInvokeFeedback();
            }

            @Override
            public void timeoutError(Context context, String requestId, FeedbackIn feedbackIn, DialogInterface dialog) {
                super.timeoutError(context, requestId, feedbackIn, dialog);
                afterInvokeFeedback();
            }

        });
    }

    /**
     * 开始请求反馈
     */
    private void beforeInvokeFeedback() {
        buttonView.setVisibility(View.GONE);
        loadingView.setVisibility(View.VISIBLE);
    }

    /**
     * 结束请求反馈
     */
    private void afterInvokeFeedback() {
        buttonView.setVisibility(View.VISIBLE);
        loadingView.setVisibility(View.GONE);
    }

    private InvokeParam invokeParam;
    private TakePhoto takePhoto;
    private String imgDir = Environment.getExternalStorageDirectory() + "/temp";
    private String imgBase64;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
        takePhoto.onCreate(savedInstanceState);
        File imgDirFile = new File(imgDir);
        if (!imgDirFile.exists() && !imgDirFile.mkdirs()) {
            this.dismiss();
            LKToast.showTip(FeedbackDialogFragment.this.getContext(), R.string.can_not_use_storage);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        takePhoto.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        takePhoto.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(getActivity(), type, invokeParam, this);
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }

    @Override
    public void takeSuccess(TResult result) {
        String imgPath = "file:///" + result.getImage().getCompressPath();
        imgBase64 = LKBase64.toBase64(imgPath);
        LKImageLoader.load(imgPath, imageView);
    }

    @Override
    public void takeFail(TResult result, String msg) {

    }

    @Override
    public void takeCancel() {

    }

    /**
     * 调用取照片
     * @param fromGallery true:相册;false:拍照.
     */
    private void takePhoto(boolean fromGallery) {
        Uri uri = Uri.fromFile(new File(imgDir, "/" + System.currentTimeMillis() + ".jpg"));
        takePhoto.onEnableCompress(new CompressConfig.Builder().setMaxSize(102400)
                .setMaxPixel(1024)
                .enableReserveRaw(false)
                .create(), false);
        takePhoto.setTakePhotoOptions(new TakePhotoOptions.Builder().setWithOwnGallery(false).create());
        CropOptions cropOptions = new CropOptions.Builder().setWithOwnCrop(false).create();
        if (fromGallery) {
            takePhoto.onPickFromGalleryWithCrop(uri, cropOptions);
        } else {
            takePhoto.onPickFromCaptureWithCrop(uri, cropOptions);
        }
    }

}
