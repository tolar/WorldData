package cz.vaclavtolar.world_data.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.text.style.SuperscriptSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blongho.country_data.World;
import com.google.android.material.slider.RangeSlider;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cz.vaclavtolar.world_data.R;
import cz.vaclavtolar.world_data.dto.Country;
import cz.vaclavtolar.world_data.dto.IntervalLimits;
import cz.vaclavtolar.world_data.dto.Settings;
import cz.vaclavtolar.world_data.service.CountriesService;
import cz.vaclavtolar.world_data.service.PreferencesUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static final String COUNTRIES_KEY = "countries";

    public static final String CZECHIA = "Czechia";
    public static final String HOLY_SEE = "Holy See";

    public static final String DATA_UPDATED_KEY = "updated";
    private static final String CABO_VERDE = "Cabo Verde";
    private static final String BAHAMAS = "Bahamas";
    private static final String CONGO_BRAZZAVILLE = "Congo (Brazzaville)";
    private static final String CONGO_KINSHASA = "Congo (Kinshasa)";
    private static final String ESWATINI = "Eswatini";
    private static final String GAMBIA = "Gambia";
    private static final String TAIWAN = "Taiwan";
    private static final String KOSOVO = "Kosovo";
    private static final String BURMA = "Burma";
    private static final String WEST_BANK_AND_GAZA = "West Bank and Gaza";

    private CountriesAdapter countriesAdapter;

    private final IntervalLimits intervalLimits = new IntervalLimits();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        World.init(getApplicationContext());

        setContentView(R.layout.activity_main);

        countriesAdapter = new CountriesAdapter(this);
        RecyclerView itemsRecyler = findViewById(R.id.countries);
        itemsRecyler.setAdapter(countriesAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        itemsRecyler.setLayoutManager(layoutManager);

        setupToolbar();
        initSettings();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
    }

    private void setTableTitles() {
        Settings settings = PreferencesUtil.getSettingsFromPreferences(getApplicationContext());
        ((TextView)findViewById(R.id.col1_title)).setText(getColumnTitle(settings.getColumn1()));
        ((TextView)findViewById(R.id.col2_title)).setText(getColumnTitle(settings.getColumn2()));
    }

    private SpannableStringBuilder getColumnTitle(Settings.Column column) {
        SpannableStringBuilder colTitle = new SpannableStringBuilder();
        switch (column) {
            case POPULATION:
                colTitle  = new SpannableStringBuilder(getString(R.string.population));
                break;
            case AREA:
                String areaLabel = getString(R.string.area);
                String areaLabelWithUnits = areaLabel + " (km2)";
                SpannableStringBuilder csArea = new SpannableStringBuilder(areaLabelWithUnits);
                csArea.setSpan(new SuperscriptSpan(), areaLabelWithUnits.length() - 2, areaLabelWithUnits.length() - 1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                csArea.setSpan(new RelativeSizeSpan(0.75f), areaLabelWithUnits.length() - 2, areaLabelWithUnits.length() - 1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                colTitle  = csArea;
                break;
            case DENSITY:
                String densityLabel  = getString(R.string.density);
                String densityLabelWithUnits = densityLabel + " (/km2)";
                SpannableStringBuilder csDensity = new SpannableStringBuilder(densityLabelWithUnits);
                csDensity.setSpan(new SuperscriptSpan(), densityLabelWithUnits.length() - 2, densityLabelWithUnits.length() - 1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                csDensity.setSpan(new RelativeSizeSpan(0.75f), densityLabelWithUnits.length() - 2, densityLabelWithUnits.length() - 1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                colTitle  = csDensity;

                break;
        }
        return colTitle;
    }

    private void initSettings() {
        Settings settings = PreferencesUtil.getSettingsFromPreferences(getApplicationContext());
        if (settings == null) {
            settings = new Settings();
        }
        PreferencesUtil.storeSettingsToPreferences(getApplicationContext(), settings);

    }

    @Override
    protected void onStart() {
        super.onStart();
        setTableTitles();

        // nacteme data z preferences
        List<Country> countries = getDataFromPreferences();
        if (countries != null && !countries.isEmpty()) {
            countriesAdapter.setCountries(countries);
            countriesAdapter.notifyDataSetChanged();
        }
        for (Country ctr: countries) {
            updateIntervalLimits(intervalLimits, ctr);
        }
        updateSliders();

        Call<List<Country>> call = CountriesService.getInstance().getAllCountries();
        call.enqueue(new Callback<List<Country>>() {
            @Override
            public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {
                if (response.isSuccessful()) {

                    List<Country> countries = response.body();
                    for (Country ctr : countries) {
                        if (ctr.getAlpha2Code() != null) {
                            ctr.setCountryCzechName(CountriesService.getInstance().getCountryCzechName(ctr.getAlpha2Code()));
                            ctr.setCountryChineseName(CountriesService.getInstance().getCountryChineseName(ctr.getAlpha2Code()));
                        }
                        updateIntervalLimits(intervalLimits, ctr);
                        prepareMetadataData(ctr);
                    }
                    removeErrorText();
                    storeDataToPreferences(countries);
                    countriesAdapter.setCountries(countries);
                    countriesAdapter.notifyDataSetChanged();
                    updateSliders();
                }
            }

            @Override
            public void onFailure(Call<List<Country>> call, Throwable t) {
                addErrorTextView();
                List<Country> countries = getDataFromPreferences();
                for (Country ctr: countries) {
                    updateIntervalLimits(intervalLimits, ctr);
                }
                countriesAdapter.setCountries(countries);
                countriesAdapter.notifyDataSetChanged();
                updateSliders();
            }
        });

    }

    private void updateSliders() {
        ((RangeSlider)findViewById(R.id.rangePopulation)).setValues((float)intervalLimits.getPopulationMin(), (float)intervalLimits.getPopulationMax());
        ((RangeSlider)findViewById(R.id.rangePopulation)).setValueFrom((float)intervalLimits.getPopulationMin());
        ((RangeSlider)findViewById(R.id.rangePopulation)).setValueTo((float)intervalLimits.getPopulationMax());
        ((RangeSlider)findViewById(R.id.rangeArea)).setValues((float)intervalLimits.getAreaMin(), (float)intervalLimits.getAreaMax());
        ((RangeSlider)findViewById(R.id.rangeArea)).setValueFrom((float)intervalLimits.getAreaMin());
        ((RangeSlider)findViewById(R.id.rangeArea)).setValueTo((float)intervalLimits.getAreaMax());
        ((RangeSlider)findViewById(R.id.rangeDensity)).setValues((float)intervalLimits.getDensityMin(), (float)intervalLimits.getDensityMax());
        ((RangeSlider)findViewById(R.id.rangeDensity)).setValueFrom((float)intervalLimits.getDensityMin());
        ((RangeSlider)findViewById(R.id.rangeDensity)).setValueTo((float)intervalLimits.getDensityMax());

    }

    private void updateIntervalLimits(IntervalLimits intervalLimits, Country ctr) {
        if (ctr.getPopulation() != null) {
            if (ctr.getPopulation() < intervalLimits.getPopulationMin()) {
                intervalLimits.setPopulationMin(ctr.getPopulation());
            }
            if (ctr.getPopulation() > intervalLimits.getPopulationMax()) {
                intervalLimits.setPopulationMax(ctr.getPopulation());
            }
        }
        if (ctr.getArea() != null) {
            if (ctr.getArea() < intervalLimits.getAreaMin()) {
                intervalLimits.setAreaMin((long)Math.floor(ctr.getArea()));
            }
            if (ctr.getArea() > intervalLimits.getAreaMax()) {
                intervalLimits.setAreaMax((long)Math.ceil(ctr.getArea()));
            }
        }
        if (ctr.getDensity() < intervalLimits.getDensityMin()) {
            intervalLimits.setDensityMin((long)Math.floor(ctr.getDensity()));
        }
        if (ctr.getDensity() > intervalLimits.getDensityMax()) {
            intervalLimits.setDensityMax((long)Math.ceil(ctr.getDensity()));
        }

    }

    private void prepareMetadataData(Country ctr) {
        if (ctr.getCountryCzechName() != null) {
            ctr.setCountryCzechNameNoDiacritics(removeDiacriticalMarks(ctr.getCountryCzechName().toLowerCase()));
        }
        if (ctr.getPopulation() != null && ctr.getArea() != null) {
            ctr.setDensity(ctr.getPopulation() / ctr.getArea());
        }
    }

    public static String removeDiacriticalMarks(String string) {
        return Normalizer.normalize(string, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

    /*
    private void fixCountryData(Country ctr) {
        if (CZECHIA.equals(ctr.getCountryRegion())) {
            Country.CountryCode countryCode = new Country.CountryCode();
            countryCode.setIso2("CZ");
            countryCode.setIso3("CZE");
            ctr.setCountryCode(countryCode);
            ctr.setCountryCzechName(CountriesService.getInstance().getCountryCzechName(ctr.getCountryCode().getIso2()));
        } else if (HOLY_SEE.equals(ctr.getCountryRegion())) {
            Country.CountryCode countryCode = new Country.CountryCode();
            countryCode.setIso2("VA");
            countryCode.setIso3("VAT");
            ctr.setCountryCode(countryCode);
            ctr.setCountryCzechName(CountriesService.getInstance().getCountryCzechName(ctr.getCountryCode().getIso2()));
        } else if (CABO_VERDE.equals(ctr.getCountryRegion())) {
            Country.CountryCode countryCode = new Country.CountryCode();
            countryCode.setIso2("CV");
            countryCode.setIso3("CPV");
            ctr.setCountryCode(countryCode);
            ctr.setCountryCzechName(CountriesService.getInstance().getCountryCzechName(ctr.getCountryCode().getIso2()));
        } else if (CONGO_BRAZZAVILLE.equals(ctr.getCountryRegion())) {
            Country.CountryCode countryCode = new Country.CountryCode();
            countryCode.setIso2("CG");
            countryCode.setIso3("COG");
            ctr.setCountryCode(countryCode);
            ctr.setCountryCzechName(CountriesService.getInstance().getCountryCzechName(ctr.getCountryCode().getIso2()));
        } else if (CONGO_KINSHASA.equals(ctr.getCountryRegion())) {
            Country.CountryCode countryCode = new Country.CountryCode();
            countryCode.setIso2("CD");
            countryCode.setIso3("COD");
            ctr.setCountryCode(countryCode);
            ctr.setCountryCzechName(CountriesService.getInstance().getCountryCzechName(ctr.getCountryCode().getIso2()));
        } else if (ESWATINI.equals(ctr.getCountryRegion())) {
            Country.CountryCode countryCode = new Country.CountryCode();
            countryCode.setIso2("SZ");
            countryCode.setIso3("SWZ");
            ctr.setCountryCode(countryCode);
            ctr.setCountryCzechName(CountriesService.getInstance().getCountryCzechName(ctr.getCountryCode().getIso2()));
        } else if (GAMBIA.equals(ctr.getCountryRegion())) {
            Country.CountryCode countryCode = new Country.CountryCode();
            countryCode.setIso2("GM");
            countryCode.setIso3("GMB");
            ctr.setCountryCode(countryCode);
            ctr.setCountryCzechName(CountriesService.getInstance().getCountryCzechName(ctr.getCountryCode().getIso2()));
        } else if (ctr.getCountryRegion() != null && ctr.getCountryRegion().startsWith(TAIWAN)) {
            Country.CountryCode countryCode = new Country.CountryCode();
            countryCode.setIso2("TW");
            countryCode.setIso3("TWN");
            ctr.setCountryCode(countryCode);
            ctr.setCountryCzechName(CountriesService.getInstance().getCountryCzechName(ctr.getCountryCode().getIso2()));
        } else if (KOSOVO.equals(ctr.getCountryRegion())) {
            Country.CountryCode countryCode = new Country.CountryCode();
            countryCode.setIso2("XK");
            countryCode.setIso3("XXK");
            ctr.setCountryCode(countryCode);
            ctr.setCountryCzechName(CountriesService.getInstance().getCountryCzechName(ctr.getCountryCode().getIso2()));
        } else if (BURMA.equals(ctr.getCountryRegion())) {
            Country.CountryCode countryCode = new Country.CountryCode();
            countryCode.setIso2("MM");
            countryCode.setIso3("MMR");
            ctr.setCountryCode(countryCode);
            ctr.setCountryCzechName(CountriesService.getInstance().getCountryCzechName(ctr.getCountryCode().getIso2()));
        } else if (WEST_BANK_AND_GAZA.equals(ctr.getCountryRegion())) {
            Country.CountryCode countryCode = new Country.CountryCode();
            countryCode.setIso2("PS");
            countryCode.setIso3("PSE");
            ctr.setCountryCode(countryCode);
            ctr.setCountryCzechName(CountriesService.getInstance().getCountryCzechName(ctr.getCountryCode().getIso2()));
        }
    }

     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        initSearchAction(menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void initSearchAction(Menu menu) {
        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setIconifiedByDefault(true); // Do not iconify the widget; expand it by default
        EditText searchEditText = searchView.findViewById(R.id.search_src_text);
        searchEditText.setTextColor(getResources().getColor(R.color.primaryTextColor));
        searchEditText.setHintTextColor(getResources().getColor(R.color.colorGreyBorder));
        searchEditText.setBackgroundColor(getResources().getColor(R.color.colorWhiteBackground));
        ImageView searchIcon = searchView.findViewById(R.id.search_button);
        searchIcon.setColorFilter(getResources().getColor(R.color.primaryTextColor));
        ImageView closeIcon = searchView.findViewById(R.id.search_close_btn);
        closeIcon.setColorFilter(getResources().getColor(R.color.primaryTextColor));

        SearchView.OnQueryTextListener textChangeListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String query) {
                updateCountriesAdapter(query);
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                updateCountriesAdapter(query);
                return true;
            }
        };
        searchView.setOnQueryTextListener(textChangeListener);
    }

    private void updateCountriesAdapter(String query) {
        if (TextUtils.isEmpty(query)) {
            countriesAdapter.setQuery(null);
            //Text is cleared, do your thing
        } else {
            countriesAdapter.setQuery(removeDiacriticalMarks(query.toLowerCase()));
        }
        countriesAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_show_info:
                startInfoActivity();
                return true;
            case R.id.action_settings:
                startSettingsActivity();
                return true;
            case R.id.action_filter:
                showFilterSliders();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void startSettingsActivity() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    private void startInfoActivity() {
        Intent intent = new Intent(this, InfoActivity.class);
        startActivity(intent);
    }

    private void showFilterSliders() {

    }


    private void removeErrorText() {
        ((LinearLayout) findViewById(R.id.mainContainer)).removeView(findViewById(R.id.noInternetText));
    }

    private void addErrorTextView() {

        TextView textView = new TextView(getApplicationContext());
        textView.setId(R.id.noInternetText);
        textView.setLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, // Width of TextView
                RelativeLayout.LayoutParams.WRAP_CONTENT)); // Height of TextView
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textView.setPadding(15, 15, 15, 15);
        textView.setTextColor(getResources().getColor(R.color.colorErrorText));
        textView.setText(R.string.connection_error_message);
        ((LinearLayout) findViewById(R.id.mainContainer)).addView(textView, 0);
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
        Type listOfCountryObjects = new TypeToken<ArrayList<Country>>() {
        }.getType();
        List<Country> countries = gson.fromJson(json, listOfCountryObjects);
        if (countries == null) {
            return new ArrayList<>();
        }
        return countries;
    }


}
