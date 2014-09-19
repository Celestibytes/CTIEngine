package celestibytes.ctie.d2;

import okkapel.kkplglutil.rendering.GLHandler;
import okkapel.kkplglutil.rendering.GLRenderObjPointer;
import okkapel.kkplglutil.rendering.RenderBufferGenerator;

import org.lwjgl.opengl.GL15;

public class World {
	public short[] tiles;
	
	private GLRenderObjPointer rptr;
	
	public World() {
		tiles = new short[32*32];
		
		RenderBufferGenerator rbg = RenderBufferGenerator.INSTANCE;
		
		float tilesize = 25f;
		
		for(int x=0;x<32;x++) {
			for(int y=0;y<32;y++) {
				rbg.addVertexWColorWUV(x*tilesize, y*tilesize, 0f, Tile.BLOCKCLR[(y*32+x)%5], 0f, 0f);
				rbg.addVertexWColorWUV(x*tilesize, (y+1)*tilesize, 0f, Tile.BLOCKCLR[(y*32+x)%5], 0f, 0f);
				rbg.addVertexWColorWUV((x+1)*tilesize, (y+1)*tilesize, 0f, Tile.BLOCKCLR[(y*32+x)%5], 0f, 0f);
				
				rbg.addVertexWColorWUV((x+1)*tilesize, (y+1)*tilesize, 0f, Tile.BLOCKCLR[(y*32+x)%5], 0f, 0f);
				rbg.addVertexWColorWUV((x+1)*tilesize, y*tilesize, 0f, Tile.BLOCKCLR[(y*32+x)%5], 0f, 0f);
				rbg.addVertexWColorWUV(x*tilesize, y*tilesize, 0f, Tile.BLOCKCLR[(y*32+x)%5], 0f, 0f);
			}
		}
		
		rptr = GLHandler.createROBJ(rbg.createBuffer(), GL15.GL_STATIC_DRAW, null, 32*32*6);
		
	}
	
	public void renderWorld() {
		GLHandler.renderRendPtr(rptr);
	}
	
}
