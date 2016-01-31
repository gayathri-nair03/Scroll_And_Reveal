package com.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by gayathri_nair on 27/01/16.
 */
public class MyFrag extends Fragment {

    private View rootView;
    private int amountScrolled;
    private int holderWidth;
    private int actualBldngHeight;
    private int leftOver;
    private int quarterHolderWidth;
    private TextView label1, label2, label3, label4;
    private int lowerLimit1, lowerLimit2, lowerLimit3, lowerLimit4;

    public static MyFrag newInstance() {
        return new MyFrag();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.frag_clip, container, false);
        initViews();
        return rootView;
    }

    private void initViews() {
        ClipHsv hsv = (ClipHsv) rootView.findViewById(R.id.hsv);
        View milestone = rootView.findViewById(R.id.milestone);
        final View booking = rootView.findViewById(R.id.stage_booking);
        final View possession = rootView.findViewById(R.id.stage_possess);
        final LinearLayout hold = (LinearLayout) rootView.findViewById(R.id.hold);
        final ImageView bldngImg = (ImageView) rootView.findViewById(R.id.imgViewToChange);
        final ImageView refImg = (ImageView) rootView.findViewById(R.id.dummyImgRef);
        label1 = (TextView) rootView.findViewById(R.id.label_stage1);
        label2 = (TextView) rootView.findViewById(R.id.label_stage2);
        label3 = (TextView) rootView.findViewById(R.id.label_stage3);
        label4 = (TextView) rootView.findViewById(R.id.label_stage4);

        int[] milestoneLocn = new int[2];
        milestone.getLocationInWindow(milestoneLocn);
        final int milestoneX = milestoneLocn[0];

        final int halfScreen = (Utils.getScreenWidthUsingDisplayMetrics(getActivity())) / 2;
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) booking.getLayoutParams();
        lp.setMargins(halfScreen, 0, 0, 0);

        LinearLayout.LayoutParams lpLast = (LinearLayout.LayoutParams) possession.getLayoutParams();
        int leftMargin = (int) Utils.convertDpToPixel(getActivity(), 10);
        lpLast.setMargins(leftMargin, 0, halfScreen, 0);

        leftOver = (int) Utils.convertDpToPixel(getActivity(), 75);
        final int lineWidth = (int) Utils.convertDpToPixel(getActivity(), 2);

        rootView.post(new Runnable() {
            @Override
            public void run() {
                int[] bookingLocn = new int[2];
                int[] possLocn = new int[2];
                actualBldngHeight = refImg.getHeight() - leftOver;
                booking.getLocationOnScreen(bookingLocn);
                possession.getLocationOnScreen(possLocn);
                holderWidth = possLocn[0] - bookingLocn[0];
//                adding height of last possession stage
                holderWidth = holderWidth + lineWidth;
                quarterHolderWidth = holderWidth / 4;
                initLimits();
            }
        });


        final RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) bldngImg.getLayoutParams();

        hsv.setOnScrollViewListener(new ClipHsv.HorizontalScrollChangeListener() {
            @Override
            public void onHorScrollChanged(ClipHsv v, int l) {

                interpretScrolling(l);

                int[] layoutLocn = new int[2];
                hold.getLocationInWindow(layoutLocn);
                int layoutX = layoutLocn[0];

                amountScrolled = milestoneX - layoutX;
                float ratio = (float) actualBldngHeight / holderWidth;
                float newHeight = (ratio * amountScrolled) + leftOver;

                rlp.height = (int) newHeight;
                bldngImg.requestLayout();
            }
        });

    }

    private void initLimits() {
        lowerLimit1 = quarterHolderWidth;
        lowerLimit2 = (2 * quarterHolderWidth);
        lowerLimit3 = (3 * quarterHolderWidth);
        lowerLimit4 = holderWidth;
    }

    private void interpretScrolling(int l) {
        if (l >= lowerLimit1 && l < lowerLimit2) {
            label1.setVisibility(View.VISIBLE);
            label2.setVisibility(View.INVISIBLE);
            label3.setVisibility(View.INVISIBLE);
            label4.setVisibility(View.INVISIBLE);
        } else if (l >= lowerLimit2 && l < lowerLimit3) {
            label1.setVisibility(View.VISIBLE);
            label2.setVisibility(View.VISIBLE);
            label3.setVisibility(View.INVISIBLE);
            label4.setVisibility(View.INVISIBLE);
        } else if (l >= lowerLimit3 && l < lowerLimit4) {
            label1.setVisibility(View.VISIBLE);
            label2.setVisibility(View.VISIBLE);
            label3.setVisibility(View.VISIBLE);
            label4.setVisibility(View.INVISIBLE);
        } else if (l >= lowerLimit4) {
            label1.setVisibility(View.VISIBLE);
            label2.setVisibility(View.VISIBLE);
            label3.setVisibility(View.VISIBLE);
            label4.setVisibility(View.VISIBLE);
        } else {
            System.out.println("LOL! Do Nothing");
        }
    }

}
