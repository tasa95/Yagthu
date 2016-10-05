package com.app.android.yagthu.widgets;

import android.content.Context;
import android.preference.PreferenceCategory;
import android.util.AttributeSet;

import com.app.android.yagthu.R;

/**
 * Object: Widget of category block
 * Used by: FragmentParams
 *
 * @author Fllo (Florent Blot) & Tasa (Thierry Allard Saint Albin)
 * @version 1.0
 */
public class CategoryPreferencesWidget extends PreferenceCategory {

    public CategoryPreferencesWidget(Context context) {
        super(context);
        setLayoutResource(R.layout.preferences_category);
    }

    public CategoryPreferencesWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayoutResource(R.layout.preferences_category);
    }
}
