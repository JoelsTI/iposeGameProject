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
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC);
        player = FXGL.entityBuilder()
                .at(400, 400)
                .viewWithBBox("-up-down.png")
                .with(physics)
                .with(new CollidableComponent(true))
                .with(new Player())
                .type(EntityTypes.PLAYER)
                .buildAndAttach();


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
                Point2D center = player.getCenter();//.subtract(37/2.0, 13/2.0);

                Vec2 dir = Vec2.fromAngle(player.getRotation() - 90);
                System.out.println(dir);
                System.out.println(dir.toPoint2D());
                spawn("Bullet", new SpawnData(center.getX(), center.getY()).put("dir", dir.toPoint2D()));
            }
        }, KeyCode.SPACE);
        FXGL.onKey(KeyCode.E, () -> player.rotateBy(1));
        FXGL.onKey(KeyCode.Q, () -> player.rotateBy(-1));

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

    @Override
    protected void initGameVars(Map<String, Object> vars){
        vars.put("kills", 0);
    }
    public static void main(String[] args) {
        launch(args);

    }
}
