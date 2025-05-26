package Model;

import java.time.LocalDate;

public class RoundTripTicket extends Ticket {
    private LocalDate departureDate;
    private LocalDate returnDate;
    private String seat;

    public RoundTripTicket(double basePrice,
                           Passenger passenger,
                           String trainId,
                           LocalDate departureDate,
                           LocalDate returnDate,
                           String seat) {
        super(basePrice, passenger, trainId);
        this.departureDate = departureDate;
        this.returnDate = returnDate;
        this.seat = seat;
    }

    @Override
    public double calculateFinalPrice() {
        // Скидка 10% на билеты туда-обратно + скидка пассажира
        return passenger.calculateTicketPrice(basePrice);
    }

    // Геттеры
    public LocalDate getDepartureDate() { return departureDate; }
    public LocalDate getReturnDate() { return returnDate; }
    public String getSeat() { return seat; }
}
