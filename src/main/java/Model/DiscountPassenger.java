package Model;
import java.time.LocalDate;

/**
 * Абстрактный класс для льготных пассажиров. 
 * Добавляет общую логику для расчёта скидок.
 */
public abstract class DiscountPassenger extends Passenger {
    protected double discountRate; // Размер скидки (например, 0.5 для 50%)

    public DiscountPassenger(String firstName,
                             String lastName,
                             LocalDate birthDate,
                             String contactPhone,
                             int carriageNumber,
                             int seatNumber,
                             String trainId,
                             double discountRate) {
        super(firstName, lastName, birthDate, contactPhone, carriageNumber, seatNumber, trainId);
        setDiscountRate(discountRate);
    }

    // Валидация скидки
    public void setDiscountRate(double discountRate) {
        if (discountRate < 0 || discountRate > 1) {
            throw new IllegalArgumentException("Скидка должна быть в диапазоне [0.0, 1.0]");
        }
        this.discountRate = discountRate;
    }

    @Override
    public String getDetails() {
        return String.format(
                "[Льготный] %s | Скидка: %.0f%%",
                super.getDetails(),
                discountRate * 100
        );
    }

    /**
     * Расчёт стоимости билета с учётом скидки.
     * @param basePrice - базовая цена билета
     * @return итоговая стоимость
     */
    @Override
    public double calculateTicketPrice(double basePrice) {
        return basePrice * (1 - discountRate);
    }
}