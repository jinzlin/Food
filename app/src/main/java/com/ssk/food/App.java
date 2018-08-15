package com.ssk.food;

import com.ssk.food.server.ServerEvent;
import com.zhtx.mindlib.base.BaseApp;

/**
 * Author by ljz
 * PS:
 */
public class App extends BaseApp {
    @Override
    public void onCreate() {
        super.onCreate();

        ServerEvent.init(this);


        setmApiUrl(getString(R.string.app_url_api));
        setmSpName(getString(R.string.app_sp));
    }
}
