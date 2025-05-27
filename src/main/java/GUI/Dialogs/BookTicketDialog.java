package GUI.Dialogs;

import Model.*;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import java.time.LocalDate;
import javafx.collections.ObservableList;
import javafx.util.StringConverter;

public class BookTicketDialog extends Dialog<Ticket> {
    private final ComboBox<Passenger> passengerCombo = new ComboBox<>();
    private final ComboBox<String> typeCombo = new ComboBox<>();
    private final TextField priceField = new TextField();
    private final TextField trainIdField = new TextField();
    private final Spinner<Integer> carriageSpinner = new Spinner<>(1, 10, 1);
    private final Spinner<Integer> seatSpinner = new Spinner<>(1, 30, 1);
    private final DatePicker departurePicker = new DatePicker();
    private final DatePicker returnPicker = new DatePicker();
    private final DatePicker seasonStartPicker = new DatePicker(); // Новое поле для сезонного билета
    private final Spinner<Integer> durationSpinner = new Spinner<>(1, 365, 30);

    public BookTicketDialog(ObservableList<Passenger> passengers) {
        setupUI(passengers);
        setupLogic();
        setupValidation();
    }

    private void setupUI(ObservableList<Passenger> passengers) {
        setTitle("Оформление билета");
        setHeaderText("Выберите параметры билета");

        // Настройка ComboBox для пассажиров
        passengerCombo.setItems(passengers);
        passengerCombo.setConverter(new PassengerConverter());
        passengerCombo.setPromptText("Выберите пассажира");

        // Настройка ComboBox для типа билета
        typeCombo.getItems().addAll("Одноразовый", "Туда-обратно", "Сезонный");
        typeCombo.setValue("Одноразовый");

        // Настройка формата полей
        priceField.setPromptText("Введите цену");
        trainIdField.setPromptText("Номер поезда");
        seasonStartPicker.setValue(LocalDate.now());

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        // Общие поля
        grid.add(createRequiredLabel("Пассажир:"), 0, 0);
        grid.add(passengerCombo, 1, 0);
        grid.add(createRequiredLabel("Тип билета:"), 0, 1);
        grid.add(typeCombo, 1, 1);
        grid.add(createRequiredLabel("Цена:"), 0, 2);
        grid.add(priceField, 1, 2);
        grid.add(createRequiredLabel("Поезд:"), 0, 3);
        grid.add(trainIdField, 1, 3);
        grid.add(createRequiredLabel("Вагон:"), 0, 4);
        grid.add(carriageSpinner, 1, 4);
        grid.add(createRequiredLabel("Место:"), 0, 5);
        grid.add(seatSpinner, 1, 5);

        // Динамические поля
        grid.add(createRequiredLabel("Дата отправления:"), 0, 6);
        grid.add(departurePicker, 1, 6);
        grid.add(createRequiredLabel("Дата возвращения:"), 0, 7);
        grid.add(returnPicker, 1, 7);
        grid.add(createRequiredLabel("Дата начала:"), 0, 8);
        grid.add(seasonStartPicker, 1, 8);
        grid.add(createRequiredLabel("Срок действия (дни):"), 0, 9);
        grid.add(durationSpinner, 1, 9);

        toggleDynamicFields("Одноразовый");
        getDialogPane().setContent(grid);
        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
    }

