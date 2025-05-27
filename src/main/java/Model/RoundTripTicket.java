package Model;

import java.time.LocalDate;

public class RoundTripTicket extends Ticket {
    private LocalDate departureDate;
    private LocalDate returnDate;

    public RoundTripTicket(double basePrice,
                           Passenger passenger,
                           String trainId,
                           int carriageNumber,
                           int seatNumber,
                           LocalDate departureDate,
                           LocalDate returnDate) {
        super(basePrice, passenger, trainId, carriageNumber, seatNumber);
        validateDates(departureDate, returnDate);
        this.departureDate = departureDate;
        this.returnDate = returnDate;
    }

    private void validateDates(LocalDate departure, LocalDate returnDate) {
        if (departure == null || returnDate == null) {
            throw new IllegalArgumentException("Даты отправления и возвращения обязательны");
        }
        if (returnDate.isBefore(departure)) {
            throw new IllegalArgumentException("Дата возвращения не может быть раньше отправления");
        }
    }

    @Override
    public double calculateFinalPrice() {
        // Скидка 15% на билеты туда-обратно + скидка пассажира
        return passenger.calculateTicketPrice(basePrice) * 0.85;
    }

    // Геттеры
    public LocalDate getDepartureDate() { return departureDate; }
    public LocalDate getReturnDate() { return returnDate; }
}