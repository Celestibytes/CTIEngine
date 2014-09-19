package celestibytes.ctie.entity;

import celestibytes.ctie.input.BasicGameInput;
import celestibytes.ctie.util.Direction;

public class EntityPlayer extends EntityLiving {
	@Override
	public void positionUpdate(float delta) {
		movingx = true;
		movingy = true;
		
		if(BasicGameInput.getPlayerMovingX() == Direction.LEFT) {
			targetdx = -100f;
		} else if(BasicGameInput.getPlayerMovingX() == Direction.RIGHT) {
			targetdx = 100f;
		} else {
			movingx = false;
		}
		
		if(BasicGameInput.getPlayerMovingY() == Direction.UP) {
			targetdy = -100f;
		} else if(BasicGameInput.getPlayerMovingY() == Direction.DOWN) {
			targetdy = 100f;
		} else {
			movingy = false;
		}
		
		super.positionUpdate(delta);
	}
}
