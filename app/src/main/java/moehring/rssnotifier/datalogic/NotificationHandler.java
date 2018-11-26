package moehring.rssnotifier.datalogic;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import java.util.Random;

import moehring.rssnotifier.R;
import moehring.rssnotifier.activities.WebActivity;

public class NotificationHandler {

    private static Random random = new Random();

    public static void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = context.getString(R.string.channel_name);
            String description = context.getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(context.getString(R.string.channel_name), name, importance);
            channel.setDescription(description);

            android.app.NotificationManager notificationManager = context.getSystemService(android.app.NotificationManager.class);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);
        }
    }

    public static void showNotification(Context context, String text, String link) {
        Intent intent = new Intent(context, WebActivity.class);
        Bundle b = new Bundle();
        b.putString("link", link);
        intent.putExtras(b);

        System.out.println(intent.getExtras().getString("link"));

        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, context.getString(R.string.channel_name))
                .setSmallIcon(R.drawable.ic_notification)
                .setColor(context.getResources().getColor(R.color.colorPrimary, context.getTheme()))
                .setContentTitle(context.getResources().getString(R.string.new_feed_item))
                .setContentText(text)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(text))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(contentIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        notificationManager.notify(random.nextInt(), mBuilder.build());
    }
}
