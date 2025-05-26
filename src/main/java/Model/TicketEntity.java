import java.time.LocalDateTime;
import java.util.UUID;
/**
 * Абстрактный базовый класс для всех сущностей системы.
 * Содержит общие поля и методы для наследования.
 */
public abstract class TicketEntity {
    protected final UUID id;          // Уникальный идентификатор
    protected LocalDateTime createdAt; // Дата создания
    protected String status;          // Статус сущности

    public TicketEntity() {
        this.id = UUID.randomUUID();
        this.createdAt = LocalDateTime.now();
        this.status = "ACTIVE";
    }

    // Геттеры
    public UUID getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getStatus() {
        return status;
    }

    // Сеттер для статуса
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Абстрактный метод для получения деталей сущности.
     * Должен быть реализован в подклассах.
     */
    public abstract String getDetails();

    @Override
    public String toString() {
        return String.format(
                "[%s] Created: %s | Status: %s",
                id.toString().substring(0, 8),
                createdAt.toLocalDate(),
                status
        );
    }
}