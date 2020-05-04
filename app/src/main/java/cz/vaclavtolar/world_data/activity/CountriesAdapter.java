package cz.vaclavtolar.world_data.activity;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.os.ConfigurationCompat;
import androidx.core.os.LocaleListCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmadrosid.svgloader.SvgLoader;
import com.blongho.country_data.World;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cz.vaclavtolar.world_data.R;
import cz.vaclavtolar.world_data.dto.Country;
import cz.vaclavtolar.world_data.dto.Settings;
import cz.vaclavtolar.world_data.service.PreferencesUtil;

public class CountriesAdapter extends RecyclerView.Adapter<CountriesAdapter.ViewHolder> {

    public static final int ROUND_LIMIT = 10;
    static DecimalFormat formatterNoDecimal;
    static DecimalFormat formatterWithDecimal;

    static {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(' ');
        formatterNoDecimal = new DecimalFormat("###,###,###", symbols);
        formatterWithDecimal = new DecimalFormat("###,###,###.##", symbols);
    }

    private String query;

    private List<Country> countries = new ArrayList<>();
    private final Activity activity;
    private Comparator<Country> populationComparator;
    private Comparator<Country> areaComparator;
    private Comparator<Country> densityComparator;

    public CountriesAdapter(Activity activity) {
        this.activity = activity;
        this.query = null;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(activity.getApplicationContext()).inflate(R.layout.country_item, parent, false);
        return new CountriesAdapter.ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int index) {
        CardView itemView = viewHolder.itemView;
        Country country = getFilteredCountries().get(index);
        if (country != null) {

            TextView orderView = itemView.findViewById(R.id.order);
            orderView.setText((country.getOrder()) + ".");

            ImageView flagImageView = itemView.findViewById(R.id.flag);
            if (country.getAlpha2Code() != null) {
                flagImageView.setImageResource(World.getFlagOf(country.getAlpha2Code()));
            } else {
                flagImageView.setImageResource(World.getFlagOf(country.getName()));
            }
            if (flagImageView.getDrawable() == null && country.getFlag() != null) {
                // no flag found, try set flag as SVG image
                SvgLoader.pluck().with(activity).load(country.getFlag(), flagImageView);
            }
            TextView countryTextView = itemView.findViewById(R.id.country);

            if (isSetCzechLanguage() && country.getCountryCzechName() != null) {
                countryTextView.setText(country.getCountryCzechName());
            } else {
                countryTextView.setText(country.getName());
            }

            Settings settings = PreferencesUtil.getSettingsFromPreferences(activity.getApplicationContext());
            Double col1Val = getColumnValue(settings.getColumn1(), country);
            Double col2Val = getColumnValue(settings.getColumn2(), country);

            TextView col1TextView = itemView.findViewById(R.id.col1);
            if (col1Val != null) {
                if (col1Val < ROUND_LIMIT) {
                    col1TextView.setText(String.valueOf(formatterWithDecimal.format(col1Val)));
                } else {
                    col1TextView.setText(String.valueOf(formatterNoDecimal.format(Math.round(col1Val))));
                }
            } else {
                col1TextView.setText("-");
            }
            TextView col2TextView = itemView.findViewById(R.id.col2);
            if (col2Val != null) {
                if (col2Val < ROUND_LIMIT) {
                    col2TextView.setText(String.valueOf(formatterWithDecimal.format(col2Val)));
                } else {
                    col2TextView.setText(String.valueOf(formatterNoDecimal.format(Math.round(col2Val))));
                }
            } else {
                col2TextView.setText("-");
            }
        }
    }

    private Double getColumnValue(Settings.Column column, Country country) {
        Double colVal = null;
        switch (column) {
            case POPULATION: colVal = country.getPopulation().doubleValue(); break;
            case AREA: colVal = country.getArea(); break;
            case DENSITY: colVal = country.getDensity(); break;
        }
        return colVal;
    }

    @Override
    public int getItemCount() {
        return getFilteredCountries().size();
    }

