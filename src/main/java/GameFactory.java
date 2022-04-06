import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.*;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

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
    public Entity newBullet(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(EntityTypes.BULLET)
                .viewWithBBox(new Circle(10, 2, 2, Color.BLUE))
                .with(new CollidableComponent(true))
                .with(new ProjectileComponent(new Point2D(0, -1), 300))
                .build();
    }
    @Spawns("Enemy")
    public Entity newEnemy(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(EntityTypes.ENEMY)
                .viewWithBBox(new Rectangle(40, 40, Color.RED))
                .with(new CollidableComponent(true))
                .build();
    }

//    @Spawns("border")
//    public Entity Border(SpawnData data) {
//
//    }
}
