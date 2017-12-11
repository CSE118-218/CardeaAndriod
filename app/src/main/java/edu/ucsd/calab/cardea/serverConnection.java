package edu.ucsd.calab.cardea;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.Vibrator;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import static java.lang.Math.log;

/**
 * Created by youzhenghong on 17/11/2017.
 */

public class serverConnection extends BroadcastReceiver {

    public static final String ESA_BROADCAST_SAVED_PRED_FILE = "edu.ucsd.calab.extrasensory.broadcast.saved_prediction_file";
    public static final String ESA_BROADCAST_EXTRA_KEY_TIMESTAMP = "timestamp";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("vibarate", "received vibarate");
        if (ESA_BROADCAST_SAVED_PRED_FILE.equals(intent.getAction())) {
            Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            // Vibrate for 400 milliseconds
            v.vibrate(400);
            Date currentDate = new Date();
            String newTimestamp = intent.getStringExtra(ESA_BROADCAST_EXTRA_KEY_TIMESTAMP);
            String userID = MainActivity.user;
            String prediction = readESALabelsFileForMinute(userID, newTimestamp, true);
            //Log.i("predictions:    ", prediction);
            Log.i("newTime    ", newTimestamp);
            Log.i("user    ", userID);
            Log.i("user    ", prediction);
            try {
                pushToServer(prediction);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private void pushToServer(final String predStr) {
        RequestQueue queue = Volley.newRequestQueue(null);
        String url = "http://ec2-54-202-77-233.us-west-2.compute.amazonaws.com:8000/activity/progress/update";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response.toString());
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                params.put("pred", predStr);
                return params;
            }
        };
        queue.add(postRequest);
    }

    private String readESALabelsFileForMinute(String uuidPrefix,String timestamp,boolean serverOrUser) {
        try {
            File esaFilesDir = MainActivity.userFileDir;
            if (esaFilesDir == null) {
                Log.i("error 1    ", "no such file");
                // Cannot find the directory where the label files should be
                return null;
            }
            String fileSuffix = serverOrUser ? SERVER_PREDICTIONS_FILE_SUFFIX : USER_REPORTED_LABELS_FILE_SUFFIX;
            File minuteLabelsFile = new File(esaFilesDir,UUID_DIR_PREFIX+
                    uuidPrefix+"/"+timestamp + fileSuffix);
            // Read the file:
            StringBuilder text = new StringBuilder();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(minuteLabelsFile));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            bufferedReader.close() ;
            return text.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static final String SERVER_PREDICTIONS_FILE_SUFFIX = ".server_predictions.json";
    private static final String USER_REPORTED_LABELS_FILE_SUFFIX = ".user_reported_labels.json";
    private static final String UUID_DIR_PREFIX = "extrasensory.labels.";



}