    public List<Country> getFilteredCountries() {

        List<Country> result = countries;
        Collections.sort(result, getComparator());
        populateOrderForCountries(this.countries);

        if (query != null) {
            List<Country> filteredCountries = new ArrayList<>();
            for (int i = 0; i < countries.size(); i++) {
                Country country = countries.get(i);

                String countryName = country.getName().toLowerCase();
                if (isSetCzechLanguage()) {
                    countryName = country.getCountryCzechNameNoDiacritics();
                }
                if (countryName != null && countryName.contains(query)) {
                    filteredCountries.add(country);
                }
            }
            result = filteredCountries;
        }
        return result;
    }

    private void populateOrderForCountries(List<Country> countries) {
        int order = 1;
        for (Country country : countries) {
            country.setOrder(order++);
        }
    }

    private boolean isSetCzechLanguage() {
        LocaleListCompat locales = ConfigurationCompat.getLocales(activity.getApplicationContext().getResources().getConfiguration());
        if (!locales.isEmpty()) {
            return locales.get(0).getLanguage() == "cs";
        }
        return false;
    }

    private Comparator<? super Country> getComparator() {
        Settings settings = PreferencesUtil.getSettingsFromPreferences(activity.getApplicationContext());
        switch (settings.getColumn1()) {
            case POPULATION: return getPopulationComparator();
            case AREA: return getAreaComparator();
            case DENSITY: return getDensityComparator();
        }
        return getPopulationComparator();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private CardView itemView;

        public ViewHolder(@NonNull CardView itemView) {
            super(itemView);
            this.itemView = itemView;
        }
    }

    public void setCountries(List<Country> countries) {
        this.countries.clear();
        for (int i = 0; i < countries.size(); i++) {
            final Country country =  countries.get(i);
            if (countryHasData(country) && !isAntarctica(country)) {
                this.countries.add(country);
            }
        }
        notifyDataSetChanged();
    }

    private boolean countryHasData(Country country) {
        return country.getPopulation() != null && country.getArea() != null;
    }

    private boolean isAntarctica(Country country) {
        return "AQ".equals(country.getAlpha2Code());
    }

    public Comparator<Country> getPopulationComparator() {
        if (populationComparator == null) {
            populationComparator = new PopulationCompator();
        }
        return populationComparator;
    }

    public Comparator<Country> getAreaComparator() {
        if (areaComparator == null) {
            areaComparator = new AreaComparator();
        }
        return areaComparator;
    }

    public Comparator<Country> getDensityComparator() {
        if (densityComparator == null) {
            densityComparator = new DensityComparator();
        }
        return densityComparator;
    }

    class PopulationCompator implements Comparator<Country> {

        @Override
        public int compare(Country item1, Country item2) {
            if (item1 != null && item2 != null) {
                if (item1.getPopulation() == item2.getPopulation()) {
                    return 0;
                }
                if (item1.getPopulation() < item2.getPopulation()) {
                    return 1;
                }
                if (item1.getPopulation() > item2.getPopulation()) {
                    return -1;
                }
            }
            return 0;
        }
    }

    class AreaComparator implements Comparator<Country> {

        @Override
        public int compare(Country item1, Country item2) {
            if (item1 != null && item2 != null) {
                if (item1.getArea() == item2.getArea()) {
                    return 0;
                }
                if (item1.getArea() < item2.getArea()) {
                    return 1;
                }
                if (item1.getArea() > item2.getArea()) {
                    return -1;
                }
            }
            return 0;
        }
    }

    class DensityComparator implements Comparator<Country> {

        @Override
        public int compare(Country item1, Country item2) {
            if (item1 != null && item2 != null) {
                if (item1.getDensity() == item2.getDensity()) {
                    return 0;
                }
                if (item1.getDensity() < item2.getDensity()) {
                    return 1;
                }
                if (item1.getDensity() > item2.getDensity()) {
                    return -1;
                }
            }
            return 0;
        }
    }

}
