package pl.krzesniak.gymapp.dto.meals;

import com.sun.jna.platform.unix.solaris.LibKstat;
import lombok.Data;
import pl.krzesniak.gymapp.enums.MeasureUnit;

@Data
public class CreateIngredientDetailsDTO {
    private String name;
    private MeasureUnit measureUnit;
    private String amountOverall;
    private Integer preciseAmount ;
}
