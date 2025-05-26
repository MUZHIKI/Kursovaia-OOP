package Model;

import java.time.LocalDate;

public class SeasonalTicket extends Ticket {
    private LocalDate startDate;
    private LocalDate endDate;
    private int totalTrips;    // Общее количество поездок
    private int usedTrips;     // Использованные поездки

    public SeasonalTicket(double basePrice,
                          Passenger passenger,
                          String trainId,
                          LocalDate startDate,
                          LocalDate endDate,
                          int totalTrips) {
        super(basePrice, passenger, trainId);
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalTrips = totalTrips;
        this.usedTrips = 0;
    }

    @Override
    public double calculateFinalPrice() {
        // Скидка 10% на билеты туда-обратно + скидка пассажира
        return passenger.calculateTicketPrice(basePrice);
    }

    public void useTrip() {
        if (usedTrips < totalTrips) {
            usedTrips++;
        } else {
            setStatus("USED");
        }
    }

    // Геттеры
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
    public int getTotalTrips() { return totalTrips; }
    public int getUsedTrips() { return usedTrips; }
}
