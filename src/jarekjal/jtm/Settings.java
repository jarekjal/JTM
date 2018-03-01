package jarekjal.jtm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;


public class Settings {

    private Model model;
    @FXML private TextField textField;
    @FXML private Label dirLabel;


    public Settings(Model m){
        model = m;
    }


    @FXML
    public void actionOKpresed(ActionEvent actionEvent) {

        if (SettingsModel.get().setNumber(Integer.parseInt(textField.getText()))){
            if (SettingsModel.get().isCorrect()) {
                model.setDir(SettingsModel.get().getFileList());
            }
            ((Stage)(((Node) actionEvent.getSource()).getScene().getWindow())).close();
        }
    }

    @FXML
    public void actionBrowse() {
        DirectoryChooser dc = new DirectoryChooser();
        File choosenDir =  dc.showDialog(null);
        if (choosenDir == null || !choosenDir.isDirectory() || !choosenDir.exists() ){
            System.out.println("Błąd: wybrano zły katalog! : " + choosenDir);
        } else {
            if (SettingsModel.get().setDirectory(choosenDir)) {
                System.out.println("Wybrano: " + choosenDir);
                SettingsModel.get().setDirectory(choosenDir);
                update();


            }
        }
    }

    public void update() {
        dirLabel.setText("Katalog: " + SettingsModel.get().getDirectory());
        textField.setText("" + SettingsModel.get().getNumber());
    }
}
