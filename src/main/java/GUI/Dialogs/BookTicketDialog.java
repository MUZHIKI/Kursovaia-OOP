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
    private final DatePicker departurePicker = new DatePicker();
    private final DatePicker returnPicker = new DatePicker();
    private final TextField seatField = new TextField();
    private final Spinner<Integer> durationSpinner = new Spinner<>(1, 365, 30);

    public BookTicketDialog(ObservableList<Passenger> passengers) {
        setupUI(passengers);
        setupLogic();
    }

    private void setupUI(ObservableList<Passenger> passengers) {
        setTitle("Оформление билета");
        setHeaderText("Выберите параметры билета");

        passengerCombo.setItems(passengers);
        passengerCombo.setConverter(new PassengerConverter());

        typeCombo.getItems().addAll("Одноразовый", "Туда-обратно", "Сезонный");
        typeCombo.setValue("Одноразовый");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        grid.add(new Label("Пассажир:"), 0, 0);
        grid.add(passengerCombo, 1, 0);
        grid.add(new Label("Тип билета:"), 0, 1);
        grid.add(typeCombo, 1, 1);
        grid.add(new Label("Базовая цена:"), 0, 2);
        grid.add(priceField, 1, 2);
        grid.add(new Label("Дата отправления:"), 0, 3);
        grid.add(departurePicker, 1, 3);
        grid.add(new Label("Место:"), 0, 4);
        grid.add(seatField, 1, 4);
        grid.add(new Label("Дата возвращения:"), 0, 5);
        grid.add(returnPicker, 1, 5);
        grid.add(new Label("Срок действия (дни):"), 0, 6);
        grid.add(durationSpinner, 1, 6);

        getDialogPane().setContent(grid);
        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
    }

    private void setupLogic() {
        typeCombo.valueProperty().addListener((obs, oldVal, newVal) -> {
            departurePicker.setVisible(newVal.equals("Одноразовый") || newVal.equals("Туда-обратно"));
            seatField.setVisible(!newVal.equals("Сезонный"));
            returnPicker.setVisible(newVal.equals("Туда-обратно"));
            durationSpinner.setVisible(newVal.equals("Сезонный"));
        });

        setResultConverter(buttonType -> {
            if (buttonType == ButtonType.OK) {
                try {
                    return createTicket();
                } catch (IllegalArgumentException e) {
                    showError(e.getMessage());
                }
            }
            return null;
        });
    }

    private Ticket createTicket() {
        Passenger passenger = passengerCombo.getValue();
        double basePrice = Double.parseDouble(priceField.getText());
        String type = typeCombo.getValue();

        if (passenger == null) throw new IllegalArgumentException("Выберите пассажира");
        if (basePrice <= 0) throw new IllegalArgumentException("Цена должна быть больше 0");

        switch (type) {
            case "Одноразовый":
                validateDate(departurePicker.getValue());
                validateSeat(seatField.getText());
                return new OneWayTicket(
                        basePrice,
                        passenger,
                        passenger.getTrainId(),
                        departurePicker.getValue(),
                        seatField.getText()
                );
            case "Туда-обратно":
                validateDate(departurePicker.getValue());
                validateDate(returnPicker.getValue());
                validateSeat(seatField.getText());
                return new RoundTripTicket(
                        basePrice,
                        passenger,
                        passenger.getTrainId(),
                        departurePicker.getValue(),
                        returnPicker.getValue(),
                        seatField.getText()
                );
            case "Сезонный":
                return new SeasonalTicket(
                        basePrice,
                        passenger,
                        passenger.getTrainId(),
                        LocalDate.now(),
                        LocalDate.now().plusDays(durationSpinner.getValue()),
                        durationSpinner.getValue()
                );
            default:
                throw new IllegalArgumentException("Неизвестный тип билета");
        }
    }

    private void validateDate(LocalDate date) {
        if (date == null) throw new IllegalArgumentException("Укажите дату");
        if (date.isBefore(LocalDate.now())) throw new IllegalArgumentException("Дата не может быть в прошлом");
    }

    private void validateSeat(String seat) {
        if (!seat.matches("\\d{1,2}[A-Z]")) throw new IllegalArgumentException("Формат места: 12A");
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
            return null; // Не требуется для ComboBox
        }
    }
}