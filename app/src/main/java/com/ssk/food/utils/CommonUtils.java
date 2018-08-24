package com.ssk.food.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.ssk.food.R;
import com.zhtx.mindlib.base.BaseApp;
import com.zhtx.mindlib.utils.L;
import com.zhtx.mindlib.utils.MSharedPreferences;
import com.zhtx.mindlib.utils.MToast;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 作者: ljz.
 * @date 2018/6/19.
 * 描述：
 */

public class CommonUtils {

    private static MSharedPreferences mSharedPreferences = BaseApp.getInstance().getAppComponent().getMSharedPreferences();
    private static MToast mToast = BaseApp.getInstance().getAppComponent().getMToast();


    /**
     * 设置标题栏
     *
     * @param context activity
     * @param title   标题
     */
    public static void setTitleBar(final Activity context, String title) {
        setTitleBar(context, title, null, null);
    }

    /**
     * 设置标题栏
     *
     * @param context         activity
     * @param title           标题
     * @param more            更多
     * @param onClickListener 更多点击监听
     */
    public static void setTitleBar(final Activity context, String title, String more, View.OnClickListener onClickListener) {
        ImageView ivTitleBarBack = context.findViewById(R.id.iv_titlebar_back);
        TextView tvTitleBarTitle = context.findViewById(R.id.tv_titlebar_title);
        TextView tvTitleBarMore = context.findViewById(R.id.tv_titlebar_more);
        tvTitleBarTitle.setText(title);
        ivTitleBarBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                View view = context.getWindow().peekDecorView();
                if (view != null) {
                    InputMethodManager inputmanger = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                context.finish();
            }
        });
        if (null != more) {
            tvTitleBarMore.setText(more);
        }
        if (null != onClickListener) {
            tvTitleBarMore.setOnClickListener(onClickListener);
        }
    }

    /**
     * 设置透明
     */
    public static void setTransparentForWindow(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
            activity.getWindow()
                    .getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow()
                    .setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }


    /**
     * 保存HashMap
     *
     * @param status  存储key
     * @param hashMap 存储值
     */
    public static void saveMap(String status, HashMap<String, Object> hashMap) {
        mSharedPreferences.setInt(status, hashMap.size());
        int i = 0;
        for (HashMap.Entry<String, Object> entry : hashMap.entrySet()) {
            mSharedPreferences.remove(status + "_key_" + i);
            mSharedPreferences.setString(status + "_key_" + i, entry.getKey());
            mSharedPreferences.remove(status + "_value_" + i);
            mSharedPreferences.setString(status + "_value_" + i, (String) entry.getValue());
            i++;
        }
    }

    /**
     * 取出HashMap
     *
     * @param status 存储key
     * @return HashMap
     */
    public static HashMap<String, Object> loadMap(String status) {
        HashMap<String, Object> hashMap = new HashMap<>();
        int size = mSharedPreferences.getInt(status, 0);
        for (int i = 0; i < size; i++) {
            String key = mSharedPreferences.getString(status + "_key_" + i, null);
            String value = mSharedPreferences.getString(status + "_value_" + i, null);
            hashMap.put(key, value);
        }
        return hashMap;
    }

    /**
     * 更新HashMap
     *
     * @param status  存储key
     * @param hashMap HashMap
     */
    public static void updataMap(String status, HashMap<String, Object> hashMap) {
        int size = mSharedPreferences.getInt(status, 0);
        for (int i = 0; i < size; i++) {
            Iterator iter = hashMap.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                String key1 = (String) entry.getKey();
                String key2 = mSharedPreferences.getString(status + "_key_" + i, null);
                if (key1.equals(key2)) {
                    mSharedPreferences.remove(status + "_value_" + i);
                    mSharedPreferences.setString(status + "_value_" + i, (String) entry.getValue());
                }
            }
        }
    }

    /**
     * 判断是否负责人
     *
     * @return true负责人
     */
//    public static boolean isAdministrator() {
//        String statusType = mSharedPreferences.getString(ConstantUtlis.SP_STATUS_TYPE, "");
//        return "负责人".equals(statusType);
//    }

    /**
     * 判断是否负责人
     *
     * @return true负责人
     */
//    public static boolean isAdministrator2() {
//        if (!isAdministrator()) {
//            mToast.showToast("无权限操作");
//        }
//        return isAdministrator();
//    }

    public static void exitLogon() {
        mSharedPreferences.setBoolean(ConstantUtlis.SP_IS_LOGIN, false);
        try {
            BaseApp.getInstance().getmActivityStack().finishAllActivity();
        } catch (Exception e) {
            L.e("Exception:" + e);
        }
    }


    /**
     * 与多个字符串相等判断
     *
     * @param str     比较字符串
     * @param targrts 目标字符串
     * @return
     */
    public static boolean isEquals(String str, String... targrts) {
        for (int i = 0; i < targrts.length; i++) {
            if (str.equals(targrts[i])) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断字符串是否都是字母
     *
     * @param str
     * @return
     */
    public static boolean isStr(String str) {
        String regex = "[a-zA-Z]{1,}";
        Matcher m = Pattern.compile(regex).matcher(str);
        return m.matches();
    }

    /**
     * 判断字符串是否都是数字
     *
     * @param str
     * @return
     */
    //判断字符串都是数字
    public static boolean isDigit(String str) {
        Pattern pattern = Pattern.compile("[0-9]{1,}");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }


}
