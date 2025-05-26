package Model;

import java.time.LocalDate;

/**
 * Класс для представления кассира, отвечающего за продажу билетов.
 * Наследует данные от Employee и добавляет функционал учёта продаж.
 */
public class TicketAgent extends Employee {
    private int ticketsSold; // Количество проданных билетов

    public TicketAgent(String firstName,
                       String lastName,
                       LocalDate birthDate,
                       String contactPhone,
                       LocalDate hireDate,
                       String department) {
        super(firstName, lastName, birthDate, contactPhone, hireDate, department);
        this.ticketsSold = 0; // Начальное значение
    }

    /**
     * Увеличивает счётчик проданных билетов.
     */
    public void sellTicket() {
        ticketsSold++;
    }

    /**
     * Возвращает описание обязанностей кассира.
     * @return строка с описанием
     */
    @Override
    public String performDuty() {
        return "Продажа и оформление билетов";
    }

    /**
     * Возвращает детализированную информацию о кассире.
     * @return строка с ID, отделом, датой приёма и продажами
     */
    @Override
    public String getDetails() {
        return String.format(
                "[Кассир] %s | Продано билетов: %d",
                super.getDetails(),
                ticketsSold
        );
    }

    // Геттер для GUI
    public int getTicketsSold() {
        return ticketsSold;
    }
}
