package celestibytes.ctie.entity;

import celestibytes.ctie.input.BasicGameInput;
import celestibytes.ctie.util.Direction;

public class EntityPlayer extends EntityLiving {
	@Override
	public void positionUpdate(float delta) {
		movingx = true;
		movingy = true;
		float plrSpd = 400f; 
		
		if(BasicGameInput.getPlayerMovingX() == Direction.LEFT) {
			targetdx = -plrSpd;
		} else if(BasicGameInput.getPlayerMovingX() == Direction.RIGHT) {
			targetdx = plrSpd;
		} else {
			movingx = false;
		}
		
		if(BasicGameInput.getPlayerMovingY() == Direction.UP) {
			targetdy = -plrSpd;
		} else if(BasicGameInput.getPlayerMovingY() == Direction.DOWN) {
			targetdy = plrSpd;
		} else {
			movingy = false;
		}
		
		super.positionUpdate(delta);
	}
}
