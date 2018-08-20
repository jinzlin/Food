package com.ssk.food.server;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.ssk.food.R;
import com.ssk.food.utils.CommonUtils;
import com.zhtx.mindlib.base.BaseActivity;
import com.zhtx.mindlib.base.BaseApp;
import com.zhtx.mindlib.utils.BroadcastManager;
import com.zhtx.mindlib.utils.EventTag;
import com.zhtx.mindlib.widge.MDialog;

/**
 * 作者: ljz.
 * @date 2018/7/4.
 * 描述：
 */

public class ServerEvent {

    private static ServerEvent instance;
    private Context context;
    private Context lastContext;


    public ServerEvent(Context context) {
        this.context = context;
        requestEvent();
    }

    public static void init(Context context) {
        if (instance == null) {
            synchronized (ServerEvent.class) {
                if (instance == null) {
                    instance = new ServerEvent(context);
                }
            }
        }
    }

    public static ServerEvent getInstance() {
        return instance;
    }

    private void requestEvent() {
        BroadcastManager.getInstance(context).addAction(EventTag.TAG_EVENT_REQUEST, new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (intent.getStringExtra("data")) {
                    case EventTag.TAG_EVENT_REQUEST_RANDOM_ERROR:
                        lastContext = BaseApp.getInstance().getmActivityStack().getLastActivity();
                        if (lastContext != null) {
                            try {
                                MDialog mDialog = ((BaseActivity) lastContext).getmDialog();
                                mDialog.setCancelable(false);
                                mDialog.initDialog()
                                        .withTitie(context.getString(R.string.dialog_title))
                                        .withContene(context.getString(R.string.other_phone_login))
                                        .setBtn1(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                CommonUtils.exitLogon();
//                                                Intent intent = new Intent(lastContext, LoginFragment.class);
//                                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                                lastContext.startActivity(intent);
//                                                ((Activity) lastContext).finish();
                                            }
                                        }).show();
                            } catch (Exception e) {
                            }
                        }
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
