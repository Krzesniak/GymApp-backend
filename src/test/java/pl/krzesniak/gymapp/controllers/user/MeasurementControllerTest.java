package pl.krzesniak.gymapp.controllers.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import pl.krzesniak.gymapp.dto.meals.MealsNotVerifiedDTO;
import pl.krzesniak.gymapp.dto.registration.MeasurementDTO;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
class MeasurementControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void shouldReturnAllMeasurements() throws Exception {

        MvcResult login = mockMvc.perform(post("/login")
                .content("{\"username\": \"peterczak\", \"password\": \"password\"}"))
                .andExpect(status().is(200))
                .andReturn();

        AuthControllerTest.LoginResponseDTO loginResponseDTO =
                objectMapper.readValue(login.getResponse().getContentAsString(), AuthControllerTest.LoginResponseDTO.class);

        MvcResult mvcResult = mockMvc.perform(get("/measurements")
                .header("Authorization", loginResponseDTO.getAccessToken()))
                .andExpect(status().is(200))
                .andReturn();
        List<MeasurementDTO> measurements =
                objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                        objectMapper.getTypeFactory().constructCollectionType(List.class, MeasurementDTO.class));
        assertEquals(5, measurements.size());
    }

}