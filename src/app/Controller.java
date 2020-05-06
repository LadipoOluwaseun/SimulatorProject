package app;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    ApplicationRunner appRun;
    String currentScn = "prepareScene.fxml";
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
    ArrayList<Integer> teamList;
    // teamScene
    public Button viewPrepareBtn, createTeamBtn, addChar, removeChar;
    public TextField teamName, char1, char2, char3, editCharInput;
    public Label sprocOutput;
    public ListView currentTeams;

    public Controller(PassableServices services) {
        appRun = services.applicationRunner;
        charList = appRun.characterService.getCharacters();

        // this line will reset the state of the database before each run.
        // it is to make testing easier and will be removed in final product
        appRun.teamService.clearTeams();
    }

    public void initialize(URL location, ResourceBundle resources) {
        p1CharacterSelect.getItems().addAll(charList);
        p2CharacterSelect.getItems().addAll(charList);
        if (currentScn == "teamScene.fxml") {
            try {
                ArrayList<String> teamNames = new ArrayList<String>();
                for (int ID : appRun.teamService.getIDs()) {
                    teamNames.add(appRun.teamService.getName(ID));
                }
                currentTeams.getItems().addAll(teamNames);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void setBeginMatchButton(ActionEvent actionEvent){
        //TODO: Should populate all input data into the database upon button press
    }

    public void createTeam(ActionEvent event) throws SQLException {
        appRun.teamService.addTeam(teamName.getText(), char1.getText(), char2.getText(), char3.getText(), 1);
        sprocOutput.setText(appRun.teamService.getOutput());
    }

    public void addChar(ActionEvent event) {
        //TODO: add character to selected team
    }

    public void removeChar(ActionEvent event) throws SQLException {
        appRun.teamService.removeCharFromTeam(editCharInput.getText());
        sprocOutput.setText(appRun.teamService.getOutput());
    }

    // methods for switching scenes
    public void viewTeam(ActionEvent event) throws IOException {
        changeScene(event, "teamScene.fxml");
    }

    public void viewPrepare(ActionEvent event) throws IOException {
        changeScene(event, "prepareScene.fxml");
    }

    public void changeScene(ActionEvent event, String fxml) throws IOException {
        currentScn = fxml;
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
