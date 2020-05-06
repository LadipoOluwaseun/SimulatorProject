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
        SQLWarning warns = cs.getWarnings();
        while (warns != null) {
            System.out.println(warns.getMessage());
            warns = warns.getNextWarning();
        }
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

    public void clearTeams() {
        String SQL = "DELETE FROM Teams";
        Connection con = dbService.getConnection();
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(SQL);
            System.out.println("cleared all teams");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
