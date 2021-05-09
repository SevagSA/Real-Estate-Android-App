package com.example.localehelper;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import java.util.Locale;

/**
 * A class to help applications internationalize their UI easier.
 */
public class LocaleHelper {

    private final Context context;

    /**
     * Constructor with context class attribute.
     * @param context The context of the Activity that this class is being called by.
     *                Used for getResources() and getSharedPreferences().
     */
    public LocaleHelper(Context context) {
        this.context = context;
    }


    /**
     * To change the locale of an application.
     * @param lang The language code of the new locale (e.g. "hy", "en", "fr").
     * @param sharedPrefName The name of the shared preference file where the chosen
     *                       locale will be stored.
     * @param sharedPrefKey The key under which the chosen locale will be stored in the
     *                      {@code sharedPrefName} shared preference file.
     */
    public void changeLocale(String lang, String sharedPrefName, int sharedPrefKey) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
        SharedPreferences sp = context.getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE);
        SharedPreferences.Editor Ed = sp.edit();
        Ed.putString(context.getString(sharedPrefKey), lang);
        Ed.apply();
    }


}
