package com.example.filmin.Notification;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;
import androidx.lifecycle.MutableLiveData;

import com.example.filmin.Data.FILM.Film;
import com.example.filmin.Detail.DetailMovie;
import com.example.filmin.MainActivity;
import com.example.filmin.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

public class ReleaseReminder extends BroadcastReceiver {
    private final int ID_RELEASE = 101;
    private final MutableLiveData<ArrayList<Film>> movielist = new MutableLiveData<>();
    private final ArrayList<Film> list = new ArrayList<>();
    private final Date date = Calendar.getInstance().getTime();
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private final String today = simpleDateFormat.format(date);

    public ReleaseReminder() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        releaseReminder(context);

    }

    private void releaseReminder(Context context) {

        String url = "https://api.themoviedb.org/3/discover/movie?api_key=54b7a429ded4390f260d02b214ba785f&primary_release_date.gte=" + today + "&primary_release_date.lte=" + today;

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray array = responseObject.getJSONArray("results");

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject o = array.getJSONObject(i);
                        Film films = new Film(
                                o.getString("overview"),
                                o.getString("title"),
                                o.getString("poster_path"),
                                o.getString("release_date"),
                                o.getString("backdrop_path"),
                                o.getString("original_language"),
                                o.getString("popularity"),
                                o.getString("vote_count"),
                                o.getInt("id"),
                                o.getString("vote_average")
                        );
                        list.add(films);
                    }
                    movielist.postValue(list);

                } catch (JSONException e) {
                    Log.d("Exception", e.getMessage());
                }
                showReminderNotification(context);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());

            }
        });
    }

    private void showReminderNotification(Context context) {
        int REQUEST_CODEMOVIE = 111;
        String CHANNEL_ID = "Channel2";
        String CHANNEL_NAME = "Release Movie";
        String title;
        String message;
        Intent intent;
        PendingIntent pendingIntent;

        NotificationManager notificationManager
                = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);

            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});

            builder.setChannelId(CHANNEL_ID);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Bitmap bmp = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.newwws);

        int numMovie = list.size();
        if (numMovie == 0) {
            intent = new Intent(context, MainActivity.class);
            pendingIntent = TaskStackBuilder.create(context)
                    .addNextIntent(intent)
                    .getPendingIntent(REQUEST_CODEMOVIE, PendingIntent.FLAG_UPDATE_CURRENT);
            title = null;
            message = context.getString(R.string.no_movie);
            builder.setSmallIcon(R.drawable.newwws)
                    .setLargeIcon(bmp)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                    .setSound(alarmSound)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);

            if (notificationManager != null) {
                notificationManager.notify(0, builder.build());
            }
        } else {
            intent = new Intent(context, DetailMovie.class);
            for (int i = 0; i < list.size(); i++) {
                intent.putExtra(DetailMovie.MOVIE_KEY, list.get(i));
                pendingIntent = TaskStackBuilder.create(context)
                        .addNextIntent(intent)
                        .getPendingIntent(i, PendingIntent.FLAG_UPDATE_CURRENT);
                title = list.get(i).getTitle() + "" + context.getString(R.string.has_been_released);
                message = list.get(i).getDesc() + " ";
                builder.setSmallIcon(R.drawable.newwws)
                        .setLargeIcon(bmp)
                        .setContentTitle(title)
                        .setContentText(message)
                        .setContentIntent(pendingIntent)
                        .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                        .setSound(alarmSound)
                        .setAutoCancel(true);

                if (notificationManager != null) {
                    notificationManager.notify(i, builder.build());
                }
            }
        }
    }


    public void setReminderMovie(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReleaseReminder.class);

        String time = "08:00";
        String[] timeArray = time.split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND, 0);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_RELEASE, intent, 0);

        if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, pendingIntent);
        }

        Toast.makeText(context, context.getString(R.string.repeating_movie), Toast.LENGTH_SHORT).show();
    }

    public void cancelReminderMovie(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReleaseReminder.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_RELEASE, intent, 0);
        pendingIntent.cancel();
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }

        Toast.makeText(context, context.getString(R.string.cancel_repeating_movie), Toast.LENGTH_SHORT).show();
    }

}
