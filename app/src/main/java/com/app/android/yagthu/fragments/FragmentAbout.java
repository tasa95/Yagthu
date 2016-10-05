package com.app.android.yagthu.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.android.yagthu.R;
import com.app.android.yagthu.YagApplication;

/**
 * Object: Fragment for About (how it work, the goal, etc)
 * Used by: Application Context
 *
 * @author Fllo (Florent Blot)
 * @version 1.0
 */
public class FragmentAbout extends Fragment {

    // Debug
    private static final String DEBUG_TAG = "Fragment About";

    /**
     * Arguments and Instantiate elements
     */
    private Activity activity;

    /**
     * Views elements
     */
    private TextView introAbout, howitworkAbout,
            whyAbout, teamAbout;

    // Empty constructor (required)
    public FragmentAbout() { }

    // Instance method of Fragment
    public static FragmentAbout newInstance() {
        return new FragmentAbout();
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add user experience in ACRA delivery
        YagApplication yagApp = (YagApplication) getActivity().getApplication();
        yagApp.setUserExperience("FragmentAbout");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_about, container, false);

        introAbout = (TextView) v.findViewById(R.id.about_introduction);
        introAbout.setText(Html.fromHtml("<font color=\"" + getActivity().getResources().getColor(R.color.blue) +
                "\"><strong>YAGTHU</strong></font> est une application déstinée aux élèves et aux participants des conférences du " +
                "<font color=\"" + getActivity().getResources().getColor(R.color.blue) + "\">réseau GES</font>, facilitant la gestion " +
                "des présences et des documents de <font color=\"" + getActivity().getResources().getColor(R.color.blue) + "\">l'école ESGI</font>."));

        howitworkAbout = (TextView) v.findViewById(R.id.about_how_it_works);
        howitworkAbout.setText(Html.fromHtml("<h5><font color=\"" + getActivity().getResources().getColor(R.color.blue) +
                "\">Comment ça marche ?</font></h5>" +
                "The path of the righteous man is beset on all sides by the iniquities of the selfish and " +
                "the tyranny of evil men. <br/>Blessed is he who, in the name of charity and good will, shepherds the weak through the valley of " +
                "darkness, for he is truly his brother's keeper and the finder of lost children.<br/>And I will strike down upon thee with great " +
                "vengeance and furious anger those who would attempt to poison and destroy My brothers. And you will know My name is " +
                "the Lord when I lay My vengeance upon thee."));

        whyAbout = (TextView) v.findViewById(R.id.about_why);
        whyAbout.setText(Html.fromHtml("<h5><font color=\"" + getActivity().getResources().getColor(R.color.blue) +
                "\">Pourquoi YAGTHU ?</font></h5>" +
                "The path of the righteous man is beset on all sides by the iniquities of the selfish and " +
                "the tyranny of evil men. <br/>Blessed is he who, in the name of charity and good will, shepherds the weak through the valley of " +
                "darkness, for he is truly his brother's keeper and the finder of lost children.<br/>And I will strike down upon thee with great " +
                "vengeance and furious anger those who would attempt to poison and destroy My brothers. And you will know My name is " +
                "the Lord when I lay My vengeance upon thee."));

        teamAbout = (TextView) v.findViewById(R.id.about_team);
        teamAbout.setText(Html.fromHtml("<h5><font color=\"" + getActivity().getResources().getColor(R.color.blue) +
                "\">L'équipe</font></h5>" +
                "The path of the righteous man is beset on all sides by the iniquities of the selfish and " +
                "the tyranny of evil men. <br/>Blessed is he who, in the name of charity and good will, shepherds the weak through the valley of " +
                "darkness, for he is truly his brother's keeper and the finder of lost children.<br/>And I will strike down upon thee with great " +
                "vengeance and furious anger those who would attempt to poison and destroy My brothers. And you will know My name is " +
                "the Lord when I lay My vengeance upon thee."));

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.findItem(R.id.action_params).setVisible(false);
        menu.findItem(R.id.action_user_profile).setVisible(false);
        menu.findItem(R.id.action_about).setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
