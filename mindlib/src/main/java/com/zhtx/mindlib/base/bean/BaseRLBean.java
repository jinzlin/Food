package com.zhtx.mindlib.base.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 作者: ljz.
 * 描述：刷新加载实体类（刷新加载必须继承该类）
 */

public class BaseRLBean implements Parcelable{

    protected BaseRLBean() {
    }

    public static final Creator<BaseRLBean> CREATOR = new Creator<BaseRLBean>() {
        @Override
        public BaseRLBean createFromParcel(Parcel in) {
            return new BaseRLBean();
        }

        @Override
        public BaseRLBean[] newArray(int size) {
            return new BaseRLBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
