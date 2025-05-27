package GUI;

import Model.Passenger;
import Model.Ticket;
import GUI.Dialogs.AddPassengerDialog;
import GUI.Dialogs.BookTicketDialog;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class MainWindow extends Application {
    private PassengerTableView passengerTable;
    private TicketTableView ticketTable;
    private ControlPanel controlPanel;

    private ObservableList<Passenger> passengers = FXCollections.observableArrayList();
    private ObservableList<Ticket> tickets = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) {
        initializeComponents();
        setupButtonHandlers();
        setupLayout(primaryStage);
    }

    // Инициализация компонентов интерфейса
    private void initializeComponents() {
        passengerTable = new PassengerTableView(passengers);
        ticketTable = new TicketTableView(tickets);
        controlPanel = new ControlPanel();
    }

    // Настройка обработчиков кнопок
    private void setupButtonHandlers() {
        // Добавление пассажира
        controlPanel.getAddPassengerButton().setOnAction(e -> {
            AddPassengerDialog dialog = new AddPassengerDialog();
            dialog.showAndWait().ifPresent(passenger -> {
                passengers.add(passenger);
                passengerTable.refresh(); // Обновление таблицы пассажиров
                showAlert("Успех", "Добавлен пассажир: " + passenger.getDetails(), Alert.AlertType.INFORMATION);
            });
        });

        // Оформление билета
        controlPanel.getBookTicketButton().setOnAction(e -> {
            BookTicketDialog dialog = new BookTicketDialog(passengers);
            dialog.showAndWait().ifPresent(ticket -> {
                tickets.add(ticket);
                ticketTable.refresh(); // Обновление таблицы билетов
                showAlert("Успех", "Оформлен билет: " + ticket.getTicketId(), Alert.AlertType.INFORMATION);
            });
        });

        // Заглушка для кнопки "Удалить"
        controlPanel.getDeleteButton().setOnAction(e ->
                showAlert("Внимание", "Функция удаления в разработке", Alert.AlertType.WARNING));
    }

    // Настройка макета окна
    private void setupLayout(Stage stage) {
        BorderPane root = new BorderPane();
        HBox topPanel = new HBox(10, controlPanel);

        root.setTop(topPanel);
        root.setCenter(passengerTable);
        root.setBottom(ticketTable);

        Scene scene = new Scene(root, 1200, 700);
        stage.setTitle("Железнодорожная касса");
        stage.setScene(scene);
        stage.show();
    }

    // Утилита для показа сообщений
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Точка входа
    public static void main(String[] args) {
        launch(args);
    }
}