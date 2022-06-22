package com.example.myapplication5;
import android.app.Application;
import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MessagingApp extends Application {
    private static Context context;
    private static Map<String, Uri> picturesMap;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        picturesMap = new HashMap<String, Uri>();
    }
    public static Context getAppContext() {
        return MessagingApp.context;
    }
    public static Map<String, Uri> getPictueresMap() {
        return picturesMap;
    }
}
