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
        // Пример: скидка 10% для детей и пенсионеров
        String passengerType = getPassenger().getType();
        if (passengerType.equals("Ребёнок") || passengerType.equals("Пенсионер")) {
            return getBasePrice() * 0.9;
        }
        // Наценка 20% для VIP
        if (passengerType.equals("VIP")) {
            return getBasePrice() * 1.2;
        }
        return getBasePrice();
    }

    // Для отображения в таблице
    public LocalDate getDepartureDate() {
        return departureDate;
    }
}