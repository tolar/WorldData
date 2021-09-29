package cz.vaclavtolar.world_data.service;

import java.util.List;

import cz.vaclavtolar.world_data.dto.Country;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CountriesApi {

    String BASE_URL = "https://api.countrylayer.com/v2/";

    String ACCESS_KEY = "867c01fc9699dc5cf06b17d885c2a4c6";

    @GET("v2/all")
    Call<List<Country>> getAllCountries(@Query("access_key") String accessKey);
}
