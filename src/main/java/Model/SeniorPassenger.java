// SeniorPassenger.java
package Model;

import java.time.LocalDate;
import java.time.Period;

public class SeniorPassenger extends Passenger {
    private static final int MIN_AGE = 60;

    public SeniorPassenger(String firstName,
                           String lastName,
                           String middleName,
                           LocalDate birthDate,
                           String contactPhone) {
        super(firstName, lastName, middleName, birthDate, contactPhone);
        validateAge(birthDate);
    }

    private void validateAge(LocalDate birthDate) {
        int age = Period.between(birthDate, LocalDate.now()).getYears();
        if (age < MIN_AGE) {
            throw new IllegalArgumentException("Пенсионеру должно быть не менее " + MIN_AGE + " лет");
        }
    }

    @Override
    public String getType() {
        return "Пенсионер";
    }

    @Override
    public double calculateTicketPrice(double basePrice) {
        // Скидка 30%
        return basePrice * 0.70;
    }

    @Override
    public String getDetails() {
        return "[Пенсионный] " + super.getDetails();
    }
}