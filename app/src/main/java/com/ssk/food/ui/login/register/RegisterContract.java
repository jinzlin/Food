package com.ssk.food.ui.login.register;

import com.zhtx.mindlib.base.BaseContract;

/**
 * 作者:
 * 描述:注册
 */

public interface RegisterContract {

    interface ContView extends BaseContract.BaseView {

        /**
         * 获取验证码
         */
        void getCode();

        /**
         * 下一步
         */
        void next();

        /**
         * 下一步成功
         */
        void nextSuccess();

        /**
         * 清空密码
         */
        void delPwd();

        /**
         * 显示隐藏密码
         */
        void showPwd1();

        /**
         * 显示隐藏密码
         */
        void showPwd2();

        /**
         * 注册
         */
        void register();
    }

    interface ContPresenter<T> extends BaseContract.BasePresenter<T> {

        void requestGetCode(String phone);

        void requestNext(String phone, String code);

        void requestRegister(String account, String pwd);
    }
}
