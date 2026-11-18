package app;
import javafx.application.Application;import javafx.fxml.FXMLLoader;import javafx.scene.Scene;import javafx.stage.Stage;
public class App extends Application {
 @Override public void start(Stage stage)throws Exception{
  FXMLLoader loader=new FXMLLoader(App.class.getResource("/view/main-view.fxml"));
  Scene scene=new Scene(loader.load());
  
  stage.setScene(scene);stage.setTitle("Cursos de Capacitación | DAO");stage.show();
 }
 public static void main(String[] args){launch(args);}
}
