package com.app.android.yagthu.webservices;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Object: CRUD Webservices class
 * Used by: Application Context
 *
 * @author Fllo (Florent Blot) & Tasa (Thierry Allard Saint Albin)
 * @version 1.0
 */
public class WebservicesRequest {

    // =========================================================================================
    // Request elements
    // =========================================================================================
    // REQUEST URL
    private static final String URL = "https://arcane-plateau-4988.herokuapp.com:443/api";
    // USER URL
    private static final String USER_URL = URL + "/user";
    private static final String USER_ID_URL = USER_URL + "/%1$s";
    private static final String USER_LOGIN_URL = USER_URL + "/login";
    private static final String USER_GRADES_URL = USER_URL + "/%1$s/grades";
    private static final String USER_NON_ATTENDANCE_URL = USER_URL + "/%1$s/my-non-attendance";
    private static final String CLASS_URL = URL + "/Class";
    private static final String CLASS_ID_URL = CLASS_URL + "/%1$s";
    private static final String COURSE_URL = URL + "/Course";
    private static final String FILTER_DATE_COURSE_URL = CLASS_ID_URL + "/Course";
    public static final String USER_PHOTOS = URL + "photos_amazons/yagthu-photo/download/%1$s";

    // RESQUEST ELEMENTS
    private static final int GET = 0, POST = 1, PUT = 2, DELETE = -1;
    private JSONObject response;

    // =========================================================================================
    // Request methods
    // =========================================================================================
    // =========================================================================================
    // User methods
    // =========================================================================================
    /**
     * login: connexion avec login et mot de passe
     *
     * @param login (String) Login ou email de l'utilisateur
     * @param pass (String) Mot de passe utilisateur
     * @return JSONObject
     */
    public JSONObject login(String login, String pass) {
        // Prepare args
        ArrayList<NameValuePair> argsBody = new ArrayList<>(2);
        argsBody.add(new BasicNameValuePair("email", login));
        argsBody.add(new BasicNameValuePair("password", pass));

        RestParser restParser = new RestParser(argsBody, null);
        JSONObject response = null;
        try {
            response = new JSONObject(restParser.doRequest(POST, USER_LOGIN_URL));
        } catch (JSONException jex) {
            Log.e("///-- JSON OBJECT", jex.toString());
        }

        return response;
    }

    /**
     * getUserInformations: recupere les infos d'un utilisateur
     *
     * @param userId (String) ID de l'utilisateur
     * @param token (String) Token de l'utilisateur
     * @return JSONObject
     */
    public JSONObject getUserInformations(String userId, String token) {
        // Prepare args
        ArrayList<NameValuePair> argsBody = new ArrayList<>(1);
        argsBody.add(new BasicNameValuePair("token", token));

        RestParser restParser = new RestParser(argsBody, null);
        JSONObject response = null;
        try {
            response = new JSONObject(restParser.doRequest(GET, String.format(USER_ID_URL, userId)));
        } catch (JSONException jex) {
            Log.e("///-- JSON OBJECT", jex.toString());
        }

        return response;
    }

    // =========================================================================================
    // Attendance methods
    // =========================================================================================
    /**
     * getUserNonAttendances: recupere les absences d'un utilisateur specifique
     *
     * @param userId (String) ID de l'utilisateur
     * @param token (String) Token de l'utilisateur
     * @return JSONObject
     */
    public JSONArray getUserNonAttendances(String userId, String token) {
        // Prepare args
        ArrayList<NameValuePair> argsBody = new ArrayList<>(1);
        argsBody.add(new BasicNameValuePair("token", token));

        RestParser restParser = new RestParser(argsBody, null);
        try {
            JSONObject response =
                    new JSONObject(restParser.doRequest(GET, String.format(USER_NON_ATTENDANCE_URL, userId)));
            if (response == null) {
                return null;
            }

            return new JSONArray(response.getString("response"));
        } catch (JSONException jex) {
            Log.e("///-- JSON ARRAY", jex.toString());
        }

        return null;
    }

    /**
     * getUserNonAttendances: recupere les absences d'un utilisateur specifique
     *
     * @param userId (String) ID de l'utilisateur
     * @param token (String) Token de l'utilisateur
     * @return JSONObject
     */
    public void postUserAttendance(String courseId, String userId, String token) {
        // Prepare args
        ArrayList<NameValuePair> argsBody = new ArrayList<>(2);
        argsBody.add(new BasicNameValuePair("courseId", courseId));
        argsBody.add(new BasicNameValuePair("token", token));

        RestParser restParser = new RestParser(argsBody, null);
        restParser.doRequest(GET, String.format(USER_NON_ATTENDANCE_URL, userId));
    }

    // =========================================================================================
    // Class methods
    // =========================================================================================
    /**
     * getClassInformations: recupere les infos d'une classe specifique
     *
     * @param classId (String) ID de la classe
     * @param token (String) Token de l'utilisateur
     * @return JSONObject
     */
    public JSONObject getClassInformations(String classId, String token) {
        // Prepare args
        ArrayList<NameValuePair> argsBody = new ArrayList<>(1);
        argsBody.add(new BasicNameValuePair("token", token));

        RestParser restParser = new RestParser(argsBody, null);
        JSONObject response = null;
        try {
            response = new JSONObject(restParser.doRequest(GET, String.format(CLASS_ID_URL, classId)));
        } catch (JSONException jex) {
            Log.e("///-- JSON OBJECT", jex.toString());
        }

        return response;
    }

