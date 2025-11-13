package edu.universidad.estructuras.syncup.app;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Router {
    private static Stage primaryStage;

    public static void setPrimaryStage(Stage stage) { primaryStage = stage; }

    public static void goTo(String fxmlPath, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(Router.class.getResource("/edu/universidad/estructuras/syncup/view/" + fxmlPath));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Router.class.getResource("/com/syncup/css/styles.css").toExternalForm());
        primaryStage.setTitle(title);
        primaryStage.setScene(scene);
        primaryStage.sizeToScene(); // ajuste automático
    }
}
