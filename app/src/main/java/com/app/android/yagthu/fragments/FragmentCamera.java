package com.app.android.yagthu.fragments;

import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.app.android.yagthu.R;
import com.app.android.yagthu.YagApplication;
import com.app.android.yagthu.main.YagConstants;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Object: Fragment for camera and preview
 * Used by: ManageAccountFragment
 *
 * @author Fllo (Florent Blot) & Tasa (Thierry Allard Saint Albin)
 * @version 1.0
 */
public class FragmentCamera extends Fragment {

    // Debug
    private static final String DEBUG_TAG = "Fragment Camera";
    private static final String DEBUG_PREVIEW_TAG = "Fragment Debug Preview";

    /**
     * Arguments and Instantiate elements
     */
    private static final String SELF_TAG = "SELF_TAG";
    private int SELF_POSITION;
    private Activity activity;

    /**
     * Views elements
     */
    private Camera camera;
    private CameraPreview preview;
    private Button cameraCapture;

    // Empty constructor (required)
    public FragmentCamera() { }

    // Instance method of Fragment
    public static FragmentCamera newInstance(int initSelf) {
        FragmentCamera fragment = new FragmentCamera();
        Bundle args = new Bundle();
        args.putInt(SELF_TAG, initSelf);
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add user experience in ACRA delivery
        YagApplication yagApp = (YagApplication) getActivity().getApplication();
        yagApp.setUserExperience("FragmentCamera");
        // Retrieve arguments (camera or gallery)
        if (getArguments() != null) {
            SELF_POSITION = getArguments().getInt(SELF_TAG);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_camera, container, false);

        if (SELF_POSITION == YagConstants.CAMERA) {
            // Display camera
            camera = getCameraInstance();
            if (camera != null) {
                // Get Camera parameters and apply ours
                camera.setDisplayOrientation(90);
                Camera.Parameters params = camera.getParameters();
                params.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
                params.setJpegQuality(90);
                camera.setParameters(params);

                // TODO: if Camera.getNumberOfCameras() > 2, use front camera or display rotation button
                // TODO: setFlashMode() = active flash (?)

                preview = new CameraPreview(activity, camera);
                FrameLayout containerPreview = (FrameLayout) v.findViewById(R.id.camera_preview);
                containerPreview.addView(preview);

                // Actions
                cameraCapture = (Button) v.findViewById(R.id.button_capture);
                cameraCapture.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        camera.takePicture(null, null, captureBitmap);
                    }
                });
            }
        } else {
            // Display gallery
        }
        return v;
    }

    // Get camera instance
    public static Camera getCameraInstance() {
        Camera c = null;
        try { c = Camera.open(); }
        catch (Exception e) {
            // Camera is not available (in use or does not exist)
        }
        return c;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (camera != null) {
            camera.release();
            camera = null;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (camera != null) {
            camera.release();
            camera = null;
        }
    }

    /**
     * Capture bitmap method
     */
    private PictureCallback captureBitmap = new PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            File pictureFile = getOutputTemporaryMediaFile();
            if (pictureFile == null) {
                Log.d(DEBUG_TAG, "Error creating media file, check storage permissions");
                return;
            }

            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(data);
                fos.close();
            } catch (FileNotFoundException e) {
                Log.d(DEBUG_TAG, "File not found: " + e.getMessage());
            } catch (IOException e) {
                Log.d(DEBUG_TAG, "Error accessing file: " + e.getMessage());
            }
        }
    };

    /**
     * Create temporary media file
     */
    private static File getOutputTemporaryMediaFile() {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "Yagthu-temp");
        if (! mediaStorageDir.exists()) {
            if (! mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator +
            "IMG_"+ timeStamp + ".jpg");

        return mediaFile;
    }

    /**
     * Class of preview SurfaceView
     */
    private class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
        private SurfaceHolder holder;
        private Camera surfaceCamera;

        public CameraPreview(Context context, Camera camera) {
            super(context);
            surfaceCamera = camera;

            // Install a SurfaceHolder.Callback so we get notified when the
            // underlying surface is created and destroyed.
            holder = getHolder();
            holder.addCallback(this);
            // deprecated setting, but required on Android versions prior to 3.0
            holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }

        public void surfaceCreated(SurfaceHolder holder) {
            // The Surface has been created, now tell the camera where to draw the preview.
            try {
                surfaceCamera.setPreviewDisplay(holder);
                surfaceCamera.startPreview();
            } catch (IOException e) {
                Log.d(DEBUG_PREVIEW_TAG, "Error setting camera preview: " + e.getMessage());
            }
        }

        public void surfaceDestroyed(SurfaceHolder holder) {
            // empty. Take care of releasing the Camera preview in your activity.
        }

        public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
            // If your preview can change or rotate, take care of those events here.
            // Make sure to stop the preview before resizing or reformatting it.

            if (holder.getSurface() == null){
                // preview surface does not exist
                return;
            }

            // stop preview before making changes
            try {
                surfaceCamera.stopPreview();
            } catch (Exception e){
                // ignore: tried to stop a non-existent preview
            }

            // set preview size and make any resize, rotate or
            // reformatting changes here

            // start preview with new settings
            try {
                surfaceCamera.setPreviewDisplay(holder);
                surfaceCamera.startPreview();

            } catch (Exception e){
                Log.d(DEBUG_PREVIEW_TAG, "Error starting camera preview: " + e.getMessage());
            }
        }
    }
}
