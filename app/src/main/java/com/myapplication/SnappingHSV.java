package com.myapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

/**
 * Created by gayathri_nair on 29/01/16.
 * For Snapping HSV - refer - http://stackoverflow.com/a/25615932
 */
public class SnappingHSV extends HorizontalScrollView {

    private HorizontalScrollChangeListener scrollListener;
    private boolean mIsScrolling;
    private boolean mIsTouching;
    private Runnable mScrollingRunnable;
    private int scrolledX;

    public void setOnScrollViewListener(HorizontalScrollChangeListener l) {
        this.scrollListener = l;
    }

    public SnappingHSV(Context context) {
        super(context);
    }

    public SnappingHSV(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SnappingHSV(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SnappingHSV(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onScrollChanged(int x, int t, int oldX, int oldt) {
        scrolledX = x;

        //If scroll has occured, below code executes
        if (Math.abs(oldX - x) > 0) {
            //remove existing callbacks if any
            if (mScrollingRunnable != null) {
                removeCallbacks(mScrollingRunnable);
            }

            mScrollingRunnable = new Runnable() {
                public void run() {
                    //If scrolling, but not touching, it means scroll has ended after the delay period - 200
                    if (mIsScrolling && !mIsTouching) {
                        if (scrollListener != null) {
                            scrollListener.onHorScrollEnd(SnappingHSV.this, scrolledX);
                        }
                    }
                    //set scroll as false, as now scroll has ended
                    mIsScrolling = false;
                    mScrollingRunnable = null;
                }
            };

            //postdelayed runnable to run after 200 ms
            postDelayed(mScrollingRunnable, 200);
        }

        if (scrollListener != null) {
            scrollListener.onHorScrollChanged(this, x);
        }
        super.onScrollChanged(x, t, oldX, oldt);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();

        if (action == MotionEvent.ACTION_MOVE) {
            //If moving, then scrolling and touching both true
            mIsTouching = true;
            mIsScrolling = true;
        } else if (action == MotionEvent.ACTION_UP) {
            //If not scrolling, it means scrolling has stopped
            if (mIsTouching && !mIsScrolling) {
                if (scrollListener != null) {
                    scrollListener.onHorScrollEnd(this, scrolledX);
                }
            }

            //reset value as touch event has ended
            mIsTouching = false;
        }

        return super.onTouchEvent(ev);
    }


    public interface HorizontalScrollChangeListener {

        void onHorScrollChanged(SnappingHSV v, int l);

        void onHorScrollEnd(SnappingHSV v, int l);
    }
}