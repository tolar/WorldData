package cz.vaclavtolar.corona_world.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

public class Country {

    private float confirmed;
    @JsonAlias("countrycode")
    private CountryCode countryCode;
    @JsonAlias("countryregion")
    private String countryRegion;
    private String countryCzechName;
    private String countryCzechNameNoDiacritics;
    private float deaths;
    @JsonAlias("lastupdate")
    private String lastUpdate;
    private Location location;
    @JsonAlias("provincestate")
    private String provinceState;
    private float recovered;
    private float active;

    public float getConfirmed() {
        return confirmed;
    }

    public CountryCode getCountryCode() {
        return countryCode;
    }

    public String getCountryRegion() {
        return countryRegion;
    }

    public float getDeaths() {
        return deaths;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public Location getLocation() {
        return location;
    }

    public String getProvinceState() {
        return provinceState;
    }

    public float getRecovered() {
        return recovered;
    }

    public void setConfirmed(float confirmed) {
        this.confirmed = confirmed;
    }

    public void setCountryCode(CountryCode countryCodeObject) {
        this.countryCode = countryCodeObject;
    }

    public void setCountryRegion(String countryRegion) {
        this.countryRegion = countryRegion;
    }

    public String getCountryCzechName() {
        return countryCzechName;
    }

    public void setCountryCzechName(String countryCzechName) {
        this.countryCzechName = countryCzechName;
    }

    public String getCountryCzechNameNoDiacritics() {
        return countryCzechNameNoDiacritics;
    }

    public void setCountryCzechNameNoDiacritics(String countryCzechNameNoDiacritics) {
        this.countryCzechNameNoDiacritics = countryCzechNameNoDiacritics;
    }

    public void setDeaths(float deaths) {
        this.deaths = deaths;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public void setLocation(Location locationObject) {
        this.location = locationObject;
    }

    public void setProvinceState(String provinceState) {
        this.provinceState = provinceState;
    }

    public void setRecovered(float recovered) {
        this.recovered = recovered;
    }

    public float getActive() {
        return active;
    }

    public void setActive(float active) {
        this.active = active;
    }

    public class Location {
        private float lat;
        private float lng;

        public float getLat() {
            return lat;
        }

        public float getLng() {
            return lng;
        }

        public void setLat(float lat) {
            this.lat = lat;
        }

        public void setLng(float lng) {
            this.lng = lng;
        }
    }

    public static class CountryCode {
        private String iso2;
        private String iso3;

        public String getIso2() {
            return iso2;
        }

        public String getIso3() {
            return iso3;
        }

        public void setIso2(String iso2) {
            this.iso2 = iso2;
        }

        public void setIso3(String iso3) {
            this.iso3 = iso3;
        }
    }
}





