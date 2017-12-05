package edu.ucsd.calab.usingextrasensorylabels;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Alex on 11/26/17.
 */

public class MapActivity extends AppCompatActivity {


    private WebView gMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);


        // add webview to see the map
        gMap = (WebView) findViewById(R.id.gMap);
        WebSettings webSettings = gMap.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // set webview to see the map


//        gMap.loadUrl("https://www.google.com/maps/dir/Computer+Science+and+Engineering+Building+(EBU3B),+La+Jolla,+CA+92093/64+Degrees,+Gilman+Drive,+La+Jolla,+CA/@32.8782507,-117.2420827,16z/data=!3m1!4b1!4m14!4m13!1m5!1m1!1s0x80dc06c3409a5d5f:0xa7dc3be7597d4d47!2m2!1d-117.2335235!2d32.8818006!1m5!1m1!1s0x80dc06c7b8154acf:0x51e8bb9f09bfd68!2m2!1d-117.2420341!2d32.8747459!3e2");


        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        String walkingtime = sharedPreferences.getString("Walking", "0");
        int result = Integer.parseInt(walkingtime);
        if (result >= 15) {
            gMap.loadUrl("https://www.google.com/maps/dir/Computer+Science+and+Engineering+Building+(EBU3B),+La+Jolla,+CA+92093/64+Degrees,+Gilman+Drive,+La+Jolla,+CA/@32.8782507,-117.2420827,16z/data=!3m1!4b1!4m14!4m13!1m5!1m1!1s0x80dc06c3409a5d5f:0xa7dc3be7597d4d47!2m2!1d-117.2335235!2d32.8818006!1m5!1m1!1s0x80dc06c7b8154acf:0x51e8bb9f09bfd68!2m2!1d-117.2420341!2d32.8747459!3e2");
        } else if((result <= 15) && (result > 1)) {
            gMap.loadUrl("https://www.google.com/maps/dir/Computer+Science+and+Engineering+Building+(EBU3B),+La+Jolla,+CA+92093/Price+Center,+San+Diego,+CA/@32.8794356,-117.2376616,17z/data=!3m1!4b1!4m14!4m13!1m5!1m1!1s0x80dc06c3409a5d5f:0xa7dc3be7597d4d47!2m2!1d-117.2335235!2d32.8818006!1m5!1m1!1s0x80dc06c46a524fb9:0x68571efb243bc289!2m2!1d-117.2361952!2d32.8797205!3e2");
        } else {
            gMap.loadUrl("https://www.google.com/maps/");


        }

//// read walkingProgress from sever
//     if ( walkingProgress <=10){
////        cse -> 64 degree
//        gMap.loadUrl("https://www.google.com/maps/dir/Computer+Science+and+Engineering+Building+(EBU3B),+La+Jolla,+CA+92093/64+Degrees,+Gilman+Drive,+La+Jolla,+CA/@32.8782507,-117.2420827,16z/data=!3m1!4b1!4m14!4m13!1m5!1m1!1s0x80dc06c3409a5d5f:0xa7dc3be7597d4d47!2m2!1d-117.2335235!2d32.8818006!1m5!1m1!1s0x80dc06c7b8154acf:0x51e8bb9f09bfd68!2m2!1d-117.2420341!2d32.8747459!3e2");
//    }
//        else
//
//    {
////        cse ->pc
//        gMap.loadUrl("https://www.google.com/maps/dir/Computer+Science+and+Engineering+Building+(EBU3B),+La+Jolla,+CA+92093/Price+Center,+San+Diego,+CA/@32.8794356,-117.2376616,17z/data=!3m1!4b1!4m14!4m13!1m5!1m1!1s0x80dc06c3409a5d5f:0xa7dc3be7597d4d47!2m2!1d-117.2335235!2d32.8818006!1m5!1m1!1s0x80dc06c46a524fb9:0x68571efb243bc289!2m2!1d-117.2361952!2d32.8797205!3e2");
//    }
//
//
//        // Force links and redirects to open in the WebView instead of in a browser
//        gMap.setWebViewClient(new WebViewClient());
//    }

}
}
