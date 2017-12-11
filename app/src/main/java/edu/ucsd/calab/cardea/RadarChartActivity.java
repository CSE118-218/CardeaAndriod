package edu.ucsd.calab.cardea;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;

import java.util.ArrayList;

public class RadarChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radar_page);

        RadarChart chart = (RadarChart) findViewById(R.id.chart);

        // Last week data
        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(1f, 0));
        entries.add(new Entry(1f, 1));
        entries.add(new Entry(1f, 2));
        entries.add(new Entry(1f, 3));
        entries.add(new Entry(1f, 4));

        // This week data
        ArrayList<Entry> entries2 = new ArrayList<>();
        entries2.add(new Entry(1f, 0));
        entries2.add(new Entry(1f, 1));
        entries2.add(new Entry(1f, 2));
        entries2.add(new Entry(1f, 3));
        entries2.add(new Entry(1f, 4));


        RadarDataSet dataset_comp1 = new RadarDataSet(entries, "Last Week");

        RadarDataSet dataset_comp2 = new RadarDataSet(entries2, "This Week");

        dataset_comp1.setColor(Color.CYAN);
        dataset_comp1.setDrawFilled(true);

        dataset_comp2.setColor(Color.RED);
        dataset_comp2.setDrawFilled(true);


        ArrayList<RadarDataSet> dataSets = new ArrayList<RadarDataSet>();
        dataSets.add(dataset_comp1);
        dataSets.add(dataset_comp2);

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("Walking");
        labels.add("Running");
        labels.add("Sitting");
        labels.add("Standing");
        labels.add("Lying");





        RadarData data = new RadarData(labels, dataSets);
        chart.setData(data);
        String description = "Scale in the unit of minutes occurred for each activity";
        chart.setDescription(description);
        chart.setWebLineWidthInner(0.5f);
        chart.setDescriptionColor(Color.RED);


        //chart.setSkipWebLineCount(10);
        chart.invalidate();
        chart.animate();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }

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
