package Editor;

import com.jme3.asset.AssetManager;
import com.jme3.bounding.BoundingBox;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.debug.WireBox;
import com.jme3.scene.shape.Box;
import com.jme3.terrain.geomipmap.TerrainQuad;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.WrapMode;

public class Creator {

	Box box;
	Geometry geometry;
	Spatial stone, wall, wood, grass, midHealth, haste, ammo, midArmor,
			regeneration, ultradamage, untouchable, rocketLauncher, chainsaw,
			laser, shotgun, anvil, Box, brick, cobbleStone, cobbleStoneMessy,
			dirt, dirt2, dispenser, gold, gravel, ice, iron, ironPanel,
			lantern, lapis, mycelium, netherBrick, nether, obsidian,
			prismarine, quarz, quarz2, sand, sandStone, sandStone2, snow,
			stoneBrick, stoneCarved, stoneFloor, stoneMossy, wood2, wood3,
			wood4, wood5, black, blue, brown, gray, green, lightblue, orange,
			pink, purple, red, white, yellow, rifle, sniper, minHealth,
			maxHealth, minArmor, maxArmor, wedgeCube;
	private float modelScale = 0.77f;
	private AssetManager assetManager;

	public Geometry createModelCube() {
		WireBox wireBoxCube = new WireBox();
		Geometry modelCube = new Geometry("box", wireBoxCube);
		wireBoxCube.fromBoundingBox((BoundingBox) modelCube.getModelBound());
		wireBoxCube.setLineWidth(2f);
		final Material material = new Material(assetManager,
				"Common/MatDefs/Misc/Unshaded.j3md");
		modelCube.setMaterial(material);
		material.setColor("Color", ColorRGBA.LightGray);

		return modelCube;

	}

	public CharacterControl createPlayer() {

		CapsuleCollisionShape capsuleShape = new CapsuleCollisionShape(1.25f,
				3f, 1);
		CharacterControl player = new CharacterControl(capsuleShape, 0.05f);
		player.setJumpSpeed(10);
		player.setFallSpeed(10);
		player.setGravity(0);
		player.setPhysicsLocation(new Vector3f(128, 2.5f, 128));
		return player;
	}

	public Cube createCube(int posx, int posy, int posz, CubeType currentType) {
		Cube tmp;

		switch (currentType) {
		case HASTE:
			tmp = new Cube(haste, posx, posy, posz, currentType);
			return tmp;
		case MIDHEALTH:
			tmp = new Cube(midHealth, posx, posy, posz, currentType);
			return tmp;
		case AMMO:
			tmp = new Cube(ammo, posx, posy, posz, currentType);
			return tmp;
		case MIDARMOR:
			tmp = new Cube(midArmor, posx, posy, posz, currentType);
			return tmp;
		case REGENERATION:
			tmp = new Cube(regeneration, posx, posy, posz, currentType);
			return tmp;
		case ULTRADAMAGE:
			tmp = new Cube(ultradamage, posx, posy, posz, currentType);
			return tmp;
		case UNTOUCHABLE:
			tmp = new Cube(untouchable, posx, posy, posz, currentType);
			return tmp;
		case ROCKETLAUNCHER:
			tmp = new Cube(rocketLauncher, posx, posy, posz, currentType);
			return tmp;
		case CHAINSAW:
			tmp = new Cube(chainsaw, posx, posy, posz, currentType);
			return tmp;
		case LASER:
			tmp = new Cube(laser, posx, posy, posz, currentType);
			return tmp;
		case SHOTGUN:
			tmp = new Cube(shotgun, posx, posy, posz, currentType);
			return tmp;
		default:
			break;
		}

		if (currentType != CubeType.STONE) {

			tmp = new Cube(geometry, posx, posy, posz, currentType);
			tmp.geometry.setMaterial(ComponentLoader.getIstance().getMaterial(
					currentType));

		} else {

			ComponentLoader
					.getIstance()
					.getMaterial(currentType)
					.setTexture(
							"ColorMap",
							assetManager
									.loadTexture("Textures/wool_colored_red.png"));

			tmp = new Cube(
					assetManager.loadModel("Models/half_cube/half_cube.obj"),
					posx, posy, posz, currentType);
			tmp.geometry.setMaterial(ComponentLoader.getIstance().getMaterial(
					currentType));

		}

		return tmp;
	}

