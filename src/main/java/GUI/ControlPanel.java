package GUI;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class ControlPanel extends HBox {
    private Button addPassengerButton;
    private Button bookTicketButton;
    private Button deleteButton;

    public ControlPanel() {
        addPassengerButton = new Button("Добавить пассажира");
        bookTicketButton = new Button("Оформить билет");
        deleteButton = new Button("Удалить");

        this.setSpacing(10);
        this.getChildren().addAll(addPassengerButton, bookTicketButton, deleteButton);
    }

    // Геттеры для кнопок
    public Button getAddPassengerButton() { return addPassengerButton; }
    public Button getBookTicketButton() { return bookTicketButton; }
    public Button getDeleteButton() { return deleteButton; }
}