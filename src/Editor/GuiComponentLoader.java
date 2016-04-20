package Editor;

import com.jme3.asset.AssetManager;
import com.jme3.texture.Texture;

public class GuiComponentLoader {

	public AssetManager assetManager;
	private Texture grassTexture;
	private Texture stoneTexture;
	private Texture wallTexture;
	private Texture woodTexture;

	public GuiComponentLoader(AssetManager assetManager) {
		this.assetManager = assetManager;
		grassTexture = assetManager.loadTexture("Textures/grass_top.png");
		stoneTexture = assetManager.loadTexture("Textures/StoneTexture.png");
		wallTexture = assetManager.loadTexture("Textures/brick.png");
		woodTexture = assetManager.loadTexture("Textures/WoodTexture.png");

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

}
