package com.ssk.food.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;


/**
 * 作者: ljz.
 * @date 2018/6/14.
 * 描述：
 */

public class PageAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> mFragments = new ArrayList<>();

    public PageAdapter(FragmentManager fm, ArrayList<Fragment> mFragments) {
        super(fm);
        this.mFragments = mFragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
