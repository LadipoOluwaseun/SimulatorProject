package services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CharacterService {
    private DatabaseConnectionService dbService = null;

    public CharacterService(DatabaseConnectionService dbService) {
        this.dbService = dbService;
    }


    public boolean addCharacter(String characterName) {
        //TODO: Implement add character prompt.
        System.out.println("Add Character Not Implemented.");
        return false;
    }

    public ObservableList<String> getChatacters(){
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
}
