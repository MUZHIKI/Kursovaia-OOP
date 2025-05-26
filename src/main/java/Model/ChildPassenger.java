package Model;

import java.time.LocalDate;
import java.time.Period;

/**
 * Класс для представления детей-пассажиров (возраст ≤ 12 лет).
 * Наследует логику скидки 50% от DiscountPassenger.
 */
public class ChildPassenger extends DiscountPassenger {
    private static final int MAX_CHILD_AGE = 12;

    public ChildPassenger(String firstName,
                          String lastName,
                          LocalDate birthDate,
                          String contactPhone,
                          int carriageNumber,
                          int seatNumber,
                          String trainId) {
        super(
                firstName,
                lastName,
                birthDate,
                contactPhone,
                carriageNumber,
                seatNumber,
                trainId,
                0.5 // 50% скидка
        );
        validateAge(birthDate); // Проверка возраста
    }
    @Override
    public String getType() {
        return "Ребёнок";
    }
    /**
     * Проверяет, что возраст ребёнка не превышает 12 лет.
     * @param birthDate дата рождения
     * @throws IllegalArgumentException если возраст некорректен
     */
    private void validateAge(LocalDate birthDate) {
        int age = Period.between(birthDate, LocalDate.now()).getYears();
        if (age > MAX_CHILD_AGE) {
            throw new IllegalArgumentException(
                    "Ребёнку должно быть не больше " + MAX_CHILD_AGE + " лет"
            );
        }
    }

    @Override
    public String getDetails() {
        return "[Детский] " + super.getDetails();
    }
}