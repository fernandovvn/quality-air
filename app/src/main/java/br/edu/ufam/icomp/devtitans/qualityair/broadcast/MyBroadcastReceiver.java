package br.edu.ufam.icomp.devtitans.qualityair.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyBroadcastReceiver extends BroadcastReceiver {

    private final BroadcastListener listener;

    public MyBroadcastReceiver(BroadcastListener listener){
        this.listener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        listener.broadcastListen(context, intent);
    }

}
