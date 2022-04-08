import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.Texture;
import javafx.util.Duration;
import java.util.Random;


public class BosComponent extends Component {
    private final Texture upDown;
    private Integer bossLives = 30;
    private Integer movementspeed = 200;

    public BosComponent(String name, double x, double y) {
        Texture left = FXGL.texture(name + "-left.png");
        Texture rigth = FXGL.texture(name + "-right.png");
        upDown = FXGL.texture(name + "poep.png");
    }
    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(upDown);

        FXGL.getGameTimer().runAtInterval(() -> {
            if(entity == null){
                return;
            }
            PhysicsComponent physics = entity.getComponent(PhysicsComponent.class);

            int movementRandom = rand.nextInt(4);


            switch (movementRandom) {
                case 0:
                    physics.setVelocityY(movementspeed);
                    break;

                    case 1:
                        physics.setVelocityY(-movementspeed);
                        break;

                    case 2:
                        physics.setVelocityX(movementspeed);
                        break;

                    case 3:
                        physics.setVelocityX(-movementspeed);
                        break;

                }
            }, Duration.seconds(0.4));
        }
    Random rand = new Random();


    @Override
    public void onUpdate(double tpf) {

    }

    public int lostLife() {
        this.setLives(this.getLives() - 1);

        return this.getLives();
    }

    public Integer getLives() {
        return bossLives;
    }

    public void setLives(Integer bossLives) {
        this.bossLives = bossLives;
    }
}

