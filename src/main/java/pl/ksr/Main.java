package pl.ksr;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Main extends Application {
    private ConfigurableApplicationContext applicationContext;
    private Parent root;

    public static void main(String[] args) {
        launch(Main.class, args);
    }

    @Override
    public void init() throws Exception {
        applicationContext = SpringApplication.run(Main.class);
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/mainView.fxml"));
        fxmlLoader.setControllerFactory(applicationContext::getBean);
        root = fxmlLoader.load();
        applicationContext.stop();
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Generator podsumowań lingwistycznych");
        Scene scene = new Scene(root, 1000, 800);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @Override
    public void stop() {
        applicationContext.stop();
    }
}
