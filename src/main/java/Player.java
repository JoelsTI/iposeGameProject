import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import javafx.geometry.Point2D;

import static com.almasb.fxgl.dsl.FXGL.spawn;

public class Player extends Component{
    private PhysicsComponent physics;
    private String direction;
    private Integer movementspeed = 200;




    public void toLeft() {
        direction = "Left";
        System.out.println(direction);
        physics.setVelocityX(-movementspeed);
    }

    public void toRight() {
        direction = "Right";
        System.out.println(direction);
        physics.setVelocityX(movementspeed);
    }

    public void toUp() {
        direction = "Up";
        System.out.println(direction);
        physics.setVelocityY(-movementspeed);
    }

    public void toDown() {
        direction = "Down";
        System.out.println(direction);
        physics.setVelocityY(movementspeed);
    }

    public void stop() {
        physics.setVelocityX(0);
        physics.setVelocityY(0);
    }

    public void shoot() {
        Point2D center = entity.getCenter().subtract(37/2.0, 13/2.0);

        Vec2 dir = Vec2.fromAngle(entity.getRotation() - 90);

        spawn("bullet", new SpawnData(center.getX(), center.getY()).put("dir", dir.toPoint2D()));
    }

    public void rotateLeft(){
        entity.rotateBy(-1);
    }
    public void rotateRight(){
        entity.rotateBy(1);
    }
}