package cz.vaclavtolar.corona_world.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cz.vaclavtolar.corona_world.R;
import cz.vaclavtolar.corona_world.dto.Country;

import static cz.vaclavtolar.corona_world.activity.MainActivity.DATA_UPDATED_KEY;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        setupToolbar();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        android.text.format.DateFormat df = new android.text.format.DateFormat();
        ((TextView)findViewById(R.id.updated)).setText(df.format("HH:mm dd.MM.yyyy", getUpdatedFromPreferences()));
    }

    private Date getUpdatedFromPreferences() {
        SharedPreferences prefs = getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString(DATA_UPDATED_KEY, "");
        Date updated = gson.fromJson(json, Date.class);
        return updated;
    }
}
