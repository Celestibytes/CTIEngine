package celestibytes.ctie.util;

import celestibytes.ctie.d2.ITileWorld;
import celestibytes.lib.math.MathHelper;

// TileWorldCollisionPool
public class TWCollPool {
	
	public TWCollPool(ITileWorld tworld) {
		this.tworld = tworld;
	}
	
	private ITileWorld tworld;
	
	/** Returns the dx so that the entity can't go thru walls. */
	public float moveX(float dx, float x, float y, float width) {
		float ret = 0f;
		int add = dx < 0f ? -1 : 1;
		for(int ix = (int)x; (dx <= 0f ? ix >= (int)(x+dx) : ix < (int)(x+dx)); dx+=add) {
			if(tworld.isTileSolidAt(ix, (int)y)) {
				return 3f;
			} else {
				if(ix == (int)(x+dx)) {
					return dx;
				}
				ret += 1f;
			}
		}
		return ret; // Only reached if the player is in an "exact coordinate" like (1f,2f) or (2f, 2f)
	}
	
	public static float moveY(float dy, float x, float y, float height) {
		float ground = 000f;
		
		return MathHelper.clampFloat(y+dy, y+dy, ground-height*2)-y;
	}
}
