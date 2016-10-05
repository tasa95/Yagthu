package com.app.android.yagthu;

import android.app.Application;

import java.util.ArrayList;


/**
 * Object: Application Class
 *
 * @author Fllo (Florent Blot) & Tasa (Thierry Allard Saint Albin)
 * @version 1.0
 */
//@ReportsCrashes(formKey = "",
//        mailTo = "mail.de.florent@gmail.com",
//        mode = ReportingInteractionMode.TOAST,
//        resToastText = R.string.error_crash_report)
public class YagApplication extends Application {
    // Using navigation
    private ArrayList<String> userExperience = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
//        ACRA.init(this);
//        ACRA.getErrorReporter().putCustomData("path", getUserExperience()); // user'scenario since open application
    }

    private String getUserExperience() {
        String result = "Open application";
        if ( userExperience != null && userExperience.size() > 0)
            for (String exp : userExperience)
                result += " > " + exp;
        return result;
    }

    public void setUserExperience(String classname) {
        if (classname != null & classname.length() > 0)
            userExperience.add(classname);
        else
            return;
    }
}
