package com.myapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;

/**
 * Created by gayathri_nair on 28/01/16.
 */
public class CustomHSBV extends HorizontalScrollView implements View.OnTouchListener {

    private HorizontalScrollChangeListener scrollListener;
    private boolean currentlyScrolling;
    private boolean currentlyTouching;

    public void setOnScrollViewListener(HorizontalScrollChangeListener l) {
        this.scrollListener = l;
    }

    public CustomHSBV(Context context) {
        super(context);
    }

    public CustomHSBV(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomHSBV(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomHSBV(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldX, int oldY) {
        if (Math.abs(x - oldX) > 1) {
            currentlyScrolling = true;

        } else {
            currentlyScrolling = false;
            scrollListener.onHorScrollStopped(x);
            if (!currentlyTouching) {
                //scrolling stopped...handle here
            }
        }
        if (null != scrollListener) {
            scrollListener.onHorScrollChanged(this, x);
        }
        super.onScrollChanged(x, y, oldX, oldY);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                currentlyTouching = true;
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                currentlyTouching = false;
                if (!currentlyScrolling) {
                    //I handle the release from a drag here
                    return true;
                }
        }
        return false;
    }

    public interface HorizontalScrollChangeListener {

        void onHorScrollChanged(CustomHSBV v, int l);

        void onHorScrollStopped(int l);

    }
}
