package edu.ucsd.calab.cardea;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.Vibrator;
import android.util.Log;

<<<<<<< HEAD:app/src/main/java/edu/ucsd/calab/usingextrasensorylabels/serverConnection.java
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import static java.lang.Math.log;

=======
>>>>>>> 38b76479723afd52fb104e4d35e8cd8480d7e02e:app/src/main/java/edu/ucsd/calab/cardea/serverConnection.java
/**
 * Created by youzhenghong on 17/11/2017.
 */

public class serverConnection extends BroadcastReceiver {

    public static final String ESA_BROADCAST_SAVED_PRED_FILE = "edu.ucsd.calab.cardea.broadcast.saved_prediction_file";
    public static final String ESA_BROADCAST_EXTRA_KEY_TIMESTAMP = "timestamp";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("vibarate", "received vibarate");
        if (ESA_BROADCAST_SAVED_PRED_FILE.equals(intent.getAction())) {
            Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            // Vibrate for 400 milliseconds
            Date currentDate = new Date();
            String newTimestamp = intent.getStringExtra(ESA_BROADCAST_EXTRA_KEY_TIMESTAMP);
            String userID = MainActivity.user;
            String prediction = readESALabelsFileForMinute(userID, newTimestamp, true);
            //Log.i("predictions:    ", prediction);
            Log.i("newTime    ", newTimestamp);
            Log.i("user    ", userID);
            Log.i("user    ", prediction);
        }
        pushToServer();
    }

    private void pushToServer() {

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
