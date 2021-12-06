package pl.krzesniak.gymapp.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.krzesniak.gymapp.dto.MonthRecordExerciseDTO;
import pl.krzesniak.gymapp.dto.RecordExercisesDTO;
import pl.krzesniak.gymapp.dto.records.MaximumRecordsDTO;
import pl.krzesniak.gymapp.entities.ExerciseInTraining;
import pl.krzesniak.gymapp.entities.Training;
import pl.krzesniak.gymapp.mappers.RecordMapper;
import pl.krzesniak.gymapp.record.RecordMaximumFinder;
import pl.krzesniak.gymapp.repositories.ExercisesInTrainingRepository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.Month;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecordService {

    private final ExercisesInTrainingRepository exercisesInTrainingRepository;
    private final RecordMapper recordMapper;
    private final RecordMaximumFinder recordMaximumFinder;

    @Transactional
    public List<RecordExercisesDTO> getExercisesRecordByUsername(String username, String sortBy) {

        Comparator<ExerciseInTraining> comparator = getComparator(sortBy);
        List<ExerciseInTraining> exercise = exercisesInTrainingRepository.findByUsername(username);
        Map<String, Optional<ExerciseInTraining>> exerciseMap = exercise.stream()
                .collect(Collectors.groupingBy(element -> element.getExercise().getName(),
                        Collectors.maxBy(comparator)));
        List<RecordExercisesDTO> result = recordMapper.mapToRecordExerciseDTO(exerciseMap);
        result.sort(Comparator.comparing(RecordExercisesDTO::getExerciseName));
        for(int i=0; i<result.size(); i++){
            result.get(i).setPosition(i+1);
        }
       return result;
    }
    private Comparator<ExerciseInTraining> getComparator(String sortBy){
        switch (sortBy){
            case "weight": return Comparator.comparing(ExerciseInTraining::getWeight);
            case "repetitions": return Comparator.comparing(ExerciseInTraining::getRepetitions);
            case "duration": return Comparator.comparing(ExerciseInTraining::getDuration);
        }
        return Comparator.comparing(ExerciseInTraining::getWeight);
    }

    @Transactional
    public MonthRecordExerciseDTO getMonthExerciseRecord(String name, LocalDate date) {
        var start = date.withDayOfMonth(1).atTime(0,0);
        var end = date.withDayOfMonth(date.lengthOfMonth()).atTime(23,59);
        List<ExerciseInTraining> exercises = exercisesInTrainingRepository.findByUsernameAndDate(name, start, end);
        return MonthRecordExerciseDTO.builder()
                .exerciseName(recordMaximumFinder.getTheMostOftenExercise(exercises))
                .seriesCounter(recordMaximumFinder.getMaximumSeriesNumberInTraining(exercises))
                .longestTrainingTime(recordMaximumFinder.getMaximumDurationOfTraining(exercises))
                .exercisesCounter(recordMaximumFinder.getMaximumNumberOfExercisesInTraining(exercises))
                .build();
    }
}
