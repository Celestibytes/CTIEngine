package celestibytes.ctie.d2;

import okkapel.kkplglutil.util.Colour;

public class Tile {
	public static final Colour STONE = new Colour(.4f,.4f,.4f);
	public static final Colour GRASS = new Colour(.3f,1f,.3f);
	public static final Colour SAND = new Colour(.4f,.8f,.0f);
	public static final Colour WATER = new Colour(.55f,.6f,1f);
	public static final Colour LAVA = new Colour(1f,.3f,.3f);
	
	public static final Colour[] BLOCKCLR = new Colour[] {STONE, GRASS, SAND, WATER, LAVA};
}
