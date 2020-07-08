package regwhitton.healthcaregatewaytest.restapi;

import static springfox.documentation.builders.PathSelectors.regex;
import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.classmate.TypeResolver;

import regwhitton.healthcaregatewaytest.restapi.error.ErrorResponse;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * Sets up the publishing of the REST API that allows
 * http://localhost:8080/swagger-ui.html to work.
 */
@Configuration
@EnableSwagger2WebMvc
public class RestApiConfig {

    @Bean
    public Docket productApi() {
        List<ResponseMessage> globalMessages = Arrays.asList(
            response(HttpStatus.BAD_REQUEST, "ErrorResponse"));

        return new Docket(DocumentationType.SWAGGER_2)
            .useDefaultResponseMessages(false)
            .globalResponseMessage(RequestMethod.GET, globalMessages)
            .globalResponseMessage(RequestMethod.POST, globalMessages)
            .globalResponseMessage(RequestMethod.PUT, globalMessages)
            .additionalModels(new TypeResolver().resolve(ErrorResponse.class))
            .select()
            .apis(basePackage(RestApiConfig.class.getPackage().getName()))
            .paths(regex("/.*"))
            .build();
    }

    private ResponseMessage response(HttpStatus httpStatus, String modelRef) {
        return new ResponseMessageBuilder()
            .code(httpStatus.value())
            .message(httpStatus.getReasonPhrase())
            .responseModel(new ModelRef(modelRef))
            .build();
    }
}
