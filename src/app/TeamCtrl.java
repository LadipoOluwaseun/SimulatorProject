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
        String name = teamName.getText();
        String c1 = char1.getText();
        String c2 = char2.getText();
        String c3 = char3.getText();
        if (name.isEmpty()) name = null;
        if (c1.isEmpty()) c1 = null;
        if (c2.isEmpty()) c2 = null;
        if (c3.isEmpty()) c3 = null;
        teamServ.addTeam(name, c1, c2, c3, 1);
        sprocOutput.setText(teamServ.getOutput());
    }

    public void addChar(ActionEvent event) throws SQLException {
        if (currentTeams.getSelectionModel().getSelectedItem() == null) {
            sprocOutput.setText("Choose a team to add to first!");
            return;
        }
        if (editCharInput.getText().isEmpty()) {
            sprocOutput.setText("Enter a character's name to add to this team!");
            return;
        }
        int teamID = ((IDHolder) currentTeams.getSelectionModel().getSelectedItem()).getID();
        teamServ.addCharToTeam(editCharInput.getText(), teamID);
        sprocOutput.setText(teamServ.getOutput());
    }

    public void removeChar(ActionEvent event) throws SQLException {
        if (editCharInput.getText().isEmpty()) {
            sprocOutput.setText("Enter a character's name to remove them from their team!");
            return;
        }
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
