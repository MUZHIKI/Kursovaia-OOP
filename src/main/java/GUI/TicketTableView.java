package GUI;

import Model.Ticket;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TicketTableView extends TableView<Ticket> {
    public TicketTableView(ObservableList<Ticket> tickets) {
        // Колонка "ID билета" - показываем только первые 8 символов для читаемости
        TableColumn<Ticket, String> idCol = new TableColumn<>("ID билета");
        idCol.setCellValueFactory(new PropertyValueFactory<>("ticketId"));
        idCol.setCellFactory(column -> new javafx.scene.control.TableCell<Ticket, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    // Показываем только первые 8 символов ID для читаемости
                    setText(item.substring(0, Math.min(8, item.length())) + "...");
                }
            }
        });
        idCol.setPrefWidth(100);

        // Колонка "Стоимость"
        TableColumn<Ticket, String> priceCol = new TableColumn<>("Стоимость");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("formattedPrice"));
        priceCol.setPrefWidth(120);

        // Колонка "Статус"
        TableColumn<Ticket, String> statusCol = new TableColumn<>("Статус");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        statusCol.setPrefWidth(100);

        // Колонка "Поезд"
        TableColumn<Ticket, String> trainCol = new TableColumn<>("Поезд");
        trainCol.setCellValueFactory(new PropertyValueFactory<>("trainId"));
        trainCol.setPrefWidth(100);

        // Колонка "Пассажир" - добавим для удобства
        TableColumn<Ticket, String> passengerCol = new TableColumn<>("Пассажир");
        passengerCol.setCellFactory(column -> new javafx.scene.control.TableCell<Ticket, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    Ticket ticket = getTableView().getItems().get(getIndex());
                    if (ticket != null && ticket.getPassenger() != null) {
                        setText(ticket.getPassenger().getFirstName() + " " +
                                ticket.getPassenger().getLastName());
                    }
                }
            }
        });
        passengerCol.setPrefWidth(150);

        // Колонка "Тип билета"
        TableColumn<Ticket, String> typeCol = new TableColumn<>("Тип билета");
        typeCol.setCellFactory(column -> new javafx.scene.control.TableCell<Ticket, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    Ticket ticket = getTableView().getItems().get(getIndex());
                    if (ticket != null) {
                        setText(ticket.getClass().getSimpleName());
                    }
                }
            }
        });
        typeCol.setPrefWidth(120);

        this.getColumns().addAll(idCol, priceCol, statusCol, trainCol, passengerCol, typeCol);
        this.setItems(tickets);
        this.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Настройка высоты строк
        this.setRowFactory(tv -> {
            javafx.scene.control.TableRow<Ticket> row = new javafx.scene.control.TableRow<>();
            row.setPrefHeight(30);
            return row;
        });
    }
}