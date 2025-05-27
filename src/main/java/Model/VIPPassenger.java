// VIPPassenger.java
package Model;

import java.time.LocalDate;

public class VIPPassenger extends Passenger {
    private boolean hasLoungeAccess;
    private String specialRequests;

    public VIPPassenger(String firstName,
                        String lastName,
                        LocalDate birthDate,
                        String contactPhone,
                        boolean hasLoungeAccess,
                        String specialRequests) {
        super(firstName, lastName, birthDate, contactPhone);
        this.hasLoungeAccess = hasLoungeAccess;
        this.specialRequests = specialRequests;
    }

    @Override
    public String getType() {
        return "VIP";
    }

    @Override
    public double calculateTicketPrice(double basePrice) {
        // Наценка 20% + доступ в лаунж
        return basePrice * 1.20;
    }

    @Override
    public String getDetails() {
        return String.format(
                "[VIP] %s | Доступ в лаунж: %s | Запросы: %s",
                super.getDetails(),
                hasLoungeAccess ? "есть" : "нет",
                specialRequests
        );
    }

    // Геттеры
    public boolean hasLoungeAccess() { return hasLoungeAccess; }
    public String getSpecialRequests() { return specialRequests; }
}