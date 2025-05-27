package Model;

import java.time.LocalDate;

public class SeasonalTicket extends Ticket {
    private LocalDate startDate;
    private LocalDate endDate;
    private int totalTrips;
    private int usedTrips;

    public SeasonalTicket(double basePrice,
                          Passenger passenger,
                          String trainId,
                          int carriageNumber,
                          int seatNumber,
                          LocalDate startDate,
                          LocalDate endDate,
                          int totalTrips) {
        super(basePrice, passenger, trainId, carriageNumber, seatNumber);
        validateSeason(startDate, endDate, totalTrips);
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalTrips = totalTrips;
        this.usedTrips = 0;
    }

    private void validateSeason(LocalDate start, LocalDate end, int trips) {
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Дата начала не может быть позже окончания");
        }
        if (trips <= 0) {
            throw new IllegalArgumentException("Количество поездок должно быть больше 0");
        }
    }

    @Override
    public double calculateFinalPrice() {
        // Скидка 20% за сезонность + 10% за срок >30 дней
        long durationDays = endDate.toEpochDay() - startDate.toEpochDay();
        double durationDiscount = (durationDays > 30) ? 0.9 : 1.0;
        return passenger.calculateTicketPrice(basePrice) * 0.8 * durationDiscount;
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