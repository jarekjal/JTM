package jarekjal.jtm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import java.io.File;


public class Controller {

    @FXML
    private Text zegar;
    @FXML private Label dir;
    Model model;


    public Controller() {
        model = new Model();
    }


    @FXML
    private void mouseEntered(MouseEvent mouseEvent) {
        System.out.println("mouseEntered");
        zegar.setText("Entered");
    }


    public void mouseExited(MouseEvent mouseEvent) {
        System.out.println("mouseExited");
        zegar.setText("Exited");
    }

    @FXML
    public void actionOpenDir(ActionEvent actionEvent) {
        DirectoryChooser dc = new DirectoryChooser();
        File choosenDir =  dc.showDialog(null);
        if (choosenDir == null | !choosenDir.isDirectory() | !choosenDir.exists() ){
            System.out.println("Blad: wybrano zly katalog!");
        } else {
            model.setDir(choosenDir);
            System.out.println("Wybrano: " + choosenDir);
            dir.setText(choosenDir.toString());
        }
    }
    @FXML
    public void actionKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.SPACE) {
            System.out.println("SPACE pressed");
        }

    }
}
