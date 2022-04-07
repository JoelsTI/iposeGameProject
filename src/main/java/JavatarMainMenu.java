

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.beans.binding.StringBinding;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
        TextField textField = new TextField ();
        Button btnStart = new Button("Play");
        Button btnQuit = new Button("Quit");
        Label label = new Label("Username:");
        VBox vbox = new VBox(8);
        textField.setPrefWidth(20);
        textField.setMaxWidth(150);
        vbox.getChildren().addAll(label, textField, btnStart, btnQuit);
        vbox.setAlignment(Pos.CENTER);
        borderPane.setStyle("-fx-background-image: url('https://images5.alphacoders.com/807/thumb-1920-807117.jpg')");
        borderPane.setMinWidth(FXGL.getAppWidth());
        borderPane.setMinHeight(FXGL.getAppHeight());
            btnStart.setOnAction(e -> {
                if(textField.getLength() > 1) {
                    System.out.println(textField.getText());
                    fireNewGame();
                }
            });
        btnQuit.setOnAction(e -> fireExit());

        btnStart.setStyle("-fx-background-color: green ");
        btnQuit.setStyle("-fx-background-color: green ");
        borderPane.setCenter(vbox);

        return borderPane;
    }
}