package vn.ptit.project.epl_web.util;

import java.time.LocalDate;
import java.time.Period;

public class AgeUtil {
    public static int calculateAge(LocalDate dob) {

        LocalDate currentDate = LocalDate.now();

        // Calculate the period between the two dates
        Period period = Period.between(dob, currentDate);
        return period.getYears(); // Get the number of years
    }
}
