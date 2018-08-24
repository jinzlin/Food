package com.ssk.food.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ssk.food.R;
import com.ssk.food.bean.AppsBean;

import java.util.List;

import q.rorbin.badgeview.QBadgeView;

/**
 * 作者: ljz.
 * @date 2018/6/14.
 * 描述：
 */

public class Apps2Adapter extends BaseQuickAdapter<AppsBean, BaseViewHolder> {

    public Apps2Adapter(@Nullable List<AppsBean> data) {
        super(R.layout.item_my, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AppsBean item) {
        helper.setBackgroundRes(R.id.iv_item_my_pic, item.getPicResource());
        helper.setText(R.id.tv_item_my_title, item.getName());
    }
}
