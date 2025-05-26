package GUI;

import Model.Ticket;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TicketTableView extends TableView<Ticket> {
    public TicketTableView(ObservableList<Ticket> tickets) {
        // Колонка "ID билета"
        TableColumn<Ticket, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("ticketId"));
        idCol.setPrefWidth(150);

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
        trainCol.setPrefWidth(150);

        this.getColumns().addAll(idCol, priceCol, statusCol, trainCol);
        this.setItems(tickets);
        this.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }
}