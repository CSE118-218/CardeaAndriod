package edu.ucsd.calab.usingextrasensorylabels;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;

import com.android.volley.Response;
import com.google.gson.Gson;

import org.json.JSONObject;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class ProgressActivity extends AppCompatActivity {

    private Double progress1;
    private Double progress2;
    private Double progress3;
    private Double progress4;
    private Double progress5;

    private Double goal1;
    private Double goal2;
    private Double goal3;
    private Double goal4;
    private Double goal5;

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

    private WebView strockTips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress_page);


        // add webview to see the tips for strock paitent
        strockTips = (WebView) findViewById(R.id.strock_tips);
        WebSettings webSettings = strockTips.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // set webview url use cdc how to prevent stroke
        strockTips.loadUrl("https://www.cdc.gov/stroke/healthy_living.htm");

        // Force links and redirects to open in the WebView instead of in a browser
        strockTips.setWebViewClient(new WebViewClient());


        // button for progress demo /  update progress after sever connection
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


                // get current and goal json from sever





                // new gson object
                Gson gson = new Gson();

                //      phase current json to java objects

                GsonPhaser progressCurrent = gson.fromJson("", GsonPhaser.class);

                //      phase goal json to java objects
                GsonPhaser progressUpdate = gson.fromJson("", GsonPhaser.class);

                progress1 = progressCurrent.walking().intValue();
                progress2 = progressCurrent.running().intValue();
                progress3 = progressCurrent.sitting().intValue();
                progress4 = progressCurrent.standing().intValue();
                progress5 = progressCurrent.lying().intValue();

                goal1 = progressUpdate.walking().intValue();
                goal2 = progressUpdate.running().intValue();
                goal3 = progressUpdate.sitting().intValue();
                goal4 = progressUpdate.standing().intValue();
                goal5 = progressUpdate.lying().intValue();


                progressBar1.setProgress((progress1/goal1)*100);
                progressBar2.setProgress((progress2/goal2)*100);
                progressBar3.setProgress((progress3/goal3)*100);
                progressBar4.setProgress((progress4/goal4)*100);
                progressBar5.setProgress((progress5/goal5)*100);


//
//              progressBar1.setProgress(prog1);
//                prog1 = prog1+5;
//
//                progressBar2.setProgress(prog2);
//                prog2 = prog2+10;
//
//                progressBar3.setProgress(prog3);
//                prog3 = prog3+17;
//
//                progressBar4.setProgress(prog4);
//                prog4 = prog4+22;
//
//                progressBar5.setProgress(prog5);
//                prog5 = prog5+11;
            }
        });
    }

}