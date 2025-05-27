package Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Абстрактный класс билета. Содержит информацию о поездке.
 */
public abstract class Ticket {
    public enum Status { ACTIVE, USED, CANCELLED }

    protected final String ticketId;
    protected final LocalDate createdAt;
    protected Status status;
    protected double basePrice; // Базовая цена
    protected Passenger passenger;
    protected String trainId;
    protected int carriageNumber;
    protected int seatNumber;
    protected final StringProperty formattedPrice = new SimpleStringProperty();

    public Ticket(double basePrice,
                  Passenger passenger,
                  String trainId,
                  int carriageNumber,
                  int seatNumber) {
        validateInput(trainId, carriageNumber, seatNumber);

        this.ticketId = UUID.randomUUID().toString();
        this.createdAt = LocalDate.now();
        this.status = Status.ACTIVE;
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
            throw new IllegalArgumentException("[Ошибка] Идентификатор поезда не может быть пустым");
        }
        if (carriage < 1 || carriage > 10) {
            throw new IllegalArgumentException("[Ошибка] Номер вагона должен быть от 1 до 10 (получено: " + carriage + ")");
        }
        if (seat < 1 || seat > 30) {
            throw new IllegalArgumentException("[Ошибка] Номер места должен быть от 1 до 30 (получено: " + seat + ")");
        }
    }

    // Обновление отформатированной цены
    protected void updateFormattedPrice() {
        formattedPrice.set(String.format("%,.2f руб.", getPrice()));
    }

    /**
     * Возвращает итоговую стоимость билета (после применения скидок).
     */
    public abstract double calculateFinalPrice();

    /**
     * Геттер для свойства "price" (используется в TableView).
     */
    public double getPrice() {
        return calculateFinalPrice();
    }

    // Геттеры
    public String getTicketId() { return ticketId; }
    public LocalDate getCreatedAt() { return createdAt; }
    public Status getStatus() { return status; }
    public String getFormattedPrice() { return formattedPrice.get(); }
    public StringProperty formattedPriceProperty() { return formattedPrice; }
    public String getTrainId() { return trainId; }
    public int getCarriageNumber() { return carriageNumber; }
    public int getSeatNumber() { return seatNumber; }
    public Passenger getPassenger() { return passenger; }
    public double getBasePrice() { return basePrice; } // Геттер для базовой цены

    // Сеттер статуса
    public void setStatus(Status status) {
        this.status = status;
        updateFormattedPrice();
    }

    @Override
    public String toString() {
        return String.format(
                "Билет %s | Статус: %s | Поезд: %s | Вагон %d, место %d | Стоимость: %s",
                ticketId.substring(0, 8), status, trainId, carriageNumber, seatNumber, formattedPrice.get()
        );
    }

    /**
     * Возвращает дату отправления (для отображения в таблице).
     */
    public abstract LocalDate getDepartureDate();
}