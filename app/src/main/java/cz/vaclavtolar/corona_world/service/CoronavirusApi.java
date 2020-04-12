package cz.vaclavtolar.corona_world.service;

import java.util.List;

import cz.vaclavtolar.corona_world.dto.Country;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CoronavirusApi {

    String BASE_URL = "https://wuhan-coronavirus-api.laeyoung.endpoint.ainize.ai";

    @GET("jhu-edu/latest")
    Call<List<Country>> getAllCountries(@Query("onlyCountries") boolean onlyCountry);

}
