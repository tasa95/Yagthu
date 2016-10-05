package com.app.android.yagthu.widgets;

import android.content.Context;
import android.preference.CheckBoxPreference;
import android.util.AttributeSet;

import com.app.android.yagthu.R;

/**
 * Object: Widget of information block
 * Used by: FragmentParams
 *
 * @author Fllo (Florent Blot) & Tasa (Thierry Allard Saint Albin)
 * @version 1.0
 */
public class InformationPreferencesWidget extends CheckBoxPreference {

    public InformationPreferencesWidget(Context context) {
        super(context);
        setLayoutResource(R.layout.preferences_information);
    }

    public InformationPreferencesWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayoutResource(R.layout.preferences_information);
    }
}
