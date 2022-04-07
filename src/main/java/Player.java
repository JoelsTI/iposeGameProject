import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import javafx.geometry.Point2D;

public class Player extends Component{
    private PhysicsComponent physics;
    public void left() {
        getEntity().setScaleX(-1);
        physics.setVelocityX(-170);
    }
    public void stop() {
        physics.setVelocityX(0);
    }
    public void rotateLeft(){
        entity.rotateBy(-1);
    }
    public void rotateRight(){
        entity.rotateBy(1);
    }
}


