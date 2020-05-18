package services;

import app.IDHolder;
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

    //------------------//
    //     SETTERS      //
    //------------------//
    public void addTeam(String name, String char1, String char2, String char3, int ID) {
        nullifyString(name);
        nullifyString(char1);
        nullifyString(char2);
        nullifyString(char3);
        String SQL = "{call insert_Team(?,?,?,?,?)}";
        Connection con = dbService.getConnection();
        try {
            CallableStatement cs = con.prepareCall(SQL);
            cs.setObject(1, char1);
            cs.setObject(2, char2);
            cs.setObject(3, char3);
            cs.setObject(4, name);
            cs.setObject(5, ID);
            cs.execute();
            SQLWarning warns = cs.getWarnings();
            if (warns != null) { output = warns.getMessage(); }
            else { output = "Team '" + name + "' created!"; }
        } catch (SQLException e) { e.printStackTrace(); }
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
        String SQL = "{call remove_from_Team(?)}";
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

    public void clearTeams() {
        String SQL = "DELETE FROM Teams";
        Connection con = dbService.getConnection();
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(SQL);
            System.out.println("Clear team on program start: ON");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    //------------------//
    //     GETTERS      //
    //------------------//
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

    public IDHolder getName(int ID) {
        String SQL = "SELECT Name FROM Teams WHERE TeamID = " + Integer.toString(ID);
        Connection con = dbService.getConnection();
        try {
            Statement stmt = con.createStatement();
            ResultSet res = stmt.executeQuery(SQL);
            res.next();
            String name = res.getString(1);
            return new IDHolder(ID, name);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // WILL BE REMOVED SOON!
    public int getID(String teamName) {
        String SQL = "Select TeamID FROM Teams WHERE Name = '" + teamName + "'";
        Connection con = dbService.getConnection();
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            ResultSet res = stmt.executeQuery(SQL);
            res.next();
            return res.getInt(1);
        } catch (SQLException throwables) {
            output = "Could not retrieve the ID of this team.";
            return -1;
        }
    }

    //------------------//
    //     UTILITY      //
    //------------------//
    public String getOutput() {
        return output;
    }

    public void nullifyString(String str) {
        if (str.isEmpty()) { str = null; }
    }
}
