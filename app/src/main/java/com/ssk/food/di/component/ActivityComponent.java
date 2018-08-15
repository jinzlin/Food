package com.ssk.food.di.component;

import android.app.Activity;

import com.ssk.food.di.module.RequestActionModule;
import com.zhtx.mindlib.di.component.AppComponent;
import com.zhtx.mindlib.di.module.AcitvityModule;
import com.zhtx.mindlib.di.scope.ActivityScope;

import dagger.Component;


/**
 * 作者: ljz.
 * @date 2017/11/15
 * 描述：ActivityComponent
 */
@ActivityScope
@Component(dependencies = AppComponent.class, modules = {AcitvityModule.class, RequestActionModule.class})
public interface ActivityComponent {

    Activity getActivity();


}
