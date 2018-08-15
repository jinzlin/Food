package com.zhtx.mindlib.di.component;

import android.content.Context;

import com.zhtx.mindlib.di.module.ApiModule;
import com.zhtx.mindlib.di.module.AppModule;
import com.zhtx.mindlib.network.RetrofitHelper;
import com.zhtx.mindlib.utils.MSharedPreferences;
import com.zhtx.mindlib.utils.MToast;

import javax.inject.Singleton;

import dagger.Component;

/**
 * 作者: ljz.
 * @date 2017/11/15
 * 描述：AppComponent
 */
@Singleton
@Component(modules = {AppModule.class, ApiModule.class})
public interface AppComponent {

    RetrofitHelper getRetrofitHelper();

    Context getContext();

    MSharedPreferences getMSharedPreferences();

    MToast getMToast();

}
