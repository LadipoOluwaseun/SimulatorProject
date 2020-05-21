package app;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

import java.awt.event.MouseEvent;
import java.io.IOException;

public class LoginCtrl extends Controller{

    public TextField user1Fld, user2Fld, pass1Fld, pass2Fld;

    public LoginCtrl(PassableServices serv) {
        super(serv);
    }

    public void login(){
        userServ.addUser(user1Fld.getText(), pass1Fld.getText());
        userServ.addUser(user2Fld.getText(), pass2Fld.getText());
    }
    //------------------//
    // switching scenes //
    //------------------//
    public void  viewPrepare(ActionEvent event) throws IOException {
        this.login();
        changeScene(event, "prepareScene.fxml", new PrepareCtrl(serv));
    }
}
