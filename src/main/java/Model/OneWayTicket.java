// OneWayTicket.java
package Model;

import java.time.LocalDate;

public class OneWayTicket extends Ticket {
    private LocalDate departureDate;
    private String seat;

    public OneWayTicket(double basePrice, Passenger passenger, String trainId,
                        LocalDate departureDate, String seat) {
        super(basePrice, passenger, trainId);
        this.departureDate = departureDate;
        this.seat = seat;
        updateFormattedPrice(); // Вызываем после полной инициализации
    }

    @Override
    public double calculateFinalPrice() {
        if (passenger == null) {
            return basePrice;
        }

        double ticketPrice = passenger.calculateTicketPrice(basePrice);

        // Если дата отправления не задана, возвращаем базовую цену
        if (departureDate == null) {
            return ticketPrice;
        }

        // Проверяем, если билет покупается заранее (более чем за 7 дней)
        LocalDate today = LocalDate.now();
        if (departureDate.isAfter(today.plusDays(7))) {
            return ticketPrice * 0.95; // 5% скидка за раннее бронирование
        }

        return ticketPrice;
    }

    // Геттеры
    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public String getSeat() {
        return seat;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
        updateFormattedPrice(); // Обновляем цену при изменении даты
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }
}