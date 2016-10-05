package com.app.android.yagthu.main;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Object: Constants elements (String, int, Tags, Arrays)
 * Used by: Application Context
 *
 * @author Fllo (Florent Blot) & Tasa (Thierry Allard Saint Albin)
 * @version 1.0
 */
public class YagConstants {

    /**
     * Constants int (Position for Fragments)
     */
    // Check NFC section
    public static final int CHECK_IN = 1;
    public static final int CHECK_OUT = 2;
    // Documents section
    public static final int DOCUMENTS_LIST = 11;
    // Users section
    public static final int USER_LOGIN = 21;
    public static final int USER_PROFILE = 22;
    // Parameters
    public static final int PARAMS = 31;
    public static final int ABOUT = 32;
    public static final int MANAGE = 33;
    // Camera
    public static final int CAMERA = 41;
    public static final int GALLERY = 42;

    /**
     * Constants String (Tags for Fragments)
     */
    // Check NFC section
    public static final String CHECK_IN_TAG = "CHECK_IN";
    public static final String CHECK_OUT_TAG = "CHECK_OUT";
    // Documents section
    public static final String DOCUMENTS_LIST_TAG = "DOCUMENTS_LIST";
    // Users section
    public static final String USER_LOGIN_TAG = "USER_LOGIN";
    public static final String USER_PROFILE_TAG = "USER_PROFILE";
    // Parameters
    public static final String PARAMS_TAG = "PARAMS";
    public static final String ABOUT_TAG = "ABOUT";
    public static final String MANAGE_TAG = "MANAGE";
    // Camera
    public static final String CAMERA_TAG = "CAMERA";

    /**
     * Constants String (Tags for Profile scores & attendance)
     */
    // Tag for 1st and 2nd score semester
    public static final String SEMESTER = "TYPE_SEMESTER";
    // Tag for attendance
    public static final String ATTENDANCE = "TYPE_ATTENDANCE";

    /**
     * Constants Preferences
     */
    // Folder destination
    public static final String PREFERENCE_NAME = "com.app.android.yagthu";

    /**
     * Constants Status
     */
    public static final String TAG_SUCCESS = "SUCCESS";
    public static final String TAG_ERROR = "ERROR";
    public static final String TAG_CANCELLED = "CANCELLED";
    public static final String TAG_MISSING_FIELD = "MISSING_FIELD";

    /**
     * SHA1 encoding method
     */
    // Convert String to SHA1
    public static String SHA1(String text)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update(text.getBytes("iso-8859-1"), 0, text.length());
        byte[] sha1hash = md.digest();
        return convertToHex(sha1hash);
    }
    // Convert byte[] to Hex
    private static String convertToHex(byte[] data) {
        StringBuilder buf = new StringBuilder();
        for (byte b : data) {
            int halfbyte = (b >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                buf.append((0 <= halfbyte) && (halfbyte <= 9) ?
                        (char) ('0' + halfbyte) : (char) ('a' + (halfbyte - 10)));
                halfbyte = b & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }

    /**
     * NFC Conversion and test methods
     **/
    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
    /**
     * Simple way to output byte[] to hex (my readable preference)
     * This version quite speedy; originally from: http://stackoverflow.com/a/9855338
     *
     * @param bytes yourByteArray
     * @return string
     *
     */
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
    /**
     * Constant-time Byte Array Comparison
     * Less overheard, safer. Originally from: http://codahale.com/a-lesson-in-timing-attacks/
     *
     * @param1 bytes[] a
     * @param2 bytes[] b
     * @return boolean
     *
     */
    public static boolean isEqual(byte[] a, byte[] b) {
        if (a.length != b.length) {
            return false;
        }

        int result = 0;
        for (int i = 0; i < a.length; i++) {
            result |= a[i] ^ b[i];
        }
        return result == 0;
    }

}