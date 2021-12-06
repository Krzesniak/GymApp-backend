package pl.krzesniak.gymapp.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Diet  extends  AbstractDefaultEntity{

    private LocalDate dietDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "breakfast_id")
    private Meal breakfast;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "second_breakfast_id")
    private Meal secondBreakfast;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "main_course_id")
    private Meal mainCourse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dinner_id")
    private Meal dinner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "snacks_id")
    private Meal snacks;

    public Diet(LocalDate date, User user){
        this.dietDate = date;
        this.user = user;
    }
}
