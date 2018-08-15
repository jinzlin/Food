package com.zhtx.mindlib.di.module;

import android.content.Context;


import com.zhtx.mindlib.base.BaseApp;
import com.zhtx.mindlib.utils.MSharedPreferences;
import com.zhtx.mindlib.utils.MToast;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * 作者: ljz.
 * @date 2017/11/15
 * 描述:App模型
 */
@Module
public class AppModule {

    private Context context;
    private String spName;

    public AppModule(BaseApp context, String spName){
        this.context = context;
        this.spName = spName;
    }

    @Provides
    @Singleton
    Context providerAppContext(){
        return context;
    }


    @Provides
    @Singleton
    MSharedPreferences providesMSharedPreferences() {
        return new MSharedPreferences(context, spName);
    }

    @Provides
    @Singleton
    MToast providesMToast() {
        return new MToast(context);
    }

}
