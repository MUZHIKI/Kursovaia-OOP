package Model;

import java.time.LocalDate;

/**
 * Абстрактный класс для льготных пассажиров.
 * Реализует общую логику расчёта скидки.
 */
public abstract class DiscountPassenger extends Passenger {
    protected double discountRate; // Размер скидки (например, 0.5 для 50%)

    public DiscountPassenger(String firstName,
                             String lastName,
                             LocalDate birthDate,
                             String contactPhone,
                             double discountRate) {
        super(firstName, lastName, birthDate, contactPhone);
        setDiscountRate(discountRate);
    }

    /**
     * Устанавливает размер скидки с валидацией.
     * @param discountRate значение от 0.0 до 1.0
     */
    public void setDiscountRate(double discountRate) {
        if (discountRate < 0 || discountRate > 1) {
            throw new IllegalArgumentException("Скидка должна быть в диапазоне [0.0, 1.0]");
        }
        this.discountRate = discountRate;
    }

    /**
     * Расчёт стоимости билета с учётом скидки.
     * @param basePrice базовая цена
     * @return итоговая стоимость
     */
    @Override
    public double calculateTicketPrice(double basePrice) {
        return basePrice * (1 - discountRate);
    }

    /**
     * Абстрактный метод для получения типа пассажира.
     */
    @Override
    public abstract String getType();
}