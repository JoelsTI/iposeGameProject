import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.HitBox;
import com.sun.javafx.geom.Point2D;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.scene.layout.BorderPane;
import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.entity.GameWorld;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import javafx.scene.layout.FlowPane;

import static com.almasb.fxgl.dsl.FXGL.*;

public class Game extends GameApplication{

    private Entity player;

    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setFullScreenFromStart(true);
        gameSettings.setFullScreenAllowed(true);
        gameSettings.setWidth(15 * 70);
        gameSettings.setHeight(10 * 70);
        gameSettings.setTitle("Javatar Game");
        gameSettings.setVersion("1.0");
        gameSettings.setDeveloperMenuEnabled(true);
    }

    protected void initScreenBounds() {
        Entity walls = entityBuilder()
                .type(EntityTypes.WALL)
                .collidable()
                .buildScreenBounds(150);

        getGameWorld().addEntity(walls);
    }

    @Override
    protected void initGame(){
        FXGL.getGameWorld().addEntityFactory(new GameFactory());
        FXGL.setLevelFromMap("templateLevel.tmx");
//        FXGL.spawn("Player");

//        player = FXGL.entityBuilder()
//                .at(400, 400)
//                .viewWithBBox("-up-down.png")
//                .with(new CollidableComponent(true))
//                .type(EntityTypes.PLAYER)
//                .buildAndAttach();
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

        initScreenBounds();

    }
    @Override
    protected void initInput(){
//        FXGL.onKey(KeyCode.D, () -> player.translateX(5));
//
//        FXGL.onKey(KeyCode.A, () -> player.translateX(-5));
//
//        FXGL.onKey(KeyCode.W, () -> player.translateY(-5));
//
//        FXGL.onKey(KeyCode.S, () -> player.translateY(5));
//        getInput().addAction(new UserAction("Shoot") {
//            @Override
//            protected void onActionBegin() {
//                spawn("Bullet", player.getX() + 29, player.getY());
//            }
//        }, KeyCode.SPACE);
//        FXGL.onKey(KeyCode.Q, () -> player.rotateBy(1));
//        FXGL.onKey(KeyCode.E, () -> player.rotateBy(-1));

        String direction;
        Integer movementspeed = 200;

//        getInput().addAction(new UserAction("Move up") {
//            @Override
//            protected void onActionBegin() {
//                FXGL.player.moveUp();
//            }
//        }, KeyCode.W);
    }
    @Override
    protected void onPreInit() {
        getSettings().setGlobalMusicVolume(100);
        loopBGM("test.mp3");
    }
    @Override
    protected void initPhysics(){
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityTypes.BULLET, EntityTypes.ENTITEIT) {
            @Override
            protected void onCollision(Entity bullet, Entity entiteit) {
                FXGL.inc("kills", +1);
                bullet.removeFromWorld();
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

    private static final double SPEED = 100.0;
    private double dx = 0.0;
    private double dy = -SPEED;

    @Override
    public void onUpdate(double tpf){
//        ghost.translateX(dx * tpf);
//        ghost.translateY(dy * tpf);
    }

    @Override
    protected void initGameVars(Map<String, Object> vars){
        vars.put("kills", 0);
    }
    public static void main(String[] args) {
        launch(args);
    }
}
