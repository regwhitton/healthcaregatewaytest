package regwhitton.healthcaregatewaytest;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import regwhitton.healthcaregatewaytest.service.model.BorderInfo;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class HealthcaregatewaytestApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void shouldFetchBorderInfo() throws Exception {

        BorderInfo borderInfo = restTemplate.getForObject(
            "http://localhost:{port}/borderInfo?currency={currency}", BorderInfo.class, "" + port, "GBP");

        assertThat(borderInfo.getCurrency()).isEqualTo("GBP");
        assertThat(borderInfo.getCountries()).contains("ATA", "GGY", "IMN", "JEY", "SGS", "GBR", "ZWE");
        assertThat(borderInfo.getBorderingCountriesWithDifferentCurrency()).contains("IRL", "BWA", "MOZ", "ZAF", "ZMB");
    }
}
