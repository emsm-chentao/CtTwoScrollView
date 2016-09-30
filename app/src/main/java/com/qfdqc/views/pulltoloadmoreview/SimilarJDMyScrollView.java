package com.qfdqc.views.pulltoloadmoreview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 *  控制两个 MyScrollView  类似于京东淘宝  商品详情下拉加载更多
 */
public class SimilarJDMyScrollView extends ScrollView {
    private static String TAG = SimilarJDMyScrollView.class.getName();

    public void setScrollListener(ScrollListener scrollListener) {
        this.mScrollListener = scrollListener;
    }

    private ScrollListener mScrollListener;

    public SimilarJDMyScrollView(Context context) {
        super(context);
    }

    public SimilarJDMyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SimilarJDMyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                if (mScrollListener != null) {
                    int contentHeight = getChildAt(0).getHeight();
                    int scrollHeight = getHeight();
                    //Log.d(TAG,"scrollY:"+getScrollY()+"contentHeight:"+contentHeight+" scrollHeight"+scrollHeight+"object:"+this);
                    int scrollY = getScrollY();
                    mScrollListener.onScroll(scrollY);
                    if (scrollY + scrollHeight >= contentHeight || contentHeight <= scrollHeight) {
                        mScrollListener.onScrollToBottom();
                    } else {
                        mScrollListener.notBottom();
                    }
                    if (scrollY == 0) {
                        mScrollListener.onScrollToTop();
                    }
                }
                break;
        }
        boolean result = super.onTouchEvent(ev);
        requestDisallowInterceptTouchEvent(false);
       // Log.d("tttt", "我是scrollView" + result);
         return result;
    }

    public interface ScrollListener {
        void onScrollToBottom();

        void onScrollToTop();

        void onScroll(int scrollY);

        void notBottom();
    }
}
