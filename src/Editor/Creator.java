package Editor;

import com.jme3.asset.AssetManager;
import com.jme3.bounding.BoundingBox;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
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
	Spatial health, haste, ammo, armor, regeneration, ultradamage, untouchable,
			rocketLauncher, chainsaw, laser, shotgun, rifle, sniper, wedgeCube;
	private float modelScale = 0.5f;
	private float weaponsScale = 0.2f;
	private int rocketLauncherSize, shotgunSize, rifleSize, laserSize,
			sniperSize, minHealthSize, midHealthSize, maxHealthSize,
			minArmorSize, midArmorSize, maxArmorSize, hasteSize, ammoSize,
			regenerationSize, ultradamageSize, untouchableSize;
	private int maxElement = 3;
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
		if (EditorManager.getIstance().isWedgeActive()) {
			tmp = new Cube(wedgeCube, posx, posy, posz, currentType);
			tmp.geometry.setMaterial(ComponentLoader.getIstance().getMaterial(
					currentType));
			return tmp;
		}
		updateInformation(currentType);
		switch (currentType) {
		case HASTE:

			tmp = new Cube(haste, posx, posy, posz, currentType);
			return tmp;
		case MINHEALTH:
			tmp = new Cube(health, posx, posy, posz, currentType);
			tmp.geometry.setMaterial(ComponentLoader.getIstance().getMaterial(
					CubeType.MINHEALTH));
			return tmp;
		case MINARMOR:
			tmp = new Cube(armor, posx, posy, posz, currentType);
			tmp.geometry.setMaterial(ComponentLoader.getIstance().getMaterial(
					CubeType.MINARMOR));
			return tmp;
		case MIDHEALTH:
			tmp = new Cube(health, posx, posy, posz, currentType);
			tmp.geometry.setMaterial(ComponentLoader.getIstance().getMaterial(
					CubeType.MIDHEALTH));
			return tmp;
		case MIDARMOR:
			tmp = new Cube(armor, posx, posy, posz, currentType);
			tmp.geometry.setMaterial(ComponentLoader.getIstance().getMaterial(
					CubeType.MIDARMOR));
			return tmp;
		case MAXHEALTH:
			tmp = new Cube(health, posx, posy, posz, currentType);
			tmp.geometry.setMaterial(ComponentLoader.getIstance().getMaterial(
					CubeType.MAXHEALTH));
			return tmp;
		case MAXARMOR:
			tmp = new Cube(armor, posx, posy, posz, currentType);
			tmp.geometry.setMaterial(ComponentLoader.getIstance().getMaterial(
					CubeType.MAXARMOR));
			return tmp;
		case AMMO:
			tmp = new Cube(ammo, posx, posy, posz, currentType);
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
		case RIFLE:
			tmp = new Cube(rifle, posx, posy, posz, currentType);
			return tmp;
		case SNIPER:
			tmp = new Cube(sniper, posx, posy, posz, currentType);
			return tmp;

		default:
			tmp = new Cube(geometry, posx, posy, posz, currentType);
			tmp.geometry.setMaterial(ComponentLoader.getIstance().getMaterial(
					currentType));

			return tmp;
		}

		// {
		//
		// ComponentLoader
		// .getIstance()
		// .getMaterial(currentType)
		// .setTexture(
		// "ColorMap",
		// assetManager
		// .loadTexture("Textures/wool_colored_red.png"));
		//
		// tmp = new Cube(
		// assetManager.loadModel("Models/half_cube/half_cube.obj"),
		// posx, posy, posz, currentType);
		// tmp.geometry.setMaterial(ComponentLoader.getIstance().getMaterial(
		// currentType));
		//
		// }
		//
		// return tmp;
	}

	public void init(AssetManager assetManager) {

		this.assetManager = assetManager;
		initPrototypeCube();
	}

	public void reset() {
		rocketLauncherSize = shotgunSize = rifleSize = laserSize = sniperSize = minHealthSize = midHealthSize = maxHealthSize = minArmorSize = midArmorSize = maxArmorSize = hasteSize = ammoSize = regenerationSize = ultradamageSize = untouchableSize = 0;

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
		Texture texture = ComponentLoader.getIstance().getTerrainTexture();
		texture.setWrap(WrapMode.Repeat);

		material.setTexture("Tex1", texture);
		material.setFloat("Tex1Scale", 128f);
		terrainQuad.setMaterial(material);

		RigidBodyControl terrainControl = new RigidBodyControl(0);
		terrainQuad.setLocalTranslation(SIZEMAP / 2, 0, SIZEMAP / 2);
		terrainQuad.addControl(terrainControl);
		//
		// DirectionalLight sun = new DirectionalLight();
		// sun.setColor(ColorRGBA.White);
		// sun.setDirection(new Vector3f(-.5f, -.5f, -.5f).normalizeLocal());
		// terrainQuad.addLight(sun);

		return terrainQuad;
	}

	public AssetManager getAssetManager() {
		return assetManager;
	}

	private void initPrototypeCube() {
		box = new Box(1, 1, 1);
		geometry = new Geometry("Box", box);
		health = assetManager.loadModel(CubikArenaPath.getHealthpath());
		health.scale(modelScale);
		haste = assetManager.loadModel(CubikArenaPath.getHastepath());
		haste.setMaterial(ComponentLoader.getIstance().getMaterial(
				CubeType.HASTE));
		haste.scale(modelScale);
		ammo = assetManager.loadModel(CubikArenaPath.getAmmopath());
		ammo.setMaterial(ComponentLoader.getIstance()
				.getMaterial(CubeType.AMMO));
		ammo.scale(modelScale);
		armor = assetManager.loadModel(CubikArenaPath.getArmorpath());
		armor.scale(modelScale);
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

		// weapons

		rocketLauncher = assetManager.loadModel(CubikArenaPath
				.getRocketlauncherpath());
		rocketLauncher.scale(weaponsScale + 0.1f);
		chainsaw = assetManager.loadModel(CubikArenaPath.getChainsawpath());
		chainsaw.scale(weaponsScale);
		laser = assetManager.loadModel(CubikArenaPath.getLaserpath());
		laser.scale(weaponsScale);
		shotgun = assetManager.loadModel(CubikArenaPath.getShotgunpath());
		shotgun.scale(weaponsScale + 0.2f);
		sniper = assetManager.loadModel(CubikArenaPath.getSniperpath());
		sniper.setMaterial(ComponentLoader.getIstance().getMaterial(
				CubeType.SNIPER));
		sniper.scale(weaponsScale + 0.01f);
		rifle = assetManager.loadModel(CubikArenaPath.getRiflepath());
		rifle.scale(weaponsScale - 0.1f);

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

	public boolean canCreate(CubeType currentType) {

		switch (currentType) {
		case ROCKETLAUNCHER:

			return rocketLauncherSize < maxElement;
		case SHOTGUN:
			return shotgunSize < maxElement;
		case RIFLE:
			return rifleSize < maxElement;
		case LASER:
			return laserSize < maxElement;
		case SNIPER:
			return sniperSize < maxElement;
		case MINHEALTH:
			return minHealthSize < maxElement;
		case MIDHEALTH:
			return midHealthSize < maxElement;
		case MAXHEALTH:
			return maxHealthSize < maxElement;
		case MINARMOR:
			return minArmorSize < maxElement;
		case MIDARMOR:
			return midArmorSize < maxElement;
		case MAXARMOR:
			return maxArmorSize < maxElement;
		case HASTE:
			return hasteSize < maxElement;
		case AMMO:
			return ammoSize < maxElement;
		case REGENERATION:
			return regenerationSize < maxElement;
		case ULTRADAMAGE:
			return ultradamageSize < maxElement;
		case UNTOUCHABLE:
			return untouchableSize < maxElement;
		default:
			return true;
		}

	}

	public void remove(CubeType cubeType) {

		switch (cubeType) {
		case ROCKETLAUNCHER:
			rocketLauncherSize--;
			break;
		case SHOTGUN:
			shotgunSize--;
			break;
		case RIFLE:
			rifleSize--;
			break;
		case LASER:
			laserSize--;
			break;
		case SNIPER:
			sniperSize--;
			break;
		case MINHEALTH:
			minHealthSize--;
			break;
		case MIDHEALTH:
			midHealthSize--;
			break;
		case MAXHEALTH:
			maxHealthSize--;
			break;
		case MINARMOR:
			minArmorSize--;
			break;
		case MIDARMOR:
			midArmorSize--;
			break;
		case MAXARMOR:
			maxArmorSize--;
			break;
		case HASTE:
			hasteSize--;
			break;
		case AMMO:
			ammoSize--;
			break;
		case REGENERATION:
			regenerationSize--;
			break;
		case ULTRADAMAGE:
			ultradamageSize--;
			break;
		case UNTOUCHABLE:
			untouchableSize--;
			break;
		default:

		}

	}

	public void updateInformation(CubeType cubeType) {

		switch (cubeType) {
		case ROCKETLAUNCHER:
			rocketLauncherSize++;
			break;
		case SHOTGUN:
			shotgunSize++;
			break;
		case RIFLE:
			rifleSize++;
			break;
		case LASER:
			laserSize++;
			break;
		case SNIPER:
			sniperSize++;
			break;
		case MINHEALTH:
			minHealthSize++;
			break;
		case MIDHEALTH:
			midHealthSize++;
			break;
		case MAXHEALTH:
			maxHealthSize++;
			break;
		case MINARMOR:
			minArmorSize++;
			break;
		case MIDARMOR:
			midArmorSize++;
			break;
		case MAXARMOR:
			maxArmorSize++;
			break;
		case HASTE:
			hasteSize++;
			break;
		case AMMO:
			ammoSize++;
			break;
		case REGENERATION:
			regenerationSize++;
			break;
		case ULTRADAMAGE:
			ultradamageSize++;
			break;
		case UNTOUCHABLE:
			untouchableSize++;
			break;
		default:

		}
	}
}
