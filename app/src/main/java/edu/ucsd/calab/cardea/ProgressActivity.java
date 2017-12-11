package edu.ucsd.calab.cardea;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class ProgressActivity extends AppCompatActivity {

    public Double progress1;
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

    public void updateUI(String g, String p) throws ParseException, JSONException{
        Log.i("goal json", g);
        Log.i("progress json", p);

        // get current and goal json from sever
        // new gson object
        //JSONParser parser = new JSONParser();
        JSONObject goal = new JSONObject(g);
        JSONObject progress = new JSONObject(p);
        //      phase current json to java objects

        progress1 = progress.getDouble("walking");
        progress2 = progress.getDouble("running");
        progress3 = progress.getDouble("sitting");
        progress4 = progress.getDouble("standing");
        progress5 = progress.getDouble("lyingDown");

        goal1 = goal.getDouble("walking");
        goal2 = goal.getDouble("running");
        goal3 = goal.getDouble("sitting");
        goal4 = goal.getDouble("standing");
        goal5 = goal.getDouble("lyingDown");


        progressBar1.setProgress((int)((progress1/goal1)*100));
        progressBar2.setProgress((int)((progress2/goal2)*100));
        progressBar3.setProgress((int)((progress3/goal3)*100));
        progressBar4.setProgress((int)((progress4/goal4)*100));
        progressBar5.setProgress((int)((progress5/goal5)*100));

    }



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

        update();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
            }
        });

        // button for radar_chart

        final Button radarButton = (Button) findViewById(R.id.radarchartButton);
        radarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProgressActivity.this, RadarChartActivity.class);
                startActivity(i);

            }
        });
////        Chart for weekly progress comparision
//        RadarChart chart = (RadarChart) findViewById(R.id.chart);
//
//        ArrayList<Entry> entries = new ArrayList<>();
//        entries.add(new Entry(0f, 0));
//        entries.add(new Entry(0f, 1));
//        entries.add(new Entry(0f, 2));
//        entries.add(new Entry(0f, 3));
//        entries.add(new Entry(0f, 4));
////        entries.add(new Entry(5f, 5));
//
//        ArrayList<Entry> entries2 = new ArrayList<>();
//        entries2.add(new Entry(0f, 0));
//        entries2.add(new Entry(0f, 1));
//        entries2.add(new Entry(0f, 2));
//        entries2.add(new Entry(0f, 3));
//        entries2.add(new Entry(0f, 4));
////        entries2.add(new Entry(8f, 5));
//
//        RadarDataSet dataset_comp1 = new RadarDataSet(entries, "PastWeek");
//
//        RadarDataSet dataset_comp2 = new RadarDataSet(entries2, "ThisWeek");
//
//        dataset_comp1.setColor(Color.CYAN);
//        dataset_comp1.setDrawFilled(true);
//
//        dataset_comp2.setColor(Color.RED);
//        dataset_comp2.setDrawFilled(true);
//
//
//        ArrayList<RadarDataSet> dataSets = new ArrayList<RadarDataSet>();
//        dataSets.add(dataset_comp1);
//        dataSets.add(dataset_comp2);
//
//        ArrayList<String> labels = new ArrayList<String>();
//        labels.add("Walking");
//        labels.add("Running");
//        labels.add("Sitting");
//        labels.add("Standing");
//        labels.add("Lying Down");
////        labels.add("Punctuality");
//
//
//
//
//        RadarData data = new RadarData(labels, dataSets);
//        chart.setData(data);
//        String description = "Weekly Activity Analysis (scale of 1-10), 10 being the highest";
//        chart.setDescription(description);
//        chart.setWebLineWidthInner(0.5f);
//        chart.setDescriptionColor(Color.RED);
//
//        //chart.setSkipWebLineCount(10);
////        dataSet.addEntry(...);
////        exampleData.notifyDataChanged(); // let the data know a dataSet changed
////        chart.notifyDataSetChanged(); // let the chart know it's data changed
//        chart.invalidate();
//        chart.animate();
////        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
////        setSupportActionBar(toolbar);
    }


    public void update() {
        //ScrollingActivity sc = new ScrollingActivity();
        //Log.i("userID:   ", MainActivity.user);
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://ec2-54-202-77-233.us-west-2.compute.amazonaws.com:8000/activity/progress?user=admin";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Do something with response
                        //mTextView.setText(response.toString());

                        // Process the JSON
                        try{
                            // Loop through the array elements
                            JSONObject goal = response.getJSONObject(0);
                            JSONObject progress = response.getJSONObject(1);
                            updateUI(goal.toString(), progress.toString());

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        // Do something when error occurred
                        Log.i("error msg", "error getting jsonArray");
                    }
                }
        );

        queue.add(jsonArrayRequest);
    }




}