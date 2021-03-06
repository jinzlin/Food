package com.ssk.food.di.component;

import android.app.Activity;

import com.ssk.food.di.module.RequestActionModule;
import com.ssk.food.di.module.TableBarModule;
import com.ssk.food.ui.handled.HandledFragment;
import com.ssk.food.ui.login.forgetpwd.ForgetPwdActivity;
import com.ssk.food.ui.login.login.LoginFragment;
import com.ssk.food.ui.login.register.RegisterFragment;
import com.ssk.food.ui.main.MainActivity;
import com.ssk.food.ui.my.MyFragment;
import com.ssk.food.ui.order.OrderFragment;
import com.ssk.food.ui.shop.ShopFragment;
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


    void inject(MainActivity mainActivity);

    /**
     * 登录
     * @param loginFragment 登录
     */
    void inject(LoginFragment loginFragment);

    /**
     * 注册
     * @param registerFragment 注册
     */
    void inject(RegisterFragment registerFragment);

    /**
     * 忘记密码
     * @param forgetPwdActivity 忘记密码
     */
    void inject(ForgetPwdActivity forgetPwdActivity);

    /**
     * 待处理
     */
    void inject(HandledFragment handledFragment);

    /**
     * 店铺
     */
    void inject(ShopFragment shopFragment);

    /**
     * 订单
     */
    void inject(OrderFragment orderFragment);

    /**
     * 我的
     */
    void inject(MyFragment myFragment);
}
