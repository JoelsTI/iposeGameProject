import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.app.scene.SimpleGameMenu;
import com.almasb.fxgl.core.collection.PropertyMap;
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
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import static com.almasb.fxgl.dsl.FXGL.*;


public class Game extends GameApplication{
    double tijd = 0;
    private Entity player;
    private int levelCounter = 1;


    @Override
    protected void initSettings(GameSettings gameSettings) {
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
        FXGL.setLevelFromMap("level" + this.levelCounter + ".tmx");
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
            protected void onActionEnd() {
                player.getComponent(Player.class).rotateStop();
            }
        }, KeyCode.Q);
        getInput().addAction(new UserAction("r_right") {
            @Override
            protected void onAction() {
                player.getComponent(Player.class).rotateRight();
            }
            protected void onActionEnd() {
                player.getComponent(Player.class).rotateStop();
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
        getSettings().setGlobalMusicVolume(3);
        loopBGM("test.mp3");
    }

    @Override
    public void onUpdate(double tpf) {
        inc("levelTime", tpf);
       int kills = FXGL.getWorldProperties().getInt("kills");
        if(this.levelCounter == 1 && kills == 6 ){
            this.levelCounter++;
            System.out.println("Ga naar het volgende level!");
            FXGL.setLevelFromMap("level" + this.levelCounter + ".tmx");
            player = getGameWorld().spawn("player");
        }
        else if(this.levelCounter == 2 && kills == 30 ){
            this.levelCounter++;
            System.out.println("Ga naar het volgende level!");
            FXGL.setLevelFromMap("level" + this.levelCounter + ".tmx");
            player = getGameWorld().spawn("player");
        }
        else if(this.levelCounter == 3 && kills ==  40){
            this.levelCounter = 1;
            System.out.println("BOSS VERSLAGEN");
            player.removeFromWorld();
            FXGL.getGameController().gotoMainMenu();
            Duration userTime = Duration.seconds(getd("levelTime") );
            tijd = userTime.toMillis();
            tijd = Math.round(tijd);
            tijd = tijd / 1000;
            System.out.println(tijd + " seconden over de game gedaan");
            // Assigning the content of the file
            String text
                    =String.valueOf(tijd) + " seconden gespeeld";
            try {
                FileWriter myWriter = new FileWriter("C:\\Users\\Joel\\Desktop\\FXGLGames-master\\VERBETERING\\src\\main\\java\\score.txt", true);
                myWriter.append(text);
                myWriter.append("\n--------------------------------------------------------");
                myWriter.append("\n");
                myWriter.close();
                System.out.println("Successfully wrote to the file.");
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
        Label bossHP = new Label();
        Label bossText = new Label("Boss hp:");

        bossHP.setStyle("-fx-text-fill: white");
        bossHP.setTranslateY(40);
        bossHP.setTranslateX(65);
        bossHP.textProperty().bind(FXGL.getWorldProperties().intProperty("bossLives").asString());

        bossText.setTranslateY(40);
        bossText.setTranslateX(20);
        bossText.setStyle("-fx-text-fill: white");

        if(this.levelCounter == 3){
            FXGL.getGameScene().addUINode(bossHP);
            FXGL.getGameScene().addUINode(bossText);
        }
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
                if (lives <= 0) {
                    FXGL.getGameController().gotoMainMenu();
                    try {
                        FileWriter myWriter = new FileWriter("C:\\Users\\Joel\\Desktop\\FXGLGames-master\\VERBETERING\\src\\main\\java\\score.txt", true);
                        myWriter.append("DOOD GEGAAN! WAT EEN NOOB!");
                        myWriter.append("\n--------------------------------------------------------");
                        myWriter.append("\n");
                        myWriter.close();
                        System.out.println("Successfully wrote to the file.");
                    } catch (IOException e) {
                        System.out.println("An error occurred.");
                        e.printStackTrace();
                    }
                }
            }
        });
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityTypes.PLAYER, EntityTypes.BOSS) {
            @Override
            protected void onCollisionEnd(Entity player, Entity boss) {
                super.onCollisionEnd(player, boss);
                int lives = player.getComponent(Player.class).lostLife();
                FXGL.inc("lives",-1);
                if (lives <= 0) {
                    System.out.println(lives);
                    FXGL.getGameController().gotoMainMenu();
                    try {
                        FileWriter myWriter = new FileWriter("C:\\Users\\Joel\\Desktop\\FXGLGames-master\\VERBETERING\\src\\main\\java\\score.txt", true);
                        myWriter.append("DOOD GEGAAN! WAT EEN NOOB!");
                        myWriter.append("\n--------------------------------------------------------");
                        myWriter.append("\n");
                        myWriter.close();
                        System.out.println("Successfully wrote to the file.");
                    } catch (IOException e) {
                        System.out.println("An error occurred.");
                        e.printStackTrace();
                    }

                }
            }
        });
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityTypes.BULLET, EntityTypes.BOSS) {
            @Override
            protected void onCollision(Entity bullet, Entity boss) {
                bullet.removeFromWorld();
                int bossLives = boss.getComponent(BosComponent.class).lostLife();
                FXGL.inc("bossLives",-1);
                if (bossLives == 0) {
                    FXGL.inc("kills", +10);
                    boss.removeFromWorld();
                }
            }
        });
    }

    @Override
    protected void initUI(){
        Label kills = new Label();
        Label hp = new Label();
        Label hpText = new Label("Lives:");
        Label killsText = new Label("Kills:");
        Label bossHP = new Label("bossLives");

        hp.setTranslateY(10);
        hp.setTranslateX(50);
        hp.setStyle("-fx-text-fill: white");
        hp.textProperty().bind(FXGL.getWorldProperties().intProperty("lives").asString());

        hpText.setTranslateY(10);
        hpText.setTranslateX(20);
        hpText.setStyle("-fx-text-fill: white");

        kills.setStyle("-fx-text-fill: white");
        kills.setTranslateY(25);
        kills.setTranslateX(45);
        kills.textProperty().bind(FXGL.getWorldProperties().intProperty("kills").asString());

        killsText.setTranslateY(25);
        killsText.setTranslateX(20);
        killsText.setStyle("-fx-text-fill: white");

        bossHP.textProperty().bind(FXGL.getWorldProperties().intProperty("bossLives").asString());

        FXGL.getGameScene().addUINode(kills);
        FXGL.getGameScene().addUINode(hp);
        FXGL.getGameScene().addUINode(hpText);
        FXGL.getGameScene().addUINode(killsText);
        FXGL.getGameScene().setBackgroundColor(Color.BLACK);
    }

    @Override
    protected void initGameVars(Map<String, Object> vars){
        vars.put("kills", 0);
        vars.put("lives", 5);
        vars.put("levelTime", 0.0);
        vars.put("bossLives", 30);
    }
    public static void main(String[] args) {
        launch(args);
    }
}
