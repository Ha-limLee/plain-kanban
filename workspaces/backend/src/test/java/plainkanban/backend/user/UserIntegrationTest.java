package plainkanban.backend.user;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
public class UserIntegrationTest {
  @Container
  @ServiceConnection
  static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

  @Autowired
  private MockMvc mockMvc;

  @Test
  @Order(1)
  void signUp() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    Map<String, String> body = new HashMap<>() {
      {
        put("name", "john");
        put("email", "john@gmail.com");
        put("password", "1111");
      }
    };
    String content = objectMapper.writeValueAsString(body);

    this.mockMvc
        .perform(
            MockMvcRequestBuilders
                .post("/api/v1/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
        .andExpect(
            MockMvcResultMatchers
                .status()
                .isCreated());

    // sign up using same email
    this.mockMvc.perform(
        MockMvcRequestBuilders
            .post("/api/v1/sign-up")
            .contentType(MediaType.APPLICATION_JSON)
            .content(content))
        .andExpect(
            MockMvcResultMatchers
                .status()
                .isConflict());
  }

  @Test
  @Order(2)
  void logIn() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    Map<String, String> body = new HashMap<>() {
      {
        put("email", "john@gmail.com");
        put("password", "1111");
      }
    };
    String content = objectMapper.writeValueAsString(body);

    this.mockMvc
        .perform(MockMvcRequestBuilders
            .post("/api/v1/log-in")
            .contentType(MediaType.APPLICATION_JSON)
            .content(content))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }
}
