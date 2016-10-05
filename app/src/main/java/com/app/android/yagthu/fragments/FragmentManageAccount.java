package com.app.android.yagthu.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.android.yagthu.R;
import com.app.android.yagthu.YagApplication;
import com.app.android.yagthu.main.MainActivity;
import com.app.android.yagthu.main.YagConstants;
import com.app.android.yagthu.models.User;

/**
 * Object: Fragment for change Account information
 * Used by: ProfileFragment
 *
 * @author Fllo (Florent Blot) & Tasa (Thierry Allard Saint Albin)
 * @version 1.0
 */
public class FragmentManageAccount extends Fragment {

    // Debug
    private static final String DEBUG_TAG = "Fragment Manage Account";

    /**
     * Arguments and Instantiate elements
     */
    private Activity activity;
    private User user;

    /**
     * Views elements
     */
    private ImageView accountAvatar;
    private TextView accountAvatarStatus;

    // Empty constructor (required)
    public FragmentManageAccount() { }

    // Instance method of Fragment
    public static FragmentManageAccount newInstance(User user) {
        FragmentManageAccount frag = new FragmentManageAccount();
        Bundle args = new Bundle();
        args.putParcelable("USER", user);
        frag.setArguments(args);
        return frag;
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
        YagApplication application = (YagApplication) getActivity().getApplication();
        application.setUserExperience("FragmentManageAccount");

        if (getArguments() != null) {
            user = getArguments().getParcelable("USER");
        }
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
        View v = inflater.inflate(R.layout.fragment_manage_account, container, false);

        accountAvatar = (ImageView) v.findViewById(R.id.account_user_avatar);
        accountAvatarStatus = (TextView) v.findViewById(R.id.account_user_avatar_status);

        accountAvatarStatus.setText("En cours de validation");

        ((EditText) v.findViewById(R.id.user_account_login)).setText(user.getLogin());
        ((EditText) v.findViewById(R.id.user_account_email)).setText(user.getEmail());
        ((EditText) v.findViewById(R.id.user_account_firstname)).setText(user.getFirstName());
        ((EditText) v.findViewById(R.id.user_account_lastname)).setText(user.getName());

        final Bitmap avatarBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.photo);
        accountAvatar.post(new Runnable() {
            @Override
            public void run() {
                accountAvatar.setImageBitmap(setBitmapCropped(avatarBitmap));
            }
        });

        // Open camera fragment
        accountAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: display camera with animation (start oval from avatar to current content)
                // Check the device
                if (doesCameraExist(activity)) {
                    // Open the camera
                    ((MainActivity) getActivity()).handleFragment(YagConstants.CAMERA, null );
                } else {
                    // Open the gallery
                    ((MainActivity) getActivity()).handleFragment(YagConstants.GALLERY, null );
                }
            }
        });

//        SharedPreferences prefs = getActivity().
//                getSharedPreferences(YagthuConstants.PREFERENCE_NAME, Context.MODE_PRIVATE);
//        userFirstname.setText(prefs.getString("user_login", "Thierry"));
//        userLastname.setText("Allard Saint-Albin");

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
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

    // Check device's camera
    private boolean doesCameraExist(Context context) {
        if (context.getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA))
            return true;
        else
            return false;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}