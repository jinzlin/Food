package com.zhtx.mindlib.widge;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * 作者: Coding Farmer_5199.
 * @date 2018/5/14.
 * 描述：为了解决google原生的bug 临时解决方法  https://blog.csdn.net/zhangyiacm/article/details/78287655
 */

public class MLinearLayoutManager extends LinearLayoutManager {

    public MLinearLayoutManager(Context context){
        super(context);
    }

    public MLinearLayoutManager(Context context, int orientation, boolean reverseLayout){
        super(context,orientation,reverseLayout);
    }

    public MLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes){
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        try {
            super.onLayoutChildren(recycler, state);
        }catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }
    }
}
