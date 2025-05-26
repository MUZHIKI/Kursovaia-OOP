//package GUI.Dialogs;
//
//import Model.Passenger.*;
//import javafx.scene.control.*;
//import javafx.scene.layout.GridPane;
//import java.time.LocalDate;
//import java.util.Optional;
//
//public class AddPassengerDialog extends Dialog<Passenger> {
//    private ComboBox<String> typeComboBox = new ComboBox<>();
//    private TextField firstNameField = new TextField();
//    private TextField lastNameField = new TextField();
//    private DatePicker birthDatePicker = new DatePicker();
//    private TextField phoneField = new TextField();
//    private CheckBox vipLoungeCheck = new CheckBox("Доступ в VIP-зал");
//    private TextArea specialRequestsArea = new TextArea();
//
//    public AddPassengerDialog() {
//        setTitle("Добавить пассажира");
//        buildLayout();
//        setResultConverter();
//    }
//
//    private void buildLayout() {
//        GridPane grid = new GridPane();
//        grid.setHgap(10);
//        grid.setVgap(10);
//
//        // Выбор типа пассажира
//        typeComboBox.getItems().addAll("Обычный", "Ребёнок", "Пенсионер", "VIP");
//        typeComboBox.setValue("Обычный");
//
//        // Поля для всех типов
//        grid.addRow(0, new Label("Тип:"), typeComboBox);
//        grid.addRow(1, new Label("Имя:"), firstNameField);
//        grid.addRow(2, new Label("Фамилия:"), lastNameField);
//        grid.addRow(3, new Label("Дата рождения:"), birthDatePicker);
//        grid.addRow(4, new Label("Телефон:"), phoneField);
//
//        // VIP-поля (скрыты по умолчанию)
//        grid.addRow(5, vipLoungeCheck);
//        grid.addRow(6, new Label("Особые запросы:"), specialRequestsArea);
//        vipLoungeCheck.setVisible(false);
//        specialRequestsArea.setVisible(false);
//
//        // Динамическое отображение VIP-полей
//        typeComboBox.setOnAction(e -> {
//            boolean isVIP = typeComboBox.getValue().equals("VIP");
//            vipLoungeCheck.setVisible(isVIP);
//            specialRequestsArea.setVisible(isVIP);
//        });
//
//        getDialogPane().setContent(grid);
//        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
//    }
//
//    private void setResultConverter() {
//        setResultConverter(buttonType -> {
//            if (buttonType == ButtonType.OK) {
//                return createPassenger();
//            }
//            return null;
//        });
//    }
//
//    private Passenger createPassenger() {
//        String type = typeComboBox.getValue();
//        try {
//            return switch (type) {
//                case "Обычный" -> new RegularPassenger(
//                        firstNameField.getText(),
//                        lastNameField.getText(),
//                        birthDatePicker.getValue(),
//                        phoneField.getText(),
//                        0, 0, "" // Заглушки для параметров вагона/поезда
//                );
//                case "Ребёнок" -> new ChildPassenger(
//                        firstNameField.getText(),
//                        lastNameField.getText(),
//                        birthDatePicker.getValue(),
//                        phoneField.getText(),
//                        0, 0, ""
//                );
//                case "Пенсионер" -> new SeniorPassenger(
//                        firstNameField.getText(),
//                        lastNameField.getText(),
//                        birthDatePicker.getValue(),
//                        phoneField.getText(),
//                        0, 0, ""
//                );
//                case "VIP" -> new VIPPassenger(
//                        firstNameField.getText(),
//                        lastNameField.getText(),
//                        birthDatePicker.getValue(),
//                        phoneField.getText(),
//                        1, // VIP-вагон
//                        0,  // Место (заглушка)
//                        "VIP-TRAIN-001",
//                        vipLoungeCheck.isSelected(),
//                        specialRequestsArea.getText()
//                );
//                default -> throw new IllegalArgumentException("Неизвестный тип");
//            };
//        } catch (IllegalArgumentException e) {
//            showErrorAlert(e.getMessage());
//            return null;
//        }
//    }
//
//    private void showErrorAlert(String message) {
//        Alert alert = new Alert(Alert.AlertType.ERROR);
//        alert.setContentText(message);
//        alert.showAndWait();
//    }
//}