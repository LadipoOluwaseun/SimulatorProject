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

public class RecordCtrl extends Controller {

    public ChoiceBox charBox;
    public TextField teamFld;
    public Label recordLbl;

    public RecordCtrl(PassableServices serv) {
        super(serv);
    }

    public void populateDropDown(MouseEvent event) {
        charBox.getItems().clear();
        String team = teamFld.getText();
        int teamID = teamServ.getID(team);
        if (teamID == -1) {
            System.out.println(teamServ.getOutput());
            return;
        }
        ArrayList<String> chars = charServ.getCharsFromTeam(teamID);
        if (chars.isEmpty()) { System.out.println(charServ.getOutput()); }
        else { charBox.getItems().addAll(chars); }
    }

    public void displayRecord(ActionEvent event) {
        String charName = charBox.getSelectionModel().getSelectedItem().toString();
        String record = charServ.getRecord(charServ.getID(charName));
        recordLbl.setText(record);
    }

    public void viewPrepare(ActionEvent event) throws IOException {
        changeScene(event, "prepareScene.fxml", new PrepareCtrl(serv));
    }
}
