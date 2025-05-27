// Ticket.java
package Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.time.LocalDateTime;
import java.util.UUID;

public abstract class Ticket {
    protected final String ticketId;       // Строковый UUID
    protected LocalDateTime createdAt;
    protected String status;
    protected double basePrice;
    protected Passenger passenger;
    protected String trainId;
    protected final StringProperty formattedPrice = new SimpleStringProperty();

    public Ticket(double basePrice, Passenger passenger, String trainId) {
        this.ticketId = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
        this.status = "ACTIVE";
        this.basePrice = basePrice;
        this.passenger = passenger;
        this.trainId = trainId;
        // Не вызываем updateFormattedPrice() в конструкторе базового класса
        // Это будет сделано в конструкторах наследников после полной инициализации
    }

    protected void updateFormattedPrice() {
        double finalPrice = calculateFinalPrice();
        formattedPrice.set(String.format("%.2f руб.", finalPrice));
    }

    // Абстрактный метод для реализации в подклассах
    public abstract double calculateFinalPrice();

    // Геттеры
    public String getTicketId() { return ticketId; }

    // Этот метод нужен для PropertyValueFactory
    public String getFormattedPrice() {
        return formattedPrice.get();
    }

    // Этот метод для привязки данных в JavaFX
    public StringProperty formattedPriceProperty() {
        return formattedPrice;
    }

    public String getStatus() { return status; }
    public String getTrainId() { return trainId; }
    public Passenger getPassenger() { return passenger; }
    public double getBasePrice() { return basePrice; }

    public void setStatus(String status) {
        this.status = status;
        updateFormattedPrice(); // Обновление при изменении статуса
    }
}