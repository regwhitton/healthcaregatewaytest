package regwhitton.healthcaregatewaytest.service.country;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import regwhitton.healthcaregatewaytest.service.exception.NotFoundException;
import regwhitton.healthcaregatewaytest.service.model.BorderInfo;

@Service
public class CountryService {

    @Autowired
    private RestCountriesClient restCountriesClient;

    public BorderInfo borderInfoByCurrency(String currencyCode) throws NotFoundException {
        List<RestCountriesCountry> restCountries = restCountriesClient.restCountriesByCurrency(currencyCode);
        List<String> countriesUsingCurrency = extractCountryCodes(restCountries);
        List<String> borderingFiltered = borderingCountriesExcluding(restCountries, countriesUsingCurrency);
        return borderInfo(currencyCode, countriesUsingCurrency, borderingFiltered);
    }

    private List<String> extractCountryCodes(List<RestCountriesCountry> restCountries) {
        return restCountries.stream()
            .map(c -> c.getAlpha3Code())
            .collect(toList());
    }

    private List<String> borderingCountriesExcluding(List<RestCountriesCountry> restCountries,
            List<String> excludeCountries) {
        return restCountries.stream()
            .flatMap(c -> c.getBorders().stream())
            .filter(c -> !excludeCountries.contains(c))
            .collect(toList());
    }

    private BorderInfo borderInfo(
            String currencyCode, List<String> countriesUsingCurrency, List<String> borderingFiltered) {

        BorderInfo borderInfo = new BorderInfo();
        borderInfo.setCurrency(currencyCode);
        borderInfo.setCountries(countriesUsingCurrency);
        borderInfo.setBorderingCountriesWithDifferentCurrency(borderingFiltered);
        return borderInfo;
    }
}
