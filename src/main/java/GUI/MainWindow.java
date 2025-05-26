package GUI;

import Model.Passenger;
import Model.Ticket;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
        // Инициализация компонентов
        passengerTable = new PassengerTableView(passengers);
        ticketTable = new TicketTableView(tickets);
        controlPanel = new ControlPanel();

        // Настройка обработчиков кнопок
        controlPanel.getAddPassengerButton().setOnAction(e -> System.out.println("Добавить пассажира"));
        controlPanel.getBookTicketButton().setOnAction(e -> System.out.println("Оформить билет"));
        controlPanel.getDeleteButton().setOnAction(e -> System.out.println("Удалить"));

        // Разметка
        BorderPane root = new BorderPane();
        HBox topPanel = new HBox(10, controlPanel);
        root.setTop(topPanel);
        root.setCenter(passengerTable);
        root.setBottom(ticketTable);

        Scene scene = new Scene(root, 1000, 600);
        primaryStage.setTitle("Ж/Д Касса");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}