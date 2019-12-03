package com.example.filmin.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.filmin.R;

import java.util.Objects;

/**
 * Implementation of App Widget functionality.
 */
public class FavWidget extends AppWidgetProvider {
    public static final String TOAST_ACTION = "com.example.filmin.TOAST_ACTION";
    public static final String EXTRA_ITEM = "com.example.filmin.EXTRA_ITEM";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        Intent intent = new Intent(context, StackWidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.fav_widget);
        remoteViews.setRemoteAdapter(R.id.stack, intent);
        remoteViews.setEmptyView(R.id.stack, R.id.empty_view);

        Intent toastintent = new Intent(context, FavWidget.class);
        toastintent.setAction(FavWidget.TOAST_ACTION);
        toastintent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        PendingIntent toastpending = PendingIntent.getBroadcast(context, 0, toastintent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setPendingIntentTemplate(R.id.stack, toastpending);
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (Objects.equals(intent.getAction(), TOAST_ACTION)) {
            int index = intent.getIntExtra(EXTRA_ITEM, 0);
            Toast.makeText(context, context.getString(R.string.touched) + index, Toast.LENGTH_SHORT).show();
        }
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName thisWidget = new ComponentName(context, FavWidget.class);
        int[] appWidgetId = appWidgetManager.getAppWidgetIds(thisWidget);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.stack);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

