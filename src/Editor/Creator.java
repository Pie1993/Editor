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
	private Vector3f cursorPos;

	Box box;
	Geometry geometry;
	Spatial healt, wedgeCube;
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
		if (currentType != CubeType.STONE) {

			tmp = new Cube(geometry, posx, posy, posz, currentType);
			tmp.geometry.setMaterial(ComponentLoader.getIstance()
					.getMaterial(currentType));

		} else {

			ComponentLoader
					.getIstance()
					.getMaterial(currentType)
					.setTexture(
							"ColorMap",
							assetManager
									.loadTexture("Models/half_cube/brick.png"));

			tmp = new Cube(
					assetManager.loadModel("Models/half_cube/half_cube.obj"),
					posx, posy, posz, currentType);
			tmp.geometry.setMaterial(ComponentLoader.getIstance()
					.getMaterial(currentType));

		}

		return tmp;
	}

	public void init(Vector3f cursorPos, AssetManager assetManager) {
		this.cursorPos = cursorPos;

		this.assetManager = assetManager;

	}

	public static Creator getIstance() {
		if (creator == null)
			creator = new Creator();
		return creator;
	}

	private Creator() {

	}

	private static Creator creator;

	public TerrainQuad createScene(AssetManager assetManager, int SIZEMAP) {
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
		return terrainQuad;
	}

	public void initPrototypeCube() {
		box = new Box(1, 1, 1);
		geometry = new Geometry("Box", box);

		healt = assetManager.loadModel("Models/health/health.obj");

		healt.setMaterial(ComponentLoader.getIstance().getMaterial(
				CubeType.WOOD));
		healt.scale(0.77f);

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
