package services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class TeamService {
    private DatabaseConnectionService dbService = null;

    public TeamService(DatabaseConnectionService dbService) {
        this.dbService = dbService;
    }


    public boolean addTeam(String teamName) {
        //TODO: Implement add character prompt.
        System.out.println("Add Team Not Implemented.");
        return false;
    }

    public ObservableList<String> getTeams(){
        ArrayList<String> teams = new ArrayList<String>();
        String teamQuery = "SELECT name FROM teams";

        Connection connection = dbService.getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet teamResults = statement.executeQuery(teamQuery);
            while(teamResults.next()){
                teams.add(teamResults.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return FXCollections.observableArrayList(teams);
    }
}
