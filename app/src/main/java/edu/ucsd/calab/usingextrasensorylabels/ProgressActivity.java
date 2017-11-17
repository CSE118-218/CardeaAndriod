package edu.ucsd.calab.usingextrasensorylabels;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

/**
 * Created by UsernameChecksOut on 11/5/17.
 */

public class ProgressActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private Button button;
    private int prog = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress_page);

        button = (Button) findViewById(R.id.progressButton);
        progressBar = (ProgressBar) findViewById(R.id.progressBar2);
        progressBar.setMax(100);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setProgress(prog);
                prog = prog+5;
            }
        });
    }

}