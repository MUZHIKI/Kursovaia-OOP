package GUI;

import Model.Passenger;
import Model.VIPPassenger;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PassengerTableView extends TableView<Passenger> {

    public PassengerTableView(ObservableList<Passenger> passengers) {
        setupColumns();
        this.setItems(passengers);
        this.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private void setupColumns() {
        // Колонка "Имя"
        TableColumn<Passenger, String> firstNameCol = new TableColumn<>("Имя");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        // Колонка "Фамилия"
        TableColumn<Passenger, String> lastNameCol = new TableColumn<>("Фамилия");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        // Колонка "Отчество"
        TableColumn<Passenger, String> middleNameCol = new TableColumn<>("Отчество");
        middleNameCol.setCellValueFactory(new PropertyValueFactory<>("middleName"));

        // Колонка "Дата рождения"
        TableColumn<Passenger, LocalDate> birthDateCol = new TableColumn<>("Дата рождения");
        birthDateCol.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        birthDateCol.setCellFactory(column -> new TableCell<>() {
            private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

            @Override
            protected void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setText(empty || date == null ? "" : date.format(formatter));
            }
        });

        // Колонка "Телефон"
        TableColumn<Passenger, String> phoneCol = new TableColumn<>("Телефон");
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("contactPhone"));

        TableColumn<Passenger, Boolean> vipAccessCol = new TableColumn<>("VIP доступ");
        vipAccessCol.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Boolean isVip, boolean empty) {
                super.updateItem(isVip, empty);
                if (empty || getItem() == null) {
                    setText("");
                } else {
                    Passenger passenger = getTableView().getItems().get(getIndex());
                    if (passenger instanceof VIPPassenger) {
                        boolean access = ((VIPPassenger) passenger).hasLoungeAccess();
                        setText(access ? "Да" : "Нет");
                    } else {
                        setText("Н/Д");
                    }
                }
            }
        });

// Колонка "Пожелания"
        TableColumn<Passenger, String> specialRequestsCol = new TableColumn<>("Пожелания");
        specialRequestsCol.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(String request, boolean empty) {
                super.updateItem(request, empty);
                if (empty || getItem() == null) {
                    setText("");
                } else {
                    Passenger passenger = getTableView().getItems().get(getIndex());
                    if (passenger instanceof VIPPassenger) {
                        String requests = ((VIPPassenger) passenger).getSpecialRequests();
                        setText(requests.isEmpty() ? "-" : requests);
                    } else {
                        setText("-");
                    }
                }
            }
        });

        // Добавление всех колонок
        this.getColumns().addAll(
                firstNameCol,
                lastNameCol,
                middleNameCol,
                birthDateCol,
                phoneCol,
                vipAccessCol,
                specialRequestsCol
        );
    }
}