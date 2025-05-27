package Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Абстрактный класс билета. Содержит информацию о поездке:
 * - Поезд
 * - Вагон
 * - Место
 * - Стоимость
 */
public abstract class Ticket {
    protected final String ticketId;       // Уникальный ID
    protected LocalDateTime createdAt;    // Дата создания
    protected String status;              // ACTIVE, USED, CANCELLED
    protected double basePrice;           // Базовая стоимость
    protected Passenger passenger;        // Пассажир
    protected String trainId;             // Идентификатор поезда
    protected int carriageNumber;         // Номер вагона (1-10)
    protected int seatNumber;             // Номер места (1-30)
    protected final StringProperty formattedPrice = new SimpleStringProperty();

    public Ticket(double basePrice,
                  Passenger passenger,
                  String trainId,
                  int carriageNumber,
                  int seatNumber) {
        validateInput(trainId, carriageNumber, seatNumber);

        this.ticketId = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
        this.status = "ACTIVE";
        this.basePrice = basePrice;
        this.passenger = passenger;
        this.trainId = trainId;
        this.carriageNumber = carriageNumber;
        this.seatNumber = seatNumber;
        updateFormattedPrice();
    }

    // Валидация входных данных
    private void validateInput(String trainId, int carriage, int seat) {
        if (trainId == null || trainId.trim().isEmpty()) {
            throw new IllegalArgumentException("Идентификатор поезда не может быть пустым");
        }
        if (carriage < 1 || carriage > 10) {
            throw new IllegalArgumentException("Номер вагона должен быть от 1 до 10");
        }
        if (seat < 1 || seat > 30) {
            throw new IllegalArgumentException("Номер места должен быть от 1 до 30");
        }
    }

    // Обновление отформатированной цены
    protected void updateFormattedPrice() {
        formattedPrice.set(String.format("%.2f руб.", calculateFinalPrice()));
    }

    /**
     * Абстрактный метод для расчёта итоговой стоимости.
     */
    public abstract double calculateFinalPrice();

    // Геттеры
    public String getTicketId() { return ticketId; }
    public String getFormattedPrice() { return formattedPrice.get(); }
    public StringProperty formattedPriceProperty() { return formattedPrice; }
    public String getStatus() { return status; }
    public String getTrainId() { return trainId; }
    public int getCarriageNumber() { return carriageNumber; }
    public int getSeatNumber() { return seatNumber; }

    // Сеттер статуса
    public void setStatus(String status) {
        this.status = status;
        updateFormattedPrice();
    }

    @Override
    public String toString() {
        return String.format(
                "Билет %s | Поезд: %s | Вагон %d, место %d | Стоимость: %s",
                ticketId.substring(0, 8), trainId, carriageNumber, seatNumber, formattedPrice.get()
        );
    }
}