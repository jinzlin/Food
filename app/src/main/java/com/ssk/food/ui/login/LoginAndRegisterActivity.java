package com.ssk.food.ui.login;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.ssk.food.R;
import com.ssk.food.adapter.PageAdapter;
import com.ssk.food.ui.login.login.LoginFragment;
import com.ssk.food.ui.login.register.RegisterFragment;
import com.zhtx.mindlib.base.BaseActivity;

import java.util.ArrayList;

import butterknife.BindView;

public class LoginAndRegisterActivity extends BaseActivity {

    @BindView(R.id.tab_login_register)
    SlidingTabLayout tabLoginRegister;
    @BindView(R.id.vp_login_register)
    ViewPager vpLoginRegister;

    private ArrayList<Fragment> mFragments = new ArrayList<>();

    @Override
    public int initResource() {
        return R.layout.activity_login_and_register;
    }

    @Override
    public void initInject() {

    }

    @Override
    public void initData() {
        String[] mTitles = new String[]{getString(R.string.login_title), getString(R.string.register_title)};

        mFragments.add(new LoginFragment());
        mFragments.add(new RegisterFragment());

        PageAdapter adapter = new PageAdapter(getSupportFragmentManager(), mFragments, mTitles);
        vpLoginRegister.setAdapter(adapter);
        tabLoginRegister.setViewPager(vpLoginRegister);
        tabLoginRegister.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                vpLoginRegister.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

}
