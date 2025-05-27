// SeasonalTicket.java
package Model;

import java.time.LocalDate;

public class SeasonalTicket extends Ticket {
    private LocalDate startDate;
    private LocalDate endDate;
    private int totalTrips;
    private int usedTrips;

    public SeasonalTicket(double basePrice, Passenger passenger, String trainId,
                          LocalDate startDate, LocalDate endDate, int totalTrips) {
        super(basePrice, passenger, trainId);
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalTrips = totalTrips;
        this.usedTrips = 0;
        updateFormattedPrice(); // Вызываем после полной инициализации
    }

    @Override
    public double calculateFinalPrice() {
        if (passenger == null || startDate == null || endDate == null) {
            return basePrice * 0.8; // Базовая скидка 20%
        }

        long durationDays = endDate.toEpochDay() - startDate.toEpochDay();
        double discount = (durationDays > 30) ? 0.9 : 1.0; // Дополнительная скидка для долгосрочных билетов

        return passenger.calculateTicketPrice(basePrice) * 0.8 * discount;
    }

    // Геттеры
    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public int getTotalTrips() {
        return totalTrips;
    }

    public int getUsedTrips() {
        return usedTrips;
    }

    public int getRemainingTrips() {
        return totalTrips - usedTrips;
    }

    // Сеттеры
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
        updateFormattedPrice();
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
        updateFormattedPrice();
    }

    public void setTotalTrips(int totalTrips) {
        this.totalTrips = totalTrips;
        updateFormattedPrice();
    }

    // Метод для использования поездки
    public boolean useTrip() {
        if (usedTrips < totalTrips) {
            usedTrips++;
            return true;
        }
        return false;
    }
}