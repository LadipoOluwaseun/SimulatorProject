package app;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.TeamService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TeamCtrl extends Controller {
    public TextField teamName, char1, char2, char3, editCharInput, searchField;
    public Label sprocOutput;
    public ListView currentTeams, searchResultList;

    public TeamCtrl(PassableServices serv) { super(serv); }

    public void initialize(URL location, ResourceBundle resources) {
        try {
            ArrayList<IDHolder> teamNames = new ArrayList<IDHolder>();
            for (int ID : teamServ.getIDs()) teamNames.add(teamServ.getName(ID));
            currentTeams.getItems().addAll(teamNames);
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void createTeam(ActionEvent event) {
        teamServ.addTeam(teamName.getText(), char1.getText(), char2.getText(), char3.getText(), 1);
        sprocOutput.setText(teamServ.getOutput());
    }

    public void addChar(ActionEvent event) throws SQLException {
        int teamID = ((IDHolder) currentTeams.getSelectionModel().getSelectedItem()).getID();
        teamServ.addCharToTeam(editCharInput.getText(), teamID);
    }

    public void removeChar(ActionEvent event) throws SQLException {
        teamServ.removeCharFromTeam(editCharInput.getText());
        sprocOutput.setText(teamServ.getOutput());
    }

    public void searchChar(ActionEvent event) {
        String searchString = searchField.getText();
        searchResultList.getItems().clear();
        searchResultList.getItems().addAll(charServ.searchCharacters(searchString));
    }

    public void viewPrepare(ActionEvent event) throws IOException {
        changeScene(event, "prepareScene.fxml", new PrepareCtrl(serv));
    }
}
