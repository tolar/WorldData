package cz.vaclavtolar.world_data.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import cz.vaclavtolar.world_data.R;
import cz.vaclavtolar.world_data.dto.Settings;
import cz.vaclavtolar.world_data.service.PreferencesUtil;

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

        ((RadioButton)findViewById(R.id.column1_population)).setChecked(settings.getColumn1() == Settings.Column.POPULATION);
        ((RadioButton)findViewById(R.id.column1_area)).setChecked(settings.getColumn1() == Settings.Column.AREA);
        ((RadioButton)findViewById(R.id.column1_density)).setChecked(settings.getColumn1() == Settings.Column.DENSITY);

        ((RadioButton)findViewById(R.id.column2_population)).setChecked(settings.getColumn2() == Settings.Column.POPULATION);
        ((RadioButton)findViewById(R.id.column2_area)).setChecked(settings.getColumn2() == Settings.Column.AREA);
        ((RadioButton)findViewById(R.id.column2_density)).setChecked(settings.getColumn2() == Settings.Column.DENSITY);

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
            case R.id.column1_population:
                if (checked) {
                    settings.setColumn1(Settings.Column.POPULATION);
                }
                break;
            case R.id.column1_area:
                if (checked) {
                    settings.setColumn1(Settings.Column.AREA);
                }
                break;
            case R.id.column1_density:
                if (checked) {
                    settings.setColumn1(Settings.Column.DENSITY);
                }
                break;
        }
        PreferencesUtil.storeSettingsToPreferences(getApplicationContext(), settings);
    }

    public void onCol2RadioClicked(View view) {
        Settings settings = PreferencesUtil.getSettingsFromPreferences(getApplicationContext());
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.column2_population:
                if (checked) {
                    settings.setColumn2(Settings.Column.POPULATION);
                }
                break;
            case R.id.column2_area:
                if (checked) {
                    settings.setColumn2(Settings.Column.AREA);
                }
                break;
            case R.id.column2_density:
                if (checked) {
                    settings.setColumn2(Settings.Column.DENSITY);
                }
                break;
        }
        PreferencesUtil.storeSettingsToPreferences(getApplicationContext(), settings);

    }
}
