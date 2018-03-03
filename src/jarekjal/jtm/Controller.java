package jarekjal.jtm;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

import java.io.File;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
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
                zegar.setText(""+m.params[0]);
                break;
            case "clear":
                zegar.setText("0:00:000");
                tytul.setText("");
                break;
            case "title":
                tytul.setText((String)m.params[0]);
                break;
            case "counter":
                counter.setText((String)m.params[0]);
                break;
            case "list":
                Map<File, Duration> map = (HashMap<File, Duration>)(m.params[0]);
                createResultList(map);
                break;
            default:
                break;
        }
    }

    private void createResultList(Map<File, Duration> map) {
        ResultList.getInstance().setMap(map);
        ResultList.getInstance().createResultListWindow();
    }

    public void actionSettings(ActionEvent actionEvent) { //uzyc do pobrania parenta dla nowego okna???
        Properties.getInstance().setModel(model);
        Properties.getInstance().createProperiesWindow();

    }

}
