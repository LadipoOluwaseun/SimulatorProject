package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private static ApplicationRunner appRunner = new ApplicationRunner();
    PassableServices services = new PassableServices(appRunner);

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("prepareScene.fxml"));
        Controller control = new Controller(services);
        loader.setController(control);
        Parent root = loader.load();

        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args) {
        appRunner.runApplication(args);
        launch(args);
    }

}
