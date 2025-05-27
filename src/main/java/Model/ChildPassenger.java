// ChildPassenger.java
package Model;

import java.time.LocalDate;
import java.time.Period;

public class ChildPassenger extends Passenger {
    private static final int MAX_AGE = 12;

    public ChildPassenger(String firstName,
                          String lastName,
                          String middleName,
                          LocalDate birthDate,
                          String contactPhone) {
        super(firstName, lastName, middleName ,birthDate, contactPhone);
        validateAge(birthDate);
    }

    private void validateAge(LocalDate birthDate) {
        int age = Period.between(birthDate, LocalDate.now()).getYears();
        if (age > MAX_AGE) {
            throw new IllegalArgumentException("Ребёнку должно быть не больше " + MAX_AGE + " лет");
        }
    }

    @Override
    public String getType() {
        return "Ребёнок";
    }

    @Override
    public double calculateTicketPrice(double basePrice) {
        // Скидка 50%
        return basePrice * 0.50;
    }

    @Override
    public String getDetails() {
        return "[Детский] " + super.getDetails();
    }
}