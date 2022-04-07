import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.Effect;
import com.almasb.fxgl.dsl.components.EffectComponent;
import com.almasb.fxgl.dsl.components.OffscreenCleanComponent;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.*;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.entity.components.TimeComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyDef;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.dsl.components.RandomMoveComponent;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import static com.almasb.fxgl.core.math.FXGLMath.random;
import static com.almasb.fxgl.dsl.FXGL.entityBuilder;

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

    @Spawns("player")
    public Entity newPlayer(SpawnData data){
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC);

        return FXGL.entityBuilder(data)
                .viewWithBBox("isaac.png")
                .scale(0.2,0.2)
                .with(physics)
                .with(new CollidableComponent(true))
                .with(new Player())
                .type(EntityTypes.PLAYER)
                .build();
    }

    @Spawns("bullet")
    public Entity newBullet(SpawnData data) {
        Point2D dir = data.get("dir");
        var effectComponent = new EffectComponent();
        var e = entityBuilder(data)
                .type(EntityTypes.BULLET)
                .viewWithBBox("tear.png")
                .with(new ProjectileComponent(dir, 500))
                .with(new OffscreenCleanComponent())
                .with(new TimeComponent())
                .with(effectComponent)
                .collidable()
                .build();

        e.setOnActive(() -> {
            effectComponent.startEffect(new SuperSlowTimeEffect());
        });

        return e;
    }

    class SuperSlowTimeEffect extends Effect {

        public SuperSlowTimeEffect() {
            super(Duration.seconds(0.5));
        }

        @Override
        public void onStart(Entity entity) {
            entity.getComponent(TimeComponent.class).setValue(0.05);
        }

        @Override
        public void onEnd(Entity entity) {
            entity.getComponent(TimeComponent.class).setValue(3.0);
        }
    }

    @Spawns("platform")
    public Entity newPlatform(SpawnData data){
        return FXGL.entityBuilder(data)
                .type(EntityTypes.PLATFORM)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new PhysicsComponent())
                .build();
    }

    @Spawns("wall")
    public Entity newwall(SpawnData data){
        return FXGL.entityBuilder(data)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new PhysicsComponent())
                .build();
    }


    @Spawns("boss")
    public Entity spawnBoss(SpawnData data){
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC);

        return FXGL.entityBuilder(data)
                .bbox(new HitBox(BoundingShape.box(150, 150)))
                .with(new BosComponent(data.get("name"), data.getX(), data.getY()))
                .with(new RandomMoveComponent(new Rectangle2D(0,0,615,615), random(50,120)))
                .with(physics)
                .with(new CollidableComponent(true))
                .type(EntityTypes.BOSS)
                .build();
    }
    @Spawns("ghost")
    public Entity spawnGhost(SpawnData data){
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC);

        return FXGL.entityBuilder(data)
                .bbox(new HitBox(BoundingShape.box(20, 20)))
                .scale(0.5, 0.5)
                .with(new GhostComponent(data.get("name"), data.getX(), data.getY()))
                .with(new RandomMoveComponent(new Rectangle2D(0,0,615,615), random(50,120)))
                .with(physics)
                .with(new CollidableComponent(true))
                .type(EntityTypes.GHOST)
                .build();
    }


}