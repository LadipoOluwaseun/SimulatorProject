package services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;

public class CharacterService {
    private DatabaseConnectionService dbService = null;
    public String output;

    public CharacterService(DatabaseConnectionService dbService) {
        this.dbService = dbService;
        output = "";
    }

    public boolean addCharacter(String characterName) {
        //TODO: Implement add character prompt.
        System.out.println("Add Character Not Implemented.");
        return false;
    }

    public ObservableList<String> getCharacters(){
        ArrayList<String> characters = new ArrayList<String>();
        String characterQuery = "SELECT name FROM characters";

        Connection connection = dbService.getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet characterResults = statement.executeQuery(characterQuery);
            while(characterResults.next()){
                characters.add(characterResults.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return FXCollections.observableArrayList(characters);
    }

    public ArrayList<String> getCharsFromTeam(int teamID) {
        String SQL = "Select Name FROM Characters WHERE TeamID = " + Integer.toString(teamID);
        Connection con = dbService.getConnection();
        ArrayList<String> chars = new ArrayList<String>();
        try {
            Statement stmt = con.createStatement();
            ResultSet res = stmt.executeQuery(SQL);
            while (res.next()) {
                chars.add(res.getString(1));
            }
        } catch (SQLException throwables) {
            output = "Error getting characters from team " + Integer.toString(teamID) + ".";
        }
        return chars;
    }

    public int getID(String name) {
        String SQL = "Select CharacterID FROM Characters WHERE Name = '" + name + "'";
        Connection con = dbService.getConnection();
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            ResultSet res = stmt.executeQuery(SQL);
            res.next();
            return res.getInt(1);
        } catch (SQLException throwables) {
            output = "Could not retrieve the ID of this character.";
            return -1;
        }
    }

    public String getName(int ID) {
        String SQL = "Select Name FROM Characters WHERE CharacterID = " + Integer.toString(ID);
        Connection con = dbService.getConnection();
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            ResultSet res = stmt.executeQuery(SQL);
            res.next();
            return res.getString(1);
        } catch (SQLException throwables) {
            output = "Could not retrieve the name of this character.";
            return null;
        }
    }

    public String getOutput() {
        return output;
    }

    public void healAll() {
        String SQL = "UPDATE Characters SET Health = 100";
        Connection con = dbService.getConnection();
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(SQL);
            System.out.println("Heal all characters on program start: ON");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public int getHealth(int charID) {
        String SQL = "Select Health FROM Characters WHERE CharacterID = " + Integer.toString(charID);
        Connection con = dbService.getConnection();
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            ResultSet res = stmt.executeQuery(SQL);
            res.next();
            return res.getInt(1);
        } catch (SQLException throwables) {
            output = "Could not retrieve the health of this character.";
            return -1;
        }
    }

    public ArrayList<String> searchCharacters(String searchString) {
        String SQL = "{call search_Characters(?)}";
        Connection con = dbService.getConnection();
        try {
            CallableStatement cs = con.prepareCall(SQL);
            cs.setObject(1, searchString);
            cs.execute();
            SQLWarning warns = cs.getWarnings();
            if (warns != null) { output = warns.getMessage(); }
            else { output = ""; }
            ResultSet rs = cs.getResultSet();
            ArrayList<String> results = new ArrayList<String>();
            while (rs.next()) {
                results.add(rs.getString(1));
            }
            return results;
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }
}
