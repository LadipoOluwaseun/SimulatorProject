package app;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.CharacterService;
import services.TeamService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PrepareCtrl extends Controller {
    public ChoiceBox p1Char, p2Char;
    public TextField user1Fld, user2Fld, team1Fld, team2Fld;
    public FileChooser fc;

    public PrepareCtrl(PassableServices serv) {
        super(serv);
        fc = new FileChooser();
        // dev testing only
        charServ.healAll();
//        teamServ.clearTeams();
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

    public void chooseImport(ActionEvent event) throws FileNotFoundException {
//        File file = fc.showOpenDialog(new Stage());
//        if (file != null) {
//            Parser.parse(file, addServ);
//        }
        DirectoryChooser chooser = new DirectoryChooser();
        File selectedDirectory = chooser.showDialog(new Stage());
        if (selectedDirectory != null) {
            Parser.parseInOrder(selectedDirectory, addServ);
        }
    }

    //------------------//
    // switching scenes //
    //------------------//
    public void viewTeam(ActionEvent event) throws IOException {
        changeScene(event, "teamScene.fxml", new TeamCtrl(serv));
    }

    public void viewBattle(ActionEvent event) throws IOException {
        ArrayList<Integer> charIDs = new ArrayList<Integer>();
        String char1 = p1Char.getSelectionModel().getSelectedItem().toString();
        charIDs.add(charServ.getID(char1));
        String char2 = p2Char.getSelectionModel().getSelectedItem().toString();
        charIDs.add(charServ.getID(char2));
        changeScene(event, "battleScene.fxml", new BattleCtrl(serv, charIDs));
    }
}
