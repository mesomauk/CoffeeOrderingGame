import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class JavaFXTemplate extends Application {
    @Override
    //feel free to remove the starter code from this method
    public void start(Stage primaryStage) throws Exception {
        System.out.println(getClass().getResource("/FXML/MyFXML.fxml"));
        System.out.println(getClass().getResource("/styles/style1.css"));

        try {
            Parent root =  FXMLLoader.load((getClass().getResource("/FXML/MyFXML.fxml")));
            primaryStage.setTitle("Mesoma Ukachukwu Make Coffee");
            Scene scene = new Scene(root, 400, 400);
            scene.getStylesheets().add(getClass().getResource("/styles/style1.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        launch(args);
    }
}
