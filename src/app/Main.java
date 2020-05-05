package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private static ApplicationRunner appRunner = new ApplicationRunner();
    Stage window;

    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = fxmlLoader.load();
        Controller controller = fxmlLoader.getController();
        PassableServices services = new PassableServices(appRunner);
        controller.setupCharacterList(services);
        controller.initialize();
        window.setTitle("Prototpye Start Screen");
        window.setScene(new Scene(root, 1000, 700));
        window.show();
    }

    public static void main(String[] args) {
        appRunner.runApplication(args);
        launch(args);
    }

}
