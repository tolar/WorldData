package cz.vaclavtolar.corona_stats.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;

import java.util.List;

import cz.vaclavtolar.corona_stats.R;
import cz.vaclavtolar.corona_stats.dto.Country;
import cz.vaclavtolar.corona_stats.service.CoronaWorldService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CountryActivity extends AppCompatActivity {

    public static final String COUNTRY_ISO_2 = "COUNTRY_ISO2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);

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
        final String iso2Code = getIntent().getStringExtra(CountryActivity.COUNTRY_ISO_2);
        CoronaWorldService.getInstance().getCountry(iso2Code).enqueue(new Callback<List<Country>>() {
            @Override
            public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {
                Log.d("CR","CtrResponse: " + response.body().toString());
            }

            @Override
            public void onFailure(Call<List<Country>> call, Throwable t) {
                Log.e("CR", "CtrResponse",t);
            }
        });
    }
}
