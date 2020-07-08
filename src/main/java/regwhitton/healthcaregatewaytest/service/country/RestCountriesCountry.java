package regwhitton.healthcaregatewaytest.service.country;

import java.util.List;

import lombok.Data;

@Data
public class RestCountriesCountry {

    /**
     * ISO 3166-1 country code
     */
    private String alpha3Code;
    
    /**
     * ISO 3166-1 country codes of bordering countries
     */
    private List<String> borders;
}
