/**
 * Check how many dining dollars on average you should have at a certain point.
 * Removes Thanksgiving, Winter Break, and Spring Break from calculations.
 * Assumes you are in school for half of finals week (arbitrary average)
 * Includes weekends
 * 
 * Based off 2023-2024 academic calendar
 */
import java.util.*;
import java.time.*;
import java.time.temporal.ChronoUnit;

public class DiningDollarCalculator{
    private static LocalDate STARTING_FALL = LocalDate.of(2023, 9, 28);
    private static LocalDate ENDING_FALL = LocalDate.of(2023, 12, 12);
    
    private static LocalDate STARTING_WINTER = LocalDate.of(2024, 1, 8);
    private static LocalDate ENDING_WINTER = LocalDate.of(2024, 3, 20);

    private static LocalDate STARTING_SPRING = LocalDate.of(2024, 4, 1);
    private static LocalDate ENDING_SPRING = LocalDate.of(2024, 6, 11);

    private static LocalDate STARTING_THANKS = LocalDate.of(2023, 11, 23);
    private static LocalDate ENDING_THANKS = LocalDate.of(2023, 11, 26);

    private static final long NUM_DAYS = 
        ChronoUnit.DAYS.between(STARTING_FALL,ENDING_FALL) - 
        ChronoUnit.DAYS.between(STARTING_THANKS,ENDING_THANKS) +
        ChronoUnit.DAYS.between(STARTING_WINTER,ENDING_WINTER) + 
        ChronoUnit.DAYS.between(STARTING_SPRING,ENDING_SPRING);

    LocalDate[] starts = {STARTING_FALL, STARTING_WINTER, STARTING_SPRING};
    LocalDate[] ends = {ENDING_FALL, ENDING_WINTER, ENDING_SPRING};
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("How much money was your dining plan? (No dollar sign):");

        double money = sc.nextDouble();
        double averagePerDay = money / NUM_DAYS;
        long days = calculateNumDays(LocalDate.now());
        double remainderLeft = money - averagePerDay*days;

        System.out.printf("You should have ~~$%.2f, with an average of $%.2f spent per day.", 
            remainderLeft, averagePerDay); 
        
        sc.close();
    }

    public static long calculateNumDays(LocalDate now){
        if (now.isAfter(ENDING_SPRING)){ //school year been over
            return NUM_DAYS;
        }
        else if (now.isAfter(ENDING_WINTER)){ //during spring quarter
            long days = ChronoUnit.DAYS.between(STARTING_FALL,ENDING_FALL) - 
            ChronoUnit.DAYS.between(STARTING_THANKS,ENDING_THANKS) +
            ChronoUnit.DAYS.between(STARTING_WINTER,ENDING_WINTER);

            if (ChronoUnit.DAYS.between(STARTING_SPRING, now) < 0){ //if during spring break, dont add spring break days
                return days;
            }
            return days + ChronoUnit.DAYS.between(STARTING_SPRING, now); 

        }
        else if (now.isAfter(ENDING_FALL)){ //during winter quarter
            long days = ChronoUnit.DAYS.between(STARTING_FALL,ENDING_FALL) - 
            ChronoUnit.DAYS.between(STARTING_THANKS,ENDING_THANKS);

            if (ChronoUnit.DAYS.between(STARTING_WINTER, now) < 0){ //if during winter break, dont add winter break days
                return days;
            }
            return days + ChronoUnit.DAYS.between(STARTING_WINTER, now);
        }
        else if (now.isBefore(STARTING_FALL)){ //school year hasnt started 
            return 0;
        }
        else { //during fall quarter
            long days = ChronoUnit.DAYS.between(STARTING_FALL, now);
            if (now.isAfter(ENDING_THANKS)){ //subtract thanksgiving days if passed
                days -= ChronoUnit.DAYS.between(STARTING_THANKS, ENDING_THANKS);
            }
            if (now.isBefore(ENDING_THANKS) && now.isAfter(STARTING_THANKS)){ //if during thanksgiving, dont add break days
                days -= ChronoUnit.DAYS.between(STARTING_THANKS, now);
            }
            return days;
        }
        
    }
}