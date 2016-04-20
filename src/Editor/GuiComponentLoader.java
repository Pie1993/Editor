package Editor;

import com.jme3.asset.AssetManager;
import com.jme3.texture.Texture;

public class GuiComponentLoader {

	public AssetManager assetManager;
	private Texture grassTexture;
	private Texture stoneTexture;
	private Texture wallTexture;
	private Texture woodTexture;

	private GuiComponentLoader() {

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

	public static GuiComponentLoader getIstance() {
		if (componentLoader == null)
			componentLoader = new GuiComponentLoader();
		return componentLoader;

	}

	public void init(AssetManager assetManager) {
		this.assetManager = assetManager;
		grassTexture = assetManager.loadTexture("Textures/grass_top.png");
		stoneTexture = assetManager.loadTexture("Textures/StoneTexture.png");
		wallTexture = assetManager.loadTexture("Textures/brick.png");
		woodTexture = assetManager.loadTexture("Textures/WoodTexture.png");

	}

	private static GuiComponentLoader componentLoader;
}
