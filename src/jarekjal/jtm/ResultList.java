package jarekjal.jtm;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResultList {

    private ResultList(){}
    private Map<File, Duration> map = null;
    private List<Utwor> lista = null;
    private ObservableList<Utwor> data = null;
    @FXML TableColumn colName;
    @FXML TableColumn colTime;

    public static ResultList getInstance() {
        return instance;
    }

    private static ResultList instance = new ResultList();
    @FXML private  TableView<Utwor> tabela;

    public void setMap(Map<File,Duration> map) {
        this.map = map;
        lista = new ArrayList<>();
        for (File f : map.keySet()){
            Utwor u = new Utwor(f.getName().toString(), Stoper.formatDuration(map.get(f)));
            lista.add(u);
        }
        data = FXCollections.observableList(lista);


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

        colName.setCellValueFactory(
                new PropertyValueFactory<Utwor, String>("name"));



        colTime.setCellValueFactory(
                new PropertyValueFactory<Utwor, String>("time"));

        tabela.setItems(data);
        //tabela.getColumns().addAll(colName, colTime);


        properties.show();

    }

    public static class Utwor{
        private final SimpleStringProperty name;
        private final SimpleStringProperty time;

        private Utwor(String n, String t){
            name = new SimpleStringProperty(n);
            time = new SimpleStringProperty(t);
        }

        public String getName() {
            return name.get();
        }

        public String getTime() {
            return time.get();
        }

        public void setTime(String t){
            time.set(t);
        }

        public void setName(String n){
            name.set(n);
        }


    }

}
