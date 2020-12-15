package sample.GUI;


import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import sample.models.*;

import java.net.URL;
import java.util.ResourceBundle;

public class GadgetFormController implements Initializable {

    public ChoiceBox cmbGadgetType;

    public TextField txtTitle;
    public TextField txtDisplay;

    public VBox laptopPane;

    public ChoiceBox cmbCore;

    public TextField txtVolumeDisc;

    public CheckBox chkLightKey;


    public VBox tabletPane;

    public TextField txtDpi;

    public CheckBox chkCamera;


    public VBox smartphonePane;

    public CheckBox chkOneSim;

    public CheckBox chkTwoSim;

    public TextField txtMpCamera;

    public TextField txtBattery;

    final String GADGET_LAPTOP = "Ноутбук";
    final String GADGET_TABLET = "Планшет";
    final String GADGET_SMARTPHONE = "Смартфон";

    public GadgetModel gadgetModel;
    private Integer id = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cmbGadgetType.setItems(FXCollections.observableArrayList(
                GADGET_LAPTOP,
                GADGET_TABLET,
                GADGET_SMARTPHONE
        ));

        cmbGadgetType.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            updatePanes((String) newValue);
        });
        //выпадающий список выбора процессора
        cmbCore.getItems().setAll(
                Laptop.TypeCore.one,
                Laptop.TypeCore.two,
                Laptop.TypeCore.fore,
                Laptop.TypeCore.eight

        );

        cmbCore.setConverter(new StringConverter<Laptop.TypeCore>() {
            @Override
            public String toString(Laptop.TypeCore object) {
                switch (object) {
                    case one:
                        return "одноядерный";
                    case two:
                        return "двухядерный";
                    case fore:
                        return "четырехядерный";
                    case eight:
                        return "восьмиядерный";
                }
                return null;
            }

            @Override
            public Laptop.TypeCore fromString(String string) {
                return null;
            }
        });

        updatePanes("");
    }


    public void updatePanes(String value) {
        this.laptopPane.setVisible(value.equals(GADGET_LAPTOP));
        this.laptopPane.setManaged(value.equals(GADGET_LAPTOP));

        this.tabletPane.setVisible(value.equals(GADGET_TABLET));
        this.tabletPane.setManaged(value.equals(GADGET_TABLET));

        this.smartphonePane.setVisible(value.equals(GADGET_SMARTPHONE));
        this.smartphonePane.setManaged(value.equals(GADGET_SMARTPHONE));
    }

    public void onSaveClick(ActionEvent actionEvent) {
        if (this.id != null) {
            Gadget gadget = getGadget();
            gadget.id = this.id;

            this.gadgetModel.edit(gadget);
        } else {
            this.gadgetModel.add(getGadget());
        }

        ((Stage)((Node)actionEvent.getSource()).getScene().getWindow()).close();
    }


    public void onCancelClick(ActionEvent actionEvent) {
        ((Stage)((Node)actionEvent.getSource()).getScene().getWindow()).close();
    }


    //метод добавления записи
    public Gadget getGadget (){
        Gadget result = null;
        double display = Double.parseDouble(this.txtDisplay.getText());
        String title = this.txtTitle.getText();



        switch ((String)this.cmbGadgetType.getValue()) {
            case GADGET_LAPTOP:
                /*
                Laptop.TypeCore type = null;
                switch (this.cmbCore.getValue().toString())
                {
                    case "одноядерный":
                        type = Laptop.TypeCore.one;
                        break;
                    case "двухядерный":
                        type = Laptop.TypeCore.two;
                        break;
                    case "четырехядерный":
                        type = Laptop.TypeCore.fore;
                        break;
                    case "восьмиядерный":
                        type = Laptop.TypeCore.eight;
                        break;
                }*/
                result = new Laptop(
                        title,
                        display,
                        Integer.parseInt(this.txtVolumeDisc.getText()),
                        (Laptop.TypeCore)this.cmbCore.getValue(),
                        this.chkLightKey.isSelected());
                break;
            case GADGET_TABLET:
                result = new Tablet(
                        title,
                        display,
                        this.chkCamera.isSelected(),
                        Double.parseDouble(this.txtDpi.getText())
                );
                break;
            case GADGET_SMARTPHONE:
                result = new Smartphone(
                        title,
                        display,
                        Double.parseDouble(this.txtMpCamera.getText()),
                        this.txtBattery.getText(),
                        this.chkOneSim.isSelected(),
                        this.chkTwoSim.isSelected());
                break;
        }
        return result;
    }
    public void setGadget(Gadget gadget) {

        this.cmbGadgetType.setDisable(gadget != null);
        this.id = gadget != null ? gadget.id : null;
        if (gadget != null) {
            this.txtDisplay.setText(String.valueOf(gadget.getSizeDisplay()));
            this.txtTitle.setText(gadget.getTitle());

            if (gadget instanceof Laptop) {
                this.cmbGadgetType.setValue(GADGET_LAPTOP);
                this.chkLightKey.setSelected(((Laptop) gadget).keyBacklight);
                this.cmbCore.setValue(((Laptop) gadget).typeCore);
                this.txtVolumeDisc.setText(String.valueOf(((Laptop) gadget).sizeDisc));

            } else if (gadget instanceof Tablet) {
                this.cmbGadgetType.setValue(GADGET_TABLET);
                this.chkCamera.setSelected(((Tablet) gadget).camera);
                this.txtDpi.setText(String.valueOf(((Tablet) gadget).getDpi()));

            } else if (gadget instanceof Smartphone) {
                this.cmbGadgetType.setValue(GADGET_SMARTPHONE);
                this.txtBattery.setText(String.valueOf(((Smartphone) gadget).battery));
                this.txtMpCamera.setText(String.valueOf(((Smartphone) gadget).megapixels));
                this.chkOneSim.setSelected(((Smartphone) gadget).oneSim);
                this.chkTwoSim.setSelected(((Smartphone) gadget).twoSim);
            }
        }
    }


}

