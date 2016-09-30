package com.qfdqc.views.pulltoloadmoreview;

import android.content.Context;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;


/**
 * ct主要防止在缩放图片时使用ViewPager出现的系统异常
 */
public class CtZoomViewPager extends ViewPager {

    public CtZoomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CtZoomViewPager(Context context) {
        super(context);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException e) {
        } catch (ArrayIndexOutOfBoundsException e) {
        }
        return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            getParent().requestDisallowInterceptTouchEvent(true);
        }
        return super.dispatchTouchEvent(ev);
    }
}
