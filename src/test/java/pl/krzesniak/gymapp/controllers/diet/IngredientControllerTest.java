package pl.krzesniak.gymapp.controllers.diet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import pl.krzesniak.gymapp.dto.meals.IngredientFilterDTO;
import pl.krzesniak.gymapp.dto.meals.MealHeaderDTO;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
class IngredientControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getIngredientsById() throws Exception {
        mockMvc.perform(get("/ingredients/1b575db2-8a78-4140-8d45-fc9e45d81d80"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.ingredientName", Matchers.is("Ziemniaki")));

    }
    @Test
    void getFilteredIngredients() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/ingredients/filters?searchString=zi"))
                .andExpect(status().is(200))
                .andReturn();
        List<IngredientFilterDTO> ingredients =
                objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                        objectMapper.getTypeFactory().constructCollectionType(List.class, IngredientFilterDTO.class));
        assertTrue(ingredients.stream()
                .allMatch(ingredientFilterDTO -> ingredientFilterDTO.getIngredientName().toLowerCase().contains("zi")));
        assertEquals(6, ingredients.size());
    }

}