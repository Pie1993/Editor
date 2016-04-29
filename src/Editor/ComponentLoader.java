package Editor;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.texture.Texture;

public class ComponentLoader {

	public AssetManager assetManager;
	private Material materials[];
	private Texture texture[];
	private Texture terrainTexture;
	private int sizeMaterials = 62;
	private int sizeTextures = 52;

	private ComponentLoader() {

	}

	public static ComponentLoader getIstance() {
		if (componentLoader == null)
			componentLoader = new ComponentLoader();
		return componentLoader;

	}

	public Material getMaterial(CubeType type) {

		switch (type) {
		case ANVIL:
			return materials[0];
		case BOX:
			return materials[1];
		case BRICK:
			return materials[2];
		case COBBLESTONE:
			return materials[3];
		case COBBLESTONEMESSY:
			return materials[4];
		case DIRT:
			return materials[5];
		case DIRT2:
			return materials[6];
		case DISPENSER:
			return materials[7];
		case GOLD:
			return materials[8];
		case GRASS:
			return materials[9];
		case GRAVEL:
			return materials[10];
		case ICE:
			return materials[11];
		case IRON:
			return materials[12];
		case IRONPANEL:
			return materials[13];
		case LANTERN:
			return materials[14];
		case LAPIS:
			return materials[15];
		case MYCELIUM:
			return materials[16];
		case NETHERBRICK:
			return materials[17];
		case NETHER:
			return materials[18];
		case OBSIDIAN:
			return materials[19];
		case PRISMARINE:
			return materials[20];
		case QUARZ:
			return materials[21];
		case QUARZ2:
			return materials[22];
		case SAND:
			return materials[23];
		case SANDSTONE:
			return materials[24];
		case SANDSTONE2:
			return materials[25];
		case SNOW:
			return materials[26];
		case STONEBRICK:
			return materials[27];
		case STONECARVED:
			return materials[28];
		case STONE:
			return materials[29];
		case STONEFLOOR:
			return materials[30];
		case STONEMOSSY:
			return materials[31];
		case WOOD:
			return materials[32];
		case WOOD2:
			return materials[33];
		case WOOD3:
			return materials[34];
		case WOOD4:
			return materials[35];
		case WOOD5:
			return materials[36];
		case BLACK:
			return materials[37];
		case BLUE:
			return materials[38];
		case BROWN:
			return materials[39];
		case GRAY:
			return materials[40];
		case GREEN:
			return materials[41];
		case LIGHTBLUE:
			return materials[42];
		case ORANGE:
			return materials[43];
		case PINK:
			return materials[44];
		case PURPLE:
			return materials[45];
		case RED:
			return materials[46];
		case WHITE:
			return materials[47];
		case YELLOW:
			return materials[48];
		case MINHEALTH:
			return materials[49];
		case MINARMOR:
			return materials[50];
		case MIDHEALTH:
			return materials[51];
		case MIDARMOR:
			return materials[52];
		case MAXHEALTH:
			return materials[53];
		case MAXARMOR:
			return materials[54];
		case ULTRADAMAGE:
			return materials[55];
		case REGENERATION:
			return materials[56];
		case HASTE:
			return materials[57];
		case UNTOUCHABLE:
			return materials[58];
		case AMMO:
			return materials[59];
		case SNIPER:
			return materials[60];
		case RIFLE:
			return materials[61];
		default:
			return materials[0];

		}
	}

	public void init(AssetManager assetManager) {
		this.assetManager = assetManager;
		// load texture

		// material
		materials = new Material[sizeMaterials];
		texture = new Texture[sizeTextures];
		for (int i = 0; i < sizeMaterials; i++)
			materials[i] = new Material(assetManager,
					"Common/MatDefs/Misc/Unshaded.j3md");
		loadTextures();
		loadMaterials();
		terrainTexture = texture[9];

		// materials[4].setColor("Color", ColorRGBA.Blue); // health
		// materials[5].setColor("Color", ColorRGBA.Yellow); // haste
		// materials[6].setColor("Color", ColorRGBA.Red); // ammo
		// materials[7].setColor("Color", ColorRGBA.Gray); // armor
		// materials[8].setColor("Color", ColorRGBA.Green); // regeneration
		// materials[9].setColor("Color", ColorRGBA.LightGray); // ultradamage
		// materials[10].setColor("Color", ColorRGBA.Cyan); // untouchable

	}

