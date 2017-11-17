package edu.ucsd.calab.usingextrasensorylabels;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;

/**
 * Created by youzhenghong on 17/11/2017.
 */

public class serverConnection extends BroadcastReceiver {

    public static final String ESA_BROADCAST_SAVED_PRED_FILE = "edu.ucsd.calab.extrasensory.broadcast.saved_prediction_file";
    public static final String ESA_BROADCAST_EXTRA_KEY_TIMESTAMP = "timestamp";
    @Override
    public void onReceive(Context context, Intent intent) {

        if (ESA_BROADCAST_SAVED_PRED_FILE.equals(intent.getAction())) {
            Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            // Vibrate for 400 milliseconds
            v.vibrate(400);
        }
        pushToServer();
    }

    private void pushToServer() {

    }


}
