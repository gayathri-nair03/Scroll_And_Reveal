package com.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by gayathri_nair on 29/01/16.
 */
public class ContentFragment extends Fragment {

    private View rootView;
    private int amountScrolled;
    private int holderWidth;
    private int quarterHolderWidth;
    private TextView label1, label2, label3, label4;
    private int lowerLimit1, lowerLimit2, lowerLimit3, lowerLimit4;
    private SnappingHSV hsv;
    private int snapLimit0, snapLimit1, snapLimit2, snapLimit3;
    private ImageView refImageToReuce;
    private int refImgHt;

    public static ContentFragment newInstance() {
        return new ContentFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.scroll_reveal, container, false);
        initViews();
        return rootView;
    }

    private void initViews() {
        hsv = (SnappingHSV) rootView.findViewById(R.id.hsv);
        View milestone = rootView.findViewById(R.id.milestone);
        final View firstIndicatorInHSV = rootView.findViewById(R.id.stage_0);
        final View lastIndicatorInHSV = rootView.findViewById(R.id.stage_final);
        final LinearLayout hsvHolder = (LinearLayout) rootView.findViewById(R.id.hold);
        refImageToReuce = (ImageView) rootView.findViewById(R.id.dummyImgTop);
        label1 = (TextView) rootView.findViewById(R.id.label_stage1);
        label2 = (TextView) rootView.findViewById(R.id.label_stage2);
        label3 = (TextView) rootView.findViewById(R.id.label_stage3);
        label4 = (TextView) rootView.findViewById(R.id.label_stage4);

        //Milestone is the centrally placed vertical indicator, to which the HSV snaps
        int[] milestoneLocn = new int[2];
        milestone.getLocationInWindow(milestoneLocn);
        //The fixed X coordinate of the milestone view
        final int milestoneX = milestoneLocn[0];

        //The HSV will begin from the center of the screen. Set left margin to first view of HSV
        final int halfScreenWidth = (Utils.getScreenWidthUsingDisplayMetrics(getActivity())) / 2;
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) firstIndicatorInHSV.getLayoutParams();
        lp.setMargins(halfScreenWidth, 0, 0, 0);

        //The HSV will last till the center of the screen. Set a left and right margin to last view of HSV
        LinearLayout.LayoutParams lpLast = (LinearLayout.LayoutParams) lastIndicatorInHSV.getLayoutParams();
        int leftMargin = (int) Utils.convertDpToPixel(getActivity(), 10);
        lpLast.setMargins(leftMargin, 0, halfScreenWidth, 0);

        //Width of the snapping views.
        final int lineWidth = (int) Utils.convertDpToPixel(getActivity(), 2);

        rootView.post(new Runnable() {
            @Override
            public void run() {
                int[] bookingLocn = new int[2];
                int[] possLocn = new int[2];
                firstIndicatorInHSV.getLocationOnScreen(bookingLocn);
                lastIndicatorInHSV.getLocationOnScreen(possLocn);

                //HSV width is calculated as X(last indicator)-X(first indicator)
                holderWidth = possLocn[0] - bookingLocn[0];
                //adding height of last indicator
                //Total HSV width = holderWidth + width of last view
                holderWidth = holderWidth + lineWidth;
                quarterHolderWidth = holderWidth / 4;
                //Getting initial height of the reference image
                refImgHt = refImageToReuce.getHeight();
                initLimits();
                initSnapLimits();
            }
        });


        //Below code is when the dummy image is above the actual image
        //and the dummy image's bottom is gradually reduced to REVEAL the actual image
        hsv.setOnScrollViewListener(new SnappingHSV.HorizontalScrollChangeListener() {
            @Override
            public void onHorScrollChanged(SnappingHSV v, int l) {

                setTextVisibilities(l);

                //Get updated X location of the start of HSV holder, on scroll.
                int[] layoutLocn = new int[2];
                hsvHolder.getLocationInWindow(layoutLocn);
                int layoutX = layoutLocn[0];

                /**Actual amount scrolled wrt to the central indicator (milestone) would be
                 the difference between milestoneX and layoutX**/
                amountScrolled = milestoneX - layoutX;

                /**Here the aim is to reveal the image underneath, like a curtain does.
                 * So we have a curtain imageview above the actual imageview and
                 * we reduce the bottom of the curtain imageview,
                 * so that the actual image underneath is "REVEALED"**/

                float ratio = (float) refImgHt / holderWidth;
                float difference = (float) (holderWidth - amountScrolled);
                float newBottom = ratio * difference;
                refImageToReuce.setBottom((int) newBottom);
            }

            @Override
            public void onHorScrollEnd(SnappingHSV v, int l) {
                //At the end of scroll event, snap to the closest indicator
                snap(l);
            }
        });

    }

    private void initSnapLimits() {
        /** These limits define the threshold after which a snap has to occur
         * When the user has scrolled past half of an interval, the HSV will snap to the immediately next indicator
         * When the user has scrolled back past half of an interval, the HSV will snap to the immediately previous indicator
         */
        int halfInterval = quarterHolderWidth / 2;
        snapLimit0 = halfInterval;
        snapLimit1 = lowerLimit1 + halfInterval;
        snapLimit2 = lowerLimit2 + halfInterval;
        snapLimit3 = lowerLimit3 + halfInterval;
    }

    private void initLimits() {
        /**These limits define the intervals of each snap. The HSV snaps at multiples of (totalWidth/4)
         * Within these intervals, the visibilites of certain textviews are switched.
         **/
        lowerLimit1 = quarterHolderWidth;
        lowerLimit2 = (2 * quarterHolderWidth);
        lowerLimit3 = (3 * quarterHolderWidth);
        lowerLimit4 = holderWidth;
    }

    private void setTextVisibilities(int l) {
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
            label1.setVisibility(View.INVISIBLE);
        }
    }

    private void snap(int l) {
        //Snap basically translates to a smooth scroll operation
        if (l < lowerLimit1) {
            if (l > snapLimit0) {
                hsv.smoothScrollTo((quarterHolderWidth), 0);
            } else {
                hsv.smoothScrollTo(0, 0);
            }
        } else if (l >= lowerLimit1 && l < lowerLimit2) {
            if (l > snapLimit1) {
                hsv.smoothScrollTo((2 * quarterHolderWidth), 0);
            } else {
                hsv.smoothScrollTo((quarterHolderWidth), 0);
            }
        } else if (l >= lowerLimit2 && l < lowerLimit3) {
            if (l > snapLimit2) {
                hsv.smoothScrollTo((3 * quarterHolderWidth), 0);
            } else {
                hsv.smoothScrollTo((2 * quarterHolderWidth), 0);
            }
        } else if (l >= lowerLimit3 && l < lowerLimit4) {
            if (l > snapLimit3) {
                hsv.smoothScrollTo(holderWidth, 0);
            } else {
                hsv.smoothScrollTo((3 * quarterHolderWidth), 0);
            }
        } else if (l >= lowerLimit4) {
            hsv.smoothScrollTo(holderWidth, 0);
        }
    }

}
