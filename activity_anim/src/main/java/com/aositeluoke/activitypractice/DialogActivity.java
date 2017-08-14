package com.aositeluoke.activitypractice;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Window;

/**
 * 类描述:使用Dialog主题的Activity
 * 作者:xues
 * 时间:2017年08月08日
 */

public class DialogActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        Window window = getWindow();
        window.setGravity(Gravity.BOTTOM);
        DisplayMetrics dm = new DisplayMetrics();
        window.getWindowManager().getDefaultDisplay().getMetrics(dm);
        window.setLayout(dm.widthPixels, window.getAttributes().height);
//        window.setBackgroundDrawable(new ColorDrawable(0x00000000));

    }
}
