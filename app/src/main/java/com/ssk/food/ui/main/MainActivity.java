package com.ssk.food.ui.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.githang.statusbar.StatusBarCompat;
import com.ssk.food.App;
import com.ssk.food.R;
import com.ssk.food.bean.TabBean;
import com.ssk.food.di.component.DaggerActivityComponent;
import com.ssk.food.di.module.RequestActionModule;
import com.ssk.food.server.RequestAction;
import com.ssk.food.ui.handled.HandledFragment;
import com.ssk.food.ui.my.MyFragment;
import com.ssk.food.ui.order.OrderFragment;
import com.ssk.food.ui.shop.ShopFragment;
import com.ssk.food.utils.CommonUtils;
import com.zhtx.mindlib.base.BaseActivity;
import com.zhtx.mindlib.base.BaseApp;
import com.zhtx.mindlib.di.module.AcitvityModule;
import com.zhtx.mindlib.utils.BroadcastManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 作者:Coding Farmer_5199
 * 描述:
 */

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.ContView {

    @BindView(R.id.vp_main)
    ViewPager vpMain;
    @BindView(R.id.ct_main)
    CommonTabLayout ctMain;

    private List<Fragment> mTabs = new ArrayList<Fragment>();
    private int dot;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    @Override
    public int initResource() {
        return R.layout.activity_main;
    }

    @Override
    public void initInject() {
        DaggerActivityComponent
                .builder()
                .appComponent(BaseApp.getInstance().getAppComponent())
                .acitvityModule(new AcitvityModule(this))
                .requestActionModule(new RequestActionModule(new RequestAction()))
                .build()
                .inject(this);

    }

    @Override
    public void initData() {
        CommonUtils.setTransparentForWindow(this);
        initCommonTabLayout();
    }



    @Override
    public void initCommonTabLayout() {
        String[] mTitles = new String[]{getString(R.string.main_handled), getString(R.string.main_shop), getString(R.string.main_order), getString(R.string.main_my)};
        int[] mIconUnselectIds = {R.mipmap.ic_main_handled_unselect, R.mipmap.ic_main_shop_unselect, R.mipmap.ic_main_order_unselect, R.mipmap.ic_main_my_unselect};
        int[] mIconSelectIds = {R.mipmap.ic_main_handled_select, R.mipmap.ic_main_shop_select, R.mipmap.ic_main_order_select, R.mipmap.ic_main_my_select};
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabBean(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }

        ctMain.setTabData(mTabEntities);
        ctMain.hideMsg(1);
        ctMain.getMsgView(1).setBackgroundColor(getResources().getColor(R.color.colorMain));

        ctMain.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                vpMain.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        vpMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ctMain.setCurrentTab(position);
                if (position == 1 || position == 3) {
                    StatusBarCompat.setStatusBarColor(MainActivity.this, ContextCompat.getColor(MainActivity.this, R.color.transparent));
                } else {
                    StatusBarCompat.setStatusBarColor(MainActivity.this, ContextCompat.getColor(MainActivity.this, R.color.white));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mTabs.add(new HandledFragment());
        mTabs.add(new ShopFragment());
        mTabs.add(new OrderFragment());
        mTabs.add(new MyFragment());
        vpMain.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mTabs.get(position);
            }

            @Override
            public int getCount() {
                return mTabs.size();
            }

        });
        vpMain.setCurrentItem(0);
        vpMain.setOffscreenPageLimit(2);
    }

    long mExitTime;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                mToast.showToast("再按一次退出程序");
                mExitTime = System.currentTimeMillis();
            } else {
                App.getInstance().mActivityStack.AppExit();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
