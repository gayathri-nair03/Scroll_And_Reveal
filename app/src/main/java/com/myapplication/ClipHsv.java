package com.myapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

/**
 * Created by gayathri_nair on 27/01/16.
 */
public class ClipHsv extends HorizontalScrollView {

    private HorizontalScrollChangeListener scrollListener;

    public void setOnScrollViewListener(HorizontalScrollChangeListener l) {
        this.scrollListener = l;
    }

    public ClipHsv(Context context) {
        super(context);
    }

    public ClipHsv(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ClipHsv(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ClipHsv(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        if (null != scrollListener) {
            scrollListener.onHorScrollChanged(this, l);
        }
        super.onScrollChanged(l, t, oldl, oldt);
    }


    public interface HorizontalScrollChangeListener {

        void onHorScrollChanged(ClipHsv v, int l);
    }
}
