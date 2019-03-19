package com.lichkin.framework.app.android.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lichkin.android.core.R;

/**
 * 标题
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public class Title extends LinearLayout {

    public Title(Context context) {
        super(context);
        init(context, null);
    }

    public Title(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public Title(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    /**
     * 初始化
     */
    private void init(Context context, @Nullable AttributeSet attrs) {
        initParams(context, attrs);
        initLayouts(context);
    }

    // 自定义参数
    private String text;

    /**
     * 初始化参数
     */
    private void initParams(Context context, @Nullable AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Title);

            text = typedArray.getString(R.styleable.Title_text);

            typedArray.recycle();
        }
    }

    // 自定义布局
    private TextView txt;

    /**
     * 初始化布局
     */
    private void initLayouts(Context context) {
        LayoutInflater.from(context).inflate(R.layout.title, this, true);

        txt = this.findViewById(R.id.txt);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) Title.this.getContext()).finish();
            }
        });
        txt.setText(text == null ? "" : text);
    }

}
