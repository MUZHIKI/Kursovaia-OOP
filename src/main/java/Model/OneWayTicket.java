// OneWayTicket.java
package Model;

import java.time.LocalDate;

public class OneWayTicket extends Ticket {
    private LocalDate departureDate;

    public OneWayTicket(double basePrice, Passenger passenger,
                        String trainId, int carriageNumber, int seatNumber,
                        LocalDate departureDate) {
        super(basePrice, passenger, trainId, carriageNumber, seatNumber);
        this.departureDate = departureDate;
    }

    @Override
    public double calculateFinalPrice() {
        // Логика расчёта скидки
        double discount = departureDate.isAfter(LocalDate.now().plusDays(7)) ? 0.95 : 1.0;
        return passenger.calculateTicketPrice(basePrice) * discount;
    }
}