	private void loadMaterials() {

		materials[0].setTexture("ColorMap", texture[0]);
		materials[1].setTexture("ColorMap", texture[1]);
		materials[2].setTexture("ColorMap", texture[2]);
		materials[3].setTexture("ColorMap", texture[3]);
		materials[4].setTexture("ColorMap", texture[4]);
		materials[5].setTexture("ColorMap", texture[5]);
		materials[6].setTexture("ColorMap", texture[6]);
		materials[7].setTexture("ColorMap", texture[7]);
		materials[8].setTexture("ColorMap", texture[8]);
		materials[9].setTexture("ColorMap", texture[9]);
		materials[10].setTexture("ColorMap", texture[10]);
		materials[11].setTexture("ColorMap", texture[11]);
		materials[12].setTexture("ColorMap", texture[12]);
		materials[13].setTexture("ColorMap", texture[13]);
		materials[14].setTexture("ColorMap", texture[14]);
		materials[15].setTexture("ColorMap", texture[15]);
		materials[16].setTexture("ColorMap", texture[16]);
		materials[17].setTexture("ColorMap", texture[17]);
		materials[18].setTexture("ColorMap", texture[18]);
		materials[19].setTexture("ColorMap", texture[19]);
		materials[20].setTexture("ColorMap", texture[20]);
		materials[21].setTexture("ColorMap", texture[21]);
		materials[22].setTexture("ColorMap", texture[22]);
		materials[23].setTexture("ColorMap", texture[23]);
		materials[24].setTexture("ColorMap", texture[24]);
		materials[25].setTexture("ColorMap", texture[25]);
		materials[26].setTexture("ColorMap", texture[26]);
		materials[27].setTexture("ColorMap", texture[27]);
		materials[28].setTexture("ColorMap", texture[28]);
		materials[29].setTexture("ColorMap", texture[29]);
		materials[30].setTexture("ColorMap", texture[30]);
		materials[31].setTexture("ColorMap", texture[31]);
		materials[32].setTexture("ColorMap", texture[32]);
		materials[33].setTexture("ColorMap", texture[33]);
		materials[34].setTexture("ColorMap", texture[34]);
		materials[35].setTexture("ColorMap", texture[35]);
		materials[36].setTexture("ColorMap", texture[36]);
		materials[37].setTexture("ColorMap", texture[37]);
		materials[38].setTexture("ColorMap", texture[38]);
		materials[39].setTexture("ColorMap", texture[39]);
		materials[40].setTexture("ColorMap", texture[40]);
		materials[41].setTexture("ColorMap", texture[41]);
		materials[42].setTexture("ColorMap", texture[42]);
		materials[43].setTexture("ColorMap", texture[43]);
		materials[44].setTexture("ColorMap", texture[44]);
		materials[45].setTexture("ColorMap", texture[45]);
		materials[46].setTexture("ColorMap", texture[46]);
		materials[47].setTexture("ColorMap", texture[47]);
		materials[48].setTexture("ColorMap", texture[48]);
		materials[49].setColor("Color", ColorRGBA.Green);
		materials[49].setColor("GlowColor", ColorRGBA.Green);
		materials[50].setColor("Color", ColorRGBA.Green);
		materials[50].setColor("GlowColor", ColorRGBA.Green);
		materials[51].setColor("Color", ColorRGBA.Yellow);
		materials[51].setColor("GlowColor", ColorRGBA.Yellow);
		materials[52].setColor("Color", ColorRGBA.Yellow);
		materials[52].setColor("GlowColor", ColorRGBA.Yellow);
		materials[53].setColor("Color", ColorRGBA.Blue);
		materials[53].setColor("GlowColor", ColorRGBA.Blue);
		materials[54].setColor("Color", ColorRGBA.Red);
		materials[54].setColor("GlowColor", ColorRGBA.Red);
		materials[55].setColor("Color", ColorRGBA.Orange);
		materials[55].setColor("GlowColor", ColorRGBA.Orange);
		materials[56].setColor("Color", ColorRGBA.Magenta);
		materials[56].setColor("GlowColor", ColorRGBA.Magenta);
		materials[57].setColor("Color", ColorRGBA.White);
		materials[57].setColor("GlowColor", ColorRGBA.White);
		materials[58].setColor("Color", ColorRGBA.Cyan);
		materials[58].setColor("GlowColor", ColorRGBA.Cyan);
		materials[59].setTexture("ColorMap", texture[49]);
		materials[60].setTexture("ColorMap", texture[50]);
		materials[61].setTexture("ColorMap", texture[51]);

	}

