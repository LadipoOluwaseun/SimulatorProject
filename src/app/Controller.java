package app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.CharacterService;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public Button beginMatchButton;
    public Button viewTeamBtn;
    public ChoiceBox p1CharacterSelect;
    public ChoiceBox p2CharacterSelect;
    public TextField p1Username;
    public TextField p2Username;
    public TextField p1Teamname;
    public TextField p2Teamname;
    ObservableList<String> characterList;
    public Button viewPrepareBtn;
    public Controller() {
        //Cannot have fields in controller
    }

    @FXML
    public void initialize() {
        //At the moment the Choicebox will not display data
        p1CharacterSelect.getItems().addAll(characterList);
        p2CharacterSelect.getItems().addAll(characterList);
        System.out.println(p1CharacterSelect.getItems().toString());
    }

    public void setBeginMatchButton(ActionEvent actionEvent){
        //TODO: Should populate all input data into the database upon button press
    }

    public void setupCharacterList(PassableServices services) {
        characterList = services.applicationRunner.characterService.getChatacters();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    // Switches the current scene from the 'preparing for battle' scene (prepareScene) to
    // the 'view/edit your teams' scene (teamScene)
    public void viewTeams(ActionEvent event) throws IOException {
        changeView(event, "teamScene.fxml");
    }

    public void viewPrepare(ActionEvent event) throws IOException {
        changeView(event, "sample.fxml");
    }

    public void changeView(ActionEvent event, String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxml));
        Scene prepareScene = new Scene(loader.load());
        Stage window = new Stage();
        window.setScene(prepareScene);
        window.show();
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
}
