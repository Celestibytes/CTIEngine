package celestibytes.ctie.util;

import celestibytes.ctie.util.CollisionHelper.CollisionShape;

public interface ICollidable {
	public int getX();
	public int getY();
	
	public CollisionShape getCollShape();
}
