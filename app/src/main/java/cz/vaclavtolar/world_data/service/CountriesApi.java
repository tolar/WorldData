package cz.vaclavtolar.world_data.service;

import java.util.List;

import cz.vaclavtolar.world_data.dto.Country;
import retrofit2.Call;
import retrofit2.http.GET;

public interface CountriesApi {

    String BASE_URL = "https://restcountries.eu/rest/";

    @GET("v2/all")
    Call<List<Country>> getAllCountries();
}
