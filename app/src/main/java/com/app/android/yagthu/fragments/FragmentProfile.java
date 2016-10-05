package com.app.android.yagthu.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.app.android.yagthu.R;
import com.app.android.yagthu.YagApplication;
import com.app.android.yagthu.adapters.AdapterAttendanceScore;
import com.app.android.yagthu.adapters.AdapterSemesterScore;
import com.app.android.yagthu.adapters.AdapterViewPagerProfile;
import com.app.android.yagthu.animation.ZoomOutPageTransformer;
import com.app.android.yagthu.main.MainActivity;
import com.app.android.yagthu.main.YagConstants;
import com.app.android.yagthu.models.Attendance;
import com.app.android.yagthu.models.Classes;
import com.app.android.yagthu.models.Grades;
import com.app.android.yagthu.models.User;
import com.app.android.yagthu.webservices.WebservicesRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Object: Fragment for Profile user
 * Used by: Application Context
 *
 * @author Fllo (Florent Blot) & Tasa (Thierry Allard Saint Albin)
 * @version 1.0
 */
public class FragmentProfile extends Fragment {

    // Debug
    private static final String DEBUG_TAG = "Fragment Profile";

    /**
     * Arguments and Instantiate elements
     */
    private Activity activity;
    private WebservicesRequest webservices;
    private SharedPreferences prefs;

    private User currentUser;
    private Classes currentClass;
    private ArrayList<Grades> arraySemesterOne = new ArrayList<>();
    private ArrayList<Grades> arraySemesterTwo = new ArrayList<>();
    private ArrayList<Attendance> arrayAttendance = new ArrayList<>();

    /**
     * Views elements
     */
    private ImageView userAvatar, userManageAccount;
    private TextView userFirstname, userLastname, userPromotion;
    private ViewPager viewpagerProfile;
    private AdapterViewPagerProfile adapterProfile;
    private ListView listSemesterOne, listSemesterTwo, listAttendance;

    // Empty constructor (required)
    public FragmentProfile() { }

    // Instance method of Fragment
    public static FragmentProfile newInstance() {
        return new FragmentProfile();
    }

    // Attach the Fragment to its parent Activity
    @Override
    public void onAttach(Activity activity_) {
        super.onAttach(activity_);
        this.activity = activity_;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add user experience in ACRA delivery
        YagApplication yagApp = (YagApplication) getActivity().getApplication();
        yagApp.setUserExperience("FragmentProfile");
        // SharedPreferences
        prefs = getActivity().getSharedPreferences(YagConstants.PREFERENCE_NAME, Context.MODE_PRIVATE);
        // Get user informations
        new GetUserInformations().execute();
        // Get grades from semesters
        new GetSemestersGrades().execute();
        // Populate attendance list
        new GetNonAttendances().execute();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        userAvatar = (ImageView) v.findViewById(R.id.user_avatar);
        userFirstname = (TextView) v.findViewById(R.id.user_firstname);
        userLastname = (TextView) v.findViewById(R.id.user_lastname);
        userPromotion = (TextView) v.findViewById(R.id.user_promotion);
        userManageAccount = (ImageView) v.findViewById(R.id.user_manage_account);

        final Bitmap avatarBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.photo);
        userAvatar.post(new Runnable() {
            @Override
            public void run() {
                userAvatar.setImageBitmap(setBitmapCropped(avatarBitmap));
            }
        });

