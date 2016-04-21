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

		default:
			return materials[0];
		}
	}

	public void init(AssetManager assetManager) {
		this.assetManager = assetManager;
		grassTexture = assetManager.loadTexture("Textures/grass_top.png");
		stoneTexture = assetManager.loadTexture("Textures/StoneTexture.png");
		wallTexture = assetManager.loadTexture("Textures/brick.png");
		woodTexture = assetManager.loadTexture("Textures/WoodTexture.png");
		materials = new Material[10];
		for (int i = 0; i < 10; i++)
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
		materials[4].setColor("Color", ColorRGBA.Blue);

	}

	private static ComponentLoader componentLoader;
}
