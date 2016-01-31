package com.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadInitialFragment();
    }

    private void loadInitialFragment() {
//        replaceFragmentInDefaultLayout(MyFrag.newInstance(), false);
//        replaceFragmentInDefaultLayout(DummFrag.newInstance(), false);
//        replaceFragmentInDefaultLayout(HSV1Frag.newInstance(), false);
        replaceFragmentInDefaultLayout(HSV2Frag.newInstance(), false);
//        replaceFragmentInDefaultLayout(HSV3Frag.newInstance(), false);
    }

    public void replaceFragmentInDefaultLayout(Fragment fragmentToBeLoaded,
                                               boolean addToBackStack) {
        if (!getSupportFragmentManager().isDestroyed()) {
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.mainFrameLayout, fragmentToBeLoaded,
                    "myfrag");
            if (addToBackStack) {
                fragmentTransaction.addToBackStack("myfrag");
            }
            fragmentTransaction.commitAllowingStateLoss();
        } else {
        }
    }
}