        // Display a fragment to manage account informations
        userManageAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).handleFragment(YagConstants.MANAGE, currentUser);
            }
        });

        // Display a ViewPager with user informations
        adapterProfile = new AdapterViewPagerProfile(v);
        viewpagerProfile = (ViewPager) v.findViewById(R.id.user_viewpager);
        viewpagerProfile.setOffscreenPageLimit(3);
        viewpagerProfile.setAdapter(adapterProfile);

        // Animate ViewPager for API 11 and higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            viewpagerProfile.setPageTransformer(true, new ZoomOutPageTransformer());

        listSemesterOne = (ListView) v.findViewById(R.id.user_list_semester_one);
        listSemesterTwo = (ListView) v.findViewById(R.id.user_list_semester_two);
        listAttendance = (ListView) v.findViewById(R.id.user_list_attendance);

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.findItem(R.id.action_params).setVisible(true);
        menu.findItem(R.id.action_user_profile).setVisible(false);
        menu.findItem(R.id.action_about).setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    // Crop a Bitmap image
    private Bitmap setBitmapCropped(Bitmap b) {
        Bitmap croppedBitmap;

        if (b.getWidth() >= b.getHeight()) {
            croppedBitmap = Bitmap.createBitmap(
                    b,
                    b.getWidth()/2 - b.getHeight()/2,
                    0,
                    b.getHeight(),
                    b.getHeight()
            );

        } else {
            croppedBitmap = Bitmap.createBitmap(
                    b,
                    0,
                    b.getHeight()/2 - b.getWidth()/2,
                    b.getWidth(),
                    b.getWidth()
            );

        }

        return setCircularShape(croppedBitmap);
    }

    // Create a rounded Bitmap image with strokes
    private Bitmap setCircularShape(Bitmap b) {
        int targetWidth = (int)(80 * getActivity().getResources().getDisplayMetrics().density);
        int targetHeight = (int)(80 * getActivity().getResources().getDisplayMetrics().density);

        Bitmap resultBitmap = Bitmap.createBitmap(targetWidth,
                targetHeight,Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(resultBitmap);
        Path path = new Path();

        path.addCircle(((float) targetWidth - 1) / 2,
                ((float) targetHeight - 1) / 2,
                (Math.min(((float) targetWidth),
                        ((float) targetHeight)) / 2),
                Path.Direction.CCW);

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(getResources().getColor(R.color.white));
        paint.setStrokeWidth(20);
        paint.setAntiAlias(true);

        canvas.clipPath(path);
        canvas.drawBitmap(b,
                new Rect(0, 0, b.getWidth(), b.getHeight()),
                new Rect(0, 0, targetWidth, targetHeight), null);
        canvas.drawPath(path, paint);

        return resultBitmap;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    // =========================================================================================
    // AsyncTask methods
    // =========================================================================================
    // GET USER INFORMATIONS
    private class GetUserInformations extends AsyncTask<Void, Void, String> {
        private JSONObject response;
        private JSONObject error = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            webservices = new WebservicesRequest();

            response = webservices.getUserInformations(
                    prefs.getString("user_id", ""),
                    prefs.getString("user_token", ""));

            if ( response != null ) {
                // Populate user model
                try {
                    currentUser = new User(
                            response.getString("id"),
                            prefs.getString("user_token", ""),
                            response.getString("Smartphone_id"),
                            response.getString("Name"),
                            response.getString("FirstName"),
                            response.getString("Login"),
                            response.getString("Login"),
                            response.getString("email"),
                            response.getBoolean("Admin"),
                            response.getBoolean("Professor"),
                            response.getBoolean("emailVerified"),
                            response.getString("class_id"),
                            response.getString("photosName")
                    );

                    return YagConstants.TAG_SUCCESS;

                } catch (JSONException jex) {
                    Log.e("///-- LOOP RESPONSE:", jex.toString());
                    return YagConstants.TAG_CANCELLED;
                }
            }

            return errorResponse(error);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if ( result.equals( YagConstants.TAG_SUCCESS )) {
                // Display result
                userFirstname.setText(currentUser.getFirstName());
                userLastname.setText(currentUser.getName());

                // Display class infos
                new GetClassInformations().execute(currentUser.getClassId());
            } else
            if ( getActivity() != null )
                ((MainActivity) getActivity())
                        .showAlert("Connexion", result);
        }
    }

    // GET CLASS INFORMATIONS
    private class GetClassInformations extends AsyncTask<String, Void, String> {
        private JSONObject response;
        private JSONObject error = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            webservices = new WebservicesRequest();

            response = webservices.getClassInformations(
                    (String) params[0], prefs.getString("user_token", ""));

            if ( response != null ) {
                // Populate user model
                try {
                    currentClass = new Classes(
                            response.getString("name"),
                            response.getString("id")
                    );

                    return YagConstants.TAG_SUCCESS;

                } catch (JSONException jex) {
                    Log.e("///-- LOOP RESPONSE:", jex.toString());
                    return YagConstants.TAG_CANCELLED;
                }
            }

            return errorResponse(error);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if ( result.equals( YagConstants.TAG_SUCCESS )) {
                // Display result
                userPromotion.setText(currentClass.getName());

//                Picasso.with(FragmentProfile.this.getActivity().getBaseContext())
//                        .load(String.format(webservices.USER_PHOTOS, currentUser.getPhotosName())).into(userAvatar);
            } else
            if ( getActivity() != null )
                ((MainActivity) getActivity())
                        .showAlert("Connexion", result);
        }
    }

    // GET USER GRADES
    private class GetSemestersGrades extends AsyncTask<Void, Void, String> {
        private JSONArray response;
        private JSONObject error = null;
        private String resut;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            webservices = new WebservicesRequest();

            response = webservices.getUserGrades(
                    prefs.getString("user_id", ""),
                    prefs.getString("user_token", "") );

            if ( response != null ) {
                // Check if there is any error
                if (response.length() > 0) {
                    // Populate arrays
                    try {
                        Grades grade;

                        for (int i = 0; i < response.length(); ++i) {
                            grade = new Grades();
                            JSONObject item = response.getJSONObject(i);

                            grade.setType(YagConstants.SEMESTER);
                            grade.setId(item.getString("id"));
                            grade.setGrades(Integer.valueOf(item.getString("grades")));
                            grade.setDisciplineId(item.getString("disciplineId"));
                            grade.setName(item.getString("name"));
                            grade.setProfessorName(item.getString("professorName"));
                            grade.setCoef(item.getString("coef"));
                            grade.setEcts(item.getString("ects"));
                            grade.setCc1(item.getString("CC1"));
                            grade.setCc2(item.getString("CC2"));
                            grade.setTest(item.getString("TEST"));

                            if (!item.isNull("semester") && item.getInt("semester") == 1) {
                                grade.setSemester(item.getInt("semester"));
                                arraySemesterOne.add(grade);
                            } else if (!item.isNull("semester") && item.getInt("semester") == 2) {
                                grade.setSemester(item.getInt("semester"));
                                arraySemesterTwo.add(grade);
                            }
                        }

                        // Display custom Object (used for legend)
                        grade = new Grades("Matières", 0, "", "", "", 1, "",
                                "Coef.", "ETCS", "CC1", "CC2", "Exam", YagConstants.SEMESTER);
                        arraySemesterOne.add(0, grade);
                        grade.setSemester(2);
                        arraySemesterTwo.add(0, grade);

                        return YagConstants.TAG_SUCCESS;

                    } catch (JSONException jex) {
                        Log.e("///-- LOOP RESPONSE:", jex.toString());
                        return YagConstants.TAG_CANCELLED;
                    }

                } else {
//                    try {
//                        error = new JSONObject(response);
//                    } catch (JSONException jex) {
//                        Log.e("///-- LOGIN RESPONSE:", jex.toString());
//                    }
                    return errorResponse(error);
                }
            }

            return errorResponse(error);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if ( result.equals( YagConstants.TAG_SUCCESS )) {
                // Display arrays
                listSemesterOne.setAdapter(
                        new AdapterSemesterScore(getActivity(), arraySemesterOne));
                listSemesterTwo.setAdapter(
                        new AdapterSemesterScore(getActivity(), arraySemesterTwo));
            } else
                if ( getActivity() != null )
                    ((MainActivity) getActivity())
                            .showAlert("Connexion", result);
        }
    }

    // GET USER NON ATTENDANCES
    private class GetNonAttendances extends AsyncTask<Void, Void, String> {
        private JSONArray response;
        private JSONObject error = null;
        private String resut;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            webservices = new WebservicesRequest();

            response = webservices.getUserNonAttendances(
                    prefs.getString("user_id", ""),
                    prefs.getString("user_token", ""));

            if ( response != null ) {
                // Check if there is any error
                if (response.length() > 0) {
                    // Populate arrays
                    try {
                        Attendance attendance;
                        // Test.
                        boolean t0 = true; String t1 = "Absence", t2 = "Retard";

                        for (int i = 0; i < response.length(); ++i) {
                            attendance = new Attendance();
                            JSONObject item = response.getJSONObject(i);

                            attendance.setCourseName(item.getString("Name"));
                            attendance.setDateStart(item.getString("startDate"));
                            attendance.setDateEnd(item.getString("endDate"));
                            // Test.
                            attendance.setJustified("Non");
                            if (t0) { t0 = false; attendance.setType(t2); }
                            else { t0 = true; attendance.setType(t1); }

                            arrayAttendance.add(attendance);
                        }

                        // Display custom Object (used for legend)
                        attendance = new Attendance("", "", "", "Date", "", "Justifié", "Matières", "Type");
                        arrayAttendance.add(0, attendance);

                        return YagConstants.TAG_SUCCESS;

                    } catch (JSONException jex) {
                        Log.e("///-- LOOP RESPONSE:", jex.toString());
                        return YagConstants.TAG_CANCELLED;
                    }

                } else {
//                    try {
//                        error = new JSONObject(response);
//                    } catch (JSONException jex) {
//                        Log.e("///-- LOGIN RESPONSE:", jex.toString());
//                    }
                    return errorResponse(error);
                }
            }

            return errorResponse(error);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if ( result.equals( YagConstants.TAG_SUCCESS )) {
                // Display arrays
                listAttendance.setAdapter(
                        new AdapterAttendanceScore(getActivity(), arrayAttendance));
            } else
            if ( getActivity() != null )
                ((MainActivity) getActivity())
                        .showAlert("Connexion", result);
        }
    }

    // ERROR
    private String errorResponse(JSONObject error) {
        String msg = "";
        if (error != null) {
            try {
                if (!error.isNull("name"))
                    msg += error.getString("name").toString() + ": ";
                if (!error.isNull("message"))
                    msg += error.getString("message").toString();
            } catch (JSONException jex) {
                Log.d("///-- ERROR RESPONSE:", jex.toString());
            }
        }

        if ( msg == "" )
            return "Une erreur est survenue";
        return msg;
    }
}
