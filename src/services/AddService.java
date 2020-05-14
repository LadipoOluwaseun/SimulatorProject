package services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;

public class AddService {
    private DatabaseConnectionService dbService = null;
    public String output;

    public AddService(DatabaseConnectionService dbService) {
        this.dbService = dbService;
        output = "";
    }

    public void addAbility(int abilityID, String name, String type, int strength, String statusEffect) {
        String SQL = "{call add_to_Abilities(?,?,?,?,?)}";
        Connection con = dbService.getConnection();
        try {
            CallableStatement cs = con.prepareCall(SQL);
            cs.setObject(1, abilityID);
            cs.setObject(2, name);
            cs.setObject(3, type);
            cs.setObject(4, strength);
            cs.setObject(5, statusEffect);
            cs.execute();
            SQLWarning warns = cs.getWarnings();
            if (warns != null) { output = warns.getMessage(); }
            else { output = "Ability '" + name + "' created!"; }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void addBattle(int battleID, int winnerID, int loserID) {
        String SQL = "{call add_to_Battles(?,?,?)}";
        Connection con = dbService.getConnection();
        try {
            CallableStatement cs = con.prepareCall(SQL);
            cs.setObject(1, battleID);
            cs.setObject(2, winnerID);
            cs.setObject(3, loserID);
            cs.execute();
            SQLWarning warns = cs.getWarnings();
            if (warns != null) { output = warns.getMessage(); }
            else { output = ""; }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void addCharacter(int characterID, String name, String className, int teamID, String description,
                             int health, int powerLevel) {
        String SQL = "{call add_to_Characters(?,?,?,?,?,?,?)}";
        Connection con = dbService.getConnection();
        try {
            CallableStatement cs = con.prepareCall(SQL);
            cs.setObject(1, characterID);
            cs.setObject(2, name);
            cs.setObject(3, className);
            cs.setObject(4, teamID);
            cs.setObject(5, description);
            cs.setObject(6, health);
            cs.setObject(7, powerLevel);
            cs.execute();
            SQLWarning warns = cs.getWarnings();
            if (warns != null) { output = warns.getMessage(); }
            else { output = ""; }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void addTeam(int teamID, String name, int owner) {
        String SQL = "{call add_to_Teams(?,?,?)}";
        Connection con = dbService.getConnection();
        try {
            CallableStatement cs = con.prepareCall(SQL);
            cs.setObject(1, teamID);
            cs.setObject(2, name);
            cs.setObject(3, owner);
            cs.execute();
            SQLWarning warns = cs.getWarnings();
            if (warns != null) { output = warns.getMessage(); }
            else { output = ""; }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void clear(String table) {
        String SQL = "DELETE FROM " + table;
        Connection con = dbService.getConnection();
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(SQL);
            System.out.println("Clear " + table + " on program start: ON");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
