package GUI.Dialogs;

import Model.*;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import java.time.LocalDate;

public class AddPassengerDialog extends Dialog<Passenger> {
    private final GridPane grid = new GridPane();
    private final TextField firstNameField = new TextField();
    private final TextField lastNameField = new TextField();
    private final DatePicker birthDatePicker = new DatePicker();
    private final TextField phoneField = new TextField();
    private final Spinner<Integer> carriageSpinner = new Spinner<>(1, 10, 1);
    private final Spinner<Integer> seatSpinner = new Spinner<>(1, 30, 1);
    private final TextField trainIdField = new TextField();
    private final ComboBox<String> typeComboBox = new ComboBox<>();

    // VIP-поля
    private final CheckBox loungeAccessCheck = new CheckBox("Доступ в VIP-зал");
    private final TextArea specialRequestsArea = new TextArea();

    public AddPassengerDialog() {
        setupDialog();
        setupTypeDependentUI();
        setupResultConverter();
    }

    private void setupDialog() {
        this.setTitle("Добавить пассажира");
        this.setHeaderText("Заполните данные пассажира");

        typeComboBox.getItems().addAll("Обычный", "Ребёнок", "Пенсионер", "VIP");
        typeComboBox.setValue("Обычный");
        typeComboBox.setOnAction(e -> toggleVipFields());

        grid.setHgap(10);
        grid.setVgap(10);
        buildBaseForm();
        this.getDialogPane().setContent(grid);
        this.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
    }

    private void buildBaseForm() {
        grid.add(new Label("Тип:"), 0, 0);
        grid.add(typeComboBox, 1, 0);
        grid.add(new Label("Имя:"), 0, 1);
        grid.add(firstNameField, 1, 1);
        grid.add(new Label("Фамилия:"), 0, 2);
        grid.add(lastNameField, 1, 2);
        grid.add(new Label("Дата рождения:"), 0, 3);
        grid.add(birthDatePicker, 1, 3);
        grid.add(new Label("Телефон:"), 0, 4);
        grid.add(phoneField, 1, 4);
        grid.add(new Label("Вагон:"), 0, 5);
        grid.add(carriageSpinner, 1, 5);
        grid.add(new Label("Место:"), 0, 6);
        grid.add(seatSpinner, 1, 6);
        grid.add(new Label("Поезд:"), 0, 7);
        grid.add(trainIdField, 1, 7);
    }

    private void setupTypeDependentUI() {
        // VIP-поля (изначально скрыты)
        grid.add(new Label("VIP доступ:"), 0, 8);
        grid.add(loungeAccessCheck, 1, 8);
        grid.add(new Label("Особые пожелания:"), 0, 9);
        grid.add(specialRequestsArea, 1, 9);
        toggleVipFields();
    }

    private void toggleVipFields() {
        boolean isVip = "VIP".equals(typeComboBox.getValue());
        loungeAccessCheck.setVisible(isVip);
        specialRequestsArea.setVisible(isVip);
        grid.getChildren().forEach(node -> {
            Integer row = GridPane.getRowIndex(node);
            if (row != null && row >= 8) {
                node.setVisible(isVip);
                node.setManaged(isVip);
            }
        });
    }

    private void setupResultConverter() {
        this.setResultConverter(buttonType -> {
            if (buttonType == ButtonType.OK) {
                try {
                    return createPassenger();
                } catch (IllegalArgumentException e) {
                    showError(e.getMessage());
                    return null;
                }
            }
            return null;
        });
    }

    private Passenger createPassenger() {
        String type = typeComboBox.getValue();
        return switch (type) {
            case "Ребёнок" -> createChildPassenger();
            case "Пенсионер" -> createSeniorPassenger();
            case "VIP" -> createVipPassenger();
            default -> createRegularPassenger();
        };
    }

    private ChildPassenger createChildPassenger() {
        return new ChildPassenger(
                firstNameField.getText(),
                lastNameField.getText(),
                birthDatePicker.getValue(),
                phoneField.getText(),
                carriageSpinner.getValue(),
                seatSpinner.getValue(),
                trainIdField.getText()
        );
    }

    private SeniorPassenger createSeniorPassenger() {
        return new SeniorPassenger(
                firstNameField.getText(),
                lastNameField.getText(),
                birthDatePicker.getValue(),
                phoneField.getText(),
                carriageSpinner.getValue(),
                seatSpinner.getValue(),
                trainIdField.getText()
        );
    }

    private VIPPassenger createVipPassenger() {
        return new VIPPassenger(
                firstNameField.getText(),
                lastNameField.getText(),
                birthDatePicker.getValue(),
                phoneField.getText(),
                carriageSpinner.getValue(),
                seatSpinner.getValue(),
                trainIdField.getText(),
                loungeAccessCheck.isSelected(),
                specialRequestsArea.getText()
        );
    }

    private RegularPassenger createRegularPassenger() {
        return new RegularPassenger(
                firstNameField.getText(),
                lastNameField.getText(),
                birthDatePicker.getValue(),
                phoneField.getText(),
                carriageSpinner.getValue(),
                seatSpinner.getValue(),
                trainIdField.getText()
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