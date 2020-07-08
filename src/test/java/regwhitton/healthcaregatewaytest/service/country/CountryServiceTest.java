package regwhitton.healthcaregatewaytest.service.country;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.list;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import regwhitton.healthcaregatewaytest.service.exception.NotFoundException;
import regwhitton.healthcaregatewaytest.service.model.BorderInfo;

@ExtendWith(MockitoExtension.class)
class CountryServiceTest {

    @Mock
    private RestCountriesClient restCountriesClient;

    @InjectMocks
    private CountryService countryService;

    @Test()
    public void shouldFailForUnknownCurrency() throws Exception {
        given(restCountriesClient.restCountriesByCurrency("XXX"))
            .willThrow(new NotFoundException());

        assertThrows(NotFoundException.class, () -> {
            countryService.borderInfoByCurrency("XXX");
        });
    }

    @Test()
    public void shouldGetBorderInfoForCurrency() throws Exception {
        given(restCountriesClient.restCountriesByCurrency("EUR"))
            .willReturn(list(
                country("FRA", borders("CHE")),
                country("ITA", borders("CHE"))));

        BorderInfo borderInfo = countryService.borderInfoByCurrency("EUR");

        assertThat(borderInfo.getCurrency()).isEqualTo("EUR");
        assertThat(borderInfo.getCountries()).contains("FRA", "ITA");
        assertThat(borderInfo.getBorderingCountriesWithDifferentCurrency())
            .contains("CHE");
    }

    @Test()
    public void shouldRemoveBordersWithSameCurrency() throws Exception {
        given(restCountriesClient.restCountriesByCurrency("EUR"))
            .willReturn(list(
                country("FRA", borders("ITA", "CHE")),
                country("ITA", borders("FRA", "CHE"))));

        BorderInfo borderInfo = countryService.borderInfoByCurrency("EUR");

        assertThat(borderInfo.getCurrency()).isEqualTo("EUR");
        assertThat(borderInfo.getCountries()).contains("FRA", "ITA");
        assertThat(borderInfo.getBorderingCountriesWithDifferentCurrency())
            .contains("CHE");
    }

    private RestCountriesCountry country(String alpha3Code, List<String> borders) {
        RestCountriesCountry c = new RestCountriesCountry();
        c.setAlpha3Code(alpha3Code);
        c.setBorders(borders);
        return c;
    }

    private List<String> borders(String... borders) {
        return list(borders);
    }
}
