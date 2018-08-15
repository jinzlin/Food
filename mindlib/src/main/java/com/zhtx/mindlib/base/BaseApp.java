package com.zhtx.mindlib.base;

import android.app.Application;

import com.zhtx.mindlib.di.component.AppComponent;
import com.zhtx.mindlib.di.component
        .DaggerAppComponent;
import com.zhtx.mindlib.di.module.ApiModule;
import com.zhtx.mindlib.di.module.AppModule;
import com.zhtx.mindlib.utils.ActivityStack;
import com.zhy.autolayout.config.AutoLayoutConifg;


/**
 * 作者: ljz.
 * @date 2017/11/15
 */

public class BaseApp extends Application {

    private static BaseApp instance;

    AppComponent appComponent;

    public ActivityStack mActivityStack = null; // Activity 栈

    public String mApiUrl;      // 接口地址
    public String mSpName;      // 数据存储名字

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        // 自动适配
        AutoLayoutConifg.getInstance().useDeviceSize();
        // 初始化Activity 栈
        mActivityStack = ActivityStack.init();
    }

    public static BaseApp getInstance() {
        return instance;
    }

    public ActivityStack getmActivityStack() {
        return mActivityStack;
    }

    public AppComponent getAppComponent() {
        if (appComponent == null){
            setAppComponent();
        }
        return appComponent;
    }

    public void setAppComponent() {
        appComponent = DaggerAppComponent.builder()
                .apiModule(new ApiModule(getmApiUrl()))
                .appModule(new AppModule(this, getmSpName()))
                .build();
    }

    public String getmApiUrl() {
        return mApiUrl;
    }

    public void setmApiUrl(String mApiUrl) {
        this.mApiUrl = mApiUrl;
    }

    public String getmSpName() {
        return mSpName;
    }

    public void setmSpName(String mSpName) {
        this.mSpName = mSpName;
    }
}
