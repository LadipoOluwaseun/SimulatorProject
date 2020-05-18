package services;

import app.IDHolder;

import java.sql.*;
import java.util.ArrayList;

public class ItemService {
    private DatabaseConnectionService dbService = null;
    public String output;

    public ItemService(DatabaseConnectionService dbService) {
        this.dbService = dbService;
        output = "";
    }

    public ArrayList<Integer> getItemsOfChar(int charID) {
        ArrayList<Integer> itemIDs = new ArrayList<Integer>();
        String SQL = "{call select_Items(?)}";
        Connection con = dbService.getConnection();
        try {
            CallableStatement cs = con.prepareCall(SQL);
            cs.setObject(1, charID);
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                itemIDs.add(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return itemIDs;
    }

    public IDHolder getName(int ID) {
        String SQL = "SELECT Name FROM Items WHERE ItemID = " + Integer.toString(ID);
        Connection con = dbService.getConnection();
        try {
            Statement stmt = con.createStatement();
            ResultSet res = stmt.executeQuery(SQL);
            res.next();
            String name = res.getString(1);
            return new IDHolder(ID, name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // should move to BattleService once that exists
    public void executeTurn(int CharID_1, int CharID_2, int ItemID_1, int ItemID_2) {
        String SQL = "{call execute_Turn(?,?,?,?)}";
        Connection con = dbService.getConnection();
        try {
            CallableStatement cs = con.prepareCall(SQL);
            cs.setObject(1, CharID_1);
            cs.setObject(2, CharID_2);
            cs.setObject(3, ItemID_1);
            cs.setObject(4, ItemID_2);
            cs.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
