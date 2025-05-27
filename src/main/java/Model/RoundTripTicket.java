// RoundTripTicket.java
package Model;

import java.time.LocalDate;

public class RoundTripTicket extends Ticket {
    private LocalDate departureDate;
    private LocalDate returnDate;
    private String seat;

    public RoundTripTicket(double basePrice, Passenger passenger, String trainId,
                           LocalDate departureDate, LocalDate returnDate, String seat) {
        super(basePrice, passenger, trainId);
        this.departureDate = departureDate;
        this.returnDate = returnDate;
        this.seat = seat;
        updateFormattedPrice(); // Вызываем после полной инициализации
    }

    @Override
    public double calculateFinalPrice() {
        if (passenger == null) {
            return basePrice * 1.85; // Билет туда-обратно со скидкой 15%
        }

        return passenger.calculateTicketPrice(basePrice) * 1.85; // 15% скидка от двойной цены
    }

    // Геттеры
    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public String getSeat() {
        return seat;
    }

    // Сеттеры
    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
        updateFormattedPrice();
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
        updateFormattedPrice();
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }
}