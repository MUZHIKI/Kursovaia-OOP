package Model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public abstract class Ticket {
    protected final UUID ticketId;       // Уникальный ID билета
    protected LocalDateTime createdAt;  // Дата создания
    protected String status;            // ACTIVE, USED, CANCELLED
    protected double basePrice;         // Базовая стоимость
    protected Passenger passenger;      // Пассажир
    protected String trainId;           // Идентификатор поезда

    public Ticket(double basePrice, Passenger passenger, String trainId) {
        this.ticketId = UUID.randomUUID();
        this.createdAt = LocalDateTime.now();
        this.status = "ACTIVE";
        this.basePrice = basePrice;
        this.passenger = passenger;
        this.trainId = trainId;
    }

    // Геттеры
    public UUID getTicketId() { return ticketId; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public String getStatus() { return status; }
    public double getBasePrice() { return basePrice; }
    public Passenger getPassenger() { return passenger; }
    public String getTrainId() { return trainId; }

    // Сеттеры
    public void setStatus(String status) { this.status = status; }
    public void setBasePrice(double basePrice) { this.basePrice = basePrice; }

    /**
     * Абстрактный метод для расчёта итоговой стоимости.
     * @return стоимость с учётом скидок/наценок
     */
    public abstract double calculateFinalPrice();

    @Override
    public String toString() {
        return String.format(
                "Билет %s | Статус: %s | Стоимость: %.2f руб.",
                ticketId.toString().substring(0, 8), status, calculateFinalPrice()
        );
    }
}
