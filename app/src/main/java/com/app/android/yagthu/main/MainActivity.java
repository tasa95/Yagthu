package com.app.android.yagthu.main;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentFilter.MalformedMimeTypeException;
import android.content.SharedPreferences;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.app.android.yagthu.R;
import com.app.android.yagthu.YagApplication;
import com.app.android.yagthu.fragments.FragmentAbout;
import com.app.android.yagthu.fragments.FragmentCamera;
import com.app.android.yagthu.fragments.FragmentCheck;
import com.app.android.yagthu.fragments.FragmentDocuments;
import com.app.android.yagthu.fragments.FragmentLogin;
import com.app.android.yagthu.fragments.FragmentManageAccount;
import com.app.android.yagthu.fragments.FragmentParams;
import com.app.android.yagthu.fragments.FragmentProfile;
import com.app.android.yagthu.models.User;
import com.app.android.yagthu.services.NdefReaderTask;
import com.app.android.yagthu.services.NfcService;
import com.app.android.yagthu.webservices.WebservicesRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;

/**
 * Object: Main Activity to manage and display Fragments
 * Used by: Application Context
 *
 * @author Fllo (Florent Blot) & Tasa (Thierry Allard Saint Albin)
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {

    // Debug
    private static final String DEBUG_TAG = "Main Activity";

    // Instantiate application elements
    public YagApplication yagApp;
    private ActionBar actionBar;
    protected static LinkedList<String> titlesActionBar;
    private WebservicesRequest webservices;
    private Bundle savedInstanceState;

    // Activity elements
    private NfcAdapter nfcAdapter;
    private NdefReaderTask nfcReaderTask;
    private static final String MIME_TEXT_PLAIN = "text/plain";
    public SharedPreferences prefs;
    private AlertDialog dialog;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();

        /**
         * It's important, that the activity is in the foreground (resumed). Otherwise
         * an IllegalStateException is thrown.
         */
        setupForegroundDispatch(this, nfcAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;

        // Add user experience in ACRA delivery
        yagApp = (YagApplication) getApplication();
        yagApp.setUserExperience("MainActivity");

        setContentView(R.layout.activity_main);

        // Initialization of Titles
        titlesActionBar = new LinkedList<>();
        titlesActionBar.add(getResources().getString(R.string.app_name));
        // Initialization of ActionBar
        actionBar = getSupportActionBar();

        if (webservices == null)
            webservices = new WebservicesRequest();

        // Get SharedPreferences of user
        prefs = getSharedPreferences(YagConstants.PREFERENCE_NAME, Context.MODE_PRIVATE);

        // Initialization of NFC Service
        // if ( !isServiceRunning(NfcService.class) ) {
            Intent intent = new Intent(MainActivity.this, NfcService.class);
            startService(intent);
        // }
        // Initialization of NFC reader and AsyncTask
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        // Check NFC
        if (nfcAdapter == null) {
            Toast.makeText(this, "Ce smartphone ne supporte pas la norme NFC.", Toast.LENGTH_LONG).show();
            // finish();
            return;
        } else if (!nfcAdapter.isEnabled()) {
            Toast.makeText(this, "Veuillez activer le lecteur NFC de votre smartphone.", Toast.LENGTH_LONG).show();
        }

        // Redirection at creation
        if ( getIntent() != null
                && getIntent().getExtras() != null && isUserIdentified() ) {
            handleNfcIntent(getIntent());
        } else if ( savedInstanceState == null && isUserIdentified() ) {
            handleFragment( YagConstants.DOCUMENTS_LIST, false);
        } else if ( !isUserIdentified() ) {
            handleFragment( YagConstants.USER_LOGIN, null );
        }
    }

    // Method to handle NFC events
    private void handleNfcIntent(Intent intent) {
        if (intent != null) {
            // Get action NFC from NFC Reader
            String action = intent.getAction();

            // Get all datas from NFC records
            NdefRecord records = intent.getParcelableExtra("NDEFRecords");

            if ( nfcReaderTask != null
                    && !nfcReaderTask.getStatus().equals(AsyncTask.Status.FINISHED) ) {
                return;
            }

            if ( nfcReaderTask != null
                    && NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action) ) {
                nfcReaderTask = null;
            }

            // Display check fragment
            handleFragment(YagConstants.CHECK_IN, null);

            if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
                String type = intent.getType();
                if (MIME_TEXT_PLAIN.equals(type)) {

                    nfcReaderTask = new NdefReaderTask(MainActivity.this);
                    nfcReaderTask.execute(records);

                } else {
                    Log.d(DEBUG_TAG, "Wrong mime type: " + type);
                }
            }

