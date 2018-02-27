package jarekjal.jtm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class Settings {

    private Model model;
    @FXML private TextField textField;

    public Settings(Model m){
        model = m;
    }

    @FXML
    public void actionOKpresed(ActionEvent actionEvent) {
        System.out.println(textField.getText());
        model.setDefaultCount(Integer.parseInt(textField.getText()));
        ((Stage)(((Node) actionEvent.getSource()).getScene().getWindow())).close();
    }
}
