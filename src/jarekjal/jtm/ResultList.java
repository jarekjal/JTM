package jarekjal.jtm;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Map;

public class ResultList {

    private ResultList(){}
    private Map<File, Duration> map = null;

    public static ResultList getInstance() {
        return instance;
    }

    private static ResultList instance = new ResultList();
    @FXML private static TableView lista;

    public void setMap(Map<File,Duration> map) {
        this.map = map;
    }

    public void createResultListWindow() {

        for (File f : map.keySet()){
            System.out.println(f.getName() + ": " + Stoper.formatDuration(map.get(f)));
        }

        Stage properties = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("results.fxml"));
            loader.setController(this);
            Scene scene = new Scene(loader.load());
            properties.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
        properties.setTitle("Results");

        properties.show();

    }
}
