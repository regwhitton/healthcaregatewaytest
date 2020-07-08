package regwhitton.healthcaregatewaytest.restapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import regwhitton.healthcaregatewaytest.restapi.error.ErrorResponse;
import regwhitton.healthcaregatewaytest.service.country.CountryService;
import regwhitton.healthcaregatewaytest.service.exception.NotFoundException;
import regwhitton.healthcaregatewaytest.service.model.BorderInfo;

@RestController
public class BorderController {

    @Autowired
    private CountryService countryService;

    @GetMapping("/borderInfo")
    @ApiOperation(value = "Get a list of countries which use a currency and a list of bordering countries.")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Not Found", response = ErrorResponse.class) })
    @ApiParam(value = "")
    public BorderInfo getBorderInfo(
            @RequestParam(name = "currency") @ApiParam(value = "ISO 4217 currency code", example = "GBP") String currencyCode)
            throws NotFoundException {

        return countryService.borderInfoByCurrency(currencyCode);
    }
}
