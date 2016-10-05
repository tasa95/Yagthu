package com.app.android.yagthu.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.app.android.yagthu.R;
import com.app.android.yagthu.YagApplication;
import com.app.android.yagthu.adapters.AdapterDocuments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Object: Fragment contains List of downloaded Documents
 * Used by: MainActivity, Application Context
 *
 * @author Fllo (Florent Blot) & Tasa (Thierry Allard Saint Albin)
 * @version 1.0
 */
public class FragmentDocuments extends Fragment {

    // Debug
    private static final String DEBUG_TAG = "Fragment Documents List";

    /**
     * Arguments and Instantiate elements
     */
    private static final String DOCUMENTS_LIST_TAG = "DOCUMENTS_LIST_TAG";
    private int DOCUMENTS_LIST_POSITION;
    private boolean isNewDocs = false;
    private Activity activity;

    /**
     * Layout elements
     */
    private ExpandableListView listDocuments;
    private AdapterDocuments adapterDocuments;
    private ArrayList<HashMap<String, Object>> arrayDatas = new ArrayList<HashMap<String, Object>>();

    // Empty constructor (required)
    public FragmentDocuments() { }

    // Instance method of Fragment
    public static FragmentDocuments newInstance(int initCheck, boolean newDocuments) {
        // Create a new Fragment
        FragmentDocuments fragment = new FragmentDocuments();
        // Create a new Bundle (to add arguments)
        Bundle args = new Bundle();
        // Add the check state to the Fragment
        args.putInt(DOCUMENTS_LIST_TAG, initCheck);
        args.putBoolean("NEW_DOCS", newDocuments);
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
        yagApp.setUserExperience("FragmentDocuments");
        // Retrieve arguments
        if (getArguments() != null) {
            DOCUMENTS_LIST_POSITION = getArguments().getInt(DOCUMENTS_LIST_TAG);
            isNewDocs = getArguments().getBoolean("NEW_DOCS");
        }

        // Example populate ArrayList
        List<String> datasiOS = new ArrayList<String>();
        datasiOS.add("Projet iShop");
        datasiOS.add("Projet Cars");
        datasiOS.add("Hello World");
        HashMap<String, Object> arrayiOS = new HashMap<String, Object>();
        arrayiOS.put("SECTION", "Programmation iOS");
        arrayiOS.put("DATAS", datasiOS);
        arrayiOS.put("LAST_MODIFIED_DATE", "12/04/2015");

        // Example populate ArrayList
        List<String> datasRest = new ArrayList<String>();
        datasRest.add("Projet BorrowThings API");
        datasRest.add("Projet Middlewares");
        datasRest.add("Hello World");
        HashMap<String, Object> arrayRest = new HashMap<String, Object>();
        arrayRest.put("SECTION", "Programmation des APIs");
        arrayRest.put("DATAS", datasRest);
        arrayRest.put("LAST_MODIFIED_DATE", "21/01/2015");

        arrayDatas.add(arrayiOS);
        arrayDatas.add(arrayRest);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main_expandablelist, container, false);

        listDocuments = (ExpandableListView) v.findViewById(R.id.main_expandablelist);

        if ( isNewDocs ) {
            View header = getActivity()
                    .getLayoutInflater().inflate(R.layout.header_documents_list, null, false);
            listDocuments.addHeaderView(header);
        }

        adapterDocuments = new AdapterDocuments(getActivity(), arrayDatas);
        listDocuments.setAdapter(adapterDocuments);

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.findItem(R.id.action_params).setVisible(true);
        menu.findItem(R.id.action_user_profile).setVisible(true);
        menu.findItem(R.id.action_about).setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
