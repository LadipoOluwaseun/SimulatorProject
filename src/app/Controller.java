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
    PassableServices serv;
    TeamService teamServ;
    CharacterService charServ;
    // prepareScene
    public ChoiceBox p1Char, p2Char;
    public TextField user1Fld, user2Fld, team1Fld, team2Fld;

    public Controller(PassableServices serv) {
        this.serv = serv;
        teamServ = serv.applicationRunner.teamService;
        charServ = serv.applicationRunner.characterService;

        // dev testing only
//        charServ.healAll();
//        teamServ.clearTeams();
    }

    public void initialize(URL location, ResourceBundle resources) {
    }

    public void populateDropDown(MouseEvent event, TextField teamInput, ChoiceBox drop) {
        drop.getItems().clear();
        String team = teamInput.getText();
        int teamID = teamServ.getID(team);
        if (teamID == -1) {
            System.out.println(teamServ.getOutput());
            return;
        }
        ArrayList<String> chars = charServ.getCharsFromTeam(teamID);
        if (chars.isEmpty()) { System.out.println(charServ.getOutput()); }
        else { drop.getItems().addAll(chars); }
    }
    public void populateDrop1(MouseEvent event) { populateDropDown(event, team1Fld, p1Char); }
    public void populateDrop2(MouseEvent event) { populateDropDown(event, team2Fld, p2Char); }

    //------------------//
    // switching scenes //
    //------------------//
    public void viewTeam(ActionEvent event) throws IOException {
        changeScene(event, "teamScene.fxml", new TeamControl(serv));
    }

    public void viewBattle(ActionEvent event) throws IOException {
        ArrayList<Integer> charIDs = new ArrayList<Integer>();
        String char1 = p1Char.getSelectionModel().getSelectedItem().toString();
        charIDs.add(charServ.getID(char1));
        String char2 = p2Char.getSelectionModel().getSelectedItem().toString();
        charIDs.add(charServ.getID(char2));
        changeScene(event, "battleScene.fxml", new BattleControl(serv, charIDs));
    }

    public void changeScene(ActionEvent event, String fxml, Initializable control) throws IOException {
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
