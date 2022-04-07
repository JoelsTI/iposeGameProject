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
    private Integer rotationSpeed = 0;

    private Integer lives = 3;



    public void toLeft() {
        direction = "left";
        physics.setVelocityX(-movementspeed);
    }

    public void toRight() {
        direction = "right";
        physics.setVelocityX(movementspeed);
    }

    public void toUp() {
        direction = "up";
        physics.setVelocityY(-movementspeed);
    }

    public void toDown() {
        direction = "down";
        physics.setVelocityY(movementspeed);
    }

    public void rotateLeft(){
        direction = "r_left";
        physics.setAngularVelocity(rotationSpeed - 40);
    }

    public void rotateRight(){
        direction = "r_right";
        physics.setAngularVelocity(rotationSpeed + 40);
    }

    public void stop() {
        physics.setVelocityX(0);
        physics.setVelocityY(0);
    }

    public void rotateStop() {
        physics.setAngularVelocity(0);
    }

    public void shoot() {
        Point2D center = entity.getCenter().subtract(37/2.0, 13/2.0);
        Vec2 dir = Vec2.fromAngle(entity.getRotation() - 90);
        spawn("bullet", new SpawnData(center.getX(), center.getY()).put("dir", dir.toPoint2D()));
    }

    public int lostLife() {
        this.setLives(this.getLives() - 1);

        return this.getLives();
    }

    public Integer getLives() {
        return lives;
    }

    public void setLives(Integer lives) {
        this.lives = lives;
    }
}