
package cz.vaclavtolar.world_data.dto;

import java.util.List;
import java.util.Objects;

@SuppressWarnings("unused")
public class Country {

    private int order;

    private String alpha2Code;

    @Override
    public int hashCode() {
        return Objects.hash(alpha2Code);
    }

    private String alpha3Code;

    private List<String> altSpellings;

    private Double area;

    private List<String> borders;

    private List<String> callingCodes;

    private String capital;

    private String cioc;

    private List<Currency> currencies;

    private String demonym;

    private String flag;

    private Double gini;

    private List<Language> languages;

    private List<Double> latlng;

    private String name;

    private String nativeName;

    private String numericCode;

    private Long population;

    private double density;


    private String region;

    private List<RegionalBloc> regionalBlocs;

    private String subregion;

    private List<String> timezones;

    private List<String> topLevelDomain;

    private Translations translations;
    private String countryCzechName;
    private String countryCzechNameNoDiacritics;

    private String countryChineseName;
    private String countryArabicName;

    public String getAlpha2Code() {
        return alpha2Code;
    }

    public void setAlpha2Code(String alpha2Code) {
        this.alpha2Code = alpha2Code;
    }

    public String getAlpha3Code() {
        return alpha3Code;
    }

    public void setAlpha3Code(String alpha3Code) {
        this.alpha3Code = alpha3Code;
    }

    public List<String> getAltSpellings() {
        return altSpellings;
    }

    public void setAltSpellings(List<String> altSpellings) {
        this.altSpellings = altSpellings;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public List<String> getBorders() {
        return borders;
    }

    public void setBorders(List<String> borders) {
        this.borders = borders;
    }

    public List<String> getCallingCodes() {
        return callingCodes;
    }

    public void setCallingCodes(List<String> callingCodes) {
        this.callingCodes = callingCodes;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getCioc() {
        return cioc;
    }

    public void setCioc(String cioc) {
        this.cioc = cioc;
    }

    public List<Currency> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<Currency> currencies) {
        this.currencies = currencies;
    }

    public String getDemonym() {
        return demonym;
    }

    public void setDemonym(String demonym) {
        this.demonym = demonym;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Double getGini() {
        return gini;
    }

    public void setGini(Double gini) {
        this.gini = gini;
    }

    public List<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(List<Language> languages) {
        this.languages = languages;
    }

    public List<Double> getLatlng() {
        return latlng;
    }

    public void setLatlng(List<Double> latlng) {
        this.latlng = latlng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNativeName() {
        return nativeName;
    }

    public void setNativeName(String nativeName) {
        this.nativeName = nativeName;
    }

    public String getNumericCode() {
        return numericCode;
    }

    public void setNumericCode(String numericCode) {
        this.numericCode = numericCode;
    }

    public Long getPopulation() {
        return population;
    }

    public void setPopulation(Long population) {
        this.population = population;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public List<RegionalBloc> getRegionalBlocs() {
        return regionalBlocs;
    }

    public void setRegionalBlocs(List<RegionalBloc> regionalBlocs) {
        this.regionalBlocs = regionalBlocs;
    }

    public String getSubregion() {
        return subregion;
    }

    public void setSubregion(String subregion) {
        this.subregion = subregion;
    }

    public List<String> getTimezones() {
        return timezones;
    }

    public void setTimezones(List<String> timezones) {
        this.timezones = timezones;
    }

    public List<String> getTopLevelDomain() {
        return topLevelDomain;
    }

    public void setTopLevelDomain(List<String> topLevelDomain) {
        this.topLevelDomain = topLevelDomain;
    }

    public Translations getTranslations() {
        return translations;
    }

    public void setTranslations(Translations translations) {
        this.translations = translations;
    }

    public double getDensity() {
        return density;
    }

    public void setDensity(double density) {
        this.density = density;
    }

    public String getCountryCzechName() {
        return countryCzechName;
    }

    public void setCountryCzechName(String countryCzechName) {
        this.countryCzechName = countryCzechName;
    }

    public String getCountryChineseName() {
        return countryChineseName;
    }

    public void setCountryChineseName(String countryChineseName) {
        this.countryChineseName = countryChineseName;
    }

    public String getCountryArabicName() {
        return countryArabicName;
    }

    public void setCountryArabicName(String countryArabicName) {
        this.countryArabicName = countryArabicName;
    }

    public void setCountryCzechNameNoDiacritics(String countryCzechNameNoDiacritics) {
        this.countryCzechNameNoDiacritics = countryCzechNameNoDiacritics;
    }

    public String getCountryCzechNameNoDiacritics() {
        return countryCzechNameNoDiacritics;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
