package tn.esprit.autoloc.autolocapi.web.controller;

import java.util.Set;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.IntegerSchema;
import io.swagger.v3.oas.models.media.MapSchema;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

  @Bean
  public OpenAPI autoLocOpenAPI() {
    return new OpenAPI().info(new Info()
        .title("AutoLoc API")
        .description("API REST de gestion de location de véhicules")
        .version("1.0.0"));
  }

  @Bean
  public OpenApiCustomizer apiErrorResponses() {
    return openApi -> {
      if (openApi.getComponents() == null) {
        openApi.setComponents(new Components());
      }

      Schema<?> errorSchema = new Schema<>()
          .addProperty("status", new IntegerSchema())
          .addProperty("message", new StringSchema())
          .addProperty("horodatage", new StringSchema().format("date-time"))
          .addProperty("erreurs", new MapSchema().additionalProperties(new StringSchema()));
      openApi.getComponents().addSchemas("ApiError", errorSchema);

      openApi.getPaths().values().forEach(path ->
          path.readOperations().forEach(operation -> operation.getResponses().forEach((status, response) -> {
            if (Set.of("400", "404", "409", "415", "500").contains(status)) {
              response.setContent(new Content().addMediaType("application/json",
                  new MediaType().schema(new Schema<>().$ref("#/components/schemas/ApiError"))));
            }
          })));
    };
  }
}
