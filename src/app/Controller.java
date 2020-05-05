package app;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    // prepareScene
    public Button beginMatchButton;
    public Button viewTeamBtn;
    public ChoiceBox p1CharacterSelect;
    public ChoiceBox p2CharacterSelect;
    public TextField p1Username;
    public TextField p2Username;
    public TextField p1Teamname;
    public TextField p2Teamname;
    ObservableList<String> characterList;

    // teamScene
    public Button viewPrepareBtn;

    public Controller() {
        //Cannot have fields in controller
    }

    @FXML
    public void initialize() {
        p1CharacterSelect.getItems().addAll(characterList);
        p2CharacterSelect.getItems().addAll(characterList);
    }

    public void setBeginMatchButton(ActionEvent actionEvent){
        //TODO: Should populate all input data into the database upon button press
    }

    public void setupCharacterList(PassableServices services) {
        characterList = services.applicationRunner.characterService.getCharacters();
    }

    // methods for switching scenes
    public void viewTeam(ActionEvent event) throws IOException {
        changeScene(event, "teamScene.fxml");
    }

    public void viewPrepare(ActionEvent event) throws IOException {
        changeScene(event, "prepareScene.fxml");
    }

    public void changeScene(ActionEvent event, String fxml) throws IOException {
//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(getClass().getResource(fxml));
//        Scene prepareScene = new Scene(loader.load());
//        Stage window = new Stage();
//        window.setScene(prepareScene);
//        window.show();
//        ((Node)(event.getSource())).getScene().getWindow().hide();

//        Stage stage = new Stage();
//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(getClass().getResource(fxml));
//        loader.setController(this);
//        Parent root = null;
//        root = loader.load();
//        Scene scene = new Scene(root);
//        stage.setScene(scene);

//        stage.show();
//        ((Node)(event.getSource())).getScene().getWindow().hide();

        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        loader.setController(this);
        Parent root = loader.load();

        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    // deprecated
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("called parameterized initialize!");
        System.out.println(characterList.toString());
        p1CharacterSelect.getItems().addAll(characterList);
        p2CharacterSelect.getItems().addAll(characterList);
    }
}
