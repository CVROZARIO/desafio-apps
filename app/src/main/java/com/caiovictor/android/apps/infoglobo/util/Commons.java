package com.caiovictor.android.apps.infoglobo.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.caiovictor.android.apps.infoglobo.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import androidx.core.app.ShareCompat;

public class Commons {

    private static final String TAG = "Commons";

    public static Date getDateFromString(String date, String format){
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date getDateFromString(String date){
        return getDateFromString(date, Constants.DATE_DEFAULT_FORMAT);
    }

    public static String getHumanElapsedTimeFrom(Context context, Date date) {
        long diffInMillies = Math.abs(new Date().getTime() - date.getTime());
        long timeUnit;
        String v = "";

        if ((timeUnit = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS)) >= 365) {
            timeUnit = timeUnit / 365;
            v = String.format(context.getString(R.string.human_elapsed_time_base), (int)timeUnit, context.getString((int)timeUnit > 1 ? R.string.human_elapsed_time_years : R.string.human_elapsed_time_year));
        } else if ((timeUnit = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS)) >= 1) {
            v = String.format(context.getString(R.string.human_elapsed_time_base), timeUnit, context.getString(timeUnit > 1 ? R.string.human_elapsed_time_days : R.string.human_elapsed_time_day));
        } else if ((timeUnit = TimeUnit.HOURS.convert(diffInMillies, TimeUnit.MILLISECONDS)) > 0) {
            v = String.format(context.getString(R.string.human_elapsed_time_base), timeUnit, context.getString(R.string.human_elapsed_time_hour));
        } else if ((timeUnit = TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS)) > 0) {
            v = String.format(context.getString(R.string.human_elapsed_time_base), timeUnit, context.getString(R.string.human_elapsed_time_min));
        } else {
            v = context.getString(R.string.human_elapsed_time_now);
        }

        return v;
    }

    public static void doShareOut(Activity activity, String subject, String text){
        ShareCompat.IntentBuilder.from(activity)
                .setType("text/plain")
                .setChooserTitle(R.string.share_title)
                .setSubject(subject)
                .setText(text)
                .startChooser();
    }

    public static void doShareOut(Activity activity, int resSubject, int resText) {
        doShareOut(activity, activity.getString(resSubject), activity.getString(resText));
    }

    public static void doOpenUrl(Activity activity, String url){
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.parse(url));
            activity.startActivity(intent);
        }catch (Exception e){
            Log.e(TAG, e.getMessage());
            //  TODO IMPLEMENTAR TRATAMENTO DE ERRO E FIREBASE
        }
    }

    public static void doOpenUrl(Activity activity, int resUrl){
        doOpenUrl(activity, activity.getString(resUrl));
    }

    public static void doSimleStartActivity(Activity activity, Class class_) {
        activity.startActivity(new Intent(activity, class_));
    }

}
