
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.beans.binding.StringBinding;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class JavatarMainMenu extends FXGLMenu {

    public JavatarMainMenu() {
        super(MenuType.MAIN_MENU);

        this.getContentRoot().getChildren().add(this.createLayout());


    }

    public BorderPane createLayout() {
        BorderPane borderPane = new BorderPane();
        Button btnStart = new Button("Lekker spelen");
        Button btnQuit = new Button("Quit");
        VBox vbox = new VBox(8);
        vbox.getChildren().addAll(btnStart, btnQuit);
        vbox.setAlignment(Pos.CENTER);

        borderPane.setMinWidth(FXGL.getAppWidth());
        borderPane.setMinHeight(FXGL.getAppHeight());
        btnStart.setOnAction(e ->{
            fireNewGame();
        });
        btnQuit.setOnAction(e ->{
            fireExit();
        });

        btnStart.setStyle("-fx-background-color: green ");
        btnQuit.setStyle("-fx-background-color: green ");
        borderPane.setCenter(vbox);

        return borderPane;
    }
}

//    protected Button createActionButton(StringBinding stringBinding, Runnable runnable){
//        return new Button();
//    }
//
//
//    protected Node createBackground(double v, double h){
//        return FXGL.texture("pepe.png");
//    }
//
//    protected Node createProfileView(String s){
//        return new Rectangle();
//    }
//
//    protected Node createTitleView(String s){
//        return new Rectangle();
//    }
//
//    protected Node createVersionView(String s){
//        return new Rectangle();
//    }
//
//}
