package com.app.android.yagthu.fragments;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.Preference;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.android.yagthu.R;
import com.app.android.yagthu.YagApplication;
import com.app.android.yagthu.main.MainActivity;
import com.app.android.yagthu.main.YagConstants;
import com.github.machinarius.preferencefragment.PreferenceFragment;

/**
 * Object: Fragment for Preferences (custom widgets)
 * Used by: Application Context
 *
 * @author Fllo (Florent Blot) & Tasa (Thierry Allard Saint Albin)
 * @version 1.0
 */
public class FragmentParams extends PreferenceFragment {

    public FragmentParams() { }

    public static final FragmentParams newInstance() {
        return new FragmentParams();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add user experience in ACRA delivery
        YagApplication yagApp = (YagApplication) getActivity().getApplication();
        yagApp.setUserExperience("FragmentParams");

        addPreferencesFromResource(R.xml.preferences_settings);

        // Add application version
        String version;
        try {
            version = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            version = "";
        }
        findPreference("prefs_key_info_version").setSummary(version);

        // Default layout disabled folder destination
        findPreference("prefs_key_archive_documents_path")
                .setLayoutResource(R.layout.preferences_information_disabled);
        // Update folder destination
        findPreference("prefs_key_archive_documents_download")
                .setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                    public boolean onPreferenceChange(Preference preference, Object value) {
                        if ((boolean)value)
                            findPreference("prefs_key_archive_documents_path")
                                    .setLayoutResource(R.layout.preferences_information);
                        else
                            findPreference("prefs_key_archive_documents_path")
                                    .setLayoutResource(R.layout.preferences_information_disabled);
                        return true;
                    }
                });

        // Display about Fragment
        findPreference("prefs_key_info_about")
                .setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                    @Override
                    public boolean onPreferenceClick(Preference preference) {
                        ((MainActivity) getActivity()).handleFragment(YagConstants.ABOUT, null );
                        return true;
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = super.onCreateView(inflater, container, savedInstanceState);
        view.setBackgroundColor(getResources().getColor(R.color.background));
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.findItem(R.id.action_params).setVisible(false);
        menu.findItem(R.id.action_user_profile).setVisible(false);
        menu.findItem(R.id.action_about).setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }
}