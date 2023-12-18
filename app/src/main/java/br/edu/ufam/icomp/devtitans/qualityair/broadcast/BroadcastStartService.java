package br.edu.ufam.icomp.devtitans.qualityair.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import br.edu.ufam.icomp.devtitans.qualityair.service.ServiceMonitoring;

public class BroadcastStartService extends BroadcastReceiver {
    private static final String TAG = "QUALITY-AIR";
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Log.d(TAG, "BroadcastStartService");
            Intent serviceIntent = new Intent(context, ServiceMonitoring.class);
            context.startForegroundService(serviceIntent);
        }
    }
}
