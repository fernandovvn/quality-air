package br.edu.ufam.icomp.devtitans.qualityair.notification;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import br.edu.ufam.icomp.devtitans.qualityair.R;
import br.edu.ufam.icomp.devtitans.qualityair.sensors.SensorType;

public class NotificationFactory {
    public final static String channelId = "br.edu.ufam.icomp.devtitans.qualityair";
    public final static String channelName = "QualityAir";
    public final static String channelDesc = "Channel for quality air service notifications";
    public final static int ID_ALERT=0;
    private int alertMask=0;

    public final static String GROUP_SERVICE_CARE = channelId + ".group.service_care";
    public final static int channelImportance = android.app.NotificationManager.IMPORTANCE_HIGH;

    private final Context context;
    private final NotificationManagerCompat notificationManager;

    public NotificationFactory(Context context) {
        this.context = context;
        createNotificationChannel();
        notificationManager = NotificationManagerCompat.from(context);
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is not in the Support Library.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, channelName, channelImportance);
            channel.setDescription(channelDesc);
            android.app.NotificationManager notificationManager = context.getSystemService(android.app.NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public Notification buildNotification(String contentTitle, String contentText, String bigText) {
        return new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.owl0)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(bigText))
                .build();
    }

    public void showNotification(int notificationId, String contentTitle, String contentText, String bigText) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            Notification notification = buildNotification(contentTitle, contentText, bigText);
            notificationManager.notify(notificationId, notification);
        }
    }

    public void showAlert(SensorType type){
        if(alertMask==0)
            showNotification(ID_ALERT, "Qualidade ruim de ar", "Verifique as medidas e se proteja",
                    "Algumas medições realizadas no entorno retornaram valores ruins. Verifique as medidas e proteja sua respiração.");
        alertMask |= (1<<type.getValue());
    }

    public void cancelAlert(SensorType type){
        alertMask &= ~(1<<type.getValue());
        if(alertMask==0)
            notificationManager.cancel(ID_ALERT);
    }

    public void hide(int notificationId){
        notificationManager.cancel(notificationId);
    }
}
