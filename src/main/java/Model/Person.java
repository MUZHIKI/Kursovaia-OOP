package Model;
import java.time.LocalDate;
/**
 * Абстрактный класс, представляющий человека в системе.
 * Наследует базовые поля от TicketEntity и добавляет общие для всех персон характеристики.
 */
public abstract class Person extends TicketEntity {
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String contactPhone;

    public Person(String firstName, String lastName, LocalDate birthDate, String contactPhone) {
        super(); // Вызов конструктора TicketEntity
        setFirstName(firstName);
        setLastName(lastName);
        setBirthDate(birthDate);
        setContactPhone(contactPhone);
    }

    // Валидация и сеттеры
    public void setFirstName(String firstName) {
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("Имя не может быть пустым");
        }
        this.firstName = firstName.trim();
    }

    public void setLastName(String lastName) {
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("Фамилия не может быть пустой");
        }
        this.lastName = lastName.trim();
    }

    public void setBirthDate(LocalDate birthDate) {
        if (birthDate == null || birthDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Некорректная дата рождения");
        }
        this.birthDate = birthDate;
    }

    public void setContactPhone(String phone) {
        // Простая валидация номера
        if (phone == null || !phone.matches("^\\+?[0-9\\s-]{10,}$")) {
            throw new IllegalArgumentException("Некорректный формат телефона");
        }
        this.contactPhone = phone.trim();
    }

    // Геттеры
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public int getAge() {
        return LocalDate.now().getYear() - birthDate.getYear();
    }

    @Override
    public String getDetails() {
        return String.format(
                "%s %s | Возраст: %d | Телефон: %s",
                firstName, lastName, getAge(), contactPhone
        );
    }

    @Override
    public String toString() {
        return super.toString() + " | " + getDetails();
    }
}