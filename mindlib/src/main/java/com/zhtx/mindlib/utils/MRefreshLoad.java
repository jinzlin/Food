package com.zhtx.mindlib.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zhtx.mindlib.R;
import com.zhtx.mindlib.widge.MLinearLayoutManager;

/**
 * 时间：on 2017/12/2 09:35.
 * 作者：by hygo04
 * 功能：整合SwipeRefreshLayout、BaseRecyclerViewAdapterHelper的刷新加载功能，封装
 */

public class MRefreshLoad<B extends BaseQuickAdapter> implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    private Context context;
    private int itemNum = 10;                           // 一页默认10条数据
    private RefreshLoadListener mListener;              // 刷新加载监听回调
    private SwipeRefreshLayout swipeRefreshLayout;      // 刷新控件
    private RecyclerView mRecyclerView;                 // RecyclerView控件
    private B mAdapter;                                 // 继承BaseQuickAdapter适配器
    private RecyclerView.LayoutManager mLayout;         // LayoutManager
    private View emptyView;                             // 空布局
    private Drawable empty;                             // 空图标
    private String emptyText;                           // 空提示
    private boolean isAutoRefresh = true;               // 是否进入刷新，默认刷新

    public interface RefreshLoadListener {
        /**
         * 刷新
         */
        void onRefresh();

        /**
         * 加载
         */
        void onLoading();
    }

    private MRefreshLoad() {

    }

    private MRefreshLoad(MRefreshLoad<B> listener) {
        this.context = listener.context;
        this.itemNum = listener.itemNum;
        this.mListener = listener.mListener;
        this.swipeRefreshLayout = listener.swipeRefreshLayout;
        this.mRecyclerView = listener.mRecyclerView;
        this.mAdapter = listener.mAdapter;
        this.isAutoRefresh = listener.isAutoRefresh;
        this.mLayout = listener.mLayout;
        this.emptyView = listener.emptyView;
        this.empty = listener.empty;
        this.emptyText = listener.emptyText;

        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setOnRefreshListener(this);
            swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#FF5655"));
        }
        // 自动刷新
        if (isAutoRefresh) {
            swipeRefreshLayout.setRefreshing(true);
            mListener.onRefresh();
        }
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
        if (mLayout != null) {
            mRecyclerView.setLayoutManager(mLayout);
        } else {
            mRecyclerView.setLayoutManager(new MLinearLayoutManager(context));
        }
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onRefresh() {
        if (null == mListener) {
            return;
        }
        if (swipeRefreshLayout != null) {
            if (mAdapter == null) {
                mListener.onRefresh();
            } else if (!mAdapter.isLoading()) {
                mListener.onRefresh();
                // 刷新时，不能加载
                mAdapter.setEnableLoadMore(false);
            }
        }
    }

    @Override
    public void onLoadMoreRequested() {
        if (mListener == null) {
            return;
        }
        if (mAdapter != null && mAdapter.getData().size() != 0 && mAdapter.getData().size() % itemNum == 0) {
            if (swipeRefreshLayout == null) {
                mListener.onLoading();
            } else if (!swipeRefreshLayout.isRefreshing()) {
                mListener.onLoading();
                // 加载时，不能刷新
                swipeRefreshLayout.setEnabled(false);
            }
        } else if (mAdapter != null && mAdapter.getData().size() != 0 && mAdapter.getData().size() < itemNum) {
            if (swipeRefreshLayout == null) {
                mListener.onLoading();
            } else if (!swipeRefreshLayout.isRefreshing()) {
                mListener.onLoading();
                // 加载时，不能刷新
                swipeRefreshLayout.setEnabled(false);
            }
        }
    }


    /**
     * 刷新完成
     */
    public void setRefreshComplete() {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
            // 刷新完成后，开启上拉加载
            setLoadMoreTrue();
        }
    }

    /**
     * 加载完成操作类型的枚举类
     */
    public enum LoadType {
        /**
         * 加载结束
         */
        end,
        /**
         * 加载完成
         */
        complete,
        /**
         * 加载错误
         */
        fail,
        /**
         * 隐藏加载
         */
        goneend
    }

    /**
     * 加载完成由于有多种状态
     */
    public void setLoadMore(LoadType type) {
        if (mAdapter != null) {
            switch (type) {
                case end:
                    mAdapter.loadMoreEnd();
                    break;
                case complete:
                    mAdapter.loadMoreComplete();
                    break;
                case fail:
                    mAdapter.loadMoreFail();
                    break;
                case goneend:
                    mAdapter.loadMoreEnd(true);
                    break;
                default:
                    break;
            }
        }
        // 加载完成后，开启下拉刷新
        setRefreshLayoutTrue();
    }

    /**
     * 解除（加载时，不能刷新）的状态
     */
    public void setRefreshLayoutTrue() {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setEnabled(true);
        }
    }

    /**
     * 解除（刷新时，不能加载）的状态
     */
    public void setLoadMoreTrue() {
        if (mAdapter != null) {
            mAdapter.setEnableLoadMore(true);
        }
    }

    /**
     * 获取每页加载条数
     */
    public int getItemNum() {
        return itemNum;
    }

    /**
     * 获取空布局
     */
    public View getEmptyView(Activity context){
        if (null == emptyView) {
            View view = context.getLayoutInflater().inflate(R.layout.fragment_empty_view, null);
            if (null != empty) {
                view.findViewById(R.id.iv_empty).setBackground(empty);
            }
            if (!TextUtils.isEmpty(emptyText)) {
                ((TextView)view.findViewById(R.id.tv_empty)).setText(emptyText);
            }
            return view;
        }
        return emptyView;
    }

    public static class Builder<B extends BaseQuickAdapter> {
        private MRefreshLoad<B> mListener;

        public Builder() {
            this.mListener = new MRefreshLoad<>();
        }

        /**
         * LayoutManager
         */
        public Builder<B> setLayoutManager(RecyclerView.LayoutManager layout){
            mListener.mLayout = layout;
            return this;
        }

        /**
         * LayoutManager
         */
        public Builder<B> setEmptyView(View view, Drawable empty, String emptyText){
            mListener.emptyView = view;
            mListener.empty = empty;
            mListener.emptyText = emptyText;
            return this;
        }


        /**
         * 一页加载条数（itemNum == -1 没有加载只有刷新）
         */
        public Builder<B> setItemNum(int itemNum) {
            mListener.itemNum = itemNum;
            return this;
        }

        /**
         * 刷新加载监听回调
         */
        public Builder<B> setRefreshLoadListener(@NonNull RefreshLoadListener listener) {
            mListener.mListener = listener;
            return this;
        }

        /**
         * 刷新控件
         */
        public Builder<B> setSwipeRefreshLayout(SwipeRefreshLayout layout) {
            mListener.swipeRefreshLayout = layout;
            return this;
        }

        /**
         * RecyclerView控件
         */
        public Builder<B> setRecyclerView(@NonNull RecyclerView recyclerView) {
            mListener.mRecyclerView = recyclerView;
            return this;
        }

        /**
         * 适配器
         */
        public Builder<B> setAdapter(@NonNull B adapter) {
            mListener.mAdapter = adapter;
            return this;
        }

        /**
         * 自动刷新
         */
        public Builder<B> setEntryRefresh(boolean refresh) {
            mListener.isAutoRefresh = refresh;
            return this;
        }

        public MRefreshLoad<B> create(Context context) {
            return new MRefreshLoad<>(mListener);
        }
    }
}
