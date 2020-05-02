package cz.vaclavtolar.world_data.activity;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import cz.vaclavtolar.world_data.R;

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
//        CountriesService.getInstance().getCountry(iso2Code).enqueue(new Callback<List<Country>>() {
//            @Override
//            public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {
//                Log.d("CR","CtrResponse: " + response.body().toString());
//            }
//
//            @Override
//            public void onFailure(Call<List<Country>> call, Throwable t) {
//                Log.e("CR", "CtrResponse",t);
//            }
//        });
    }
}
