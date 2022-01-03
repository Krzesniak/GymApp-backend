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
import pl.krzesniak.gymapp.dto.meals.MealHeaderDTO;
import pl.krzesniak.gymapp.dto.meals.MealsNotVerifiedDTO;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
class MealControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getMealsById() throws Exception {
        mockMvc.perform(get("/meals/4f6079e9-2dd1-4a08-9a6d-811314e99f9c"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.type", Matchers.is("OBIAD")))
                .andExpect(jsonPath("$.mealDifficulty", Matchers.is("Łatwe")))
                .andExpect(jsonPath("$.urlImage", Matchers.is("https://krzesniakowo.blob.core.windows.net/food/bawarska_salatka_z_kielbasa.jpg")));

    }

    @Test
    void getNotVerifiedMeals() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/meals/not-verified"))
                .andExpect(status().is(200))
                .andReturn();
        List<MealsNotVerifiedDTO> meals =
                objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                        objectMapper.getTypeFactory().constructCollectionType(List.class, MealsNotVerifiedDTO.class));
        assertEquals(0, meals.size());
    }

    @Test
    void getFilteredMealsWithName() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/meals/filters?searchString=ba"))
                .andExpect(status().is(200))
                .andReturn();
        List<MealHeaderDTO> meals =
                objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                        objectMapper.getTypeFactory().constructCollectionType(List.class, MealHeaderDTO.class));
        assertTrue(meals.stream()
                .allMatch(mealHeaderDTO -> mealHeaderDTO.getName().toLowerCase().contains("ba")));
        assertEquals(3, meals.size());
    }

    @Test
    void getFilteredMealsWithMealType() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/meals/filters?mealType=MAIN_COURSE"))
                .andExpect(status().is(200))
                .andReturn();
        List<MealHeaderDTO> meals =
                objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                        objectMapper.getTypeFactory().constructCollectionType(List.class, MealHeaderDTO.class));
        assertTrue(meals.stream()
                .allMatch(mealHeaderDTO -> mealHeaderDTO.getType().toLowerCase().contains("obiad")));
        assertEquals(17, meals.size());
    }

    @Test
    void getFilteredMealsWithMealDifficulty() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/meals/filters?mealDifficulty=EASY"))
                .andExpect(status().is(200))
                .andReturn();
        List<MealHeaderDTO> meals =
                objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                        objectMapper.getTypeFactory().constructCollectionType(List.class, MealHeaderDTO.class));
        assertTrue(meals.stream()
                .allMatch(mealHeaderDTO -> mealHeaderDTO.getMealDifficulty().equalsIgnoreCase("Å\u0081atwe")));
        assertEquals(17, meals.size());
    }

    @Test
    void getFilteredMealsWithAllCombinations() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get(
                "/meals/filters?mealDifficulty=EASY&searchString=ba&mealType=MAIN_COURSE"))
                .andExpect(status().is(200))
                .andReturn();
        List<MealHeaderDTO> meals =
                objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                        objectMapper.getTypeFactory().constructCollectionType(List.class, MealHeaderDTO.class));
        assertTrue(meals.stream()
                .allMatch(mealHeaderDTO -> mealHeaderDTO.getMealDifficulty().equalsIgnoreCase("Å\u0081atwe")
                        && mealHeaderDTO.getName().toLowerCase().contains("ba")
                        && mealHeaderDTO.getType().equalsIgnoreCase("obiad")
                ));
        assertEquals(2, meals.size());
    }

}