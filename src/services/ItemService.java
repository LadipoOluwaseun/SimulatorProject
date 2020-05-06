package services;

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
        String SQL = "{call get_Items_of_Char(?)}";
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

    public String getName(int ID) {
        String SQL = "SELECT Name FROM Items WHERE ItemID = " + Integer.toString(ID);
        Connection con = dbService.getConnection();
        try {
            Statement stmt = con.createStatement();
            ResultSet res = stmt.executeQuery(SQL);
            res.next();
            return res.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
