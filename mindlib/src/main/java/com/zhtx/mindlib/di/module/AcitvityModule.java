package com.zhtx.mindlib.di.module;

import android.app.Activity;

import com.zhtx.mindlib.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * 作者: ljz.
 * @date 2017/11/15
 * 描述：Activity模型
 */
@Module
public class AcitvityModule {

    private Activity activity;

    public AcitvityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    Activity provideView() {
        return activity;
    }

}
