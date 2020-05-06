package app;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import services.CharacterService;
import services.ItemService;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class BattleControl implements Initializable {
    public CharacterService charServ;
    public ItemService itemServ;
    public ChoiceBox p1Item, p2Item;
    public Button p1Atk, p2Atk;
    public int charID1, charID2;
    public Label p1Char, p2Char;
    public ArrayList<Integer> itemIDs1, itemIDs2;

    public BattleControl(PassableServices services, ArrayList<Integer> charIDs) {
        charID1 = charIDs.get(0);
        charID2 = charIDs.get(1);
        charServ = services.applicationRunner.characterService;
        itemServ = services.applicationRunner.itemService;
    }

    public void initialize(URL location, ResourceBundle resources) {
        String name1 = charServ.getName(charID1);
        p1Char.setText(name1);
        String name2 = charServ.getName(charID2);
        p2Char.setText(name2);
        itemIDs1 = itemServ.getItemsOfChar(charID1);
        itemIDs2 = itemServ.getItemsOfChar(charID2);
        populateDropDown(itemIDs1, p1Item);
        populateDropDown(itemIDs2, p2Item);
    }

    public void populateDropDown(ArrayList<Integer> itemIDs, ChoiceBox drop) {
        for (int itemID : itemIDs) {
            p1Item.getItems().add(itemServ.getName(itemID));
        }
    }
}
