package com.thor.email.adapters.out.configuration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

class OpenApiConfigurationTest {

  @Test
  void buildsOpenApiMetadataWithConfiguredApplicationVersion() {
    var configuration = new OpenApiConfiguration();
    ReflectionTestUtils.setField(configuration, "appVersion", "2.3.4");

    var openApi = configuration.customOpenAPI();

    assertEquals("Email", openApi.getInfo().getTitle());
    assertEquals("2.3.4", openApi.getInfo().getVersion());
    assertEquals("Microservice responsible for sending emails.", openApi.getInfo().getDescription());
  }
}
