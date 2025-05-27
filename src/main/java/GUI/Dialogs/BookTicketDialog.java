package GUI.Dialogs;

import Model.*;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import java.time.LocalDate;
import javafx.collections.ObservableList;

public class BookTicketDialog extends Dialog<Ticket> {
    private final ComboBox<Passenger> passengerCombo = new ComboBox<>();
    private final ComboBox<String> typeCombo = new ComboBox<>();
    private final TextField priceField = new TextField();
    private final TextField trainIdField = new TextField();
    private final Spinner<Integer> carriageSpinner = new Spinner<>(1, 10, 1);
    private final Spinner<Integer> seatSpinner = new Spinner<>(1, 30, 1);
    private final DatePicker departurePicker = new DatePicker();
    private final DatePicker returnPicker = new DatePicker();
    private final Spinner<Integer> durationSpinner = new Spinner<>(1, 365, 30);

    public BookTicketDialog(ObservableList<Passenger> passengers) {
        setupUI(passengers);
        setupLogic();
    }

    private void setupUI(ObservableList<Passenger> passengers) {
        setTitle("Оформление билета");
        setHeaderText("Выберите параметры билета");

        // Настройка ComboBox для пассажиров
        passengerCombo.setItems(passengers);
        passengerCombo.setConverter(new PassengerConverter());

        // Настройка ComboBox для типа билета
        typeCombo.getItems().addAll("Одноразовый", "Туда-обратно", "Сезонный");
        typeCombo.setValue("Одноразовый");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        // Общие поля
        grid.add(new Label("Пассажир:"), 0, 0);
        grid.add(passengerCombo, 1, 0);
        grid.add(new Label("Тип билета:"), 0, 1);
        grid.add(typeCombo, 1, 1);
        grid.add(new Label("Цена:"), 0, 2);
        grid.add(priceField, 1, 2);
        grid.add(new Label("Поезд:"), 0, 3);
        grid.add(trainIdField, 1, 3);
        grid.add(new Label("Вагон:"), 0, 4);
        grid.add(carriageSpinner, 1, 4);
        grid.add(new Label("Место:"), 0, 5);
        grid.add(seatSpinner, 1, 5);

        // Динамические поля
        grid.add(new Label("Дата отправления:"), 0, 6);
        grid.add(departurePicker, 1, 6);
        grid.add(new Label("Дата возвращения:"), 0, 7);
        grid.add(returnPicker, 1, 7);
        grid.add(new Label("Срок действия (дни):"), 0, 8);
        grid.add(durationSpinner, 1, 8);

        // Скрыть лишние поля по умолчанию
        toggleDynamicFields("Одноразовый");
        getDialogPane().setContent(grid);
        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
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

    private void toggleDynamicFields(String ticketType) {
        departurePicker.setVisible(ticketType.equals("Одноразовый") || ticketType.equals("Туда-обратно"));
        returnPicker.setVisible(ticketType.equals("Туда-обратно"));
        durationSpinner.setVisible(ticketType.equals("Сезонный"));
    }

    private Ticket createTicket() {
        // Проверка обязательных полей
        validateCommonFields();

        // Получение данных
        Passenger passenger = passengerCombo.getValue();
        double price = Double.parseDouble(priceField.getText());
        String trainId = trainIdField.getText().trim();
        int carriage = carriageSpinner.getValue();
        int seat = seatSpinner.getValue();

        // Создание билета
        return switch (typeCombo.getValue()) {
            case "Одноразовый" -> createOneWayTicket(passenger, price, trainId, carriage, seat);
            case "Туда-обратно" -> createRoundTripTicket(passenger, price, trainId, carriage, seat);
            case "Сезонный" -> createSeasonalTicket(passenger, price, trainId, carriage, seat);
            default -> throw new IllegalArgumentException("Неизвестный тип билета");
        };
    }

    private OneWayTicket createOneWayTicket(Passenger passenger, double price,
                                            String trainId, int carriage, int seat) {
        validateDate(departurePicker.getValue());
        return new OneWayTicket(
                price, passenger, trainId, carriage, seat, departurePicker.getValue()
        );
    }

    private RoundTripTicket createRoundTripTicket(Passenger passenger, double price,
                                                  String trainId, int carriage, int seat) {
        validateDate(departurePicker.getValue());
        validateDate(returnPicker.getValue());
        if (returnPicker.getValue().isBefore(departurePicker.getValue())) {
            throw new IllegalArgumentException("Дата возвращения не может быть раньше отправления");
        }
        return new RoundTripTicket(
                price, passenger, trainId, carriage, seat,
                departurePicker.getValue(), returnPicker.getValue()
        );
    }

    private SeasonalTicket createSeasonalTicket(Passenger passenger, double price,
                                                String trainId, int carriage, int seat) {
        int duration = durationSpinner.getValue();
        if (duration <= 0) throw new IllegalArgumentException("Некорректный срок действия");
        return new SeasonalTicket(
                price, passenger, trainId, carriage, seat,
                LocalDate.now(), LocalDate.now().plusDays(duration), duration
        );
    }

    private void validateCommonFields() {
        if (passengerCombo.getValue() == null) throw new IllegalArgumentException("Выберите пассажира");
        if (priceField.getText().isEmpty()) throw new IllegalArgumentException("Укажите цену");
        if (trainIdField.getText().trim().isEmpty()) throw new IllegalArgumentException("Укажите поезд");
        try {
            Double.parseDouble(priceField.getText());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Некорректный формат цены");
        }

        // Валидация для типа билета
        switch (typeCombo.getValue()) {
            case "Туда-обратно":
                validateDate(returnPicker.getValue());
                if (returnPicker.getValue().isBefore(departurePicker.getValue())) {
                    throw new IllegalArgumentException("Дата возвращения не может быть раньше отправления");
                }
                break;
            case "Сезонный":
                if (durationSpinner.getValue() <= 0) {
                    throw new IllegalArgumentException("Некорректный срок действия");
                }
                break;
        }
    }

    private void validateDate(LocalDate date) {
        if (date == null) throw new IllegalArgumentException("Укажите дату");
        if (date.isBefore(LocalDate.now())) throw new IllegalArgumentException("Дата не может быть в прошлом");
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
                    passenger.getLastName() + " " + passenger.getFirstName() + " (" + passenger.getType() + ")" :
                    "";
        }

        @Override
        public Passenger fromString(String string) {
            return null;
        }
    }
}