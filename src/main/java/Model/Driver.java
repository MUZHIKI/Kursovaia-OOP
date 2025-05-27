package Model;

import java.time.LocalDate;

/**
 * Класс для представления машиниста поезда.
 * Наследует данные от Employee и добавляет информацию о лицензии и модели поезда.
 */
public class Driver extends Employee {
    private String licenseNumber; // Номер лицензии (формат "DL-XXXXX")
    private String trainModel;    // Модель управляемого поезда

    public Driver(String firstName,
                  String lastName,
                  String middleName,
                  LocalDate birthDate,
                  String contactPhone,
                  LocalDate hireDate,
                  String department,
                  String licenseNumber,
                  String trainModel) {
        super(firstName, lastName, middleName, birthDate, contactPhone, hireDate, department);
        setLicenseNumber(licenseNumber);
        setTrainModel(trainModel);
    }

    /**
     * Устанавливает номер лицензии с проверкой формата.
     * @param licenseNumber номер лицензии (должен соответствовать "DL-XXXXX")
     * @throws IllegalArgumentException если формат некорректен
     */
    public void setLicenseNumber(String licenseNumber) {
        if (licenseNumber == null || !licenseNumber.matches("DL-\\d{5}")) {
            throw new IllegalArgumentException("Некорректный формат лицензии. Пример: DL-12345");
        }
        this.licenseNumber = licenseNumber;
    }

    /**
     * Устанавливает модель поезда.
     * @param trainModel модель поезда (не может быть пустой)
     * @throws IllegalArgumentException если модель не указана
     */
    public void setTrainModel(String trainModel) {
        if (trainModel == null || trainModel.trim().isEmpty()) {
            throw new IllegalArgumentException("Модель поезда не может быть пустой");
        }
        this.trainModel = trainModel.trim();
    }

    /**
     * Возвращает описание обязанностей машиниста.
     * @return строка с описанием
     */
    @Override
    public String performDuty() {
        return String.format("Управление поездом модели '%s'", trainModel);
    }

    // Геттеры
    public String getLicenseNumber() {
        return licenseNumber;
    }

    public String getTrainModel() {
        return trainModel;
    }

    @Override
    public String getDetails() {
        return String.format(
                "[Машинист] %s | Лицензия: %s | Модель поезда: %s",
                super.getDetails(), licenseNumber, trainModel
        );
    }
}
