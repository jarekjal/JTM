package jarekjal.jtm;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;


public class Controller implements Observer {

    @FXML private Text zegar;
    @FXML private Label dir;
    @FXML private Text tytul;
    @FXML private Text counter;
    private Model model;

    public Controller() {
        model = new Model();
        model.addObserver(this);
    }


    @FXML
    public void actionOpenDir() {
        DirectoryChooser dc = new DirectoryChooser();
        File choosenDir =  dc.showDialog(null);
        if (choosenDir == null || !choosenDir.isDirectory() || !choosenDir.exists() ){
            System.out.println("Błąd: wybrano zły katalog! : " + choosenDir);
        } else {
            model.setDir(choosenDir);
            System.out.println("Wybrano: " + choosenDir);
        }
    }


    @FXML
    public void actionKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.SPACE) {
            System.out.println("SPACE pressed");
            model.spacePressed();
        }
        if (keyEvent.getCode() == KeyCode.ENTER) {
            System.out.println("ENTER pressed");
            model.enterPressed();
        }
    }


    @Override
    public void update(Observable o, Object arg) {

        Message m = (Message) arg;
        String command  = m.command;
        switch (command) {
            case "dir":
                dir.setText("Dir: " + m.params[0] + " (" + m.params[1] + ")");
                break;
            case "timer":
                zegar.setText(m.params[0]);
                break;
            case "clear":
                zegar.setText("0:00:000");
                tytul.setText("");
                break;
            case "title":
                tytul.setText(m.params[0]);
                break;
            case "counter":
                counter.setText(m.params[0]);
                break;
            default:
                break;
        }
    }

    public void actionSettings(ActionEvent actionEvent) {
        Stage settings = new Stage();
        settings.setTitle("Settings...");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("settings.fxml"));
            loader.setController(new Settings(model));

            Scene scene = new Scene(loader.load());
            settings.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }

        settings.show();
    }


}
