package cz.vaclavtolar.corona_stats.service;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import cz.vaclavtolar.corona_stats.R;
import cz.vaclavtolar.corona_stats.dto.Settings;

import static android.content.Context.MODE_PRIVATE;

public class PreferencesUtil {

    private static final String SETTINGS_KEY = "settings";

    private PreferencesUtil() {
    }

    public static Settings getSettingsFromPreferences(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(context.getString(R.string.preference_file_key), MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString(SETTINGS_KEY, null);
        if (json != null) {
            return gson.fromJson(json, Settings.class);
        }
        return null;
    }

    public static void storeSettingsToPreferences(Context context, Settings settings) {
        SharedPreferences prefs = context.getSharedPreferences(context.getString(R.string.preference_file_key), MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        Gson gson = new Gson();

        String settingsJson = gson.toJson(settings);
        prefsEditor.putString(SETTINGS_KEY, settingsJson);

        prefsEditor.commit();
    }
}
