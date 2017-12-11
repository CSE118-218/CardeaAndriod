package edu.ucsd.calab.cardea;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.BinderThread;
import android.support.annotation.Nullable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

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
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FilenameFilter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by zht on 11/17/17.
 * Updated by Prakriti Gupta.
 */

public class MainActivity extends AppCompatActivity {


    private TextView test;
    private int mInterval = 1000; // 5 seconds by default, can be changed later
    private Handler mHandler;
    private static final int RC_SIGN_IN = 9001;
    private TextView mStatusTextView;
    private LoginActivity mActivity;
    private Button mLogoutButton;
    private GoogleSignInClient mGoogleSignInClient;

    public static String user = "no user";
    public static File userFileDir;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.main_layout);


        user = getUsers().get(0);
        Log.i("user id:", user);
        mHandler = new Handler();
        startRepeatingTask();
        //        read message

        // [START configure_signin]
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // [END configure_signin]

        // [START build_client]
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        // [END build_client]
        mStatusTextView = (TextView) findViewById(R.id.text_view_id);

        final Button cognitiveButton = (Button) findViewById(R.id.cognitiveButton);
        final Button motorSkillsButton = (Button) findViewById(R.id.motorSkillsButton);
        final Button lifeStyleButton = (Button) findViewById(R.id.lifeStyleButton);

        mLogoutButton = (Button) findViewById(R.id.logout_button);
        mLogoutButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                signOut();
            }

        });

        cognitiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent launchItent = getPackageManager().getLaunchIntentForPackage("com.lumoslabs.lumosity");
                if(launchItent != null){
                    startActivity(launchItent);
                } else {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.lumoslabs.lumosity&hl=en"));
                    startActivity(browserIntent);
                }
            }
        });
        lifeStyleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchItent = getPackageManager().getLaunchIntentForPackage("com.medisafe.android.client");
                if(launchItent != null){
                    startActivity(launchItent);
                } else {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.medisafe.android.client&hl=en"));
                    startActivity(browserIntent);
                }
            }
        });


        motorSkillsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchItent = getPackageManager().getLaunchIntentForPackage("com.rstgames.balloons");
                if(launchItent != null){
                    startActivity(launchItent);
                } else {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.rstgames.balloons&hl=en"));
                    startActivity(browserIntent);
                }
            }
        });

        final Button button1 = (Button) findViewById(R.id.goalButton);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, GoalsActivity.class);
                startActivity(i);

            }
        });

        final Button button2 = (Button) findViewById(R.id.graphButton);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ProgressActivity.class);
                startActivity(i);

            }
        });

        final Button button3 = (Button) findViewById(R.id.noteButton);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ScrollingActivity.class);
                startActivity(i);

            }
        });

//        final Button button4 = (Button) findViewById(R.id.profileButton);
//        button4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(MainActivity.this, ProfileActivity.class);
//                startActivity(i);
//
//            }
//        });

        final Button gmapButton = (Button) findViewById(R.id.walkingButton);
        gmapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, MapActivity.class);
                startActivity(i);

            }
        });


    }
    // [START signOut]
    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // [START_EXCLUDE]
                        updateUI(null);
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END signOut]
    // [START revokeAccess]
    private void revokeAccess() {
        mGoogleSignInClient.revokeAccess()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // [START_EXCLUDE]
                        Intent loginActivityIntent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(loginActivityIntent);
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END revokeAccess]

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopRepeatingTask();
    }

    Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {
            try {
                test = (TextView)findViewById(R.id.recommender);
                registermessage(); //this function can change value of mInterval.
            } finally {
                // 100% guarantee that this always happens, even if
                // your update method throws an exception
                mHandler.postDelayed(mStatusChecker, mInterval);
            }
        }
    };

    void startRepeatingTask() {
        mStatusChecker.run();
    }

    void stopRepeatingTask() {
        mHandler.removeCallbacks(mStatusChecker);
    }

    public void registermessage() {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        String walkingtime = sharedPreferences.getString("Walking", "0");
        int result = Integer.parseInt(walkingtime);
        if (result >= 15) {
            test.setText("Ready for a walk?");
        } else if((result <= 15) && (result > 1)) {
            test.setText("Coffee break?");
        } else { test.setText("No goal currently active.");

        }
    }

    private void updateUI(@Nullable GoogleSignInAccount account) {

        Intent loginActivityIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(loginActivityIntent);
        //mActivity.signOut();

    }

    @Override
    public void onStart() {
        super.onStart();

        // [START on_start_sign_in]
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        mStatusTextView.setText(getString(R.string.signed_in_fmt, account.getDisplayName()));
        // [END on_start_sign_in]
    }

    public List<String> getUsers() {
        try {
            File esaFilesDir = getUsersFilesDirectory();
            userFileDir = esaFilesDir;
            Log.i("user File dir",userFileDir.toString());
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
    public File getUsersFilesDirectory() throws PackageManager.NameNotFoundException {
        // Locate the ESA saved files directory, and the specific minute-example's file:
        Context extraSensoryAppContext = getApplicationContext().createPackageContext("edu.ucsd.calab.extrasensory",0);
        File esaFilesDir = extraSensoryAppContext.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        if (!esaFilesDir.exists()) {
            return null;
        }
        return esaFilesDir;
    }

    private static final String SERVER_PREDICTIONS_FILE_SUFFIX = ".server_predictions.json";
    private static final String USER_REPORTED_LABELS_FILE_SUFFIX = ".user_reported_labels.json";
    private static final String UUID_DIR_PREFIX = "extrasensory.labels.";




}
