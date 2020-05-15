package services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;

public class AddClearService {
    private DatabaseConnectionService dbService = null;
    public String output;

    public AddClearService(DatabaseConnectionService dbService) {
        this.dbService = dbService;
        output = "";
    }

    public void addAbility(int abilityID, String name, String type, int strength, String statusEffect) {
        String SQL = "{call import_Ability(?,?,?,?,?)}";
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

    public void addItem(int itemID, String name, String type, String description, Integer power, Integer accuracy) {
        String SQL = "{call import_Item(?,?,?,?,?,?)}";
        Connection con = dbService.getConnection();
        try {
            CallableStatement cs = con.prepareCall(SQL);
            cs.setObject(1, itemID);
            cs.setObject(2, name);
            cs.setObject(3, type);
            cs.setObject(4, description);
            cs.setObject(5, power);
            cs.setObject(6, accuracy);
            cs.execute();
            SQLWarning warns = cs.getWarnings();
            if (warns != null) { output = warns.getMessage(); }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void addUser(int userID, String username, String password) {
        String SQL = "{call import_User(?,?,?)}";
        Connection con = dbService.getConnection();
        try {
            CallableStatement cs = con.prepareCall(SQL);
            cs.setObject(1, userID);
            cs.setObject(2, username);
            cs.setObject(3, password);
            cs.execute();
            SQLWarning warns = cs.getWarnings();
            if (warns != null) { output = warns.getMessage(); }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void addTeam(int teamID, String name, int owner) {
        String SQL = "{call import_Team(?,?,?)}";
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

    public void addCharacter(int characterID, String name, String className, int teamID, String description,
                             int health, int powerLevel) {
        String SQL = "{call import_Character(?,?,?,?,?,?,?)}";
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

    public void addBattle(int battleID, int winnerID, int loserID) {
        String SQL = "{call import_Battle(?,?,?)}";
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

    public void addHasItem(int characterID, int itemID, int charges) {
        String SQL = "{call insert_HasItem(?,?,?)}";
        Connection con = dbService.getConnection();
        try {
            CallableStatement cs = con.prepareCall(SQL);
            cs.setObject(1, characterID);
            cs.setObject(2, itemID);
            cs.setObject(3, charges);
            cs.execute();
            SQLWarning warns = cs.getWarnings();
            if (warns != null) { output = warns.getMessage(); }
            else { output = ""; }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void addKnowsAbility(int characterID, int abilityID) {
        String SQL = "{call insert_KnowsAbility(?,?)}";
        Connection con = dbService.getConnection();
        try {
            CallableStatement cs = con.prepareCall(SQL);
            cs.setObject(1, characterID);
            cs.setObject(2, abilityID);
            cs.execute();
            SQLWarning warns = cs.getWarnings();
            if (warns != null) { output = warns.getMessage(); }
            else { output = ""; }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void addFoughtBy(int teamID, int battleID) {
        String SQL = "{call insert_FoughtBy(?,?)}";
        Connection con = dbService.getConnection();
        try {
            CallableStatement cs = con.prepareCall(SQL);
            cs.setObject(1, teamID);
            cs.setObject(2, battleID);
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

    // CLEARS ENTIRE DATABASE!! USE WITH CAUTION!!
    public void clearDB() {
        System.out.println("Clearing entire database: ON");
        try {
            Thread.sleep(1000);
            System.out.println("Erasing in 3...");
            Thread.sleep(2000);
            System.out.println("Erasing in 2...");
            Thread.sleep(2000);
            System.out.println("Erasing in 1...");
            Thread.sleep(2000);
            clear("Abilities");
            clear("Items");
            clear("Users");
            clear("Teams");
            clear("Characters");
            clear("Battles");
            clear("HasItem");
            clear("KnowsAbility");
            clear("FoughtBy");
            System.out.println("Database cleared.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
