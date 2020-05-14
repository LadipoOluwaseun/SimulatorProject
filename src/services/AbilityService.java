package services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;

public class AbilityService {
    private DatabaseConnectionService dbService = null;
    public String output;

    public AbilityService(DatabaseConnectionService dbService) {
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

    public void clearAbilities() {
        String SQL = "DELETE FROM Abilities";
        Connection con = dbService.getConnection();
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(SQL);
            System.out.println("Clear abilities on program start: ON");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
