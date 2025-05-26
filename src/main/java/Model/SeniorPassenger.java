package Model;

import java.time.LocalDate;
import java.time.Period;

/**
 * Класс для представления пенсионеров-пассажиров (возраст ≥ 60 лет).
 * Наследует логику скидки 30% от DiscountPassenger.
 */
public class SeniorPassenger extends DiscountPassenger {
    private static final int MIN_SENIOR_AGE = 60;

    public SeniorPassenger(String firstName,
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
                0.3 // 30% скидка
        );
        validateAge(birthDate); // Проверка возраста
    }

    /**
     * Проверяет, что возраст пассажира не меньше 60 лет.
     * @param birthDate дата рождения
     * @throws IllegalArgumentException если возраст некорректен
     */
    private void validateAge(LocalDate birthDate) {
        int age = Period.between(birthDate, LocalDate.now()).getYears();
        if (age < MIN_SENIOR_AGE) {
            throw new IllegalArgumentException(
                    "Пенсионеру должно быть не меньше " + MIN_SENIOR_AGE + " лет"
            );
        }
    }
    @Override
    public String getType() {
        return "Пенсионер";
    }
    @Override
    public String getDetails() {
        return "[Пенсионный] " + super.getDetails();
    }
}