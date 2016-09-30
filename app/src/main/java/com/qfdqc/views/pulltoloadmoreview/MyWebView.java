package com.qfdqc.views.pulltoloadmoreview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebView;

/**
 * Created by Administrator on 2016/9/30.
 */
public class MyWebView extends WebView {
    private boolean allowDragTop = true;
    private float downY = 0;
    private boolean needConsumeTouch = true;

    public MyWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyWebView(Context context) {
        this(context, null);
    }

    public MyWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            downY = ev.getRawY();
            needConsumeTouch = true;
            allowDragTop = isAtTop();
        } else if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            if (!needConsumeTouch) {
                getParent().requestDisallowInterceptTouchEvent(false);
                return false;
            } else if (allowDragTop) {
                if (ev.getRawY() - downY > 2) {
                    needConsumeTouch = false;
                    getParent().requestDisallowInterceptTouchEvent(false);
                    return false;
                }
            }
        }
        getParent().requestDisallowInterceptTouchEvent(needConsumeTouch);
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 是否在listview顶部
     *
     * @return
     * @author LiuLun
     * @Time 2016年1月5日下午4:07:36
     */
    private boolean  isAtTop() {
        return getScrollY() == 0;
    }
}
