package com.lichkin.framework.app.android.fragment.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lichkin.android.my.R;
import com.lichkin.framework.app.android.utils.LKAndroidUtils;

/**
 * 等级
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public class LevelDialogFragment extends DialogFragment {

    private static final int LINE_HEIGHT = 20;
    private static final int STAR_SIZE = 16;

    @Override
    public void onStart() {
        super.onStart();
        //设置宽高
        Window window = getDialog().getWindow();
        if (window == null) {
            return;
        }
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = LKAndroidUtils.getPxValueByDpValue(300);
        layoutParams.height = LKAndroidUtils.getPxValueByDpValue(350);
        window.setAttributes(layoutParams);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_level, container);

        LinearLayout containerLayout = view.findViewById(R.id.container);

        Context context = getContext();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LKAndroidUtils.getPxValueByDpValue(300), ViewGroup.LayoutParams.WRAP_CONTENT);
        for (int i = 1; i <= 63; i++) {
            LinearLayout starContainer = new LinearLayout(context);
            starContainer.setOrientation(LinearLayout.HORIZONTAL);
            starContainer.setLayoutParams(layoutParams);
            starContainer.setGravity(Gravity.CENTER_VERTICAL);
            TextView textView = new TextView(context);
            textView.setWidth(LKAndroidUtils.getPxValueByDpValue(48));
            textView.setHeight(LKAndroidUtils.getPxValueByDpValue(LINE_HEIGHT));
            textView.setTextColor(Color.BLACK);
            textView.setText(LKAndroidUtils.getString(R.string.fragment_my__text_level) + i);
            starContainer.addView(textView);
            inflateLevel(starContainer, i);
            containerLayout.addView(starContainer);
        }

        return view;
    }

    /**
     * 填充等级
     * @param starContainer 容器对象
     * @param level 等级
     */
    public static void inflateLevel(LinearLayout starContainer, int level) {
        Context context = starContainer.getContext();
        int red = level / 16;
        int mod = level % 16;
        int green = mod / 4;
        int yellow = mod % 4;
        for (int i = 0; i < red; i++) {
            ImageView imageView = new ImageView(context);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(LKAndroidUtils.getPxValueByDpValue(STAR_SIZE), LKAndroidUtils.getPxValueByDpValue(STAR_SIZE)));
            imageView.setImageDrawable(LKAndroidUtils.getDrawable(R.drawable.level_star_red));
            starContainer.addView(imageView);
        }
        for (int i = 0; i < green; i++) {
            ImageView imageView = new ImageView(context);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(LKAndroidUtils.getPxValueByDpValue(STAR_SIZE), LKAndroidUtils.getPxValueByDpValue(STAR_SIZE)));
            imageView.setImageDrawable(LKAndroidUtils.getDrawable(R.drawable.level_star_green));
            starContainer.addView(imageView);
        }
        for (int i = 0; i < yellow; i++) {
            ImageView imageView = new ImageView(context);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(LKAndroidUtils.getPxValueByDpValue(STAR_SIZE), LKAndroidUtils.getPxValueByDpValue(STAR_SIZE)));
            imageView.setImageDrawable(LKAndroidUtils.getDrawable(R.drawable.level_star_yellow));
            starContainer.addView(imageView);
        }
    }

}
