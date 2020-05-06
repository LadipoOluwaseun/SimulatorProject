package services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;

public class TeamService {
    private DatabaseConnectionService dbService = null;
    public String output;

    public TeamService(DatabaseConnectionService dbService) {
        this.dbService = dbService;
        output = "";
    }

    public void addTeam(String name, String char1, String char2, String char3, int ID) throws SQLException {
        if (name.isEmpty()) name = null;
        if (char1.isEmpty()) char1 = null;
        if (char2.isEmpty()) char2 = null;
        if (char3.isEmpty()) char3 = null;
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
        if (warns != null) {
            output = warns.getMessage();
        } else {
            output = "Team '" + name + "' created!";
        }
    }

    public void addCharToTeam(String charName, int ID) throws SQLException {
        String SQL = "{call add_Char_to_Team(?,?)}";
        Connection con = dbService.getConnection();
        CallableStatement cs = con.prepareCall(SQL);
        cs.setObject(1, charName);
        cs.setObject(2, ID);
        cs.execute();
    }

    public void removeCharFromTeam(String charName) throws SQLException {
        String SQL = "{call remove_Char_from_Team(?)}";
        Connection con = dbService.getConnection();
        CallableStatement cs = con.prepareCall(SQL);
        cs.setObject(1, charName);
        cs.execute();
        SQLWarning warns = cs.getWarnings();
        if (warns != null) {
            output = warns.getMessage();
        } else {
            output = "Character '" + charName + "' removed!";
        }
    }

    public ArrayList<Integer> getIDs() throws SQLException {
        ArrayList<Integer> teams = new ArrayList<Integer>();
        String SQL = "SELECT TeamID FROM Teams";
        Connection con = dbService.getConnection();
        Statement stmt = con.createStatement();
        ResultSet res = stmt.executeQuery(SQL);
        while (res.next()) {
            teams.add(res.getInt(1));
        }
        return teams;
    }

    public String getName(int ID) throws SQLException {
        String SQL = "SELECT Name FROM Teams WHERE TeamID = " + Integer.toString(ID);
        Connection con = dbService.getConnection();
        Statement stmt = con.createStatement();
        ResultSet res = stmt.executeQuery(SQL);
        res.next();
        return res.getString(1);
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

    public String getOutput() {
        return output;
    }
}
