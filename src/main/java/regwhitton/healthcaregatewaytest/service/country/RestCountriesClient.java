package regwhitton.healthcaregatewaytest.service.country;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import regwhitton.healthcaregatewaytest.service.exception.NotFoundException;

@Component
public class RestCountriesClient {

    @Value("${countries.by.currency.url}")
    private String countriesByCurrencyUrl;

    List<RestCountriesCountry> restCountriesByCurrency(String currencyCode) throws NotFoundException {
        try {
            RestTemplate template = new RestTemplate();
            ResponseEntity<RestCountriesCountry[]> response = template.getForEntity(countriesByCurrencyUrl,
                RestCountriesCountry[].class, currencyCode);
            return Arrays.asList(response.getBody());
        } catch (HttpClientErrorException.NotFound ex) {
            throw new NotFoundException();
        }
    }
}
