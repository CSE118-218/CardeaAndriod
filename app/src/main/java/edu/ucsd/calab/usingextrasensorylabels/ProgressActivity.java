package edu.ucsd.calab.usingextrasensorylabels;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;



public class ProgressActivity extends AppCompatActivity {

    private ProgressBar progressBar1;
    private ProgressBar progressBar2;
    private ProgressBar progressBar3;
    private ProgressBar progressBar4;
    private ProgressBar progressBar5;

    private Button button;

    private int prog1 = 5;
    private int prog2 = 5;
    private int prog3 = 5;
    private int prog4 = 5;
    private int prog5 = 5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress_page);

        button = (Button) findViewById(R.id.progressButton);
        progressBar1 = (ProgressBar) findViewById(R.id.progressBar1);
        progressBar1.setMax(100);

        progressBar2 = (ProgressBar) findViewById(R.id.progressBar2);
        progressBar2.setMax(100);

        progressBar3 = (ProgressBar) findViewById(R.id.progressBar3);
        progressBar3.setMax(100);

        progressBar4 = (ProgressBar) findViewById(R.id.progressBar4);
        progressBar4.setMax(100);

        progressBar5 = (ProgressBar) findViewById(R.id.progressBar5);
        progressBar5.setMax(100);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressBar1.setProgress(prog1);
                prog1 = prog1+5;

                progressBar2.setProgress(prog2);
                prog2 = prog2+10;

                progressBar3.setProgress(prog3);
                prog3 = prog3+17;

                progressBar4.setProgress(prog4);
                prog4 = prog4+22;

                progressBar5.setProgress(prog5);
                prog5 = prog5+11;
            }
        });
    }

}