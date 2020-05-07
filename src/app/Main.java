package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private static ApplicationRunner appRun = new ApplicationRunner();
    PassableServices serv = new PassableServices(appRun);

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("prepareScene.fxml"));
        Controller ctrl = new Controller(serv);
        loader.setController(ctrl);
        Parent root = loader.load();

        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args) {
        appRun.runApplication(args);
        launch(args);
    }

}