	private void loadTextures() {
		texture[0] = assetManager.loadTexture(CubikArenaPath.getTexturespath()
				+ "anvil_base.png");
		texture[1] = assetManager.loadTexture(CubikArenaPath.getTexturespath()
				+ "redstone_lamp_off.png");
		texture[2] = assetManager.loadTexture(CubikArenaPath.getTexturespath()
				+ "brick.png");
		texture[3] = assetManager.loadTexture(CubikArenaPath.getTexturespath()
				+ "cobblestone.png");
		texture[4] = assetManager.loadTexture(CubikArenaPath.getTexturespath()
				+ "cobblestone_mossy.png");
		texture[5] = assetManager.loadTexture(CubikArenaPath.getTexturespath()
				+ "dirt.png");
		texture[6] = assetManager.loadTexture(CubikArenaPath.getTexturespath()
				+ "coarse_dirt.png");
		texture[7] = assetManager.loadTexture(CubikArenaPath.getTexturespath()
				+ "dispenser_front_vertical.png");
		texture[8] = assetManager.loadTexture(CubikArenaPath.getTexturespath()
				+ "gold_block.png");
		texture[9] = assetManager.loadTexture(CubikArenaPath.getTexturespath()
				+ "grass_top.png");
		texture[10] = assetManager.loadTexture(CubikArenaPath.getTexturespath()
				+ "gravel.png");
		texture[11] = assetManager.loadTexture(CubikArenaPath.getTexturespath()
				+ "ice.png");
		texture[12] = assetManager.loadTexture(CubikArenaPath.getTexturespath()
				+ "iron_block.png");
		texture[13] = assetManager.loadTexture(CubikArenaPath.getTexturespath()
				+ "door_iron_lower.png");
		texture[14] = assetManager.loadTexture(CubikArenaPath.getTexturespath()
				+ "sea_lantern.png");
		texture[15] = assetManager.loadTexture(CubikArenaPath.getTexturespath()
				+ "lapis_block.png");
		texture[16] = assetManager.loadTexture(CubikArenaPath.getTexturespath()
				+ "mycelium_top.png");
		texture[17] = assetManager.loadTexture(CubikArenaPath.getTexturespath()
				+ "nether_brick.png");
		texture[18] = assetManager.loadTexture(CubikArenaPath.getTexturespath()
				+ "netherrack.png");
		texture[19] = assetManager.loadTexture(CubikArenaPath.getTexturespath()
				+ "obsidian.png");
		texture[20] = assetManager.loadTexture(CubikArenaPath.getTexturespath()
				+ "prismarine_dark.png");
		texture[21] = assetManager.loadTexture(CubikArenaPath.getTexturespath()
				+ "quartz_block_chiseled.png");
		texture[22] = assetManager.loadTexture(CubikArenaPath.getTexturespath()
				+ "quartz_block_lines_top.png");
		texture[23] = assetManager.loadTexture(CubikArenaPath.getTexturespath()
				+ "sand.png");
		texture[24] = assetManager.loadTexture(CubikArenaPath.getTexturespath()
				+ "sandstone_top.png");
		texture[25] = assetManager.loadTexture(CubikArenaPath.getTexturespath()
				+ "red_sandstone_top.png");
		texture[26] = assetManager.loadTexture(CubikArenaPath.getTexturespath()
				+ "snow.png");
		texture[27] = assetManager.loadTexture(CubikArenaPath.getTexturespath()
				+ "stonebrick.png");
		texture[28] = assetManager.loadTexture(CubikArenaPath.getTexturespath()
				+ "stonebrick_carved.png");
		texture[29] = assetManager.loadTexture(CubikArenaPath.getTexturespath()
				+ "stone.png");
		texture[30] = assetManager.loadTexture(CubikArenaPath.getTexturespath()
				+ "stone_andesite_smooth.png");
		texture[31] = assetManager.loadTexture(CubikArenaPath.getTexturespath()
				+ "stonebrick_mossy.png");
		texture[32] = assetManager.loadTexture(CubikArenaPath.getTexturespath()
				+ "planks_big_oak.png");
		texture[33] = assetManager.loadTexture(CubikArenaPath.getTexturespath()
				+ "planks_birch.png");
		texture[34] = assetManager.loadTexture(CubikArenaPath.getTexturespath()
				+ "planks_jungle.png");
		texture[35] = assetManager.loadTexture(CubikArenaPath.getTexturespath()
				+ "planks_oak.png");
		texture[36] = assetManager.loadTexture(CubikArenaPath.getTexturespath()
				+ "planks_spruce.png");
		texture[37] = assetManager.loadTexture(CubikArenaPath.getTexturespath()
				+ "wool_colored_black.png");
		texture[38] = assetManager.loadTexture(CubikArenaPath.getTexturespath()
				+ "wool_colored_blue.png");
		texture[39] = assetManager.loadTexture(CubikArenaPath.getTexturespath()
				+ "wool_colored_brown.png");
		texture[40] = assetManager.loadTexture(CubikArenaPath.getTexturespath()
				+ "wool_colored_gray.png");
		texture[41] = assetManager.loadTexture(CubikArenaPath.getTexturespath()
				+ "wool_colored_green.png");
		texture[42] = assetManager.loadTexture(CubikArenaPath.getTexturespath()
				+ "wool_colored_light_blue.png");
		texture[43] = assetManager.loadTexture(CubikArenaPath.getTexturespath()
				+ "wool_colored_orange.png");
		texture[44] = assetManager.loadTexture(CubikArenaPath.getTexturespath()
				+ "wool_colored_pink.png");
		texture[45] = assetManager.loadTexture(CubikArenaPath.getTexturespath()
				+ "wool_colored_purple.png");
		texture[46] = assetManager.loadTexture(CubikArenaPath.getTexturespath()
				+ "wool_colored_red.png");
		texture[47] = assetManager.loadTexture(CubikArenaPath.getTexturespath()
				+ "wool_colored_white.png");
		texture[48] = assetManager.loadTexture(CubikArenaPath.getTexturespath()
				+ "wool_colored_yellow.png");
		texture[49] = assetManager.loadTexture(CubikArenaPath.getTexturespath()
				+ "AmmoTexture.png");
		texture[50] = assetManager.loadTexture(CubikArenaPath.getTexturespath()
				+ "Snipertexture.png");
		texture[51] = assetManager.loadTexture(CubikArenaPath.getTexturespath()
				+ "RifleTexture.png");

	}

	private static ComponentLoader componentLoader;

	public Texture getTerrainTexture() {
		// TODO Auto-generated method stub
		return terrainTexture;
	}
}
