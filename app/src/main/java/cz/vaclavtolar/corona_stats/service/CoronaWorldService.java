package cz.vaclavtolar.corona_stats.service;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.vaclavtolar.corona_stats.dto.Country;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import static cz.vaclavtolar.corona_stats.service.CoronavirusApi.BASE_URL;

public class CoronaWorldService {

    final static Map<String, String> countryByIso2 = new HashMap<>();

    static {
        countryByIso2.put("AF", "Afghánistán");
        countryByIso2.put("AX", "Alandy");
        countryByIso2.put("AL", "Albánie");
        countryByIso2.put("DZ", "Alžírsko");
        countryByIso2.put("AS", "Americká Samoa");
        countryByIso2.put("VI", "Americké Panenské ostrovy");
        countryByIso2.put("AD", "Andorra");
        countryByIso2.put("AO", "Angola");
        countryByIso2.put("AI", "Anguilla");
        countryByIso2.put("AQ", "Antarktida");
        countryByIso2.put("AG", "Antigua a Barbuda");
        countryByIso2.put("AR", "Argentina");
        countryByIso2.put("AM", "Arménie");
        countryByIso2.put("AW", "Aruba");
        countryByIso2.put("AU", "Austrálie");
        countryByIso2.put("AZ", "Ázerbájdžán");
        countryByIso2.put("BS", "Bahamy");
        countryByIso2.put("BH", "Bahrajn");
        countryByIso2.put("BD", "Bangladéš");
        countryByIso2.put("BB", "Barbados");
        countryByIso2.put("BE", "Belgie");
        countryByIso2.put("BZ", "Belize");
        countryByIso2.put("BY", "Bělorusko");
        countryByIso2.put("BJ", "Benin");
        countryByIso2.put("BM", "Bermudy");
        countryByIso2.put("BT", "Bhútán");
        countryByIso2.put("BO", "Bolívie");
        countryByIso2.put("BQ", "Bonaire, Svatý Eustach a Saba");
        countryByIso2.put("BA", "Bosna a Hercegovina");
        countryByIso2.put("BW", "Botswana");
        countryByIso2.put("BV", "Bouvetův ostrov");
        countryByIso2.put("BR", "Brazílie");
        countryByIso2.put("IO", "Britské indickooceánské území");
        countryByIso2.put("VG", "Britské Panenské ostrovy");
        countryByIso2.put("BN", "Brunej");
        countryByIso2.put("BG", "Bulharsko");
        countryByIso2.put("BF", "Burkina Faso");
        countryByIso2.put("BI", "Burundi");
        countryByIso2.put("CK", "Cookovy ostrovy");
        countryByIso2.put("CW", "Curaçao");
        countryByIso2.put("TD", "Čad");
        countryByIso2.put("ME", "Černá Hora");
        countryByIso2.put("CZ", "Česko");
        countryByIso2.put("CN", "Čína");
        countryByIso2.put("DK", "Dánsko");
        countryByIso2.put("CD", "Konžská demokratická republika");
        countryByIso2.put("DM", "Dominika");
        countryByIso2.put("DO", "Dominikánská republika");
        countryByIso2.put("DJ", "Džibutsko");
        countryByIso2.put("EG", "Egypt");
        countryByIso2.put("EC", "Ekvádor");
        countryByIso2.put("ER", "Eritrea");
        countryByIso2.put("EE", "Estonsko");
        countryByIso2.put("ET", "Etiopie");
        countryByIso2.put("FO", "Faerské ostrovy");
        countryByIso2.put("FK", "Falklandy (Malvíny)");
        countryByIso2.put("FJ", "Fidži");
        countryByIso2.put("PH", "Filipíny");
        countryByIso2.put("FI", "Finsko");
        countryByIso2.put("FR", "Francie");
        countryByIso2.put("GF", "Francouzská Guyana");
        countryByIso2.put("TF", "Francouzská jižní a antarktická území");
        countryByIso2.put("PF", "Francouzská Polynésie");
        countryByIso2.put("GA", "Gabon");
        countryByIso2.put("GM", "Gambie");
        countryByIso2.put("GH", "Ghana");
        countryByIso2.put("GI", "Gibraltar");
        countryByIso2.put("GD", "Grenada");
        countryByIso2.put("GL", "Grónsko");
        countryByIso2.put("GE", "Gruzie");
        countryByIso2.put("GP", "Guadeloupe");
        countryByIso2.put("GU", "Guam");
        countryByIso2.put("GT", "Guatemala");
        countryByIso2.put("GG", "Guernsey");
        countryByIso2.put("GN", "Guinea");
        countryByIso2.put("GW", "Guinea-Bissau");
        countryByIso2.put("GY", "Guyana");
        countryByIso2.put("HT", "Haiti");
        countryByIso2.put("HM", "Heardův ostrov a MacDonaldovy ostrovy");
        countryByIso2.put("HN", "Honduras");
        countryByIso2.put("HK", "Hongkong");
        countryByIso2.put("CL", "Chile");
        countryByIso2.put("HR", "Chorvatsko");
        countryByIso2.put("IN", "Indie");
        countryByIso2.put("ID", "Indonésie");
        countryByIso2.put("IQ", "Irák");
        countryByIso2.put("IR", "Írán");
        countryByIso2.put("IE", "Irsko");
        countryByIso2.put("IS", "Island");
        countryByIso2.put("IT", "Itálie");
        countryByIso2.put("IL", "Izrael");
        countryByIso2.put("JM", "Jamajka");
        countryByIso2.put("JP", "Japonsko");
        countryByIso2.put("YE", "Jemen");
        countryByIso2.put("JE", "Jersey");
        countryByIso2.put("ZA", "Jižní Afrika");
        countryByIso2.put("GS", "Jižní Georgie a Jižní Sandwichovy ostrovy");
        countryByIso2.put("SS", "Jižní Súdán");
        countryByIso2.put("JO", "Jordánsko");
        countryByIso2.put("KY", "Kajmanské ostrovy");
        countryByIso2.put("KH", "Kambodža");
        countryByIso2.put("CM", "Kamerun");
        countryByIso2.put("CA", "Kanada");
        countryByIso2.put("CV", "Kapverdy");
        countryByIso2.put("QA", "Katar");
        countryByIso2.put("KZ", "Kazachstán");
        countryByIso2.put("KE", "Keňa");
        countryByIso2.put("KI", "Kiribati");
        countryByIso2.put("CC", "Kokosové (Keelingovy) ostrovy");
        countryByIso2.put("CO", "Kolumbie");
        countryByIso2.put("KM", "Komory");
        countryByIso2.put("CG", "Konžská republika");
        countryByIso2.put("KP", "Korejská lidově demokratická republika");
        countryByIso2.put("KR", "Korejská republika");
        countryByIso2.put("XK", "Kosovo");
        countryByIso2.put("CR", "Kostarika");
        countryByIso2.put("CU", "Kuba");
        countryByIso2.put("KW", "Kuvajt");
        countryByIso2.put("CY", "Kypr");
        countryByIso2.put("KG", "Kyrgyzstán");
        countryByIso2.put("LA", "Laos");
        countryByIso2.put("LS", "Lesotho");
        countryByIso2.put("LB", "Libanon");
        countryByIso2.put("LR", "Libérie");
        countryByIso2.put("LY", "Libye");
        countryByIso2.put("LI", "Lichtenštejnsko");
        countryByIso2.put("LT", "Litva");
        countryByIso2.put("LV", "Lotyšsko");
        countryByIso2.put("LU", "Lucembursko");
        countryByIso2.put("MO", "Macao");
        countryByIso2.put("MG", "Madagaskar");
        countryByIso2.put("HU", "Maďarsko");
        countryByIso2.put("MK", "Severní Makedonie");
        countryByIso2.put("MY", "Malajsie");
        countryByIso2.put("MW", "Malawi");
        countryByIso2.put("MV", "Maledivy");
        countryByIso2.put("ML", "Mali");
        countryByIso2.put("MT", "Malta");
        countryByIso2.put("IM", "Man");
        countryByIso2.put("MA", "Maroko");
        countryByIso2.put("MH", "Marshallovy ostrovy");
        countryByIso2.put("MQ", "Martinik");
        countryByIso2.put("MU", "Mauricius");
        countryByIso2.put("MR", "Mauritánie");
        countryByIso2.put("YT", "Mayotte");
        countryByIso2.put("UM", "Menší odlehlé ostrovy USA");
        countryByIso2.put("MX", "Mexiko");
        countryByIso2.put("FM", "Mikronésie");
        countryByIso2.put("MD", "Moldavsko");
        countryByIso2.put("MC", "Monako");
        countryByIso2.put("MN", "Mongolsko");
        countryByIso2.put("MS", "Montserrat");
        countryByIso2.put("MZ", "Mosambik");
        countryByIso2.put("MM", "Myanmar");
        countryByIso2.put("NA", "Namibie");
        countryByIso2.put("NR", "Nauru");
        countryByIso2.put("DE", "Německo");
        countryByIso2.put("NP", "Nepál");
        countryByIso2.put("NE", "Niger");
        countryByIso2.put("NG", "Nigérie");
        countryByIso2.put("NI", "Nikaragua");
        countryByIso2.put("NU", "Niue");
        countryByIso2.put("NL", "Nizozemsko");
        countryByIso2.put("NF", "Norfolk");
        countryByIso2.put("NO", "Norsko");
        countryByIso2.put("NC", "Nová Kaledonie");
        countryByIso2.put("NZ", "Nový Zéland");
        countryByIso2.put("OM", "Omán");
        countryByIso2.put("PK", "Pákistán");
        countryByIso2.put("PW", "Palau");
        countryByIso2.put("PS", "Palestina");
        countryByIso2.put("PA", "Panama");
        countryByIso2.put("PG", "Papua Nová Guinea");
        countryByIso2.put("PY", "Paraguay");
        countryByIso2.put("PE", "Peru");
        countryByIso2.put("PN", "Pitcairn");
        countryByIso2.put("CI", "Pobřeží slonoviny");
        countryByIso2.put("PL", "Polsko");
        countryByIso2.put("PR", "Portoriko");
        countryByIso2.put("PT", "Portugalsko");
        countryByIso2.put("AT", "Rakousko");
        countryByIso2.put("RE", "Réunion");
        countryByIso2.put("GQ", "Rovníková Guinea");
        countryByIso2.put("RO", "Rumunsko");
        countryByIso2.put("RU", "Rusko");
        countryByIso2.put("RW", "Rwanda");
        countryByIso2.put("GR", "Řecko");
        countryByIso2.put("PM", "Saint Pierre a Miquelon");
        countryByIso2.put("SV", "Salvador");
        countryByIso2.put("WS", "Samoa");
        countryByIso2.put("SM", "San Marino");
        countryByIso2.put("SA", "Saúdská Arábie");
        countryByIso2.put("SN", "Senegal");
        countryByIso2.put("MP", "Severní Mariany");
        countryByIso2.put("SC", "Seychely");
        countryByIso2.put("SL", "Sierra Leone");
        countryByIso2.put("SG", "Singapur");
        countryByIso2.put("SK", "Slovensko");
        countryByIso2.put("SI", "Slovinsko");
        countryByIso2.put("SO", "Somálsko");
        countryByIso2.put("AE", "Spojené arabské emiráty");
        countryByIso2.put("US", "Spojené státy");
        countryByIso2.put("RS", "Srbsko");
        countryByIso2.put("CF", "Středoafrická republika");
        countryByIso2.put("SD", "Súdán");
        countryByIso2.put("SR", "Surinam");
        countryByIso2.put("SH", "Svatá Helena");
        countryByIso2.put("LC", "Svatá Lucie");
        countryByIso2.put("BL", "Svatý Bartoloměj");
        countryByIso2.put("KN", "Svatý Kryštof a Nevis");
        countryByIso2.put("MF", "Svatý Martin (FR)");
        countryByIso2.put("SX", "Svatý Martin (NL)");
        countryByIso2.put("ST", "Svatý Tomáš a Princův ostrov");
        countryByIso2.put("VC", "Svatý Vincenc a Grenadiny");
        countryByIso2.put("SZ", "Svazijsko");
        countryByIso2.put("SY", "Sýrie");
        countryByIso2.put("SB", "Šalomounovy ostrovy");
        countryByIso2.put("ES", "Španělsko");
        countryByIso2.put("SJ", "Špicberky a Jan Mayen");
        countryByIso2.put("LK", "Šrí Lanka");
        countryByIso2.put("SE", "Švédsko");
        countryByIso2.put("CH", "Švýcarsko");
        countryByIso2.put("TJ", "Tádžikistán");
        countryByIso2.put("TZ", "Tanzanie");
        countryByIso2.put("TH", "Thajsko");
        countryByIso2.put("TW", "Tchaj-wan");
        countryByIso2.put("TG", "Togo");
        countryByIso2.put("TK", "Tokelau");
        countryByIso2.put("TO", "Tonga");
        countryByIso2.put("TT", "Trinidad a Tobago");
        countryByIso2.put("TN", "Tunisko");
        countryByIso2.put("TR", "Turecko");
        countryByIso2.put("TM", "Turkmenistán");
        countryByIso2.put("TC", "Turks a Caicos");
        countryByIso2.put("TV", "Tuvalu");
        countryByIso2.put("UG", "Uganda");
        countryByIso2.put("UA", "Ukrajina");
        countryByIso2.put("UY", "Uruguay");
        countryByIso2.put("UZ", "Uzbekistán");
        countryByIso2.put("CX", "Vánoční ostrov");
        countryByIso2.put("VU", "Vanuatu");
        countryByIso2.put("VA", "Vatikán");
        countryByIso2.put("GB", "Velká Británie");
        countryByIso2.put("VE", "Venezuela");
        countryByIso2.put("VN", "Vietnam");
        countryByIso2.put("TL", "Východní Timor");
        countryByIso2.put("WF", "Wallis a Futuna");
        countryByIso2.put("ZM", "Zambie");
        countryByIso2.put("EH", "Západní Sahara");
        countryByIso2.put("ZW", "Zimbabwe");
    }


    private static CoronaWorldService instance;

    private CoronavirusApi coronavirusApi;

    private CoronaWorldService() {
        initRestApiClient();
    }

    public static CoronaWorldService getInstance() {
        if (instance == null) {
            instance = new CoronaWorldService();
        }
        return instance;
    }

    private void initRestApiClient() {
        OkHttpClient httpClient = new OkHttpClient.Builder().build();

        ObjectMapper objectMapper = new ObjectMapper();
        JacksonConverterFactory converterFactory = JacksonConverterFactory.create(objectMapper);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(converterFactory)
                .client(httpClient)
                .build();
        coronavirusApi = retrofit.create(CoronavirusApi.class);
    }

    public Call<List<Country>> getAllCountries() {
        return coronavirusApi.getAllCountries(true);
    }

    public String getCountryCzechName(String iso2) {
        return countryByIso2.get(iso2);
    }


}
