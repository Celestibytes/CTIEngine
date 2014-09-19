package celestibytes.ctie.entity;

import celestibytes.ctie.platformer.Force;
import celestibytes.lib.math.MathHelper;

public abstract class EntityMobile extends EntityBase {
	
	float dx, dy, airResistance, targetdx, targetdy;
	boolean movingx, movingy, movingz;
	
	private static Force gravity = new Force();
	
	public EntityMobile() {
		airResistance = 2f;
	}
	
	private void velocitySlide() {
		float amnt = movingx ? airResistance*2 : airResistance;
		if(!movingx) {
			targetdx = 0f;
		}
		
		if(!movingy) {
			targetdy = 0f;
		}
		
		if(dx != targetdx) {
			dx = MathHelper.clampFloat(dx+(dx < targetdx ? amnt : -amnt), (dx < targetdx ? dx : targetdx), (dx < targetdx ? targetdx : dx));
		}
		
		amnt = movingy ? airResistance*2 : airResistance;
		if(dy != targetdy) {
			dy = MathHelper.clampFloat(dy+(dy < targetdy ? amnt : -amnt), (dy < targetdy ? dy : targetdy), (dy < targetdy ? targetdy : dy));
		}
	}
	
	public void positionUpdate(float delta) {
		velocitySlide();
		this.x += dx*delta;
		this.y += dy*delta;
	}
	
	public void applyForce(Force f) {
		this.dx += f.getDX();
		this.dy += f.getDY();
	}
	
	public static void setGravity(Force f) {
		gravity = f;
	}
	
}
