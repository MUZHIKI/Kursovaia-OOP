package Model;

import java.time.LocalDate;

/**
 * Абстрактный класс для сотрудников железной дороги.
 * Наследует базовые данные от Person и добавляет служебные поля.
 */
public abstract class Employee extends Person {
    private static int nextEmployeeId = 1; // Для генерации ID

    private final String employeeId;  // Уникальный идентификатор
    private LocalDate hireDate;       // Дата приёма на работу
    private String department;        // Отдел/депо

    public Employee(String firstName,
                    String lastName,
                    String middleName,
                    LocalDate birthDate,
                    String contactPhone,
                    LocalDate hireDate,
                    String department) {
        super(firstName, lastName, middleName,birthDate, contactPhone);
        this.employeeId = generateEmployeeId();
        setHireDate(hireDate);
        setDepartment(department);
    }

    // Генерация уникального ID формата "EMP-XXXX"
    private String generateEmployeeId() {
        return String.format("EMP-%04d", nextEmployeeId++);
    }

    // Валидация и сеттеры
    public void setHireDate(LocalDate hireDate) {
        if (hireDate == null || hireDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Некорректная дата приёма на работу");
        }
        this.hireDate = hireDate;
    }

    public void setDepartment(String department) {
        if (department == null || department.trim().isEmpty()) {
            throw new IllegalArgumentException("Отдел не может быть пустым");
        }
        this.department = department.trim();
    }

    // Геттеры
    public String getEmployeeId() {
        return employeeId;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public String getDepartment() {
        return department;
    }

    /**
     * Абстрактный метод для выполнения обязанностей.
     * @return описание выполненного действия
     */
    public abstract String performDuty();

    @Override
    public String getDetails() {
        return String.format(
                "%s | ID: %s | Отдел: %s | Принят: %s",
                super.getDetails(), employeeId, department, hireDate
        );
    }
}
