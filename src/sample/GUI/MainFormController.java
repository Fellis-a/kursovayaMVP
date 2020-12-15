package sample.GUI;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import sample.models.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainFormController implements Initializable {

    public TableView<Gadget> mainTable;
    public ComboBox cmbGadgetType;

    ObservableList<Class<? extends Gadget>> gadgetTypes = FXCollections.observableArrayList(
            Gadget.class,
            Laptop.class,
            Tablet.class,
            Smartphone.class
    );

    GadgetModel gadgetModel = new GadgetModel();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gadgetModel.addDataChangedListener(gadgets -> {
            mainTable.setItems(FXCollections.observableArrayList(gadgets));
        });

        TableColumn<Gadget, String> titleColumn = new TableColumn<>("Название");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Gadget, String> displaySizeColumn = new TableColumn<>("Дисплей");
        displaySizeColumn.setCellValueFactory(new PropertyValueFactory<>("sizeDisplay"));

        TableColumn<Gadget, String> descriptionColumn = new TableColumn<>("Описание");

        descriptionColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getDescription());
        });


        mainTable.getColumns().addAll(titleColumn, displaySizeColumn, descriptionColumn);

        cmbGadgetType.setItems(gadgetTypes);

        cmbGadgetType.getSelectionModel().select(0);

        cmbGadgetType.setConverter(new StringConverter<Class>() {
            @Override
            public String toString(Class object) {
                if (Gadget.class.equals(object)) {
                    return "Все";
                } else if (Laptop.class.equals(object)) {
                    return "Ноутбук";
                } else if (Tablet.class.equals(object)) {
                    return "Планшет";
                } else if (Smartphone.class.equals(object)) {
                    return "Смартфон";
                }
                return null;
            }

            @Override
            public Class fromString(String string) {
                return null;
            }
        });

        cmbGadgetType.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            this.gadgetModel.setGadgetFilter((Class<? extends Gadget>) newValue);
        });

    }

    public void onAddClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("GadgetForm.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(this.mainTable.getScene().getWindow());

        GadgetFormController controller = loader.getController();
        controller.gadgetModel = gadgetModel;

        stage.showAndWait();
    }
    public void onEditClick(ActionEvent actionEvent) throws IOException {
        if (this.mainTable.getSelectionModel().getSelectedItem() == null) {
            return;
        }

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("GadgetForm.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(this.mainTable.getScene().getWindow());

        GadgetFormController controller = loader.getController();
        controller.setGadget((Gadget) this.mainTable.getSelectionModel().getSelectedItem());
        controller.gadgetModel = gadgetModel;
        stage.showAndWait();


    }
    public void onDeleteClick(ActionEvent actionEvent) throws IOException{
         Gadget gadget = (Gadget) this.mainTable.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Подтверждение");
        alert.setHeaderText(String.format("Точно удалить %s?", gadget.getTitle()));

        Optional<ButtonType> option = alert.showAndWait();
        if (option.get() == ButtonType.OK) {
            gadgetModel.delete(gadget.id);
        }
    }
    public void onSaveToFileClick(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Сохранить данные");
        fileChooser.setInitialDirectory(new File("."));

        File file = fileChooser.showSaveDialog(mainTable.getScene().getWindow());

        if (file != null) {
            gadgetModel.saveToFile(file.getPath());
        }
    }

    public void onLoadToFileClick(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Загрузить данные");
        fileChooser.setInitialDirectory(new File("."));

        File file = fileChooser.showOpenDialog(mainTable.getScene().getWindow());

        if (file != null) {
            gadgetModel.loadFromFile(file.getPath());
        }
    }
}
