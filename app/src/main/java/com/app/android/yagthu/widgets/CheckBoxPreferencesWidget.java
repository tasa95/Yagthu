package com.app.android.yagthu.widgets;

import android.content.Context;
import android.preference.CheckBoxPreference;
import android.util.AttributeSet;

import com.app.android.yagthu.R;

/**
 * Object: Widget of checkbox block
 * Used by: FragmentParams
 *
 * @author Fllo (Florent Blot) & Tasa (Thierry Allard Saint Albin)
 * @version 1.0
 */
public class CheckBoxPreferencesWidget extends CheckBoxPreference {

    public CheckBoxPreferencesWidget(Context context) {
        super(context);
        setLayoutResource(R.layout.preferences_checkbox);
    }

    public CheckBoxPreferencesWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayoutResource(R.layout.preferences_checkbox);
    }
}
