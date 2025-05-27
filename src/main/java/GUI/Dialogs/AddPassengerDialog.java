package GUI.Dialogs;

import Model.Passenger;
import Model.VIPPassenger;
import Model.ChildPassenger;
import Model.SeniorPassenger;
import Model.RegularPassenger;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import java.time.LocalDate;

public class AddPassengerDialog extends Dialog<Passenger> {
    private final TextField firstNameField = new TextField();
    private final TextField lastNameField = new TextField();
    private final TextField middleNameField = new TextField(); // Новое поле для отчества
    private final DatePicker birthDatePicker = new DatePicker();
    private final TextField phoneField = new TextField();
    private final ComboBox<String> typeCombo = new ComboBox<>();

    // Поля для VIP-пассажира
    private final CheckBox loungeAccessCheck = new CheckBox("Доступ в VIP-зал");
    private final TextArea specialRequestsArea = new TextArea();

    public AddPassengerDialog() {
        setupDialog();
        setupLayout();
        setupResultConverter();
    }

    private void setupDialog() {
        setTitle("Добавить пассажира");
        setHeaderText("Заполните данные пассажира");

        // Настройка ComboBox
        typeCombo.getItems().addAll("Обычный", "Ребёнок", "Пенсионер", "VIP");
        typeCombo.setValue("Обычный");
        typeCombo.setOnAction(e -> toggleVipFields());
    }

    private void setupLayout() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        // Основные поля
        grid.add(new Label("Тип:"), 0, 0);
        grid.add(typeCombo, 1, 0);
        grid.add(new Label("Имя:"), 0, 1);
        grid.add(firstNameField, 1, 1);
        grid.add(new Label("Фамилия:"), 0, 2);
        grid.add(lastNameField, 1, 2);
        grid.add(new Label("Отчество:"), 0, 3); // Добавлено отчество
        grid.add(middleNameField, 1, 3);
        grid.add(new Label("Дата рождения:"), 0, 4);
        grid.add(birthDatePicker, 1, 4);
        grid.add(new Label("Телефон:"), 0, 5);
        grid.add(phoneField, 1, 5);

        // VIP-поля (скрыты по умолчанию)
        grid.add(new Label("VIP доступ:"), 0, 6);
        grid.add(loungeAccessCheck, 1, 6);
        grid.add(new Label("Особые пожелания:"), 0, 7);
        grid.add(specialRequestsArea, 1, 7);

        toggleVipFields(); // Скрыть/показать VIP-поля
        getDialogPane().setContent(grid);
        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
    }

    private void toggleVipFields() {
        boolean isVip = "VIP".equals(typeCombo.getValue());
        loungeAccessCheck.setVisible(isVip);
        specialRequestsArea.setVisible(isVip);
    }

    private void setupResultConverter() {
        setResultConverter(buttonType -> {
            if (buttonType == ButtonType.OK) {
                try {
                    return createPassenger();
                } catch (IllegalArgumentException e) {
                    showError(e.getMessage());
                }
            }
            return null;
        });
    }

    private Passenger createPassenger() {
        // Валидация общих данных
        validateCommonFields();

        // Создание пассажира в зависимости от типа
        return switch (typeCombo.getValue()) {
            case "Ребёнок" -> createChildPassenger();
            case "Пенсионер" -> createSeniorPassenger();
            case "VIP" -> createVipPassenger();
            default -> createRegularPassenger();
        };
    }

    private void validateCommonFields() {
        if (firstNameField.getText().trim().isEmpty())
            throw new IllegalArgumentException("Введите имя");
        if (lastNameField.getText().trim().isEmpty())
            throw new IllegalArgumentException("Введите фамилию");
        if (middleNameField.getText().trim().isEmpty()) // Проверка отчества
            throw new IllegalArgumentException("Введите отчество");
        if (birthDatePicker.getValue() == null)
            throw new IllegalArgumentException("Укажите дату рождения");
        if (phoneField.getText().trim().isEmpty())
            throw new IllegalArgumentException("Введите телефон");
    }

    private ChildPassenger createChildPassenger() {
        return new ChildPassenger(
                firstNameField.getText().trim(),
                lastNameField.getText().trim(),
                middleNameField.getText().trim(), // Передача отчества
                birthDatePicker.getValue(),
                phoneField.getText().trim()
        );
    }

    private SeniorPassenger createSeniorPassenger() {
        return new SeniorPassenger(
                firstNameField.getText().trim(),
                lastNameField.getText().trim(),
                middleNameField.getText().trim(), // Передача отчества
                birthDatePicker.getValue(),
                phoneField.getText().trim()
        );
    }

    private VIPPassenger createVipPassenger() {
        return new VIPPassenger(
                firstNameField.getText().trim(),
                lastNameField.getText().trim(),
                middleNameField.getText().trim(), // Передача отчества
                birthDatePicker.getValue(),
                phoneField.getText().trim(),
                loungeAccessCheck.isSelected(),
                specialRequestsArea.getText().trim()
        );
    }

    private RegularPassenger createRegularPassenger() {
        return new RegularPassenger(
                firstNameField.getText().trim(),
                lastNameField.getText().trim(),
                middleNameField.getText().trim(), // Передача отчества
                birthDatePicker.getValue(),
                phoneField.getText().trim()
        );
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText("Некорректные данные");
        alert.setContentText(message);
        alert.showAndWait();
    }
}