package de.cgabrisch.metering.rest;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(properties = "spring.datasource.url=jdbc:tc:postgresql:15:///")
@ContextConfiguration(classes = MockOAuth2Config.class)
class MeterRestControllerIT {

  @Autowired private WebApplicationContext context;

  private MockMvc mvc;

  @BeforeEach
  void setup() {
    mvc = webAppContextSetup(context).apply(springSecurity()).build();
  }

  @Test
  void redirectsAnonymousRequestToOAuth2Authorization() throws Exception {
    mvc.perform(get("/api/v1/meter").with(anonymous()))
        .andExpect(redirectedUrlPattern("**/oauth2/authorization/dummy"));
  }

  @Test
  void oauth2AuthenticatedRequestSucceeds() throws Exception {
    mvc.perform(get("/api/v1/meter").with(oauth2Login())).andExpect(status().is2xxSuccessful());
  }
}
