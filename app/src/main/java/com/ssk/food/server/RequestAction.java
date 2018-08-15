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
     * 登录
     */
    public final static int TAG_LOGIN = 0;

    public Disposable login(RetrofitHelper retrofitHelper, HttpCallBack httpCallBack, String account, String pwd) {

        HashMap<String, Object> map = new HashMap<>(2);
        map.put("account", account);
        map.put("LoginPwd", MD5Utlis.Md5(pwd));
        return retrofitHelper.getDisposable(TAG_LOGIN, getParams("y_GasStation_login", map), httpCallBack, true);
    }


}
