package com.thor.email;

import static org.mockito.Mockito.mockStatic;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
    "spring.mongodb.uri=mongodb://localhost:27017/email-test",
    "spring.mail.host=localhost",
    "spring.mail.port=25",
    "spring.mail.username=test",
    "spring.mail.password=test"
})
class ApplicationTests {

  @Test
  void contextLoads() {
  }

  @Test
  void mainDelegatesToSpringApplication() {
    String[] args = {"--spring.main.banner-mode=off"};
    try (var springApplication = mockStatic(SpringApplication.class)) {
      Application.main(args);
      springApplication.verify(() -> SpringApplication.run(Application.class, args));
    }
  }

}
