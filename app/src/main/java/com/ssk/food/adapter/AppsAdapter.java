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

public class AppsAdapter extends BaseQuickAdapter<AppsBean, BaseViewHolder> {

    private QBadgeView badgeView;

    public AppsAdapter(@Nullable List<AppsBean> data) {
        super(R.layout.item_apps, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AppsBean item) {
        helper.setBackgroundRes(R.id.iv_item_apps, item.getPicResource());
        helper.setText(R.id.tv_item_apps, item.getName());

        if (item.getBadge() >= 0) {
            if (badgeView == null) {
                badgeView = new QBadgeView(mContext);
                badgeView.bindTarget(helper.getView(R.id.rl_item_apps_badge));
            }
            badgeView.setBadgeNumber(item.getBadge());
        }
    }
}
