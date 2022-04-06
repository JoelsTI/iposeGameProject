import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import static com.almasb.fxgl.dsl.FXGL.*;

public class Game extends GameApplication{

    private Entity player;

    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setFullScreenFromStart(true);
        gameSettings.setFullScreenAllowed(true);
        gameSettings.setTitle("Javatar Game");
        gameSettings.setVersion("1.0");
    }

    @Override
    protected void initGame(){
        getGameWorld().addEntityFactory(new GameFactory());
        player = FXGL.entityBuilder()
                .at(400, 400)
                .viewWithBBox("")
                .with(new CollidableComponent(true))
                .type(EntityTypes.PLAYER)
                .buildAndAttach();
        FXGL.getGameTimer().runAtInterval(() -> {
            int randPosX = ThreadLocalRandom.current().nextInt(60, FXGL.getGameScene().getAppWidth() -80);
            int randPosY = ThreadLocalRandom.current().nextInt(60, FXGL.getGameScene().getAppWidth() -80);
            FXGL.entityBuilder()
                    .at(randPosX, randPosY)
                    .viewWithBBox(new Rectangle(20, 20, Color.WHITE))
                    .with(new CollidableComponent(true))
                    .type(EntityTypes.ENTITEIT)
                    .buildAndAttach();
        }, Duration.millis(2000));

    }
    @Override
    protected void initInput(){
        FXGL.onKey(KeyCode.D, () -> player.translateX(5));

        FXGL.onKey(KeyCode.A, () -> player.translateX(-5));

        FXGL.onKey(KeyCode.W, () -> player.translateY(-5));

        FXGL.onKey(KeyCode.S, () -> player.translateY(5));
        getInput().addAction(new UserAction("Shoot") {
            @Override
            protected void onActionBegin() {
                getGameWorld().spawn("Bullet", player.getX() + 28, player.getY());
            }
        }, KeyCode.E);
    }

    @Override
    protected void initPhysics(){
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityTypes.PLAYER, EntityTypes.ENTITEIT) {
            @Override
            protected void onCollision(Entity player, Entity entiteit) {
                FXGL.inc("kills", +1);
                entiteit.removeFromWorld();
            }
        });
    }

    @Override
    protected void initUI(){
        Label myText = new Label("Hello There");
        myText.setStyle("-fx-text-fill: white");
        myText.setTranslateX(200);
        myText.setTranslateY(200);
        myText.textProperty().bind(FXGL.getWorldProperties().intProperty("kills").asString());
        FXGL.getGameScene().addUINode(myText);
        FXGL.getGameScene().setBackgroundColor(Color.BLACK);
    }

    @Override
    protected void initGameVars(Map<String, Object> vars){
        vars.put("kills", 0);
    }
    public static void main(String[] args) {
        launch(args);
    }
}
