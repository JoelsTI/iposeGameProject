import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.app.scene.SimpleGameMenu;
import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

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
        gameSettings.setMainMenuEnabled(true);
        gameSettings.setSceneFactory(new SceneFactory(){
            @NotNull
            public FXGLMenu newMainMenu(){
                return new JavatarMainMenu();
            }
        });
    }

    @Override
    protected void initGame(){
        FXGL.getGameWorld().addEntityFactory(new GameFactory());
        FXGL.setLevelFromMap("templateLevel.tmx");
        player = getGameWorld().spawn("player");

    }

    @Override
    protected void initInput(){
        getInput().addAction(new UserAction("toLeft") {
            @Override
            protected void onAction() {
                player.getComponent(Player.class).toLeft();
            }
            protected void onActionEnd() {
                player.getComponent(Player.class).stop();
            }
        }, KeyCode.A);

        getInput().addAction(new UserAction("toRight") {
            @Override
            protected void onAction() {
                player.getComponent(Player.class).toRight();
            }

            protected void onActionEnd() {
                player.getComponent(Player.class).stop();
            }
        }, KeyCode.D);

        getInput().addAction(new UserAction("toUp") {
            @Override
            protected void onAction() {
                player.getComponent(Player.class).toUp();
            }

            protected void onActionEnd() {
                player.getComponent(Player.class).stop();
            }
        }, KeyCode.W);

        getInput().addAction(new UserAction("toDown") {
            @Override
            protected void onAction() {
                player.getComponent(Player.class).toDown();
            }

            protected void onActionEnd() {
                player.getComponent(Player.class).stop();
            }
        }, KeyCode.S);
        getInput().addAction(new UserAction("r_left") {
            @Override
            protected void onAction() {
                player.getComponent(Player.class).rotateLeft();
            }
        }, KeyCode.Q);
        getInput().addAction(new UserAction("r_right") {
            @Override
            protected void onAction() {
                player.getComponent(Player.class).rotateRight();
            }

        }, KeyCode.E);
        getInput().addAction(new UserAction("bullet") {
            @Override
            protected void onActionBegin() {
                player.getComponent(Player.class).shoot();
            }

            protected void onActionEnd() {
                player.getComponent(Player.class).stop();
            }
        }, KeyCode.SPACE);
    }
    @Override
    protected void onPreInit() {
        getSettings().setGlobalMusicVolume(100);
        loopBGM("test.mp3");
    }
    @Override
    protected void initPhysics(){
        //gravity 0 voor 2d game
        getPhysicsWorld().setGravity(0,0);
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityTypes.BULLET, EntityTypes.GHOST) {
            @Override
            protected void onCollision(Entity bullet, Entity ghost) {
                FXGL.inc("kills", +1);
                bullet.removeFromWorld();
                ghost.removeFromWorld();
            }
        });

        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityTypes.PLAYER, EntityTypes.GHOST) {
            @Override
            protected void onCollisionEnd(Entity player, Entity ghost) {
                int lives = player.getComponent(Player.class).lostLife();
                FXGL.inc("lives", -1);
                if (lives == 0) {
                    FXGL.getGameController().gotoMainMenu();
                }

            }
        });
    }

    @Override
    protected void initUI(){
        Label myText = new Label();
        Label hp = new Label();

        hp.setTranslateY(220);
        hp.setTranslateX(200);
        hp.setStyle("-fx-text-fill: white");
        hp.textProperty().bind(FXGL.getWorldProperties().intProperty("lives").asString());

        myText.setStyle("-fx-text-fill: white");
        myText.setTranslateX(200);
        myText.setTranslateY(200);
        myText.textProperty().bind(FXGL.getWorldProperties().intProperty("kills").asString());

        FXGL.getGameScene().addUINode(myText);
        FXGL.getGameScene().addUINode(hp);
        FXGL.getGameScene().setBackgroundColor(Color.BLACK);
    }

    @Override
    protected void initGameVars(Map<String, Object> vars){
        vars.put("kills", 0);
        vars.put("lives", 3);
    }
    public static void main(String[] args) {
        launch(args);

    }
}
