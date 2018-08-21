package com.ssk.food.bean;

/**
 * 作者: ljz.
 * @date 2018/6/14.
 * 描述：
 */

public class AppsBean {
    /**
     * 图片资源
     */
    private int picResource;

    /**
     * 名称
     */
    private String name;

    /**
     * badge
     */
    private int badge;

    public AppsBean(int picResource, String name) {
        this.picResource = picResource;
        this.name = name;
        this.badge = -1;
    }

    public AppsBean(int picResource, String name, int badge) {
        this.picResource = picResource;
        this.name = name;
        this.badge = badge;
    }

    public int getPicResource() {
        return picResource;
    }

    public void setPicResource(int picResource) {
        this.picResource = picResource;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBadge() {
        return badge;
    }

    public void setBadge(int badge) {
        this.badge = badge;
    }
}
