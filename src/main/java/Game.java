import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.CollisionHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

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
        player = FXGL.entityBuilder()
                .at(400, 400)
                .viewWithBBox("pepe.png")
                .with(new CollidableComponent(true))
                .scale(0.2, 0.2)
                .type(EntityTypes.PLAYER)
                .buildAndAttach();
        FXGL.entityBuilder()
                .at(200, 200)
                .viewWithBBox(new Rectangle(20, 20, Color.WHITE))
                .with(new CollidableComponent(true))
                .type(EntityTypes.ENTITEIT)
                .buildAndAttach();
        FXGL.getGameScene().setBackgroundColor(Color.BLACK);
    }
    @Override
    protected void initInput(){
        FXGL.onKey(KeyCode.D, () -> {
            player.translateX(5);
        });
        FXGL.onKey(KeyCode.A, () -> {
            player.translateX(-5);
        });
        FXGL.onKey(KeyCode.W, () -> {
            player.translateY(-5);
        });
        FXGL.onKey(KeyCode.S, () -> {
            player.translateY(5);
        });

    }

    @Override
    protected void initPhysics(){
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityTypes.PLAYER, EntityTypes.ENTITEIT) {
            @Override
            protected void onCollision(Entity player, Entity entiteit) {
                entiteit.removeFromWorld();
            }
        });
    }
    public static void main(String[] args) {
        launch(args);
    }
}
