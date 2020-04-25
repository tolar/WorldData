package cz.vaclavtolar.corona_stats.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import cz.vaclavtolar.corona_stats.R;
import cz.vaclavtolar.corona_stats.dto.Settings;
import cz.vaclavtolar.corona_stats.service.PreferencesUtil;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Settings settings = PreferencesUtil.getSettingsFromPreferences(getApplicationContext());
        if (settings == null) {
            settings = new Settings();
        }
        PreferencesUtil.storeSettingsToPreferences(getApplicationContext(), settings);

        ((RadioButton)findViewById(R.id.column1_confirmed)).setChecked(settings.getColumn1() == Settings.Column.CONFIRMED);
        ((RadioButton)findViewById(R.id.column1_active)).setChecked(settings.getColumn1() == Settings.Column.ACTIVE);
        ((RadioButton)findViewById(R.id.column1_recovered)).setChecked(settings.getColumn1() == Settings.Column.RECOVERED);
        ((RadioButton)findViewById(R.id.column1_deaths)).setChecked(settings.getColumn1() == Settings.Column.DEATHS);

        ((RadioButton)findViewById(R.id.column2_confirmed)).setChecked(settings.getColumn2() == Settings.Column.CONFIRMED);
        ((RadioButton)findViewById(R.id.column2_active)).setChecked(settings.getColumn2() == Settings.Column.ACTIVE);
        ((RadioButton)findViewById(R.id.column2_recovered)).setChecked(settings.getColumn2() == Settings.Column.RECOVERED);
        ((RadioButton)findViewById(R.id.column2_deaths)).setChecked(settings.getColumn2() == Settings.Column.DEATHS);

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

    public void onCol1RadioClicked(View view) {
        Settings settings = PreferencesUtil.getSettingsFromPreferences(getApplicationContext());
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.column1_confirmed:
                if (checked) {
                    settings.setColumn1(Settings.Column.CONFIRMED);
                }
                break;
            case R.id.column1_active:
                if (checked) {
                    settings.setColumn1(Settings.Column.ACTIVE);
                }
                break;
            case R.id.column1_recovered:
                if (checked) {
                    settings.setColumn1(Settings.Column.RECOVERED);
                }
                break;
            case R.id.column1_deaths:
                if (checked) {
                    settings.setColumn1(Settings.Column.DEATHS);
                }
                break;
        }
        PreferencesUtil.storeSettingsToPreferences(getApplicationContext(), settings);
    }

    public void onCol2RadioClicked(View view) {
        Settings settings = PreferencesUtil.getSettingsFromPreferences(getApplicationContext());
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.column2_confirmed:
                if (checked) {
                    settings.setColumn2(Settings.Column.CONFIRMED);
                }
                break;
            case R.id.column2_active:
                if (checked) {
                    settings.setColumn2(Settings.Column.ACTIVE);
                }
                break;
            case R.id.column2_recovered:
                if (checked) {
                    settings.setColumn2(Settings.Column.RECOVERED);
                }
                break;
            case R.id.column2_deaths:
                if (checked) {
                    settings.setColumn2(Settings.Column.DEATHS);
                }
                break;
        }
        PreferencesUtil.storeSettingsToPreferences(getApplicationContext(), settings);

    }
}
