package services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;

public class TeamService {
    private DatabaseConnectionService dbService = null;

    public TeamService(DatabaseConnectionService dbService) {
        this.dbService = dbService;
    }


    public void addTeam(String name, String char1, String char2, String char3, int ID) throws SQLException {
        String SQL = "{call create_Team(?,?,?,?,?)}";
        Connection con = dbService.getConnection();
        CallableStatement cs = con.prepareCall(SQL);
        cs.setObject(1, char1);
        cs.setObject(2, char2);
        cs.setObject(3, char3);
        cs.setObject(4, name);
        cs.setObject(5, ID);
        cs.execute();
        System.out.println("execution finished");
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
