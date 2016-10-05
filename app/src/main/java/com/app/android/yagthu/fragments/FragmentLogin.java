package com.app.android.yagthu.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.app.android.yagthu.R;
import com.app.android.yagthu.YagApplication;
import com.app.android.yagthu.main.MainActivity;

/**
 * Object: Fragment for Signin/Signup
 * Used by: Application Context
 *
 * @author Fllo (Florent Blot) & Tasa (Thierry Allard Saint Albin)
 * @version 1.0
 */
public class FragmentLogin extends Fragment {

    // Debug
    private static final String DEBUG_TAG = "Fragment Login";

    /**
     * Arguments and Instantiate elements
     */
    private static final String LOGIN_TAG = "LOGIN_TAG";
    private int LOGIN_POSITION;
    private Activity activity;

    /**
     * Views elements
     */
    private EditText editLogin, editPassword;
    private TextView buttonSignIn;

    // Empty constructor (required)
    public FragmentLogin() { }

    // Instance method of Fragment
    public static FragmentLogin newInstance(int initCheck) {
        FragmentLogin fragment = new FragmentLogin();
        Bundle args = new Bundle();
        args.putInt(LOGIN_TAG, initCheck);
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
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add user experience in ACRA delivery
        YagApplication yagApp = (YagApplication) getActivity().getApplication();
        yagApp.setUserExperience("FragmentLogin");
        // Retrieve the check state argument
        if (getArguments() != null) {
            LOGIN_POSITION = getArguments().getInt(LOGIN_TAG);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        editLogin = (EditText) v.findViewById(R.id.edit_login);
        editPassword = (EditText) v.findViewById(R.id.edit_password);
        buttonSignIn = (TextView) v.findViewById(R.id.button_signin);

        if ( ((MainActivity) activity).prefs != null
            && ((MainActivity) activity).prefs.getString("user_email", "") != null ) {
            editLogin.setText( ((MainActivity) activity).prefs.getString("user_email", "") );
        }

        editPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN &&
                        keyCode == KeyEvent.KEYCODE_ENTER) {
                    hideKeyboard();

                    ((MainActivity) activity).checkLogIn(
                            editLogin.getText().toString(),
                            editPassword.getText().toString());
                    return true;
                }
                return false;
            }
        });

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) activity).checkLogIn(
                        editLogin.getText().toString(),
                        editPassword.getText().toString());
            }
        });

        return v;
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager)
                getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.findItem(R.id.action_params).setVisible(false);
        menu.findItem(R.id.action_user_profile).setVisible(false);
        menu.findItem(R.id.action_about).setVisible(true);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
