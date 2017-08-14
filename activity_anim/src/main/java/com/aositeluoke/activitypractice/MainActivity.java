package com.aositeluoke.activitypractice;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Method;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private static final String TAG = "MainActivity";
    private View statusBar;
    private View navigationBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       /*
       * MainActivity(HomeFragment CategoryFragment CartFragment MineFragment)沉浸式状态栏方案
       * 4.4之前的做法：4.4之前的状态栏和导航栏区域不可使用，将状态栏控件和导航栏控件隐藏掉
       * 4.4(含)之后的做法：4.4(含)至5.0(未含有)，在Fragment中添加状态栏控件和导航栏控件，在代码中设置状态栏和导航栏的高度(参考开源项目)，
       * 判断当前手机是否有导航栏，没有导航栏的手机把导航栏控件隐藏掉
       */
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_left2right).setOnClickListener(this);
        findViewById(R.id.btn_dialog_activity).setOnClickListener(this);
        findViewById(R.id.btn_alpha).setOnClickListener(this);
        findViewById(R.id.btn_scale).setOnClickListener(this);
        findViewById(R.id.btn_sort).setOnClickListener(this);
        findViewById(R.id.btn_bottom_in_out_activity).setOnClickListener(this);
        navigationBar = findViewById(R.id.iv_navigation_bar);
        statusBar = findViewById(R.id.view_status_bar);


        //设置状态栏和标题栏的高度
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //大于等于4.4设置状态栏高度和导航栏高度
            //设置状态栏的背景
            statusBar.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
            //设置状态栏的高度
            ViewGroup.LayoutParams statusBarParams = statusBar.getLayoutParams();
            statusBarParams.height = getStatusBarHeight(this);
            statusBar.setLayoutParams(statusBarParams);
            statusBar.setVisibility(View.VISIBLE);
            //有导航栏
            if (checkDeviceHasNavigationBar(getApplication())) {
                //设置导航栏的背景颜色
                navigationBar.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
                //设置导航栏的高度
                ViewGroup.LayoutParams params = navigationBar.getLayoutParams();
                params.height = getNavigationHeight(this);
                navigationBar.setLayoutParams(params);
                navigationBar.setVisibility(View.VISIBLE);
            } else {
                //无导航栏
                navigationBar.setVisibility(View.GONE);
            }

        } else {
            //小于4.4 导航栏区域和状态栏区域不可使用
            statusBar.setVisibility(View.GONE);
            navigationBar.setVisibility(View.GONE);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_left2right:
                navigationBar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                startActivity(new Intent(this, RightInRightOutActivity.class));
                break;
            case R.id.btn_dialog_activity:
                navigationBar.setBackgroundColor(Color.parseColor("#ff0000"));
                startActivity(new Intent(this, DialogActivity.class));
                break;
            case R.id.btn_alpha:
                navigationBar.setBackgroundColor(Color.parseColor("#ffF000"));
                startActivity(new Intent(this, AlphaActivity.class));
                break;
            case R.id.btn_scale:
                navigationBar.setBackgroundColor(Color.parseColor("#ffFf00"));
                startActivity(new Intent(this, ScaleActivity.class));
                break;
            case R.id.btn_bottom_in_out_activity:
                navigationBar.setBackgroundColor(Color.parseColor("#ffFFF0"));
                startActivity(new Intent(this, BottomInOutActivity.class));
                break;
            case R.id.btn_sort:
                sort();
                break;
        }
    }


    public void startActivity(Bundle bundle, Class<AppCompatActivity> clazz) {
        //如果当前系统的版本大于等于5.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startActivityAfterLollipop(bundle, clazz);
        } else {
            startActivityOther(bundle, clazz);
        }

    }

    /**
     * 5.0以上使用该方式启动Activity
     *
     * @param bundle
     * @param clazz
     */
    @TargetApi(21)
    private void startActivityAfterLollipop(Bundle bundle, Class<AppCompatActivity> clazz) {
        Intent intent = new Intent(this, clazz);
        if (bundle != null)
            intent.putExtra("bundle", bundle);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());

    }

    /**
     * 5.0之前使用该方式
     *
     * @param bundle
     * @param clazz
     */
    private void startActivityOther(Bundle bundle, Class<AppCompatActivity> clazz) {
        Intent intent = new Intent(this, clazz);
        if (bundle != null)
            intent.putExtra("bundle", bundle);
        startActivity(intent);
    }

    private int[] mNumbers = new int[]{2, 1, 4, 35, 6, 21, 32, 45, 21, 23, 8, 9, 12};

    /**
     * 冒泡排序算法
     */
    private void sort() {
        StringBuilder sb = new StringBuilder();
        for (int mNumber : mNumbers) {
            if (sb.length() > 0) {
                sb.append(",");
            }
            sb.append(mNumber);
        }

        Log.i("原来的数据", sb + "");

        int length = mNumbers.length;
        for (int i = 0; i < length - 1; i++) {
            for (int j = 0; j < length - i - 1; j++) {
                if (mNumbers[j] < mNumbers[j + 1]) {
                    int temp = mNumbers[j];
                    mNumbers[j] = mNumbers[j + 1];
                    mNumbers[j + 1] = temp;
                }

            }
        }

        sb.delete(0, sb.length());

        for (int mNumber : mNumbers) {
            if (sb.length() > 0) {
                sb.append(",");
            }
            sb.append(mNumber);
        }

        Log.i("", sb + "");

    }


    /**
     * 检查是否有导航栏
     *
     * @param context
     * @return
     */
    public boolean checkDeviceHasNavigationBar(Context context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = ((String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys"));
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hasNavigationBar;

    }


    /**
     * 切换Fragment
     *
     * @param clazz     例如：HomeFragment.class
     * @param contentID Activity放置Fragment的FrameLayout id
     */
    protected void changeFragment(Class<Fragment> clazz, @IdRes int contentID) {

        String tagName = clazz.getName();
        FragmentManager manager = getSupportFragmentManager();
        Fragment tempFragment = manager.findFragmentByTag(tagName);
        FragmentTransaction transaction = manager.beginTransaction();

        if (tempFragment == null) {
            try {
                //初始化Fragment
                tempFragment = (Fragment) Class.forName(tagName).newInstance();
                //往Activity中添加Fragment
                transaction.add(contentID, tempFragment, tagName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        //遍历已经添加到Activity的Fragment
        List<Fragment> fragments = manager.getFragments();
        if (fragments != null && !fragments.isEmpty()) {
            for (Fragment fragment : fragments) {
                //如果是当前的tagName则显示Fragment否则隐藏Fragment
                if (fragment.getTag().equals(tagName)) {
                    transaction.show(fragment);
                } else {
                    transaction.hide(fragment);
                }

            }
        }
        //提交事务
        transaction.commit();
    }


    /**
     * 获取状态栏的高度
     *
     * @param context
     * @return
     */
    private int getStatusBarHeight(Context context) {
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId);
    }

    /**
     * 获取导航栏的高度
     *
     * @param context
     * @return
     */
    public int getNavigationHeight(Context context) {
        int resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId);
    }


}