    // =========================================================================================
    // Course methods
    // =========================================================================================
    /**
     * getCoursesDateFilter: recupere les cours posterieurs a une date specifique
     *
     * @param classId (String) ID de la classe
     * @param token (String) Token de l'utilisateur
     * @return JSONObject
     */
    public JSONArray getCoursesDateFilter(String classId, String date, String token) {
        // Prepare args
        ArrayList<NameValuePair> argsBody = new ArrayList<>(2);
        argsBody.add(new BasicNameValuePair("filter[where][startDate][gt]", date));
        argsBody.add(new BasicNameValuePair("token", token));

        RestParser restParser = new RestParser(argsBody, null);
        try {
            JSONObject response =
                    new JSONObject(restParser.doRequest(GET,
                            String.format(FILTER_DATE_COURSE_URL, classId)));
            if (response == null) {
                return null;
            }

            return new JSONArray(response.getString("response"));
        } catch (JSONException jex) {
            Log.e("///-- JSON ARRAY", jex.toString());
        }

        return null;
    }

    // =========================================================================================
    // Grades methods
    // =========================================================================================
    /**
     * getUserGrades: recupere les notes d'un utilisateur specifique
     *
     * @param userId (String) ID de l'utilisateur
     * @param token (String) Token de l'utilisateur
     * @return JSONObject
     */
    public JSONArray getUserGrades(String userId, String token) {
        // Prepare args
        ArrayList<NameValuePair> argsBody = new ArrayList<>(1);
        argsBody.add(new BasicNameValuePair("token", token));

        RestParser restParser = new RestParser(argsBody, null);
        try {
            return new JSONArray(restParser.doRequest(GET, String.format(USER_GRADES_URL, userId)));
        } catch (JSONException jex) {
            Log.e("///-- JSON ARRAY", jex.toString());
        }

        return null;
    }

    // =========================================================================================
    // REST JSON Parser method
    // =========================================================================================
    private class RestParser {
        private InputStream inputStream = null;
        private String jsonReturn = null;

        ArrayList<NameValuePair> params = new ArrayList<>(),
                                 headers = new ArrayList<>();

        public RestParser(ArrayList<NameValuePair> body, ArrayList<NameValuePair> head) {
            params = new ArrayList<>();
            headers = new ArrayList<>();

            if (body != null && body.size() > 0) params = body;
            if (head != null && head.size() > 0) headers = head;
        }

        public String doRequest(int method, String url) {
            switch (method) {
                /** GET REQUEST **/
                case GET: {
                    String paramsValues = "";
                    if ( params != null && !params.isEmpty() ) {
                        paramsValues += "?";
                        for (NameValuePair p : params) {
                            String paramString = null;
                            try {
                                paramString = p.getName() + "=" + URLEncoder.encode(p.getValue(), "UTF-8");
                            } catch (UnsupportedEncodingException e) {
                                Log.e("///-- GET ENCODING", e.toString());
                            }
                            if (paramsValues.length() > 1) {
                                paramsValues += "&" + paramString;
                            } else {
                                paramsValues += paramString;
                            }
                        }
                    }

                    url += paramsValues;
                    HttpGet request = new HttpGet(url);
                    Log.d("///-- GET REQUEST:", url);

                    if ( headers != null && !headers.isEmpty() )
                        for (NameValuePair h : headers)
                            request.addHeader(h.getName(), h.getValue());

                    jsonReturn = executeRequest(request);
                    break;
                }
                /** POST REQUEST **/
                case POST: {
                    HttpPost request = new HttpPost(url);
                    Log.d("///-- POST REQUEST:", url);

                    if ( headers != null && !headers.isEmpty() )
                        for (NameValuePair h : headers)
                            request.addHeader(h.getName(), h.getValue());

                    if ( params != null && !params.isEmpty() ) {
                        try {
                            request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                        } catch (UnsupportedEncodingException e) {
                            Log.e("///-- POST ENCODING", e.toString());
                        }
                    }

                    jsonReturn = executeRequest(request);
                    break;
                }
            }

            return jsonReturn;
        }

        private String executeRequest(HttpUriRequest req) {
            String result = null;
            try {
                HttpClient client = new DefaultHttpClient();

                HttpResponse httpResponse = client.execute(req);
                int status = httpResponse.getStatusLine().getStatusCode();
                Log.d("///-- REQUEST RESPONSE:", "Status response: " + status);
                String reason = httpResponse.getStatusLine().getReasonPhrase();
                Log.d("///-- REQUEST RESPONSE:", "Reason response: "+reason);
                HttpEntity entity = httpResponse.getEntity();

                if (entity != null) {
                    inputStream = entity.getContent();
                }

            } catch (UnsupportedEncodingException ex) {
                Log.e("///-- ENCODING", ex.toString());
            } catch (ClientProtocolException ex) {
                Log.e("///-- PROTOCOL", ex.toString());
            } catch (Exception ex) {
                Log.e("///-- NETWORKING", ex.toString());
                ex.printStackTrace();
            }

            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;

                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }

                inputStream.close();
                result = sb.toString();
                Log.d("///-- JSON RESPONSE:", result);
            } catch (Exception e) {
                Log.e("///-- BUFFER ERROR", "Error converting result " + e.toString());
            }

            return result;
        }
    }
}