    private Label createRequiredLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-text-fill: black; -fx-font-weight: bold;");
        return label;
    }

    private void setupLogic() {
        typeCombo.valueProperty().addListener((obs, oldVal, newVal) ->
                toggleDynamicFields(newVal)
        );

        setResultConverter(buttonType -> {
            if (buttonType == ButtonType.OK) {
                try {
                    return createTicket();
                } catch (IllegalArgumentException | NullPointerException e) {
                    showError(e.getMessage());
                }
            }
            return null;
        });
    }

    private void setupValidation() {
        departurePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(date.isBefore(LocalDate.now()));
            }
        });

        seasonStartPicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(date.isBefore(LocalDate.now()));
            }
        });
    }

    private void toggleDynamicFields(String ticketType) {
        departurePicker.setVisible(ticketType.equals("Одноразовый") || ticketType.equals("Туда-обратно"));
        returnPicker.setVisible(ticketType.equals("Туда-обратно"));
        seasonStartPicker.setVisible(ticketType.equals("Сезонный"));
        durationSpinner.setVisible(ticketType.equals("Сезонный"));
    }

    private Ticket createTicket() {
        validateCommonFields();

        Passenger passenger = passengerCombo.getValue();
        double price = Double.parseDouble(priceField.getText());
        String trainId = trainIdField.getText().trim();
        int carriage = carriageSpinner.getValue();
        int seat = seatSpinner.getValue();

        return switch (typeCombo.getValue()) {
            case "Одноразовый" -> createOneWayTicket(passenger, price, trainId, carriage, seat);
            case "Туда-обратно" -> createRoundTripTicket(passenger, price, trainId, carriage, seat);
            case "Сезонный" -> createSeasonalTicket(passenger, price, trainId, carriage, seat);
            default -> throw new IllegalArgumentException("Неизвестный тип билета");
        };
    }

    private void validateCommonFields() {
        if (passengerCombo.getValue() == null)
            throw new IllegalArgumentException("Выберите пассажира");
        if (priceField.getText().isEmpty())
            throw new IllegalArgumentException("Укажите цену");
        if (trainIdField.getText().trim().isEmpty())
            throw new IllegalArgumentException("Укажите поезд");

        try {
            Double.parseDouble(priceField.getText());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Некорректный формат цены");
        }

        switch (typeCombo.getValue()) {
            case "Одноразовый":
                validateDate(departurePicker.getValue(), "Дата отправления");
                break;
            case "Туда-обратно":
                validateDate(departurePicker.getValue(), "Дата отправления");
                validateDate(returnPicker.getValue(), "Дата возвращения");
                if (returnPicker.getValue().isBefore(departurePicker.getValue())) {
                    throw new IllegalArgumentException("Дата возвращения не может быть раньше отправления");
                }
                break;
            case "Сезонный":
                validateDate(seasonStartPicker.getValue(), "Дата начала");
                if (durationSpinner.getValue() <= 0) {
                    throw new IllegalArgumentException("Срок действия должен быть больше 0 дней");
                }
                break;
        }
    }

    private void validateDate(LocalDate date, String fieldName) {
        if (date == null)
            throw new IllegalArgumentException("Укажите " + fieldName);
        if (date.isBefore(LocalDate.now()))
            throw new IllegalArgumentException(fieldName + " не может быть в прошлом");
    }

    private OneWayTicket createOneWayTicket(Passenger passenger, double price,
                                            String trainId, int carriage, int seat) {
        return new OneWayTicket(
                price, passenger, trainId, carriage, seat, departurePicker.getValue()
        );
    }

    private RoundTripTicket createRoundTripTicket(Passenger passenger, double price,
                                                  String trainId, int carriage, int seat) {
        return new RoundTripTicket(
                price, passenger, trainId, carriage, seat,
                departurePicker.getValue(), returnPicker.getValue()
        );
    }

    private SeasonalTicket createSeasonalTicket(Passenger passenger, double price,
                                                String trainId, int carriage, int seat) {
        return new SeasonalTicket(
                price, passenger, trainId, carriage, seat,
                seasonStartPicker.getValue(), durationSpinner.getValue()
        );
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private static class PassengerConverter extends StringConverter<Passenger> {
        @Override
        public String toString(Passenger passenger) {
            return passenger != null ?
                    passenger.getLastName() + " " + passenger.getFirstName() + " (" + passenger.getType() + ")" : "";
        }

        @Override
        public Passenger fromString(String string) {
            return null;
        }
    }
}