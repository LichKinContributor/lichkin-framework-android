package com.lichkin.framework.app.android.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lichkin.android.buttons.R;
import com.lichkin.framework.app.android.utils.LKAndroidUtils;

/**
 * 列表对象
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public class ListItem extends LinearLayout {

    public ListItem(Context context) {
        super(context);
        init(context, null);
    }

    public ListItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ListItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
    private Drawable icon;
    private String text;
    private boolean firstItem;
    private boolean lastItem;

    /**
     * 初始化参数
     */
    private void initParams(Context context, @Nullable AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ListItem);

            icon = typedArray.getDrawable(R.styleable.ListItem_icon);
            text = typedArray.getString(R.styleable.ListItem_text);
            firstItem = typedArray.getBoolean(R.styleable.ListItem_firstItem, false);
            lastItem = typedArray.getBoolean(R.styleable.ListItem_lastItem, false);

            typedArray.recycle();
        }
    }

    // 自定义布局
    private ImageView img;
    private TextView txt;
    private LinearLayout topLine;
    private LinearLayout bottomLine;

    /**
     * 初始化布局
     */
    private void initLayouts(Context context) {
        LayoutInflater.from(context).inflate(R.layout.list_item, this, true);

        LinearLayout container = this.findViewById(R.id.container);
        container.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ListItem.this.performClick();
            }
        });

        img = this.findViewById(R.id.img);
        txt = this.findViewById(R.id.txt);
        topLine = this.findViewById(R.id.topLine);
        bottomLine = this.findViewById(R.id.bottomLine);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        if (icon == null) {
            img.setVisibility(GONE);
            LayoutParams layoutParams = (LayoutParams) txt.getLayoutParams();
            layoutParams.setMarginStart((int) LKAndroidUtils.getDimension(R.dimen.dimen_list_item_margin_left));
            txt.setLayoutParams(layoutParams);
        } else {
            img.setVisibility(VISIBLE);
            img.setBackground(icon);
        }

        txt.setText(text == null ? "" : text);

        topLine.setVisibility(firstItem ? VISIBLE : GONE);
        bottomLine.setVisibility(lastItem ? GONE : VISIBLE);
    }

}
