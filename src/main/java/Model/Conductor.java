package Model;

import java.time.LocalDate;

/**
 * Класс для представления проводника поезда.
 * Наследует базовые данные от Employee и добавляет функционал, связанный с вагоном.
 */
public class Conductor extends Employee {
    private int assignedCarriage; // Номер закреплённого вагона (1-10)

    public Conductor(String firstName,
                     String lastName,
                     LocalDate birthDate,
                     String contactPhone,
                     LocalDate hireDate,
                     String department,
                     int assignedCarriage) {
        super(firstName, lastName, birthDate, contactPhone, hireDate, department);
        setAssignedCarriage(assignedCarriage); // Валидация номера вагона
    }

    /**
     * Устанавливает номер закреплённого вагона с проверкой диапазона.
     * @param assignedCarriage номер вагона (1-10)
     * @throws IllegalArgumentException если номер вне диапазона
     */
    public void setAssignedCarriage(int assignedCarriage) {
        if (assignedCarriage < 1 || assignedCarriage > 10) {
            throw new IllegalArgumentException("Номер вагона должен быть от 1 до 10");
        }
        this.assignedCarriage = assignedCarriage;
    }

    /**
     * Возвращает описание обязанностей проводника.
     * @return строка с описанием
     */
    @Override
    public String performDuty() {
        return String.format(
                "Проверка билетов в вагоне %d. Оказание помощи пассажирам.",
                assignedCarriage
        );
    }

    /**
     * Проверяет билет пассажира в закреплённом вагоне.
     * @param passenger пассажир
     * @return true, если билет действителен и вагон совпадает
     */
    public boolean checkTicket(Passenger passenger) {
        return passenger.getCarriageNumber() == assignedCarriage
                && passenger.getStatus().equals("ACTIVE");
    }

    // Геттеры
    public int getAssignedCarriage() {
        return assignedCarriage;
    }

    @Override
    public String getDetails() {
        return String.format(
                "[Проводник] %s | Вагон: %d",
                super.getDetails(),
                assignedCarriage
        );
    }
}
