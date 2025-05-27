package GUI;

import Model.Ticket;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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
        idCol.setPrefWidth(200);

        // Колонка "Стоимость"
        TableColumn<Ticket, String> priceCol = new TableColumn<>("Стоимость");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("formattedPrice"));

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

        // Добавление колонок в таблицу
        this.getColumns().addAll(
                idCol,
                priceCol,
                statusCol,
                trainCol,
                carriageCol,
                seatCol
        );
    }
}