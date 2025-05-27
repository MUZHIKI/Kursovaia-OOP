package Model;

import java.time.LocalDate;

public class RegularPassenger extends Passenger {

    public RegularPassenger(String firstName,
                            String lastName,
                            LocalDate birthDate,
                            String contactPhone) {
        super(firstName, lastName, birthDate, contactPhone);
    }

    @Override
    public String getType() {
        return "Обычный";
    }

    @Override
    public double calculateTicketPrice(double basePrice) {
        return basePrice; // Без скидок
    }

    @Override
    public String getDetails() {
        return "[Обычный] " + super.getDetails();
    }
}