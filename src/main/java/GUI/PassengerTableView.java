package GUI;

import Model.Passenger;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class PassengerTableView extends TableView<Passenger> {
    public PassengerTableView(ObservableList<Passenger> passengers) {
        // Колонка "Имя"
        TableColumn<Passenger, String> firstNameCol = new TableColumn<>("Имя");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        // Колонка "Фамилия"
        TableColumn<Passenger, String> lastNameCol = new TableColumn<>("Фамилия");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        // Колонка "Тип"
        TableColumn<Passenger, String> typeCol = new TableColumn<>("Тип");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));

        this.getColumns().addAll(firstNameCol, lastNameCol, typeCol);
        this.setItems(passengers);
    }
}