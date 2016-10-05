package com.app.android.yagthu.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

import com.app.android.yagthu.R;
import com.app.android.yagthu.YagApplication;
import com.app.android.yagthu.animation.LoadingView;

/**
 * Object: Fragment connection to check-in / check-out (with NFC Reader)
 * Used by: MainActivity, Application Context
 *
 * @author Fllo (Florent Blot) & Tasa (Thierry Allard Saint Albin)
 * @version 1.0
 */
public class FragmentCheck extends Fragment {

    // Debug
    private static final String DEBUG_TAG = "Fragment Check NFC";

    /**
     * Arguments and Instantiate elements
     *
     * @param: CHECK_NFC (String) tag used as key argument
     * @param: CHECK_NFC_POSITION (int) determine the check state (IN or OUT)
     */
    private static final String CHECK_NFC_TAG = "CHECK_NFC_TAG";
    private int CHECK_NFC_POSITION;
    public static final int FIRST_VIEW_POSITION = 0,
                SECOND_VIEW_POSITION = 1,
                LAST_VIEW_POSITION = 2;
    private Activity activity;

    /**
     * Views elements
     */
    private RelativeLayout containerLoading;
    private LoadingView loadBlue, loadBackBlue, loadLightBlue;
    private int width, start = 1;
    private AnimationSet seta, setb, setc;
    private ScaleAnimation a, b;

    // Empty constructor (required)
    public FragmentCheck() { }

    // Instance method of Fragment
    public static FragmentCheck newInstance(int initCheck) {
        // Create a new Fragment
        FragmentCheck fragment = new FragmentCheck();
        // Create a new Bundle (to add arguments)
        Bundle args = new Bundle();
        // Add the check state to the Fragment
        args.putInt(CHECK_NFC_TAG, initCheck);
        fragment.setArguments(args);

        return fragment;
    }

    // Attach the Fragment to its parent Activity
    @Override
    public void onAttach(Activity activity_) {
        super.onAttach(activity_);
        this.activity = activity_;
    }

    @Override
    public void onStart() {
        super.onStart();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add user experience in ACRA delivery
        YagApplication yagApp = (YagApplication) getActivity().getApplication();
        yagApp.setUserExperience("FragmentCheck");
        // Retrieve the check state argument
        if (getArguments() != null) {
            CHECK_NFC_POSITION = getArguments().getInt(CHECK_NFC_TAG);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main_check, container, false);

        containerLoading = (RelativeLayout) v.findViewById(R.id.main_frame_loading);
        containerLoading.post(new Runnable() {
            @Override
            public void run() {
                loadBackBlue = new LoadingView(
                        activity,
                        containerLoading.getWidth(),
                        containerLoading.getHeight(),
                        activity.getResources().getColor(R.color.light_blue),
                        LAST_VIEW_POSITION
                );
                containerLoading.addView(loadBackBlue);

                loadLightBlue = new LoadingView(
                        activity,
                        containerLoading.getWidth(),
                        containerLoading.getHeight(),
                        activity.getResources().getColor(R.color.light_blue),
                        SECOND_VIEW_POSITION
                );
                containerLoading.addView(loadLightBlue);

                loadBlue = new LoadingView(
                        activity,
                        containerLoading.getWidth(),
                        containerLoading.getHeight(),
                        activity.getResources().getColor(R.color.blue),
                        FIRST_VIEW_POSITION
                );
                containerLoading.addView(loadBlue);

                // Prepare animations
                seta = new AnimationSet(false);
                seta.addAnimation(rotationCircle(LAST_VIEW_POSITION));
                seta.addAnimation(changeCircleDimensions(SECOND_VIEW_POSITION, 1, 0.98f));

                setb = new AnimationSet(false);
                setb.addAnimation(rotationCircle(SECOND_VIEW_POSITION));
                setb.addAnimation(changeCircleDimensions(SECOND_VIEW_POSITION, 1, 0.95f));

                setc = new AnimationSet(false);
                setc.addAnimation(rotationCircle(FIRST_VIEW_POSITION));
                setc.addAnimation(changeCircleDimensions(FIRST_VIEW_POSITION, 1.04f, 0.94f));

                // Set animations to the views
                loadBackBlue.startAnimation(seta);
                loadLightBlue.startAnimation(setb);
                loadBlue.startAnimation(setc);
            }
        });

        // Test.
        /**
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ((MainActivity) activity)
                        .handleFragment(YagConstants.DOCUMENTS_LIST);
            }
        }, 8000); // 8s later
         **/

        return v;
    }

    // Rotation of circles
    private RotateAnimation rotationCircle(int position) {
        int time = 0;
        RotateAnimation r = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_PARENT, 0.5f, Animation.RELATIVE_TO_PARENT, 0.5f);
        // Rotate time
        switch(position) {
            case 0: time = 2400; break; // load Back Blue
            case 1: time = 2000; break; // load Light Blue
            case 2: time = 1800; break; // load Blue
        }
        r.setDuration(time);
        r.setInterpolator(new AccelerateDecelerateInterpolator());
        r.setRepeatCount(Animation.INFINITE);

        // Return animation
        return r;
    }

    // Change dimensions of circles
    private ScaleAnimation changeCircleDimensions(int position, final float start, final float end) {
        int time = 0;
        a = new ScaleAnimation(start, end, start, end,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        switch(position) {
            case 0: time = 2000; break; // load Back Blue
            case 1: time = 1800; break; // load Light Blue
            case 2: time = 800; break; // load Blue
        }
        a.setDuration(time);
        a.setFillAfter(true);
        a.setRepeatMode(Animation.REVERSE);
        a.setRepeatCount(Animation.INFINITE);
        a.setInterpolator(new AccelerateDecelerateInterpolator());
        return a;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
