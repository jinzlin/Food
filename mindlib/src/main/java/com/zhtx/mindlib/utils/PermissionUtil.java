package com.zhtx.mindlib.utils;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.zhtx.mindlib.R;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.functions.Consumer;

public class PermissionUtil {

    public interface RequestPermission {
        /**
         * 权限请求成功
         */
        void onRequestPermissionSuccess();

        /**
         * 用户拒绝了权限请求, 权限请求失败, 但还可以继续请求该权限
         *
         * @param permissions 请求失败的权限名
         */
        void onRequestPermissionFailure(List<String> permissions);

        /**
         * 用户拒绝了权限请求并且用户选择了以后不再询问, 权限请求失败, 这时将不能继续请求该权限, 需要提示用户进入设置页面打开该权限
         *
         * @param permissions 请求失败的权限名
         */
        void onRequestPermissionFailureWithAskNeverAgain(List<String> permissions);
    }


    public static void requestPermission(final Context context, final RequestPermission requestPermission, RxPermissions rxPermissions, String... permissions) {
        if (permissions == null || permissions.length == 0) {
            return;
        }

        List<String> needRequest = new ArrayList<>();
        //过滤调已经申请过的权限
        for (String permission : permissions) {
            if (!rxPermissions.isGranted(permission)) {
                needRequest.add(permission);
            }
        }

        if (needRequest.isEmpty()) {
            //全部权限都已经申请过，直接执行操作
            requestPermission.onRequestPermissionSuccess();
        } else {
            //没有申请过,则开始申请
            rxPermissions
                    .requestEach(needRequest.toArray(new String[needRequest.size()]))
                    .buffer(permissions.length)
                    .subscribe(new Consumer<List<Permission>>() {
                        @Override
                        public void accept(List<Permission> permissions) throws Exception {
                            for (final Permission p : permissions) {
                                if (!p.granted) {
                                    if (p.shouldShowRequestPermissionRationale) {
                                        requestPermission.onRequestPermissionFailure(Arrays.asList(p.name));
                                        return;
                                    } else {
                                        Log.e("---", "--"+p);
                                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                        builder.setTitle(context.getString(R.string.setting))
                                                .setMessage(context.getString(R.string.permission))
                                                .setNegativeButton(context.getString(R.string.cancel), new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        requestPermission.onRequestPermissionFailureWithAskNeverAgain(Arrays.asList(p.name));
                                                    }
                                                })
                                                .setPositiveButton(context.getString(R.string.setting), new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                                        intent.setData(Uri.fromParts("package", context.getPackageName(), null));
                                                        context.startActivity(intent);
                                                    }
                                                });
                                        AlertDialog dialog = builder.create();
                                        dialog.show();
                                        return;
                                    }
                                }
                            }
                            requestPermission.onRequestPermissionSuccess();
                        }
                    });
        }

    }


    /**
     * 请求摄像头权限
     */
    public static void launchCamera(Context context, RequestPermission requestPermission, RxPermissions rxPermissions) {
        requestPermission(context, requestPermission, rxPermissions,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA);
    }

    /**
     * 请求录像权限
     */
    public static void launchRecording(Context context, RequestPermission requestPermission, RxPermissions rxPermissions) {
        requestPermission(context, requestPermission, rxPermissions,
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    /**
     * 请求定位权限
     */
    public static void location(Context context, RequestPermission requestPermission, RxPermissions rxPermissions) {
        requestPermission(context, requestPermission, rxPermissions,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION);
    }

    /**
     * 请求外部存储的权限
     */
    public static void externalStorage(Context context, RequestPermission requestPermission, RxPermissions rxPermissions) {
        requestPermission(context, requestPermission, rxPermissions, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }


    /**
     * 请求发送短信权限
     */
    public static void sendSms(Context context, RequestPermission requestPermission, RxPermissions rxPermissions) {
        requestPermission(context, requestPermission, rxPermissions, Manifest.permission.SEND_SMS);
    }


    /**
     * 请求打电话权限
     */
    public static void callPhone(Context context, RequestPermission requestPermission, RxPermissions rxPermissions) {
        requestPermission(context, requestPermission, rxPermissions, Manifest.permission.CALL_PHONE);
    }


    /**
     * 请求获取手机状态的权限
     */
    public static void readPhonestate(Context context, RequestPermission requestPermission, RxPermissions rxPermissions) {
        requestPermission(context, requestPermission, rxPermissions, Manifest.permission.READ_PHONE_STATE);
    }

}

