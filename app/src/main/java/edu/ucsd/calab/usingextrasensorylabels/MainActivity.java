package edu.ucsd.calab.usingextrasensorylabels;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by zht on 11/17/17.
 */

public class MainActivity extends AppCompatActivity {






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        final Button walkButton = (Button) findViewById(R.id.walkingButton);
        final Button cognitiveButton = (Button) findViewById(R.id.cognitiveButton);
        final Button motorSkillsButton = (Button) findViewById(R.id.motorSkillsButton);
        final Button lifeStyleButton = (Button) findViewById(R.id.lifeStyleButton);

        cognitiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.lumosity.com"));
                startActivity(browserIntent);
            }
        });
        lifeStyleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.cozycal.com/"));
                startActivity(browserIntent);
            }
        });
        motorSkillsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.rstgames.balloons&hl=en"));
                startActivity(browserIntent);
            }
        });



        //https://www.cozycal.com/

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

        final Button button4 = (Button) findViewById(R.id.profileButton);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(i);

            }
        });

    }
}