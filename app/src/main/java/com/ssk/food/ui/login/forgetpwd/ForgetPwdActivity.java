package com.ssk.food.ui.login.forgetpwd;

import com.ssk.food.App;
import com.zhtx.mindlib.base.BaseActivity;
import com.zhtx.mindlib.di.module.AcitvityModule;
import com.ssk.food.R;
import com.ssk.food.di.component.DaggerActivityComponent;
import com.ssk.food.di.module.RequestActionModule;
import com.ssk.food.server.RequestAction;
import com.ssk.food.utils.CommonUtils;

/**
 * 作者:
 * 描述:忘记密码
 */

public class ForgetPwdActivity extends BaseActivity<ForgetPwdPresenter> implements ForgetPwdContract.ContView {

    @Override
    public int initResource() {
        return R.layout.activity_forget_pwd;
    }

    @Override
    public void initInject() {
        DaggerActivityComponent
                .builder()
                .appComponent(App.getInstance().getAppComponent())
                .acitvityModule(new AcitvityModule(this))
                .requestActionModule(new RequestActionModule(new RequestAction()))
                .build().inject(this);
    }

    @Override
    public void initData() {
        CommonUtils.setTitleBar(this, getString(R.string.ForgetPwd_title));
    }
}
