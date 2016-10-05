package com.app.android.yagthu.adapters;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.app.android.yagthu.R;

/**
 * Object: This adapter displays user informations (attendance, score, mark)
 * Used by: FragmentProfile
 *
 * @author Fllo (Florent Blot) & Tasa (Thierry Allard Saint Albin)
 * @version 1.0
 */
public class AdapterViewPagerProfile extends PagerAdapter {

    private View parentView;

    public AdapterViewPagerProfile(View parentView_) {
        this.parentView = parentView_;
    }

    @Override
    public Object instantiateItem(ViewGroup parent, int position) {
        int id = 0;
        switch (position) {
            case 0:
                id = R.id.user_content_semester_one;
                break;
            case 1:
                id = R.id.user_content_semester_two;
                break;
            case 2:
                id = R.id.user_content_attendance;
                break;
        }
        return parentView.findViewById(id);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return parentView.getContext()
                        .getResources().getString(R.string.viewpager_title_semester_one);
            case 1:
                return parentView.getContext()
                        .getResources().getString(R.string.viewpager_title_semester_two);
            case 2:
                return parentView.getContext()
                        .getResources().getString(R.string.viewpager_title_attendance);
        }
        return null;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == ((View) arg1);
    }

    @Override
    public void destroyItem(ViewGroup parent, int position, Object view) { }
}
