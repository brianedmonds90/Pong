import org.jbox2d.collision.AABB;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;


public class PhysicsWorld {
	private World world;
	public PhysicsWorld(){
		world=new World(new AABB(),new Vec2(0,0), false);
	}
}