//            if ( action.equals(NfcAdapter.ACTION_NDEF_DISCOVERED) ) {
//                Log.v(DEBUG_TAG, "ACTION_NDEF_DISCOVERED != null");
//                // Get the 1st NdefMessage
//                NdefMessage msg = (NdefMessage)
//                        nfcIntent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)[0];
//                // Get the playload of the first record
//                byte[] payloadData = msg.getRecords()[0].getPayload();
//
//                // Display Splash screen
//                handleFragment(YagthuConstants.CHECK_IN);
//            } else {
//                Log.v(DEBUG_TAG, "DOCUMENTS_LIST launches");
//                // Display Documents Fragment
//                handleFragment(YagthuConstants.DOCUMENTS_LIST);
//            }
        }
    }

    // Method called to display new Fragments
    public void handleFragment(int index, Object datas) {

        Fragment frag = null;
        String tag = "";

        // Switch to display Fragments and Tags
        switch(index) {
            // Add only
            case YagConstants.MANAGE:
                    frag = FragmentManageAccount.newInstance((User) datas);
                    tag = YagConstants.MANAGE_TAG;
                    actionBar.setTitle(getResources().getString(R.string.section_user_profile));
                    titlesActionBar.add(getResources().getString(R.string.section_user_profile));

            case YagConstants.PARAMS:
                if (index != YagConstants.MANAGE) {
                    frag = FragmentParams.newInstance();
                    tag = YagConstants.PARAMS_TAG;
                    actionBar.setTitle(getResources().getString(R.string.section_params));
                    titlesActionBar.add(getResources().getString(R.string.section_params));
                }

            case YagConstants.GALLERY:
            case YagConstants.CAMERA:
                if (index != YagConstants.MANAGE
                        && index != YagConstants.PARAMS) {

                    if (index == YagConstants.GALLERY)
                        frag = FragmentCamera.newInstance(YagConstants.GALLERY);
                    else
                        frag = FragmentCamera.newInstance(YagConstants.CAMERA);

                    tag = YagConstants.CAMERA_TAG;
                    actionBar.setTitle(getResources().getString(R.string.section_user_camera));
                    titlesActionBar.add(getResources().getString(R.string.section_user_camera));
                }

                actionBar.setHomeButtonEnabled(true);
                actionBar.setDisplayHomeAsUpEnabled(true);
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.main_frame, frag, tag)
                        .addToBackStack(null)
                        .commit();
                break;


            // With addToBackStack
            case YagConstants.USER_PROFILE:
                frag = FragmentProfile.newInstance();
                tag = YagConstants.USER_PROFILE_TAG;
                actionBar.setTitle(getResources().getString(R.string.section_user_profile));
                titlesActionBar.add(getResources().getString(R.string.section_user_profile));

            case YagConstants.ABOUT:
                if (index != YagConstants.USER_PROFILE) {
                    frag = FragmentAbout.newInstance();
                    tag = YagConstants.ABOUT_TAG;
                    actionBar.setTitle(getResources().getString(R.string.section_about));
                    titlesActionBar.add(getResources().getString(R.string.section_about));
                }

                actionBar.setHomeButtonEnabled(true);
                actionBar.setDisplayHomeAsUpEnabled(true);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_frame, frag, tag)
                        .addToBackStack(null)
                        .commit();
                break;

            // Without addToBackStack
            case YagConstants.CHECK_IN:
                frag = FragmentCheck.newInstance(YagConstants.CHECK_IN);
                tag = YagConstants.CHECK_IN_TAG;

            case YagConstants.USER_LOGIN:
                if (index != YagConstants.CHECK_IN) {
                    frag = FragmentLogin.newInstance(YagConstants.USER_LOGIN);
                    tag = YagConstants.USER_LOGIN_TAG;
                    actionBar.setTitle(getResources().getString(R.string.section_user_login));
                    titlesActionBar.add(getResources().getString(R.string.section_user_login));
                }

            case YagConstants.DOCUMENTS_LIST:
                if (index != YagConstants.CHECK_IN
                        && index != YagConstants.USER_LOGIN) {
                    frag = FragmentDocuments.newInstance(YagConstants.DOCUMENTS_LIST, (boolean) datas);
                    tag = YagConstants.DOCUMENTS_LIST_TAG;
                    actionBar.setTitle(getResources().getString(R.string.section_documents_list));
                    titlesActionBar.add(getResources().getString(R.string.section_documents_list));
                }

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_frame, frag, tag)
                        .commit();
                break;
        }
    }

    // Method to check user identification
    public boolean isUserIdentified() {
        if ( prefs != null
                && prefs.getString("user_email", "") != null
                && !prefs.getString("user_email", "").equals("")
                && prefs.getString("user_pass", "") != null
                && !prefs.getString("user_pass", "").equals("") ) {

            new LogIn().execute(
                    prefs.getString("user_email", ""),
                    prefs.getString("user_pass", ""));
            return true;
        } else {
            return false;
        }
    }

    // Method to display alert dialog
    public void showAlert(String title, String content) {
        if ( dialog != null && dialog.isShowing() )
            return;

        dialog = new AlertDialog.Builder(MainActivity.this)
                .setTitle(title)
                .setMessage(content)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_user_profile:
                handleFragment(YagConstants.USER_PROFILE, null );
                return true;
            case R.id.action_about:
                handleFragment(YagConstants.ABOUT, null );
                return true;
            case R.id.action_params:
                handleFragment(YagConstants.PARAMS, null );
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        /**
         * Call this before onPause, otherwise an IllegalArgumentException is thrown as well.
         */
        stopForegroundDispatch(this, nfcAdapter);
        super.onPause();
        if ( dialog != null && dialog.isShowing() ) {
            dialog.dismiss();
            dialog = null;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        /**
         * This method gets called, when a new Intent gets associated with the current activity instance.
         * Instead of creating a new activity, onNewIntent will be called. For more information have a look
         * at the documentation.
         *
         * In our case this method gets called, when the user attaches a Tag to the device.
         */
        handleNfcIntent(intent);
    }

    /**
     * @param activity The corresponding {@link Activity} requesting the foreground dispatch.
     * @param adapter The {@link NfcAdapter} used for the foreground dispatch.
     */
    public static void setupForegroundDispatch(final Activity activity, NfcAdapter adapter) {
        final Intent intent = new Intent(activity.getApplicationContext(), activity.getClass());
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        final PendingIntent pendingIntent = PendingIntent.getActivity(activity.getApplicationContext(), 0, intent, 0);

        IntentFilter[] filters = new IntentFilter[1];
        String[][] techList = new String[][]{};

        // Notice that this is the same filter as in our manifest.
        filters[0] = new IntentFilter();
        filters[0].addAction(NfcAdapter.ACTION_NDEF_DISCOVERED);
        filters[0].addCategory(Intent.CATEGORY_DEFAULT);
        try {
            filters[0].addDataType(MIME_TEXT_PLAIN);
        } catch (MalformedMimeTypeException e) {
            throw new RuntimeException("Check your mime type.");
        }

        adapter.enableForegroundDispatch(activity, pendingIntent, filters, techList);
    }

    /**
     * @param activity The corresponding {@link MainActivity} requesting to stop the foreground dispatch.
     * @param adapter The {@link NfcAdapter} used for the foreground dispatch.
     */
    public static void stopForegroundDispatch(final Activity activity, NfcAdapter adapter) {
        adapter.disableForegroundDispatch(activity);
    }

    // Detect if a service is already running in background - used to restart service and keep memory safe
    private boolean isServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (this.getSupportFragmentManager().getBackStackEntryCount() > 0) {
            // Update title ActionBar
            if (titlesActionBar.size() > 1)
                titlesActionBar.pollLast();

            this.getSupportFragmentManager().popBackStack();

            // Update title ActionBar
            actionBar.setTitle( titlesActionBar.getLast() );

            // Update home button
            if (this.getSupportFragmentManager().getBackStackEntryCount() == 1) {
                actionBar.setHomeButtonEnabled(false);
                actionBar.setDisplayHomeAsUpEnabled(false);
            }
        } else
            this.finish();
    }

    // =========================================================================================
    // AsyncTask methods
    // =========================================================================================
    // LOGIN
    public void checkLogIn(String name, String pass) { new LogIn().execute(name, pass); }
    private class LogIn extends AsyncTask<String, Void, String> {
        private JSONObject error = null, response;
        private String  login,
                        pass,
                        userid,
                        token,
                        classId;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Display check fragment
            handleFragment(YagConstants.CHECK_IN, null );
        }

        @Override
        protected String doInBackground(String... params) {
            login = params[0];
            pass = params[1];

//            // Conversion SHA1
//            try {
//                try {
//                    passwordCrypted = YagConstants.SHA1( pass );
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
//            } catch (NoSuchAlgorithmException e) {
//                e.printStackTrace();
//            }

            response = webservices.login(login, pass);
            if ( response != null ) {
                // Check if there is any error
                if (response.isNull("error")) {
                    try {
                        if ( !response.isNull("id") )
                            token = response.getString("id");
                    } catch (JSONException jex) {
                        Log.e("///-- LOGIN TOKEN:", jex.toString());
                        return YagConstants.TAG_CANCELLED;
                    }
                    try {
                        if ( !response.isNull("userId") )
                            userid = response.getString("userId");
                    } catch (JSONException jex) {
                        Log.e("///-- LOGIN USERID:", jex.toString());
                        return YagConstants.TAG_CANCELLED;
                    }
                    try {
                        if ( !response.isNull("class_id") )
                            classId = response.getString("class_id");
                    } catch (JSONException jex) {
                        Log.e("///-- LOGIN CLASSID:", jex.toString());
                        return YagConstants.TAG_CANCELLED;
                    }

                    return YagConstants.TAG_SUCCESS;
                } else {
                    try {
                        error = new JSONObject(response.getString("error"));
                    } catch (JSONException jex) {
                        Log.e("///-- LOGIN RESPONSE:", jex.toString());
                    }
                    return errorResponse(error);
                }
            }

            return errorResponse(error);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if ( result.equals( YagConstants.TAG_SUCCESS )) {
                // Save info into preferences
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("user_id", userid);
                editor.putString("user_token", token);
                editor.putString("user_email", login);
                editor.putString("user_pass", pass);
                editor.commit();

                if ( getSupportFragmentManager()
                        .findFragmentByTag(YagConstants.CHECK_IN_TAG) != null
                            && getSupportFragmentManager()
                                .findFragmentByTag(YagConstants.CHECK_IN_TAG).isVisible() ) {
                    // Display documents Fragment only if Check fragment is visible
                    handleFragment(YagConstants.DOCUMENTS_LIST, false);
                }

            } else
                showAlert("Connexion", result);
        }
    }

    // NFC RECEIVED
    public void returnFromNfc(String msg) {
        String[] parts = msg.split("-");
        String classId = parts[0].replace(":","");
        String className = parts[1].replace("Room:","");

        Toast.makeText(MainActivity.this,
                "Vous êtes présent dans la salle " + className, Toast.LENGTH_LONG).show();

        new GetCourseFromClasses().execute(classId);
    }
    private class GetCourseFromClasses extends AsyncTask<String, Void, String> {
        private JSONArray response;
        private JSONObject error = null;

        @Override
        protected String doInBackground(String... params) {
            String roomId = params[0];
            String courseId = null;

            webservices = new WebservicesRequest();
            response = webservices.getCoursesDateFilter(
                    prefs.getString("user_class", ""),
                    "2014-04-03T08:00:00.000Z", // Test.
                    prefs.getString("user_token", ""));


            if (response != null) {
                // Check if there is any error
                if (response.length() > 0) {
                    // Populate arrays
                    try {
                        for (int i = 1; i < response.length(); ++i) {
                            JSONObject item = response.getJSONObject(i);

                            if ( item.getString("classRoomId").equals(roomId) ) {
                                roomId = item.getString("id");
                                Log.i(DEBUG_TAG, "ClassRoom detected with CourseId = " +courseId);
                            }

                            // TODO: check date anterior from now
                        }

                        return courseId;

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
            if (result != null && !result.equals(YagConstants.TAG_CANCELLED)) {
                new SetAttendance().execute(result);
            } else
                showAlert("Connexion", result);
        }
    }

    // ATTENDANCE
    private class SetAttendance extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            webservices = new WebservicesRequest();
            webservices.postUserAttendance(params[0],
                    prefs.getString("user_id", ""),
                    prefs.getString("user_token", ""));

            return YagConstants.TAG_SUCCESS;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if ( result.equals( YagConstants.TAG_SUCCESS )) {
                if (getSupportFragmentManager().findFragmentByTag(YagConstants.CHECK_IN_TAG) != null
                        && getSupportFragmentManager()
                        .findFragmentByTag(YagConstants.CHECK_IN_TAG).isVisible()) {
                    // Display documents Fragment only if Check fragment is visible
                    handleFragment(YagConstants.DOCUMENTS_LIST, true);
                }

            } else
                showAlert("Connexion", result);
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