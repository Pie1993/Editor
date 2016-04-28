package Editor;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.texture.Texture;

public class ComponentLoader {

	public AssetManager assetManager;
	private Texture grassTexture;
	private Texture stoneTexture;
	private Texture wallTexture;
	private Texture woodTexture;
	private Material materials[];

	private ComponentLoader() {

	}

	public Texture getGrassTexture() {
		return grassTexture;
	}

	public Texture getStoneTexture() {
		return stoneTexture;
	}

	public Texture getWallTexture() {
		return wallTexture;
	}

	public Texture getWoodTexture() {

		return woodTexture;
	}

	public static ComponentLoader getIstance() {
		if (componentLoader == null)
			componentLoader = new ComponentLoader();
		return componentLoader;

	}

	public Material getMaterial(CubeType type) {
		switch (type) {
		case GRASS:
			return materials[0];
		case STONE:
			return materials[1];
		case WALL:
			return materials[2];
		case WOOD:
			return materials[3];
		case MIDHEALTH:
			return materials[4];
		case HASTE:
			return materials[5];
		case AMMO:
			return materials[6];
		case MIDARMOR:
			return materials[7];
		case REGENERATION:
			return materials[8];
		case ULTRADAMAGE:
			return materials[9];
		case UNTOUCHABLE:
			return materials[10];
		default:
			return materials[0];
		}
	}

	public void init(AssetManager assetManager) {
		this.assetManager = assetManager;
		// load texture
		grassTexture = assetManager.loadTexture(CubikArenaPath
				.getTexturespath() + "grass_top.png");
		stoneTexture = assetManager.loadTexture(CubikArenaPath
				.getTexturespath() + "stone.png");
		wallTexture = assetManager.loadTexture(CubikArenaPath.getTexturespath()
				+ "brick.png");
		woodTexture = assetManager.loadTexture(CubikArenaPath.getTexturespath()
				+ "planks_oak.png");

		// material
		materials = new Material[20];
		for (int i = 0; i < 20; i++)
			materials[i] = new Material(assetManager,
					"Common/MatDefs/Misc/Unshaded.j3md");

		materials[0].setTexture("ColorMap", ComponentLoader.getIstance()
				.getGrassTexture());
		materials[1].setTexture("ColorMap", ComponentLoader.getIstance()
				.getStoneTexture());
		materials[2].setTexture("ColorMap", ComponentLoader.getIstance()
				.getWallTexture());
		materials[3].setTexture("ColorMap", ComponentLoader.getIstance()
				.getWoodTexture());
		materials[4].setColor("Color", ColorRGBA.Blue); // health
		materials[5].setColor("Color", ColorRGBA.Yellow); // haste
		materials[6].setColor("Color", ColorRGBA.Red); // ammo
		materials[7].setColor("Color", ColorRGBA.Gray); // armor
		materials[8].setColor("Color", ColorRGBA.Green); // regeneration
		materials[9].setColor("Color", ColorRGBA.LightGray); // ultradamage
		materials[10].setColor("Color", ColorRGBA.Cyan); // untouchable

	}

	private static ComponentLoader componentLoader;
}
