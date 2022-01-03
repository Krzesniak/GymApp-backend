package pl.krzesniak.gymapp.controllers.training;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import pl.krzesniak.gymapp.dto.ExerciseFilterDTO;
import pl.krzesniak.gymapp.dto.ExerciseNotVerifiedDTO;
import pl.krzesniak.gymapp.dto.exercises.ExerciseIdWithNameDTO;
import pl.krzesniak.gymapp.enums.ExerciseDifficulty;
import pl.krzesniak.gymapp.enums.ExerciseType;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
class ExerciseControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getExerciseById() throws Exception {
        mockMvc.perform(get("/exercises/5f667ea1-8c04-4873-a05b-2624b7e01bd1"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.exerciseType", Matchers.is("BACK")))
                .andExpect(jsonPath("$.name", Matchers.is("Unoszenie tułowia na ławce rzymskiej")))
                .andExpect(jsonPath("$.urlImage", Matchers.is("https://krzesniakowo.blob.core.windows.net/exercises/rzymska.jpg")));

    }

    @Test
    void findAllExercises() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/exercises/all"))
                .andExpect(status().is(200))
                .andReturn();
        List<ExerciseIdWithNameDTO> exercises =
                objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                        objectMapper.getTypeFactory().constructCollectionType(List.class, ExerciseIdWithNameDTO.class));
        assertEquals(46, exercises.size());
    }

    @Test
    void getNotVerifiedExercises() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/exercises/not-verified"))
                .andExpect(status().is(200))
                .andReturn();
        List<ExerciseNotVerifiedDTO> exercises =
                objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                        objectMapper.getTypeFactory().constructCollectionType(List.class, ExerciseIdWithNameDTO.class));
        assertEquals(0, exercises.size());
    }

    @Test
    void getFilteredExercisesWithName() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/exercises/filters?searchString=wy"))
                .andExpect(status().is(200))
                .andReturn();
        List<ExerciseFilterDTO> exercises =
                objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                        objectMapper.getTypeFactory().constructCollectionType(List.class, ExerciseFilterDTO.class));
        assertTrue(exercises.stream()
                .allMatch(exercise -> exercise.getExerciseName().toLowerCase().contains("wy")));
        assertEquals(14, exercises.size());
    }

    @Test
    void getFilteredExercisesWithExerciseType() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/exercises/filters?exerciseType=CHEST"))
                .andExpect(status().is(200))
                .andReturn();
        List<ExerciseFilterDTO> exercises =
                objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                        objectMapper.getTypeFactory().constructCollectionType(List.class, ExerciseFilterDTO.class));
        assertTrue(exercises.stream()
                .allMatch(exercise -> exercise.getExerciseType() == ExerciseType.CHEST));
        assertEquals(9, exercises.size());
    }

    @Test
    void getFilteredExercisesWithExerciseDifficulty() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/exercises/filters?exerciseDifficulty=EASY"))
                .andExpect(status().is(200))
                .andReturn();
        List<ExerciseFilterDTO> exercises =
                objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                        objectMapper.getTypeFactory().constructCollectionType(List.class, ExerciseFilterDTO.class));
        assertTrue(exercises.stream()
                .allMatch(exercise -> exercise.getExerciseDifficulty().equals(ExerciseDifficulty.EASY)));
        assertEquals(14, exercises.size());
    }

    @Test
    void getFilteredExercisesCombineAllPrevious() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get(
                "/exercises/filters?exerciseDifficulty=EASY&exerciseType=CHEST&searchString=wy"))
                .andExpect(status().is(200))
                .andReturn();
        List<ExerciseFilterDTO> exercises =
                objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                        objectMapper.getTypeFactory().constructCollectionType(List.class, ExerciseFilterDTO.class));
        assertTrue(exercises.stream()
                .allMatch(exercise -> exercise.getExerciseDifficulty().equals(ExerciseDifficulty.EASY)
                        && exercise.getExerciseType().equals(ExerciseType.CHEST)
                        && exercise.getExerciseName().toLowerCase().contains("wy")
                ));
        assertEquals(1, exercises.size());
    }
}