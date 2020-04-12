package cz.vaclavtolar.corona_world.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blongho.country_data.World;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cz.vaclavtolar.corona_world.R;
import cz.vaclavtolar.corona_world.service.CoronaWorldService;
import cz.vaclavtolar.corona_world.dto.Country;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static final String CZECHIA = "Czechia";
    public static final String CESKO = "Česko";
    public static final String COUNTRIES_KEY = "countries";
    public static final String DATA_UPDATED_KEY = "updated";

    private CountriesAdapter countriesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        World.init(getApplicationContext());

        setContentView(R.layout.activity_main);

        RecyclerView itemsRecyler = findViewById(R.id.countries);
        countriesAdapter = new CountriesAdapter();
        itemsRecyler.setAdapter(countriesAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        itemsRecyler.setLayoutManager(layoutManager);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Call<List<Country>> call = CoronaWorldService.getInstance().getAllCountries();
        call.enqueue(new Callback<List<Country>>() {
            @Override
            public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {
                if (response.isSuccessful()) {
                    List<Country> countries = response.body();
                    for (Country ctr : countries) {
                        if (ctr.getCountryCode() != null) {
                            ctr.setCountryCzechName(CoronaWorldService.getInstance().getCountryCzechName(ctr.getCountryCode().getIso2()));
                        } else {
                            if (CZECHIA.equals(ctr.getCountryRegion())) {
                                ctr.setCountryCzechName(CESKO);
                            }
                        }
                    }
                    removeErrorText();
                    storeDataToPreferences(countries);
                    countriesAdapter.setCountries(countries);
                }
            }

            @Override
            public void onFailure(Call<List<Country>> call, Throwable t) {
                addErrorTextView();
                List<Country> countries = getDataFromPreferences();
                countriesAdapter.setCountries(countries);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.show_info:
                startInfoActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void startInfoActivity() {
        Intent intent = new Intent(this, InfoActivity.class);
        startActivity(intent);
    }



    private void removeErrorText() {
        ((LinearLayout)findViewById(R.id.mainContainer)).removeView(findViewById(R.id.noInternetText));
    }

    private void addErrorTextView() {

        TextView textView = new TextView(getApplicationContext());
        textView.setId(R.id.noInternetText);
        textView.setLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, // Width of TextView
                RelativeLayout.LayoutParams.WRAP_CONTENT)); // Height of TextView
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textView.setPadding(15,15,15,15);
        textView.setTextColor(getResources().getColor(R.color.colorErrorText));
        textView.setText("Aktualizace dat nebyla úspěšná.\n Zkontrolujte připojení k internetu.");
        ((LinearLayout)findViewById(R.id.mainContainer)).addView(textView, 0);
    }

    private void storeDataToPreferences(List<Country> countries) {
        SharedPreferences prefs = getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        Gson gson = new Gson();

        String countriesJson = gson.toJson(countries);
        prefsEditor.putString(COUNTRIES_KEY, countriesJson);

        String updatedJson = gson.toJson(new Date());
        prefsEditor.putString(DATA_UPDATED_KEY, updatedJson);

        prefsEditor.commit();
    }

    private List<Country> getDataFromPreferences() {
        SharedPreferences prefs = getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString(COUNTRIES_KEY, "");
        Type listOfCountryObjects = new TypeToken<ArrayList<Country>>() {}.getType();
        List<Country> countries = gson.fromJson(json, listOfCountryObjects);
        return countries;
    }
}