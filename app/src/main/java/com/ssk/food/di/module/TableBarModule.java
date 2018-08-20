package com.ssk.food.di.module;

import dagger.Module;
import dagger.Provides;

/**
 * 作者: ljz.
 * @date 2018/6/7.
 * 描述：
 */
@Module
public class TableBarModule {

    String type;

    public TableBarModule(String type) {
        this.type = type;
    }

    @Provides
    public String getType(){
        return type;
    }
}
