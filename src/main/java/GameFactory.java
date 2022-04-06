import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.IntervalPauseComponent;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.*;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.sun.scenario.DelayedRunnable;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.concurrent.Delayed;

/**
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 */
public class GameFactory implements EntityFactory {

//    @Spawns("Player")
//    public Entity newPlayer(SpawnData data){
//        PhysicsComponent physics = new PhysicsComponent();
//        physics.setBodyType(BodyType.DYNAMIC);
//
//        physics.addGroundSensor{
//            new HitBox("GROUND_SENSOR", new Point2D(16, 30), BoundingShape.box(6, 8))
//        }
//    };
//    physics.setFixtureDef(new FixtureDef().friction(0.0f));
//
//    return entityBuilder(data)
//    .type(EntityTypes.PLAYER)

    @Spawns("Bullet")
    public Entity newBullet(SpawnData data) throws InterruptedException {

        Point2D dir = data.get("dir");
        return FXGL.entityBuilder(data)
                .type(EntityTypes.BULLET)
                .viewWithBBox(new Circle(10, 2, 10, Color.BLUE))
                .with(new CollidableComponent(true))
                .with(new ProjectileComponent(dir, 100))
                .build();
    }

    @Spawns("Ghost")
    public Entity spawnGhost(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(EntityTypes.GHOST)
                .bbox(new HitBox(BoundingShape.box(20, 20)))
                .with(new GhostComponent(data.get("name"), data.getX(), data.getY()))
                .collidable()
                .build();
    }

//    @Spawns("border")
//    public Entity Border(SpawnData data) {
//
//    }
}
