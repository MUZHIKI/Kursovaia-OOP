package Model;

import java.time.LocalDate;

public class RoundTripTicket extends Ticket {
    private final LocalDate departureDate;
    private final LocalDate returnDate;

    public RoundTripTicket(double basePrice, Passenger passenger, String trainId,
                           int carriageNumber, int seatNumber,
                           LocalDate departureDate, LocalDate returnDate) {
        super(basePrice, passenger, trainId, carriageNumber, seatNumber);

        if (departureDate == null || returnDate == null) {
            throw new IllegalArgumentException("[Ошибка] Даты отправления и возвращения обязательны");
        }
        if (returnDate.isBefore(departureDate)) {
            throw new IllegalArgumentException("[Ошибка] Дата возвращения не может быть раньше отправления");
        }

        this.departureDate = departureDate;
        this.returnDate = returnDate;
    }

    @Override
    public double calculateFinalPrice() {
        // Пример: скидка 10% для детей и пенсионеров
        String passengerType = getPassenger().getType();
        if (passengerType.equals("Ребёнок") || passengerType.equals("Пенсионер")) {
            return (getBasePrice() * 0.9)*0.85;
        }
        // Наценка 20% для VIP
        if (passengerType.equals("VIP")) {
            return (getBasePrice() * 1.2)*0.85;
        }
        return getBasePrice();
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }
}