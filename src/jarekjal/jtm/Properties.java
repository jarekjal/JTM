package jarekjal.jtm;

import jarekjal.utils.FileUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;

public class Properties {

    private static Properties ourInstance = new Properties();

    public static Properties getInstance() {
        return ourInstance;
    }

    private Properties() {
    }

    private File directory = null;
    private List<File> fileList = null;
    private Predicate<File> isMP3 = (File f) -> f.toString().toLowerCase().endsWith(".mp3");
    private int number = 0;
    private int maxNumber;
    private int minNumber;
    private boolean correct = false;

    private Model model;
    @FXML private TextField textField;
    @FXML private Label dirLabel;

    public void setModel(Model m){
        model = m;
    }

    public int getNumber() {
        return number;
    }

    public List<File> getFileList() {
        return fileList;
    }

    public File getDirectory() {
        return directory;
    }

    public void createProperiesWindow(){
        Stage properties = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("settings.fxml"));
            loader.setController(this);
            Scene scene = new Scene(loader.load());
            properties.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
        properties.setTitle("Settings...");
        if (directory == null){
            textField.setDisable(true);
        }
        properties.show();
    }

    @FXML
    public void actionOKpresed(ActionEvent actionEvent) {
        try {
            number = Integer.parseInt(textField.getText());
        }
        catch (NumberFormatException ex) {
            directory = null;
            correct = false;

        }
        if (correct) {
                model.setDir(fileList);
        }
        ((Stage)(((Node) actionEvent.getSource()).getScene().getWindow())).close();

    }

    @FXML
    public void actionBrowse() {
        DirectoryChooser dc = new DirectoryChooser();
        File choosenDir =  dc.showDialog(null);
        if (choosenDir == null || !choosenDir.isDirectory() || !choosenDir.exists() ){
            System.out.println("Błąd: wybrano zły katalog! : " + choosenDir);
        } else {
            fileList = FileUtils.flatFileTree(choosenDir, isMP3);
            if (!fileList.isEmpty()) {
                System.out.println("Wybrano: " + choosenDir);
                this.directory = choosenDir;
                maxNumber = fileList.size();
                minNumber = 1;
                number = maxNumber;
                textField.setDisable(false);
                correct = true;
            } else {
                directory= null;
                fileList = null;
                number = 0;
                correct = false;
            }
            update();


        }
    }

    public void update() {
        dirLabel.setText("Katalog: " + directory);
        textField.setText("" + number);
    }
}
