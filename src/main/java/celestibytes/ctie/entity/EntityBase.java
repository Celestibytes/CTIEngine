package celestibytes.ctie.entity;

import celestibytes.ctie.d2.World;

public abstract class EntityBase {
	float x, y;
	World world;
	
	
	public float getX() {
		return this.x;
	}
	
	public float getY() {
		return this.y;
	}
	
}
