package Model;
import java.time.LocalDate;

/**
 * Класс для представления пассажира без льгот (стандартный тариф).
 * Наследует базовую логику от Passenger.
 */
public class RegularPassenger extends Passenger {

    public RegularPassenger(String firstName,
                            String lastName,
                            LocalDate birthDate,
                            String contactPhone,
                            int carriageNumber,
                            int seatNumber,
                            String trainId) {
        super(firstName, lastName, birthDate, contactPhone, carriageNumber, seatNumber, trainId);
    }

    @Override
    public String getDetails() {
        return "[Обычный] " + super.getDetails();
    }
    @Override
    public String getType() {
        return "Обычный";
    }
    /**
     * Расчёт стоимости билета с учётом базового тарифа.
     * @param basePrice - базовая цена билета
     * @return итоговая стоимость
     */
    public double calculateTicketPrice(double basePrice) {
        return basePrice; // Без скидок
    }
}