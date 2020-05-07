package app;

import app.PassableServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.TeamService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TeamControl implements Initializable {
    PassableServices serv;
    TeamService teamServ;
    public TextField teamName, char1, char2, char3, editCharInput;
    public Label sprocOutput;
    public ListView currentTeams;

    public TeamControl(PassableServices serv) {
        this.serv = serv;
        teamServ = serv.applicationRunner.teamService;
    }
    public void initialize(URL location, ResourceBundle resources) {
        try {
            ArrayList<String> teamNames = new ArrayList<String>();
            for (int ID : teamServ.getIDs()) teamNames.add(teamServ.getName(ID));
            currentTeams.getItems().addAll(teamNames);
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void createTeam(ActionEvent event) {
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

    public void viewPrepare(ActionEvent event) throws IOException {
        changeScene(event, "prepareScene.fxml", new Controller(serv));
    }
}
