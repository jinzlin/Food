package com.ssk.food.bean;

import com.google.gson.annotations.SerializedName;

/**
 * 作者: ljz.
 * @date 2018/6/20.
 * 描述：
 */

public class LoginBean {

    @SerializedName("头像")
    private String headPortrait;
    @SerializedName("账号")
    private String account;
    @SerializedName("昵称")
    private String nikeName;
    @SerializedName("随机码")
    private String random;
    @SerializedName("isMainCount")
    private String isMainCount;

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getNikeName() {
        return nikeName;
    }

    public void setNikeName(String nikeName) {
        this.nikeName = nikeName;
    }

    public String getRandom() {
        return random;
    }

    public void setRandom(String random) {
        this.random = random;
    }

    public String getIsMainCount() {
        return isMainCount;
    }

    public void setIsMainCount(String isMainCount) {
        this.isMainCount = isMainCount;
    }

    @Override
    public String toString() {
        return "LoginBean{" +
                "headPortrait='" + headPortrait + '\'' +
                ", account='" + account + '\'' +
                ", random='" + random + '\'' +
                ", isMainCount='" + isMainCount + '\'' +
                '}';
    }
}
