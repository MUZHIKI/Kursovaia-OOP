package Model;

import java.time.LocalDate;

public class OneWayTicket extends Ticket {
    private final LocalDate departureDate;

    public OneWayTicket(double basePrice, Passenger passenger, String trainId,
                        int carriageNumber, int seatNumber, LocalDate departureDate) {
        super(basePrice, passenger, trainId, carriageNumber, seatNumber);

        if (departureDate == null) {
            throw new IllegalArgumentException("[Ошибка] Дата отправления не может быть null");
        }
        if (departureDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("[Ошибка] Дата отправления не может быть в прошлом");
        }

        this.departureDate = departureDate;
    }

    @Override
    public double calculateFinalPrice() {
        // Пример: скидка 10% для пенсионеров
        if (getPassenger().getType().equals("Пенсионер")) {
            return basePrice * 0.9;
        }
        return basePrice;
    }

    // Для отображения в таблице
    public LocalDate getDepartureDate() {
        return departureDate;
    }
}