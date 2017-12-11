package edu.ucsd.calab.cardea;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class ScrollingActivity extends AppCompatActivity {

    private static final int TOP_N_PROBABLE_LABELS = 1;

    private static final String LOG_TAG = "[Using ESA]";

    public static final String ESA_BROADCAST_SAVED_PRED_FILE = "edu.ucsd.calab.extrasensory.broadcast.saved_prediction_file";
    public static final String ESA_BROADCAST_EXTRA_KEY_TIMESTAMP = "timestamp";



    private String _uuidPrefix = null;
    private String _timestamp = null;
    public static final String NO_USER = "no user";
    private static final String NO_TIMESTAMP = "no timestamp";



    private boolean fillUserSelector() {
        //pushToServer();
        boolean haveUsers = true;
        List<String> uuidPrefixes = getUsers();
        // first check if there are any users at all:
        if ((uuidPrefixes == null) || (uuidPrefixes.isEmpty())) {
            uuidPrefixes = new ArrayList<>();
            uuidPrefixes.add(NO_USER);
            haveUsers = false;
        }

        Spinner uuidSpinner = (Spinner)findViewById(R.id.selecting_uuid_prefix);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, uuidPrefixes);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        uuidSpinner.setAdapter(dataAdapter);

        uuidSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                _uuidPrefix = adapterView.getItemAtPosition(i).toString();
                Log.d(LOG_TAG,"selected item: "+_uuidPrefix);
                fillTimestampSelector();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return haveUsers;
    }

    private boolean fillTimestampSelector() {

        boolean haveTimestamps = true;
        List<String> timestamps = getTimestampsForUser(_uuidPrefix);
        // check if the user has any timestamps at all:
        if ((timestamps == null) || (timestamps.isEmpty())) {
            timestamps = new ArrayList<>();
            timestamps.add(NO_TIMESTAMP);
            haveTimestamps = false;
        }

        Spinner timestampSpinner = (Spinner)findViewById(R.id.selecting_timestamp);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, timestamps);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timestampSpinner.setAdapter(dataAdapter);

        timestampSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                _timestamp = adapterView.getItemAtPosition(i).toString();
                Log.d(LOG_TAG,"selected item: "+_timestamp);
                presentSepcificTimestampContent();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return haveTimestamps;
    }


    private void presentContent() {

        setContentView(R.layout.activity_scrolling);
     /*   Button graphButton = (Button) findViewById(R.id.graphButton);
        Button noteButton = (Button) findViewById(R.id.noteButton);
        Button goalButton = (Button) findViewById(R.id.goalButton);
        graphButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScrollingActivity.this, ProgressActivity.class);
                startActivity(intent);
            }
        });
        noteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScrollingActivity.this, NoteActivity.class);
                startActivity(intent);
            }
        });
        goalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScrollingActivity.this, GoalsActivity.class);
                startActivity(intent);
            }
        });
*/
        fillUserSelector();
        fillTimestampSelector();
        presentSepcificTimestampContent();

    }

    private void presentSepcificTimestampContent() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getTitle());

        String textToPresent;
        if (_uuidPrefix == null || _uuidPrefix == NO_USER) {
            textToPresent = "There is no ExtraSensory user on this phone.";
        }
        else if (_timestamp == null || _timestamp == NO_TIMESTAMP) {
            textToPresent = "User with UUID prefix " + _uuidPrefix + " has no saved recognition files.";
        }
        else {
            String fileContent = readESALabelsFileForMinute(_uuidPrefix, _timestamp, true);
            List<Pair<String, Double>> labelsAndProbs = parseServerPredictionLabelProbabilities(fileContent);

            double[] latLong = parseLocationLatitudeLongitude(fileContent);

            //String latlongstr = "(" + latLong.length + "): <" + latLong[0] + ", " + latLong[1] + ">";
            String pairsStr = labelsAndProbs.size() + " labels:\n";
            for (Pair pair : labelsAndProbs) {
                pairsStr += pair.first + ": " + pair.second + "\n";
            }

            textToPresent =
                    "Timestamp: " + _timestamp + "\n\n" +
                            "Location lat long: " + /*latlongstr +*/ "\n" + "----------------\n" +
                            "Server predictions:\n" + pairsStr + "\n" + "-------------\n";

        }

        TextView scrolledText = (TextView)findViewById(R.id.the_scrolled_text);
        scrolledText.setText(textToPresent);

