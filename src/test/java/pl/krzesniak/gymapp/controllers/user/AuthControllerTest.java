package pl.krzesniak.gymapp.controllers.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import springfox.documentation.RequestHandler;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldLoginAndGetExerciseById() throws Exception {
        MvcResult login = mockMvc.perform(post("/login")
                .content("{\"username\": \"peterczak\", \"password\": \"password\"}"))
                .andExpect(status().is(200))
                .andReturn();

        LoginResponseDTO loginResponseDTO =
                objectMapper.readValue(login.getResponse().getContentAsString(), LoginResponseDTO.class);

         mockMvc.perform(get("/exercises/ffb7185e-ceb0-42d9-8d28-c155c97b39ec")
                .header("Authorization", loginResponseDTO.getAccessToken()))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.exerciseType", Matchers.is("CHEST")))
                .andExpect(jsonPath("$.name", Matchers.is("Wyciskanie sztangi na ławce poziomej")))
                .andExpect(jsonPath("$.urlImage", Matchers.is("https://krzesniakowo.blob.core.windows.net/exercises/bench_press.jpg")));

    }
    @Data
    public static class LoginResponseDTO{
        private String accessToken;
        private String refreshToken;
    }
}