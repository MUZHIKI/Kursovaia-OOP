package GUI;

import Model.Ticket;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.time.LocalDate;
import javafx.beans.property.ReadOnlyObjectWrapper;

public class TicketTableView extends TableView<Ticket> {

    public TicketTableView(ObservableList<Ticket> tickets) {
        setupColumns();
        this.setItems(tickets);
        this.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private void setupColumns() {
        // Колонка "ID"
        TableColumn<Ticket, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("ticketId"));

        // Колонка "Стоимость"
        TableColumn<Ticket, Double> priceCol = new TableColumn<>("Стоимость");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        priceCol.setCellFactory(column -> new FormattedPriceCell());

        // Колонка "Статус"
        TableColumn<Ticket, String> statusCol = new TableColumn<>("Статус");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Колонка "Поезд"
        TableColumn<Ticket, String> trainCol = new TableColumn<>("Поезд");
        trainCol.setCellValueFactory(new PropertyValueFactory<>("trainId"));

        // Колонка "Вагон"
        TableColumn<Ticket, Integer> carriageCol = new TableColumn<>("Вагон");
        carriageCol.setCellValueFactory(new PropertyValueFactory<>("carriageNumber"));

        // Колонка "Место"
        TableColumn<Ticket, Integer> seatCol = new TableColumn<>("Место");
        seatCol.setCellValueFactory(new PropertyValueFactory<>("seatNumber"));

        // Колонка "Дата отправления" (универсальная)
        TableColumn<Ticket, LocalDate> departureCol = new TableColumn<>("Дата отправления");
        departureCol.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getDepartureDate())
        );
        departureCol.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setText(empty || date == null ? "" : date.format(java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy")));
            }
        });

        // Добавление колонок
        this.getColumns().addAll(
                idCol,
                priceCol,
                statusCol,
                trainCol,
                carriageCol,
                seatCol,
                departureCol
        );
    }

    // Кастомная ячейка для форматирования цены
    private static class FormattedPriceCell extends TableCell<Ticket, Double> {
        @Override
        protected void updateItem(Double price, boolean empty) {
            super.updateItem(price, empty);
            if (empty || price == null) {
                setText(null);
            } else {
                setText(String.format("%,.2f руб.", price));
            }
        }
    }
}