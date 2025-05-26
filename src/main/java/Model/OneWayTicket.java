package Model;

import java.time.LocalDate;

public class OneWayTicket extends Ticket {
    private LocalDate departureDate; // Дата отправления
    private String seat;             // Место (например, "12A")

    public OneWayTicket(double basePrice,
                        Passenger passenger,
                        String trainId,
                        LocalDate departureDate,
                        String seat) {
        super(basePrice, passenger, trainId);
        this.departureDate = departureDate;
        this.seat = seat;
    }

    @Override
    public double calculateFinalPrice() {
        // Применяем скидку пассажира к базовой цене
        return passenger.calculateTicketPrice(basePrice);
    }

    // Дополнительные геттеры
    public LocalDate getDepartureDate() { return departureDate; }
    public String getSeat() { return seat; }
}