/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/

    }


    private static final String SERVER_PREDICTIONS_FILE_SUFFIX = ".server_predictions.json";
    private static final String USER_REPORTED_LABELS_FILE_SUFFIX = ".user_reported_labels.json";
    private static final String UUID_DIR_PREFIX = "extrasensory.labels.";

    /**
     * Return the super-directory, where a users' ExtraSensory-App label files should be
     * @return The users' files' directory
     * @throws PackageManager.NameNotFoundException
     */
    private File getUsersFilesDirectory() throws PackageManager.NameNotFoundException {
        // Locate the ESA saved files directory, and the specific minute-example's file:
        Context extraSensoryAppContext = getApplicationContext().createPackageContext("edu.ucsd.calab.extrasensory",0);
        File esaFilesDir = extraSensoryAppContext.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        if (!esaFilesDir.exists()) {
            return null;
        }
        return esaFilesDir;
    }

    /**
     * Return the directory, where a user's ExtraSensory-App label files should be
     * @param uuidPrefix The prefix (8 characters) of the user's UUID
     * @return The user's files' directory
     * @throws PackageManager.NameNotFoundException
     */
    private File getUserFilesDirectory(String uuidPrefix) throws PackageManager.NameNotFoundException {
        // Locate the ESA saved files directory, and the specific minute-example's file:
        Context extraSensoryAppContext = getApplicationContext().createPackageContext("edu.ucsd.calab.extrasensory",0);
        File esaFilesDir = new File(extraSensoryAppContext.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS),UUID_DIR_PREFIX + uuidPrefix);
        if (!esaFilesDir.exists()) {
            return null;
        }
        return esaFilesDir;
    }
    public String getUser() {
        List<String> users = getUsers();
        if(users != null) {
            return users.get(0);
        }
        else {
            return null;
        }
    }
    /**
     * Get the list of users (UUID prefixes).
     * @return List of UUID prefixes (strings).
     * In case the user's directory was not found, null will be returned.
     */
    private List<String> getUsers() {
        try {
            File esaFilesDir = getUsersFilesDirectory();
            if (esaFilesDir == null) {
                return null;
            }
            String[] filenames = esaFilesDir.list(new FilenameFilter() {
                @Override
                public boolean accept(File file, String s) {
                    return s.startsWith(UUID_DIR_PREFIX);
                }
            });

            SortedSet<String> usersSet = new TreeSet<>();
            for (String filename : filenames) {
                String uuidPrefix = filename.replace(UUID_DIR_PREFIX,"");
                usersSet.add(uuidPrefix);
            }

            List<String> uuidPrefixes = new ArrayList<>(usersSet);
            return uuidPrefixes;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get the list of timestamps, for which this user has saved files from ExtraSensory App.
     * @param uuidPrefix The prefix (8 characters) of the user's UUID
     * @return List of timestamps (strings), each representing a minute that has a file for this user.
     * The list will be sorted from earliest to latest.
     * In case the user's directory was not found, null will be returned.
     */
    private List<String> getTimestampsForUser(String uuidPrefix) {
        try {
            File esaFilesDir = getUserFilesDirectory(uuidPrefix);
            if (esaFilesDir == null) {
                return null;
            }
            String[] filenames = esaFilesDir.list(new FilenameFilter() {
                @Override
                public boolean accept(File file, String s) {
                    return s.endsWith(SERVER_PREDICTIONS_FILE_SUFFIX) || s.endsWith(USER_REPORTED_LABELS_FILE_SUFFIX);
                }
            });

            SortedSet<String> userTimestampsSet = new TreeSet<>();
            for (String filename : filenames) {
                String timestamp = filename.substring(0,10); // The timestamps always occupy 10 characters
                userTimestampsSet.add(timestamp);
            }

            List<String> userTimestamps = new ArrayList<>(userTimestampsSet);
            return userTimestamps;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Read text from the label file saved by ExtraSensory App, for a particualr minute-example.
     * @param uuidPrefix The prefix (8 characters) of the user's UUID
     * @param timestamp The timestamp of the desired minute example
     * @param serverOrUser Read the server-predictions if true, and the user-reported labels if false
     * @return The text inside the file, or null if had trouble finding or reading the file
     */
    private String readESALabelsFileForMinute(String uuidPrefix,String timestamp,boolean serverOrUser) {
        try {
            File esaFilesDir = getUserFilesDirectory(uuidPrefix);
            if (esaFilesDir == null) {
                // Cannot find the directory where the label files should be
                return null;
            }
            String fileSuffix = serverOrUser ? SERVER_PREDICTIONS_FILE_SUFFIX : USER_REPORTED_LABELS_FILE_SUFFIX;
            File minuteLabelsFile = new File(esaFilesDir,timestamp + fileSuffix);

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

    private static final String JSON_FIELD_LABEL_NAMES = "label_names";
    private static final String JSON_FIELD_LABEL_PROBABILITIES = "label_probs";
    private static final String JSON_FIELD_LOCATION_COORDINATES = "location_lat_long";

    /**
     * Prse the content of a minute's server-prediction file to extract the labels and probabilities assigned to the labels.
     * @param predictionFileContent The content of a specific minute server-prediction file
     * @return List of label name and probability pairs, or null if had trouble.
     */
    private List<Pair<String,Double>> parseServerPredictionLabelProbabilities(String predictionFileContent) {
        try {
            JSONObject jsonObject = new JSONObject(predictionFileContent);
            JSONArray labelArray = jsonObject.getJSONArray(JSON_FIELD_LABEL_NAMES);
            JSONArray probArray = jsonObject.getJSONArray(JSON_FIELD_LABEL_PROBABILITIES);
            // Make sure both arrays have the same size:
            if (labelArray == null || probArray == null || labelArray.length() != probArray.length()) {
                return null;
            }
            List<Pair<String,Double>> labelsAndProbabilities = new ArrayList<>(labelArray.length());
            for (int i = 0; i < labelArray.length(); i ++) {
                String label = labelArray.getString(i);
                Double prob = probArray.getDouble(i);
                labelsAndProbabilities.add(new Pair<String, Double>(label,prob));
            }
            return labelsAndProbabilities;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get top N probable labels from every time stamp.
     * Delete the already read files.
     */
    private HashMap<String, String> findTopFromEachFile(){
        List<String> timeStamps = getTimestampsForUser(_uuidPrefix);
        HashMap<String, String> timeStampTopLabels = new HashMap<>();

        for(String ts : timeStamps){
            String fileContent = readESALabelsFileForMinute(_uuidPrefix, ts, true);
            List<Pair<String, Double>> labelsAndProbs = parseServerPredictionLabelProbabilities(fileContent);
            //timeStampTopLabels.put(ts,topNLabels(labelsAndProbs,TOP_N_PROBABLE_LABELS));
            timeStampTopLabels.put( ts, topNLabels(labelsAndProbs,TOP_N_PROBABLE_LABELS).get(0).first);
        }

        return timeStampTopLabels;
    }


    /**
     * Takes in list of pairs of <label, probabilities> and returns top N(usually 5) probabilities
     * @param labelsAndProbabilities list given to choose top n labels
     * @param n top N labels and probabilities we want to return to update the server
     * @return list of top N labels and probabilities
     */
    private List<Pair<String,Double>> topNLabels(List<Pair<String,Double>> labelsAndProbabilities, int n){
        List<Pair<String,Double>> topNlabelsAndProbabilities = new ArrayList<Pair<String,Double>>();
        Collections.sort(topNlabelsAndProbabilities, new Comparator<Pair<String, Double>>() {
            @Override
            public int compare(Pair<String, Double> p1, Pair<String, Double> p2) {
                return p1.second < p2.second? -1 : p1.second > p2.second? 1 : 0;
            }
        });
        for(int i = 0; i < n; i++){
            topNlabelsAndProbabilities.add(labelsAndProbabilities.get(i));
        }
        return topNlabelsAndProbabilities;
    }

    /**
     * Parse the content of a minute's server-prediction file to extract the representative location coordinates for that minute.
     * @param predictionFileContent The content of a specific minute server-prediction file
     * @return An array of 2 numbers (or null if had trouble parsing the file or if there were no coordinates available).
     * The numbers are decimal degrees values for latitude and longitude geographic coordinates.
     */
    private double[] parseLocationLatitudeLongitude(String predictionFileContent) {
        try {
            JSONObject jsonObject = new JSONObject(predictionFileContent);
            JSONArray locationCoordinates = jsonObject.getJSONArray(JSON_FIELD_LOCATION_COORDINATES);
            // Expect this array to have exactly 2 values:
            if (locationCoordinates == null || locationCoordinates.length() != 2) {
                return null;
            }

            double[] latitudeLongitude = new double[2];
            latitudeLongitude[0] = locationCoordinates.getDouble(0);
            latitudeLongitude[1] = locationCoordinates.getDouble(1);
            return latitudeLongitude;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }
        @Override
     protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
               presentContent();
            //pushToServer();
           }
         @Override
    public void onResume() {
             super.onResume();
              Log.d(LOG_TAG,"registring for broadcast: " + ESA_BROADCAST_SAVED_PRED_FILE);
               //this.registerReceiver(_broadcastReceiver,new IntentFilter(ESA_BROADCAST_SAVED_PRED_FILE));
           }

    @Override
    public void onPause() {
        //this.unregisterReceiver(_broadcastReceiver);
        Log.d(LOG_TAG, "Unregistered broadcast: " + ESA_BROADCAST_SAVED_PRED_FILE);
        super.onPause();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
