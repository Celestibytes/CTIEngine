package celestibytes.ctie.entity;

import celestibytes.ctie.platformer.Force;
import celestibytes.ctie.util.TWCollPool;
import celestibytes.lib.math.MathHelper;

public abstract class EntityMobile extends EntityBase {
	
	float dx, dy, airResistance, friction, targetdx, targetdy;
	boolean movingx, movingy, movingz;
	float gravity = 1000f;
	
//	private static Force gravity = new Force();
	
	public EntityMobile() {
		
	}
	
	private void velocitySlide(float delta) {
		airResistance = 120f*delta;
		friction = 50f*delta;
		gravity = 40f;
//		float amnt = movingx ? airResistance*2 : airResistance;
		float amnt = movingx ? friction*2 : friction;
		if(!movingx) {
			targetdx = 0f;
		}
		
		if((!movingy || gravity != 0f) && !isOnGround()) {
			targetdy = gravity;
		} else {
			targetdy = 0f;
			if(dy > 0f) {
				dy = 0f;
			}
		}
		
		if(dx != targetdx) {
			dx = MathHelper.clampFloat(dx+(dx < targetdx ? amnt : -amnt), (dx < targetdx ? dx : targetdx), (dx < targetdx ? targetdx : dx));
		}
		
		amnt = movingy ? airResistance*2 : airResistance;
		if(dy != targetdy) {
			dy = MathHelper.clampFloat(dy+(dy < targetdy ? amnt : -amnt), (dy < targetdy ? dy : targetdy), (dy < targetdy ? targetdy : dy));
		}
	}
	
	public boolean isOnGround() {
		return (this.y >= 0);
	}
	
	public void positionUpdate(float delta) {
		velocitySlide(delta);
		this.x += dx*delta;
		this.y += TWCollPool.moveY(dy*delta, this.x, this.y, 1f);
//		System.out.println("X: " + this.x + " Y: " + this.y + " DX: " + this.dx + " DY: " + this.dy);
	}
	
	public void applyForce(Force f) {
		this.dx += f.getDX();
		this.dy += f.getDY();
	}
	
	public void setMotionX(float dx) {
		this.dx = dx;
	}
	
	public void setMotionY(float dy) {
		this.dy = dy;
	}
	
	public void setMotion(float dx, float dy) {
		this.dx = dx;
		this.dy = dy;
	}
	
	public void applyForce(float dx, float dy) {
		this.dx += dx;
		this.dy += dy;
	}
	
	public float getDX() {
		return this.dx;
	}
	
	public float getDY() {
		return this.dy;
	}
	
//	public static void setGravity(Force f) {
//		gravity = f;
//	}
	
}
