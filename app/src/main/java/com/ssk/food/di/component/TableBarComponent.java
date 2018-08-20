package com.ssk.food.di.component;


import com.ssk.food.di.module.TableBarModule;
import com.ssk.food.ui.login.login.LoginFragment;
import com.ssk.food.ui.login.register.RegisterFragment;

import dagger.Subcomponent;

/**
 * Author by ljz
 * PS:
 */
@Subcomponent(modules = TableBarModule.class)
public interface TableBarComponent {

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

}
