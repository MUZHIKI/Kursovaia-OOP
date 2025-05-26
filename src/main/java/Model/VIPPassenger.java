package Model;

import java.time.LocalDate;

/**
 * Класс для представления VIP-пассажиров с наценкой и дополнительными услугами.
 */
public class VIPPassenger extends Passenger {
    private static final double PREMIUM_RATE = 0.2; // Наценка 20%
    private static final int VIP_CARRIAGE_MIN = 1;   // VIP-вагоны: 1-2
    private static final int VIP_CARRIAGE_MAX = 2;

    private boolean hasLoungeAccess; // Доступ в VIP-зал
    private String specialRequests;  // Особые пожелания

    public VIPPassenger(String firstName,
                        String lastName,
                        LocalDate birthDate,
                        String contactPhone,
                        int carriageNumber,
                        int seatNumber,
                        String trainId,
                        boolean hasLoungeAccess,
                        String specialRequests) {
        super(firstName, lastName, birthDate, contactPhone, carriageNumber, seatNumber, trainId);
        setCarriageNumber(carriageNumber); // Переопределяем валидацию для VIP-вагонов
        this.hasLoungeAccess = hasLoungeAccess;
        this.specialRequests = specialRequests;
    }

    // Переопределяем валидацию номера вагона для VIP
    @Override
    public void setCarriageNumber(int carriageNumber) {
        if (carriageNumber < VIP_CARRIAGE_MIN || carriageNumber > VIP_CARRIAGE_MAX) {
            throw new IllegalArgumentException(
                    "VIP-пассажиры могут быть только в вагонах " + VIP_CARRIAGE_MIN + "-" + VIP_CARRIAGE_MAX
            );
        }
        super.setCarriageNumber(carriageNumber);
    }

    // Расчёт стоимости с наценкой 20%
    @Override
    public double calculateTicketPrice(double basePrice) {
        return basePrice * (1 + PREMIUM_RATE);
    }

    // Геттеры и сеттеры для GUI
    public boolean hasLoungeAccess() {
        return hasLoungeAccess;
    }

    public void setHasLoungeAccess(boolean hasLoungeAccess) {
        this.hasLoungeAccess = hasLoungeAccess;
    }

    public String getSpecialRequests() {
        return specialRequests;
    }

    public void setSpecialRequests(String specialRequests) {
        this.specialRequests = specialRequests;
    }
    @Override
    public String getType() {
        return "VIP";
    }
    @Override
    public String getDetails() {
        String loungeStatus = hasLoungeAccess ? "Доступ в VIP-зал" : "Без доступа в VIP-зал";
        return String.format(
                "[VIP] %s | %s | Запросы: %s",
                super.getDetails(), loungeStatus, specialRequests
        );
    }
}