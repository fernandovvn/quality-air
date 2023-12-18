package br.edu.ufam.icomp.devtitans.qualityair.broadcast;

import android.content.Context;
import android.content.Intent;

public interface BroadcastListener {
    public void broadcastListen(Context context, Intent intent);
}
