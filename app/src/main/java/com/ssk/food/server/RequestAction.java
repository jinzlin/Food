package com.ssk.food.server;

import com.zhtx.mindlib.base.BaseApp;
import com.zhtx.mindlib.network.HttpCallBack;
import com.zhtx.mindlib.network.RetrofitHelper;
import com.zhtx.mindlib.utils.EnDecryptUtlis;
import com.zhtx.mindlib.utils.JsonUtils;
import com.zhtx.mindlib.utils.L;
import com.zhtx.mindlib.utils.MD5Utlis;
import com.zhtx.mindlib.utils.MSharedPreferences;
import com.zhtx.mindlib.utils.MToast;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.Disposable;

/**
 * 作者: ljz.
 * @date 2018/6/6.
 * 描述：
 */

public class RequestAction {


    private MSharedPreferences mSharedPreferences = BaseApp.getInstance().getAppComponent().getMSharedPreferences();
    private MToast mToast = BaseApp.getInstance().getAppComponent().getMToast();

    public RequestAction() {
    }


    private static Map<String, String> getParams(String func, HashMap<String, Object> hashMap) {
        String randomCode = MD5Utlis.Md5(func);
        Map<String, String> map = new HashMap<>();
        map.put("func", func);
        map.put("words", randomCode + EnDecryptUtlis.aesEncrypt(JsonUtils.createJSON(hashMap).toString(), randomCode));

        for (Object key : hashMap.keySet()) {
            L.e(func + "----------requestTag", key + "=" + hashMap.get(key));
        }
        L.e("funcwords", map.toString());
        return map;
    }


    /**
     * 账号密码登录
     */
    public final static int TAG_ACCOUNT_LOGIN = 0;

    public Disposable accountLogin(RetrofitHelper retrofitHelper, HttpCallBack httpCallBack, String account, String pwd) {
        HashMap<String, Object> map = new HashMap<>(2);
        map.put("account", account);
        map.put("LoginPwd", MD5Utlis.Md5(pwd));
        return retrofitHelper.getDisposable(TAG_ACCOUNT_LOGIN, getParams("y_GasStation_login", map), httpCallBack, true);
    }

    /**
     * 手机验证码登录
     */
    public final static int TAG_PHONE_LOGIN = 1;

    public Disposable phoneLogin(RetrofitHelper retrofitHelper, HttpCallBack httpCallBack, String phone, String code) {
        HashMap<String, Object> map = new HashMap<>(2);

        return retrofitHelper.getDisposable(TAG_PHONE_LOGIN, getParams("y_GasStation_login", map), httpCallBack, true);
    }

    /**
     * 获取登录验证码
     */
    public final static int TAG_GET_LOGIN_CODE = 2;

    public Disposable getLoginCode(RetrofitHelper retrofitHelper, HttpCallBack httpCallBack, String phone) {
        HashMap<String, Object> map = new HashMap<>(2);

        return retrofitHelper.getDisposable(TAG_GET_LOGIN_CODE, getParams("y_GasStation_login", map), httpCallBack, true);
    }

    /**
     * 获取注册验证码
     */
    public final static int TAG_GET_REGISTER_CODE = 3;

    public Disposable getRegisterCode(RetrofitHelper retrofitHelper, HttpCallBack httpCallBack, String phone) {
        HashMap<String, Object> map = new HashMap<>(2);

        return retrofitHelper.getDisposable(TAG_GET_REGISTER_CODE, getParams("y_GasStation_login", map), httpCallBack, true);
    }

    /**
     * 注册下一步
     */
    public final static int TAG_REGISTER_NEXT = 4;

    public Disposable registerNext(RetrofitHelper retrofitHelper, HttpCallBack httpCallBack, String phone, String code) {
        HashMap<String, Object> map = new HashMap<>(2);

        return retrofitHelper.getDisposable(TAG_REGISTER_NEXT, getParams("y_GasStation_login", map), httpCallBack, true);
    }

    /**
     * 注册
     */
    public final static int TAG_REGISTER = 5;

    public Disposable register(RetrofitHelper retrofitHelper, HttpCallBack httpCallBack, String account, String pwd) {
        HashMap<String, Object> map = new HashMap<>(2);

        return retrofitHelper.getDisposable(TAG_REGISTER, getParams("y_GasStation_login", map), httpCallBack, true);
    }



}
