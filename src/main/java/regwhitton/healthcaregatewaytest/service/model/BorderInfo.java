package regwhitton.healthcaregatewaytest.service.model;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BorderInfo {
    @ApiModelProperty(value="ISO 4217 currency code", example="GBP")
    private String currency;

    @ApiModelProperty("ISO 3166-1 codes of countries that use currency")
    private List<String> countries;

    @ApiModelProperty("ISO 3166-1 codes of countries that border the above countries but don't use the same currency")
    private List<String> borderingCountriesWithDifferentCurrency;
}
