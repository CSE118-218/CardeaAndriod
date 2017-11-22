package edu.ucsd.calab.usingextrasensorylabels;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.util.Log;

import static java.lang.Math.log;

/**
 * Created by youzhenghong on 17/11/2017.
 */

public class serverConnection extends BroadcastReceiver {

    public static final String ESA_BROADCAST_SAVED_PRED_FILE = "edu.ucsd.calab.extrasensory.broadcast.saved_prediction_file";
    public static final String ESA_BROADCAST_EXTRA_KEY_TIMESTAMP = "timestamp";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("vibarate", "received vibarate");
        if (ESA_BROADCAST_SAVED_PRED_FILE.equals(intent.getAction())) {
            Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            // Vibrate for 400 milliseconds
            Log.i("vibarate", "received vibarate");
            v.vibrate(0);
        }
        pushToServer();
    }

    private void pushToServer() {

    }


}
