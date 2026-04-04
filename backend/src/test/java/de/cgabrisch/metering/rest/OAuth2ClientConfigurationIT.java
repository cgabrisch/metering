package de.cgabrisch.metering.rest;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(properties = "spring.datasource.url=jdbc:tc:postgresql:15:///")
@ContextConfiguration(classes = MockOAuth2Config.class)
class OAuth2ClientConfigurationIT {

  @Autowired private WebApplicationContext context;

  private MockMvc mvc;

  @BeforeEach
  void setup() {
    mvc = webAppContextSetup(context).apply(springSecurity()).build();
  }

  @ParameterizedTest
  @MethodSource("requestBuilders")
  void redirectsAnonymousRequestToOAuth2Authorization(MockHttpServletRequestBuilder requestBuilder)
      throws Exception {
    mvc.perform(requestBuilder.with(anonymous()))
        .andExpect(redirectedUrlPattern("**/oauth2/authorization/dummy"));
  }

  @ParameterizedTest
  @MethodSource("requestBuilders")
  void oauth2AuthenticatedRequestSucceeds(MockHttpServletRequestBuilder requestBuilder)
      throws Exception {
    mvc.perform(requestBuilder.with(oauth2Login())).andExpect(status().is2xxSuccessful());
  }

  static Stream<MockHttpServletRequestBuilder> requestBuilders() {
    return Stream.of(
        post("/api/v1/meter/4711?unit=SomeUnit&description=SomeDescription").with(csrf()),
        get("/api/v1/meter"),
        post("/api/v1/meter/4711/measurement?instant=2026-03-01T14:15:16.876+02:00&measuredValue=123.456")
            .with(csrf()),
        get("/api/v1/meter/4711/measurement"));
  }
}
