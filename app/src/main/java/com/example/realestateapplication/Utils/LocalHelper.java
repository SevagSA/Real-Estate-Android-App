//package com.example.realestateapplication.Utils;
//
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.content.res.Configuration;
//
//import com.example.realestateapplication.R;
//
//import java.util.Locale;
//
//public class LocalHelper {
//
//    private final Context context;
//
//    public LocalHelper(Context context) {
//        this.context = context;
//    }
//
//    public void changeLocale(String lang) {
//        Locale locale = new Locale(lang);
//        Locale.setDefault(locale);
//        Configuration config = new Configuration();
//        config.locale = locale;
//        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
//        SharedPreferences sp = context.getSharedPreferences("User", Context.MODE_PRIVATE);
//        SharedPreferences.Editor Ed = sp.edit();
//        Ed.putString(context.getString(R.string.selected_language), lang);
//        Ed.apply();
//    }
//
//}
