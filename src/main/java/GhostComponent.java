import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.Texture;
import javafx.util.Duration;


import java.util.Random;


public class GhostComponent extends Component {

    private final Texture upDown;


    public GhostComponent(String name, double x, double y) {
        Texture left = FXGL.texture(name + "-left.png");
        Texture rigth = FXGL.texture(name + "-right.png");
        upDown = FXGL.texture(name + "monster.png");
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
                    physics.setVelocityY(50);
                    break;

                case 1:
                    physics.setVelocityY(-50);
                    break;

                case 2:
                    physics.setVelocityX(50);
                    break;

                case 3:
                    physics.setVelocityX(-50);
                    break;

            }

        }, Duration.seconds(1));
    }

    Random rand = new Random();


    @Override
    public void onUpdate(double tpf) {

    }
}
