package cz.vaclavtolar.corona_world.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.blongho.country_data.World;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cz.vaclavtolar.corona_world.R;
import cz.vaclavtolar.corona_world.dto.Country;

public class CountriesAdapter extends RecyclerView.Adapter<CountriesAdapter.ViewHolder> {

    static DecimalFormat formatter;

    static {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(' ');
        formatter = new DecimalFormat("###,###,###", symbols);
    }

    private List<Country> countries = new ArrayList<>();
    private Context context;
    private Comparator<Country> confirmedComparator;

    public CountriesAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        CardView cardView = (CardView) LayoutInflater.from(context).inflate(R.layout.country_item, parent, false);
        return new CountriesAdapter.ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int index) {
        CardView itemView = viewHolder.itemView;
        Country country = countries.get(index);
        if (country != null) {
            ImageView flagImageView = itemView.findViewById(R.id.flag);
            if (country.getCountryCode() != null) {
                flagImageView.setImageResource(World.getFlagOf(country.getCountryCode().getIso2()));
            } else {
                flagImageView.setImageResource(World.getFlagOf(country.getCountryRegion()));
            }
            TextView countryTextView = itemView.findViewById(R.id.country);
            if (country.getCountryCzechName() != null) {
                countryTextView.setText(country.getCountryCzechName());
            } else {
                countryTextView.setText(country.getCountryRegion());
            }
            TextView confirmedTextView = itemView.findViewById(R.id.confirmed);
            confirmedTextView.setText(formatter.format((Math.round(country.getConfirmed()))));
            TextView deathsTextView = itemView.findViewById(R.id.deaths);
            deathsTextView.setText(String.valueOf(formatter.format(Math.round(country.getDeaths()))));
        }
    }

    @Override
    public int getItemCount() {
        return countries.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private CardView itemView;

        public ViewHolder(@NonNull CardView itemView) {
            super(itemView);
            this.itemView = itemView;
        }
    }

    public void setCountries(List<Country> countries) {
        Collections.sort(countries, getConfirmedComparator());
        this.countries = countries;
        notifyDataSetChanged();
    }

    public Comparator<Country> getConfirmedComparator() {
        if (confirmedComparator == null) {
            confirmedComparator = new ConfirmedComparator();
        }
        return confirmedComparator;
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
