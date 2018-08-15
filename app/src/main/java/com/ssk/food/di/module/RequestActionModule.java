package com.ssk.food.di.module;


import com.ssk.food.server.RequestAction;

import dagger.Module;
import dagger.Provides;

/**
 * 作者: ljz.
 * @date 2018/6/8.
 * 描述：
 */
@Module
public class RequestActionModule {

    RequestAction requestAction;

    public RequestActionModule(RequestAction requestAction) {
        this.requestAction = requestAction;
    }

    @Provides
    public RequestAction providesRequestAction() {
        return requestAction;
    }


}
