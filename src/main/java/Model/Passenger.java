package Model;

import java.time.LocalDate;

/**
 * Абстрактный класс пассажира. Содержит только персональные данные.
 */
public abstract class Passenger extends Person {

    public Passenger(String firstName,
                     String lastName,
                     LocalDate birthDate,
                     String contactPhone) {
        super(firstName, lastName, birthDate, contactPhone);
    }

    // Общие методы для всех пассажиров
    public abstract String getType();
    public abstract double calculateTicketPrice(double basePrice);

    @Override
    public String getDetails() {
        return String.format(
                "%s %s | Возраст: %d | Телефон: %s",
                getFirstName(), getLastName(), getAge(), getContactPhone()
        );
    }
}