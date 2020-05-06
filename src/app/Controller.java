package app;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    ApplicationRunner appRun;
    // prepareScene
    public Button beginMatchButton;
    public Button viewTeamBtn;
    public ChoiceBox p1CharacterSelect;
    public ChoiceBox p2CharacterSelect;
    public TextField p1Username;
    public TextField p2Username;
    public TextField p1Teamname;
    public TextField p2Teamname;
    ObservableList<String> charList;
    ObservableList<String> teamList;
    // teamScene
    public Button viewPrepareBtn, createTeamBtn;
    public TextField teamName, char1, char2, char3;

    public Controller(PassableServices services) {
        appRun = services.applicationRunner;
        charList = appRun.characterService.getCharacters();
        teamList = appRun.teamService.getTeams();

        // this line will reset the state of the database before each run.
        // it is to make testing easier and will be removed in final product
        appRun.teamService.clearTeams();
    }

    public void initialize(URL location, ResourceBundle resources) {
        p1CharacterSelect.getItems().addAll(charList);
        p2CharacterSelect.getItems().addAll(charList);
    }

    public void setBeginMatchButton(ActionEvent actionEvent){
        //TODO: Should populate all input data into the database upon button press
    }

    public void createTeam(ActionEvent event) throws SQLException {
        appRun.teamService.addTeam(teamName.getText(), char1.getText(), char2.getText(), char3.getText(), 1);
        System.out.println("create Team button clicked");
    }

    // methods for switching scenes
    public void viewTeam(ActionEvent event) throws IOException {
        changeScene(event, "teamScene.fxml");
    }

    public void viewPrepare(ActionEvent event) throws IOException {
        changeScene(event, "prepareScene.fxml");
    }

    public void changeScene(ActionEvent event, String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        loader.setController(this);
        Parent root = loader.load();

        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
}
