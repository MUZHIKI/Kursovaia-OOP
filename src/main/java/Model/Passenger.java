package Model;
import java.time.LocalDate;

/**
 * Абстрактный класс пассажира. Учитывает ограничения:
 * - Максимум 10 вагонов (номера 1-10)
 * - Максимум 30 мест в вагоне (номера 1-30)
 */
public abstract class Passenger extends Person {
    private static final int MAX_CARRIAGE = 10;
    private static final int MAX_SEAT = 30;

    private int carriageNumber;  // Номер вагона
    private int seatNumber;      // Номер места
    private String trainId;      // Идентификатор поезда

    public Passenger(String firstName,
                     String lastName,
                     LocalDate birthDate,
                     String contactPhone,
                     int carriageNumber,
                     int seatNumber,
                     String trainId) {
        super(firstName, lastName, birthDate, contactPhone);
        setCarriageNumber(carriageNumber);
        setSeatNumber(seatNumber);
        setTrainId(trainId);
    }

    // Валидация и сеттеры
    public void setCarriageNumber(int carriageNumber) {
        if (carriageNumber < 1 || carriageNumber > MAX_CARRIAGE) {
            throw new IllegalArgumentException(
                    "Номер вагона должен быть от 1 до " + MAX_CARRIAGE
            );
        }
        this.carriageNumber = carriageNumber;
    }

    public void setSeatNumber(int seatNumber) {
        if (seatNumber < 1 || seatNumber > MAX_SEAT) {
            throw new IllegalArgumentException(
                    "Номер места должен быть от 1 до " + MAX_SEAT
            );
        }
        this.seatNumber = seatNumber;
    }

    public void setTrainId(String trainId) {
        if (trainId == null || trainId.trim().isEmpty()) {
            throw new IllegalArgumentException("Идентификатор поезда не может быть пустым");
        }
        this.trainId = trainId.trim();
    }
    public abstract double calculateTicketPrice(double basePrice);
    public abstract String getType();
    // Геттеры
    public int getCarriageNumber() {
        return carriageNumber;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public String getTrainId() {
        return trainId;
    }

    @Override
    public String getDetails() {
        return String.format(
                "%s | Поезд: %s | Вагон %d, место %d",
                super.getDetails(), trainId, carriageNumber, seatNumber
        );
    }

    public String getFullSeatInfo() {
        return String.format("Вагон %d, место %d", carriageNumber, seatNumber);
    }
}