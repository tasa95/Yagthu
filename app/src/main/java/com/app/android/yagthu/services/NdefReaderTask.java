package com.app.android.yagthu.services;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import com.app.android.yagthu.main.MainActivity;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * @author Ralf Wondratschek
 *
 * Object: Background task for reading the data. Do not block the UI thread while reading.
 * Used by: Application
 *
 * @author Ralf Wondratschek
 * @edited Fllo (Florent Blot) & Tasa (Thierry Allard Saint Albin)
 * @version 1.0
 */
public class NdefReaderTask extends AsyncTask<NdefRecord, Void, String> {

    // Debug
    private static final String DEBUG_TAG = "NDEF Reader Task";

    private MainActivity activity;

    public NdefReaderTask(Activity activity) {
        this.activity = (MainActivity) activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @SuppressLint("NewApi")
    @Override
    protected String doInBackground(NdefRecord... params) {
        NdefRecord record = params[0];
        NdefMessage ndefMessage = null;
        NdefRecord[] records = null;

        if (Build.VERSION_CODES.BASE >= Build.VERSION_CODES.JELLY_BEAN) {
            ndefMessage = new NdefMessage(record);
            records = ndefMessage.getRecords();
        } else {
            records = new NdefRecord[1];
            records[0] = record;
        }
        for (NdefRecord ndefRecord : records) {
            if (ndefRecord.getTnf() == NdefRecord.TNF_WELL_KNOWN
                    && Arrays.equals(ndefRecord.getType(), NdefRecord.RTD_TEXT)) {
                try {
                    return readText(ndefRecord);
                } catch (UnsupportedEncodingException e) {
                    Log.e(DEBUG_TAG, "Unsupported Encoding", e);
                }
            }
        }

        return null;
    }

    private String readText(NdefRecord record) throws UnsupportedEncodingException {
        /*
         * See NFC forum specification for "Text Record Type Definition" at 3.2.1
         *
         * http://www.nfc-forum.org/specs/
         *
         * bit_7 defines encoding
         * bit_6 reserved for future use, must be 0
         * bit_5..0 length of IANA language code
         */

        byte[] payload = record.getPayload();

        // Get the Text Encoding
        String textEncoding = "UTF-8";
        if ((payload[0] & 128) == 0)
            textEncoding = "UTF-8";
        else
            textEncoding = "UTF-16";

        // Get the Language Code
        int languageCodeLength = payload[0] & 0063;

        // String languageCode = new String(payload, 1, languageCodeLength, "US-ASCII");
        // e.g. "en"

        // Get the Text
        String response = new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);
        return response;
    }

    @Override
    protected void onPostExecute(String result) {
        if (result != null) {
            Log.d(DEBUG_TAG, "Read content: " + result);
            if (activity != null)
                activity.returnFromNfc(result);
        } else {
            Log.v(DEBUG_TAG, "Result returns null");
        }
    }
}
