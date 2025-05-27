package Model;

import java.time.LocalDate;

public class SeasonalTicket extends Ticket {
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final int durationDays;

    public SeasonalTicket(double basePrice, Passenger passenger, String trainId,
                          int carriageNumber, int seatNumber,
                          LocalDate startDate, int durationDays) {
        super(basePrice, passenger, trainId, carriageNumber, seatNumber);

        if (startDate == null) {
            throw new IllegalArgumentException("[Ошибка] Дата начала не может быть null");
        }
        if (durationDays <= 0) {
            throw new IllegalArgumentException("[Ошибка] Срок действия должен быть больше 0 дней");
        }

        this.startDate = startDate;
        this.durationDays = durationDays;
        this.endDate = startDate.plusDays(durationDays);
    }

    @Override
    public double calculateFinalPrice() {
        // Пример: скидка 20% для сезонных билетов
        return basePrice * 0.8;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public int getDurationDays() {
        return durationDays;
    }

    public LocalDate getDepartureDate() {
        return startDate; // Для совместимости с таблицей
    }
}