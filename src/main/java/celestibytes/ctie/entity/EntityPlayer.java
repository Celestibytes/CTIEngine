package celestibytes.ctie.entity;

import org.lwjgl.opengl.GL15;

import okkapel.kkplglutil.rendering.GLHandler;
import okkapel.kkplglutil.rendering.GLRenderMethod;
import okkapel.kkplglutil.rendering.GLRenderObjPointer;
import okkapel.kkplglutil.rendering.RenderBufferGenerator;
import celestibytes.ctie.input.BasicGameInput;
import celestibytes.ctie.util.Direction;

public class EntityPlayer extends EntityLiving {
	
	private GLRenderObjPointer plr_rptr;
	
	public EntityPlayer() {
		float width = 10f;
		float rx = 480f/2f - width/2f;
		float ry = 360f/2f - width/2f;
		RenderBufferGenerator rbg = RenderBufferGenerator.INSTANCE;
		rbg.addVertexWColorWUV(-width/2+rx, -width/2+ry, 0f, 0f, 1f, 0f, 1f, 0f, 0f);
		rbg.addVertexWColorWUV(-width/2+rx, width/2+ry, 0f, 0f, 1f, 0f, 1f, 0f, 0f);
		rbg.addVertexWColorWUV(width/2+rx, width/2+ry, 0f, 0f, 1f, 0f, 1f, 0f, 0f);
		
		rbg.addVertexWColorWUV(width/2+rx, width/2+ry, 0f, 0f, 1f, 0f, 1f, 0f, 0f);
		rbg.addVertexWColorWUV(width/2+rx, -width/2+ry, 0f, 0f, 1f, 0f, 1f, 0f, 0f);
		rbg.addVertexWColorWUV(-width/2+rx, -width/2+ry, 0f, 0f, 1f, 0f, 1f, 0f, 0f);
		this.plr_rptr = GLHandler.createROBJ(rbg.createBuffer(), GL15.GL_STATIC_DRAW, null, 6, GLRenderMethod.VERTEX_BUFFER_OBJECT);
	}
	
	public GLRenderObjPointer getRPTR() {
		return this.plr_rptr;
	}
	
	@Override
	public void positionUpdate(float delta) {
		movingx = true;
		movingy = true;
		float plrSpd = 10f; 
		
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
