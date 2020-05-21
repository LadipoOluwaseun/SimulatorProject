package app;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import services.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public PassableServices serv;
    public CharacterService charServ;
    public TeamService teamServ;
    public ItemService itemServ;
    public AddClearService addServ;
    public UserService userServ;

    public Controller(PassableServices serv) {
        this.serv = serv;
        charServ = serv.applicationRunner.characterService;
        teamServ = serv.applicationRunner.teamService;
        itemServ = serv.applicationRunner.itemService;
        addServ = serv.applicationRunner.addClearService;
        userServ = serv.applicationRunner.userService;
    }

    public void initialize(URL location, ResourceBundle resources) {
    }

    public void changeScene(ActionEvent event, String fxml, Controller ctrl) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        loader.setController(ctrl);
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
}
