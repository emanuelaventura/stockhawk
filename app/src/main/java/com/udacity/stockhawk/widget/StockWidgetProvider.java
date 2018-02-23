package com.udacity.stockhawk.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.udacity.stockhawk.R;
import com.udacity.stockhawk.ui.MainActivity;
import com.udacity.stockhawk.ui.StockDetailActivity;

public class StockWidgetProvider extends AppWidgetProvider{
    public static final String EXTRA_SYMBOL ="extra:symbol";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            Intent intent =new Intent(context, MainActivity.class);
            PendingIntent pIntent =PendingIntent.getActivity(context, 0, intent, 0);

            RemoteViews remoteviews =new RemoteViews(context.getPackageName(), R.layout.widget_initial);
            remoteviews.setOnClickPendingIntent(R.id.container,pIntent);

            Intent widgetIntent =new Intent(context, StockWidgetService.class);
            widgetIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,appWidgetId);
            widgetIntent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

            remoteviews.setRemoteAdapter(R.id.list,widgetIntent);

            Intent openDetailIntent =new Intent(context, StockDetailActivity.class);
            PendingIntent clickPendingIntent = PendingIntent.getActivity(context, 0, openDetailIntent, 0);
            remoteviews.setPendingIntentTemplate(R.id.list, clickPendingIntent);

            appWidgetManager.updateAppWidget(appWidgetId,remoteviews);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }
}
