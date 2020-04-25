package cz.vaclavtolar.corona_stats.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.os.ConfigurationCompat;
import androidx.core.os.LocaleListCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.blongho.country_data.World;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cz.vaclavtolar.corona_stats.R;
import cz.vaclavtolar.corona_stats.dto.Country;
import cz.vaclavtolar.corona_stats.dto.Settings;
import cz.vaclavtolar.corona_stats.service.PreferencesUtil;

public class CountriesAdapter extends RecyclerView.Adapter<CountriesAdapter.ViewHolder> {

    static DecimalFormat formatter;

    static {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(' ');
        formatter = new DecimalFormat("###,###,###", symbols);
    }

    private String query;

    private List<Country> countries = new ArrayList<>();
    private Context context;
    private Comparator<Country> confirmedComparator;
    private Comparator<Country> activeComparator;
    private Comparator<Country> recoveredComparator;
    private Comparator<Country> deathsComparator;

    public CountriesAdapter(Context context) {
        this.context = context;
        this.query = null;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(context).inflate(R.layout.country_item, parent, false);
        return new CountriesAdapter.ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int index) {
        CardView itemView = viewHolder.itemView;
        Country country = getFilteredCountries().get(index);
        if (country != null) {
            ImageView flagImageView = itemView.findViewById(R.id.flag);
            if (country.getCountryCode() != null) {
                flagImageView.setImageResource(World.getFlagOf(country.getCountryCode().getIso2()));
            } else {
                flagImageView.setImageResource(World.getFlagOf(country.getCountryRegion()));
            }
            TextView countryTextView = itemView.findViewById(R.id.country);

            if (isSetCzechLanguage() && country.getCountryCzechName() != null) {
                countryTextView.setText(country.getCountryCzechName());
            } else {
                countryTextView.setText(country.getCountryRegion());
            }

            Settings settings = PreferencesUtil.getSettingsFromPreferences(context);
            float col1Val = getColumnValue(settings.getColumn1(), country);
            float col2Val = getColumnValue(settings.getColumn2(), country);

            TextView confirmedTextView = itemView.findViewById(R.id.col1);
            confirmedTextView.setText(formatter.format((Math.round(col1Val))));
            TextView deathsTextView = itemView.findViewById(R.id.col2);
            deathsTextView.setText(String.valueOf(formatter.format(Math.round(col2Val))));
        }
    }

    private float getColumnValue(Settings.Column column, Country country) {
        float colVal = -1;
        switch (column) {
            case CONFIRMED: colVal = country.getConfirmed(); break;
            case ACTIVE: colVal = country.getActive(); break;
            case RECOVERED: colVal = country.getRecovered(); break;
            case DEATHS: colVal = country.getDeaths(); break;
        }
        return colVal;
    }

    @Override
    public int getItemCount() {
        return getFilteredCountries().size();
    }

    public List<Country> getFilteredCountries() {
        List<Country> result = countries;
        if (query != null) {
            List<Country> filteredCountries = new ArrayList<>();
            for (int i = 0; i < countries.size(); i++) {
                Country country = countries.get(i);

                String countryName = country.getCountryRegion().toLowerCase();
                if (isSetCzechLanguage()) {
                    countryName = country.getCountryCzechNameNoDiacritics();
                }
                if (countryName != null && countryName.contains(query)) {
                    filteredCountries.add(country);
                }
            }
            result = filteredCountries;
        }
        Collections.sort(result, getComparator());
        return result;
    }

    private boolean isSetCzechLanguage() {
        LocaleListCompat locales = ConfigurationCompat.getLocales(context.getResources().getConfiguration());
        if (!locales.isEmpty()) {
            if (locales.get(0).getLanguage() == "cs") {
                return true;
            }
        }
        return false;
    }

    private Comparator<? super Country> getComparator() {
        Settings settings = PreferencesUtil.getSettingsFromPreferences(context);
        switch (settings.getColumn1()) {
            case CONFIRMED: return getConfirmedComparator();
            case ACTIVE: return getActiveComparator();
            case RECOVERED: return getRecoveredComparator();
            case DEATHS: return getDeathsComparator();
        }
        return getConfirmedComparator();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private CardView itemView;

        public ViewHolder(@NonNull CardView itemView) {
            super(itemView);
            this.itemView = itemView;
        }
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
        notifyDataSetChanged();
    }

    public Comparator<Country> getConfirmedComparator() {
        if (confirmedComparator == null) {
            confirmedComparator = new ConfirmedComparator();
        }
        return confirmedComparator;
    }

    public Comparator<Country> getActiveComparator() {
        if (activeComparator == null) {
            activeComparator = new ActiveComparator();
        }
        return activeComparator;
    }

    public Comparator<Country> getRecoveredComparator() {
        if (recoveredComparator == null) {
            recoveredComparator = new RecoveredComparator();
        }
        return recoveredComparator;
    }

    public Comparator<Country> getDeathsComparator() {
        if (deathsComparator == null) {
            deathsComparator = new DeathComparator();
        }
        return deathsComparator;
    }

    class ConfirmedComparator implements Comparator<Country> {

        @Override
        public int compare(Country item1, Country item2) {
            if (item1 != null && item2 != null) {
                if (item1.getConfirmed() == item2.getConfirmed()) {
                    return 0;
                }
                if (item1.getConfirmed() < item2.getConfirmed()) {
                    return 1;
                }
                if (item1.getConfirmed() > item2.getConfirmed()) {
                    return -1;
                }
            }
            return 0;
        }
    }

    class ActiveComparator implements Comparator<Country> {

        @Override
        public int compare(Country item1, Country item2) {
            if (item1 != null && item2 != null) {
                if (item1.getActive() == item2.getActive()) {
                    return 0;
                }
                if (item1.getActive() < item2.getActive()) {
                    return 1;
                }
                if (item1.getActive() > item2.getActive()) {
                    return -1;
                }
            }
            return 0;
        }
    }

    class RecoveredComparator implements Comparator<Country> {

        @Override
        public int compare(Country item1, Country item2) {
            if (item1 != null && item2 != null) {
                if (item1.getRecovered() == item2.getRecovered()) {
                    return 0;
                }
                if (item1.getRecovered() < item2.getRecovered()) {
                    return 1;
                }
                if (item1.getRecovered() > item2.getRecovered()) {
                    return -1;
                }
            }
            return 0;
        }
    }

    class DeathComparator implements Comparator<Country> {

        @Override
        public int compare(Country item1, Country item2) {
            if (item1 != null && item2 != null) {
                if (item1.getDeaths() == item2.getDeaths()) {
                    return 0;
                }
                if (item1.getDeaths() < item2.getDeaths()) {
                    return 1;
                }
                if (item1.getDeaths() > item2.getDeaths()) {
                    return -1;
                }
            }
            return 0;
        }
    }
}
