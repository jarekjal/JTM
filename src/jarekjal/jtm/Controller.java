package jarekjal.jtm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import java.io.File;
import java.util.Observable;
import java.util.Observer;


public class Controller implements Observer {

    @FXML private Text zegar;
    @FXML private Label dir;
    @FXML private Text tytul;
    private Model model;
    private Stoper stoper;


    public Controller() {

        model = new Model();
        model.addObserver(this);

    }



    @FXML
    public void actionOpenDir(ActionEvent actionEvent) {
        DirectoryChooser dc = new DirectoryChooser();
        File choosenDir =  dc.showDialog(null);
        if (choosenDir == null || !choosenDir.isDirectory() || !choosenDir.exists() ){
            System.out.println("Błąd: wybrano zły katalog! : " + choosenDir);
        } else {
            model.setDir(choosenDir);
            System.out.println("Wybrano: " + choosenDir);
        }
        stoper = new Stoper(zegar);
    }
    @FXML
    public void actionKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.SPACE) {
            System.out.println("SPACE pressed");
            model.spacePressed();
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
                if ("start".equals(m.params[0])){
                    stoper.start();
                } else if ("stop".equals(m.params[0])){
                    stoper.stop();
                    model.setTimeDiff(stoper.getTimeDiff());
                }
                break;
            case "clear":
                zegar.setText("0:00:000");
                tytul.setText("");
                break;
            case "title":
                tytul.setText(m.params[0]);
                break;
            default:
                break;
        }



    }
}
