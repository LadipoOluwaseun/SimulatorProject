package app;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import services.CharacterService;
import services.TeamService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    PassableServices services;
    ApplicationRunner appRun;
    String currentScn = "prepareScene.fxml";
    TeamService teamServ;
    CharacterService charServ;
    // prepareScene
    public Button beginMatchBtn, viewTeamBtn;
    public ChoiceBox p1Char, p2Char;
    public TextField p1User, p2User, p1Team, p2Team;
    ObservableList<String> charList;
    ArrayList<Integer> teamList;
    // teamScene
    public Button viewPrepareBtn, createTeamBtn, addChar, removeChar;
    public TextField teamName, char1, char2, char3, editCharInput;
    public Label sprocOutput;
    public ListView currentTeams;

    public Controller(PassableServices services) {
        this.services = services;
        appRun = services.applicationRunner;
        teamServ = appRun.teamService;
        charServ = appRun.characterService;
        charList = appRun.characterService.getCharacters();

        // this line will reset the state of the database before each run.
        // it is to make testing easier and will be removed in final product
//        teamServ.clearTeams();
    }

    public void initialize(URL location, ResourceBundle resources) {
        if (currentScn == "prepareScene.fxml") {
        } else if (currentScn == "teamScene.fxml") {
            try {
                ArrayList<String> teamNames = new ArrayList<String>();
                for (int ID : teamServ.getIDs()) teamNames.add(teamServ.getName(ID));
                currentTeams.getItems().addAll(teamNames);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    //--------------//
    // prepareScene //
    //--------------//
    public void populateDropDown(MouseEvent event, TextField teamInput, ChoiceBox drop) {
        drop.getItems().clear();
        String team = teamInput.getText();
        int teamID = teamServ.getID(team);
        if (teamID == -1) {
            System.out.println(teamServ.getOutput());
            return;
        }
        ArrayList<String> chars = appRun.characterService.getCharsFromTeam(teamID);
        if (chars.isEmpty()) { System.out.println(appRun.characterService.getOutput()); }
        else { drop.getItems().addAll(chars); }
    }
    public void populateDrop1(MouseEvent event) { populateDropDown(event, p1Team, p1Char); }
    public void populateDrop2(MouseEvent event) { populateDropDown(event, p2Team, p2Char); }

    //-----------//
    // teamScene //
    //-----------//
    public void createTeam(ActionEvent event) throws SQLException {
        teamServ.addTeam(teamName.getText(), char1.getText(), char2.getText(), char3.getText(), 1);
        sprocOutput.setText(teamServ.getOutput());
    }

    public void addChar(ActionEvent event) throws SQLException {
        String teamName = currentTeams.getSelectionModel().getSelectedItems().get(0).toString();
        teamServ.addCharToTeam(editCharInput.getText(), teamServ.getID(teamName));
    }

    public void removeChar(ActionEvent event) throws SQLException {
        teamServ.removeCharFromTeam(editCharInput.getText());
        sprocOutput.setText(teamServ.getOutput());
    }

    //------------------//
    // switching scenes //
    //------------------//
    public void viewTeam(ActionEvent event) throws IOException {
        changeScene(event, "teamScene.fxml", this);
    }

    public void viewPrepare(ActionEvent event) throws IOException {
        changeScene(event, "prepareScene.fxml", this);
    }

    public void viewBattle(ActionEvent event) throws IOException {
        ArrayList<Integer> charIDs = new ArrayList<Integer>();
        String char1 = p1Char.getSelectionModel().getSelectedItem().toString();
        charIDs.add(charServ.getID(char1));
        String char2 = p2Char.getSelectionModel().getSelectedItem().toString();
        charIDs.add(charServ.getID(char2));
        changeScene(event, "battleScene.fxml", new BattleControl(services, charIDs));
    }

    public void changeScene(ActionEvent event, String fxml, Initializable control) throws IOException {
        currentScn = fxml;
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        loader.setController(control);
        Parent root = loader.load();

        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
}