	public void init(AssetManager assetManager) {

		this.assetManager = assetManager;
		initPrototypeCube();
	}

	public static Creator getIstance() {
		if (creator == null)
			creator = new Creator();
		return creator;
	}

	private Creator() {

	}

	private static Creator creator;

	public TerrainQuad createScene(int SIZEMAP) {
		float s[] = new float[1];
		TerrainQuad terrainQuad = new TerrainQuad("Terrain", SIZEMAP,
				SIZEMAP + 1, s);
		Material material = new Material(assetManager,
				"Common/MatDefs/Terrain/Terrain.j3md");
		material.setTexture("Alpha",
				assetManager.loadTexture("Textures/alpha.png"));
		Texture texture = ComponentLoader.getIstance().getGrassTexture();
		texture.setWrap(WrapMode.Repeat);

		material.setTexture("Tex1", texture);
		material.setFloat("Tex1Scale", 128f);
		terrainQuad.setMaterial(material);

		RigidBodyControl terrainControl = new RigidBodyControl(0);
		terrainQuad.setLocalTranslation(SIZEMAP / 2, 0, SIZEMAP / 2);
		terrainQuad.addControl(terrainControl);

		DirectionalLight sun = new DirectionalLight();
		sun.setColor(ColorRGBA.White);
		sun.setDirection(new Vector3f(-.5f, -.5f, -.5f).normalizeLocal());
		terrainQuad.addLight(sun);

		return terrainQuad;
	}

	private void initPrototypeCube() {
		box = new Box(1, 1, 1);
		geometry = new Geometry("Box", box);
		midHealth = assetManager.loadModel(CubikArenaPath.getHealthpath());
		midHealth.setMaterial(ComponentLoader.getIstance().getMaterial(
				CubeType.MIDHEALTH));
		midHealth.scale(modelScale);
		haste = assetManager.loadModel(CubikArenaPath.getHastepath());
		haste.setMaterial(ComponentLoader.getIstance().getMaterial(
				CubeType.HASTE));
		haste.scale(modelScale);
		ammo = assetManager.loadModel(CubikArenaPath.getAmmopath());
		ammo.setMaterial(ComponentLoader.getIstance()
				.getMaterial(CubeType.AMMO));
		ammo.scale(modelScale);
		midArmor = assetManager.loadModel(CubikArenaPath.getArmorpath());
		midArmor.setMaterial(ComponentLoader.getIstance().getMaterial(
				CubeType.MIDARMOR));
		midArmor.scale(modelScale);
		regeneration = assetManager.loadModel(CubikArenaPath
				.getRegenerationpath());
		regeneration.setMaterial(ComponentLoader.getIstance().getMaterial(
				CubeType.REGENERATION));
		regeneration.scale(modelScale);
		ultradamage = assetManager.loadModel(CubikArenaPath
				.getUltradamagepath());
		ultradamage.setMaterial(ComponentLoader.getIstance().getMaterial(
				CubeType.ULTRADAMAGE));
		ultradamage.scale(modelScale);
		untouchable = assetManager.loadModel(CubikArenaPath
				.getUntouchablepath());
		untouchable.setMaterial(ComponentLoader.getIstance().getMaterial(
				CubeType.UNTOUCHABLE));
		untouchable.scale(modelScale);
		rocketLauncher = assetManager.loadModel(CubikArenaPath
				.getRocketlauncherpath());
		rocketLauncher.scale(modelScale);
		chainsaw = assetManager.loadModel(CubikArenaPath.getChainsawpath());
		chainsaw.scale(modelScale);
		laser = assetManager.loadModel(CubikArenaPath.getLaserpath());
		laser.scale(modelScale);
		shotgun = assetManager.loadModel(CubikArenaPath.getShotgunpath());
		shotgun.scale(modelScale);

	}

	public Spatial createWedge() {
		final Material material1 = new Material(assetManager,
				"Common/MatDefs/Misc/Unshaded.j3md");
		material1.setColor("Color", ColorRGBA.LightGray);
		material1.getAdditionalRenderState().setWireframe(true);
		Spatial wedge = assetManager
				.loadModel("Models/half_cube/half_cube.obj");

		wedge.setMaterial(material1);

		wedgeCube = assetManager.loadModel("Models/half_cube/half_cube.obj");
		return wedge;

	}

}
