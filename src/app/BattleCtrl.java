package app;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import services.CharacterService;
import services.ItemService;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class BattleCtrl extends Controller {
    public ChoiceBox p1Item, p2Item;
    public int charID1, charID2;
    public Label p1Char, p2Char;
    public ArrayList<Integer> itemIDs1, itemIDs2;
    public ProgressBar p1Bar, p2Bar;

    public BattleCtrl(PassableServices services, ArrayList<Integer> charIDs) {
        super(services);
        charID1 = charIDs.get(0);
        charID2 = charIDs.get(1);
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
        for (int itemID : itemIDs) { drop.getItems().add(itemServ.getName(itemID)); }
    }

    public void startTurn(ActionEvent event) throws IOException {
        int ItemID_1 = ((IDHolder) p1Item.getSelectionModel().getSelectedItem()).getID();
        int ItemID_2 = ((IDHolder) p2Item.getSelectionModel().getSelectedItem()).getID();
        itemServ.executeTurn(charID1, charID2, ItemID_1, ItemID_2);
        p1Bar.setProgress(charServ.getHealth(charID1)/100.0);
        p2Bar.setProgress(charServ.getHealth(charID2)/100.0);
        boolean p1Alive = p1Bar.getProgress() != 0;
        boolean p2Alive = p2Bar.getProgress() != 0;
        if (!p1Alive && !p2Alive) {
            System.out.println("tie!");
        } else if (!p1Alive) {
            System.out.println("p1 loses!");
        } else if (!p2Alive) {
            System.out.println("p2 loses!");
        }
        if (!(p1Alive && p2Alive)) {
            changeScene(event, "prepareScene.fxml", new PrepareCtrl(serv));
        }
    }
}
