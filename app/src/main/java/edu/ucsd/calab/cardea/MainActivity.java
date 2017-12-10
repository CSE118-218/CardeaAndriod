package edu.ucsd.calab.cardea;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

/**
 * Created by zht on 11/17/17.
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

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.main_layout);


//        set text for animated recommender text
//        test = (TextView)findViewById(R.id.recommender);
        // animate the stroke tips in the goal page
  // Set focus to the textview
        // Views

        mHandler = new Handler();
        startRepeatingTask();
        //        read message


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
            test.setText("Walk to 64 Degree to eat?");
        } else if((result <= 15) && (result > 1)) {
            test.setText("Want a cup coffee from Price Center? ");
        } else { test.setText("No goal currently active.");

        }
    }

    private void updateUI(@Nullable GoogleSignInAccount account) {

        Intent loginActivityIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(loginActivityIntent);
        mActivity.signOut();

    }

}
