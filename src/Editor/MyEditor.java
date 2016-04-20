package Editor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.audio.AudioRenderer;
import com.jme3.bounding.BoundingBox;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.font.BitmapText;
import com.jme3.input.FlyByCamera;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.BatchNode;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.debug.WireBox;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;
import com.jme3.terrain.geomipmap.TerrainQuad;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.WrapMode;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.ListBox;

public class MyEditor extends SimpleApplication implements ActionListener {

	private final static int SIZEMAP = 256;
	private Ray ray = new Ray();
	final int sizeCube = 5;
	CubeType currentType = CubeType.WALL;
	TerrainQuad terrainQuad;

	BatchNode nodoScena, nodoModel;
	Spatial scena;
	Long coolDown = (long) 0;
	HashMap<String, Cube> spatialMap;
	BulletAppState bulletAppState;
	Vector3f cursorPos;
	Geometry modelCube, modelTriangle;
	boolean forward = false, backward = false, xRotate = false,
			yRotate = false, leftStrafe = false, rightStrafe = false,
			up = false, down = false, insert = false, delete = false,
			activeMouse = false;
	float xRotation, yRotation;

	Nifty myEditorScreen;
	MyScreenController myScreenController;
	GuiComponentLoader componentLoader;
	NiftyJmeDisplay display;
	ScreenNiftyCube screenNiftyCube;
	ScreenSaveMap screenSaveMap;
	ScreenLoadMap screenLoadMap;
	CameraNode cameraNode;
	String nameMap;
	ListBox listBox;
	Box box;
	Geometry geometry;
	Material materials[];
	private float moveSpeed = 0.3f;
	private float rotateSpeed = 0.8f;
	Vector3f walkDirection = new Vector3f(0, 0, 0);
	Vector3f viewDirection = new Vector3f(1, 0, 1);
	private Vector3f camDir = new Vector3f();
	private Vector3f camLeft = new Vector3f();
	private CharacterControl player;
	private float riseSpeed = 0.2f;
	private Quaternion tmpQuat = new Quaternion();
	Spatial healt, wedge, wedgeCube;

	private WireBox wireBoxCube, wireBoxTriangle;

	public enum InputMap {
		StrafeLeft, StrafeRight, MoveForward, MoveBackward, MoveUp, MoveDown, Insert, Delete, Exit, activeMouse, rotate;
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		super.stop();
		clearScene();
	}

	private void clearScene() {
		for (Map.Entry<String, Cube> entry : spatialMap.entrySet()) {
			nodoScena.detachChild(entry.getValue().geometry);
			entry.getValue().activeControl(false);

		}

		spatialMap.clear();

	}

	public static void main(String[] args) {

		MyEditor app = new MyEditor();
		app.start();

	}

	public MyEditor() {

		this.cursorPos = new Vector3f(SIZEMAP / 2 - 1, 0, SIZEMAP / 2 - 1);
		this.myScreenController = new MyScreenController(this);
		this.spatialMap = new HashMap<String, Cube>();

	}

	public void initPrototypeCube() {
		box = new Box(1, 1, 1);
		geometry = new Geometry("Box", box);
		materials = new Material[10];
		for (int i = 0; i < 10; i++)
			materials[i] = new Material(assetManager,
					"Common/MatDefs/Misc/Unshaded.j3md");
		materials[0].setTexture("ColorMap", componentLoader.getGrassTexture());
		materials[1].setTexture("ColorMap", componentLoader.getStoneTexture());
		materials[2].setTexture("ColorMap", componentLoader.getWallTexture());
		materials[3].setTexture("ColorMap", componentLoader.getWoodTexture());
		materials[4].setColor("Color", ColorRGBA.Blue);
		healt = assetManager.loadModel("Models/health/health.obj");

		healt.setMaterial(materials[4]);
		healt.scale(0.77f);
		wedge = assetManager.loadModel("Models/half_cube/half_cube.obj");
		wedge.setLocalTranslation(0, 0, 0);
		wedgeCube = wedge.clone();
		wedgeCube.setMaterial(materials[0]);

	}

	@Override
	public void simpleInitApp() {

		assetManager.registerLocator("./assets/", FileLocator.class); // /cambia
																		// percorso
		display = new NiftyJmeDisplay(getAssetManager(), inputManager,
				getAudioRenderer(), getGuiViewPort());

		screenNiftyCube = new ScreenNiftyCube(this);
		screenSaveMap = new ScreenSaveMap(this);
		screenLoadMap = new ScreenLoadMap(this);

		initListener();

		CapsuleCollisionShape capsuleShape = new CapsuleCollisionShape(1.25f,
				3f, 1);
		player = new CharacterControl(capsuleShape, 0.05f);
		player.setJumpSpeed(10);
		player.setFallSpeed(10);
		player.setGravity(0);
		cam.setLocation(new Vector3f(0, 0, 0));
		player.setPhysicsLocation(new Vector3f(128, 2.5f, 128));

		componentLoader = new GuiComponentLoader(assetManager);

		initBulletAppState();

		initScene();

		initPhysicsSpace();

		flyCam.setEnabled(true);

		flyCam.setMoveSpeed(0);
		flyCam.setRotationSpeed(rotateSpeed);
		flyCam.setDragToRotate(false);
		flyCam.setZoomSpeed(0);

		initPrototypeCube();
		initCrossHairs();
		AppSettings newSetting = new AppSettings(true);
		newSetting.setFrameRate(60);
		setSettings(newSetting);
		createWireBox();
		nodoModel = new BatchNode();
		nodoModel.attachChild(modelCube);
		rootNode.attachChild(nodoModel);

	}

	private void createWireBox() {
		wireBoxCube = new WireBox();
		modelCube = new Geometry("box", wireBoxCube);
		wireBoxCube.fromBoundingBox((BoundingBox) modelCube.getModelBound());
		wireBoxCube.setLineWidth(2f);
		final Material material = new Material(getAssetManager(),
				"Common/MatDefs/Misc/Unshaded.j3md");
		modelCube.setMaterial(material);
		material.setColor("Color", ColorRGBA.LightGray);
		final Material material1 = new Material(getAssetManager(),
				"Common/MatDefs/Misc/Unshaded.j3md");
		material1.setColor("Color", ColorRGBA.LightGray);
		material1.getAdditionalRenderState().setWireframe(true);
		wedge.setMaterial(material1);

	}

	public Camera getCam() {

		return cam;
	}

	public AssetManager getAssetManager() {
		return super.assetManager;

	}

	public InputManager getInputManager() {

		return super.inputManager;

	}

	public AudioRenderer getAudioRenderer() {
		return super.audioRenderer;

	}

	public ViewPort getViewPort() {
		return guiViewPort;
	}

	public FlyByCamera getFlyCamera() {

		return flyCam;
	}

	protected void initCrossHairs() {
		guiNode.detachAllChildren();
		guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
		BitmapText ch = new BitmapText(guiFont, false);
		ch.setSize(guiFont.getCharSet().getRenderedSize() * 2);
		ch.setText("+"); // fake crosshairs :)
		ch.setLocalTranslation(
				// center
				settings.getWidth() / 2
						- guiFont.getCharSet().getRenderedSize() / 3 * 2,
				settings.getHeight() / 2 + ch.getLineHeight() / 2, 0);
		guiNode.attachChild(ch);

	}

	private void createCube(int posx, int posy, int posz) {
		if (currentType != CubeType.STONE) {

			Cube tmp = new Cube(geometry, posx, posy, posz, currentType);
			spatialMap.put(tmp.geometry.getLocalTranslation().toString(), tmp);
			getPhysicsSpace().add(tmp.geometry);

			nodoScena.attachChild(tmp.geometry);
			tmp.geometry.setMaterial(getMaterial(currentType));
		} else {
			Spatial spatial = wedgeCube.clone();
			spatial.setLocalTranslation(posx, posy, posz);
			spatial.rotate(wedge.getLocalRotation());
			spatial.addControl(new RigidBodyControl(CollisionShapeFactory
					.createMeshShape(spatial), 0));
			getPhysicsSpace().add(spatial);
			nodoScena.attachChild(spatial);

		}

	}

	private Material getMaterial(CubeType type) {
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

	public void myClone() {

		int posx = (int) cursorPos.x;
		int posz = (int) cursorPos.z;
		int posy = (int) cursorPos.y;
		String key = "(" + posx + ".0, " + posy + ".0, " + posz + ".0)";
		if (!IsPositionValide(posx, posy, posz) || spatialMap.containsKey(key)) {
			return;
		}
		createCube(posx, posy, posz);

	}

	boolean IsPositionValide(int posx, int posy, int posz) {
		if (posx < 1 || posz < 1 || posx >= SIZEMAP || posz >= SIZEMAP
				|| posx % 2 == 0 || posz % 2 == 0 || posy % 2 == 0 || posy < 1) {
			return false;
		}
		return true;

	}

	@Override
	public void simpleUpdate(float tpf) {
		super.simpleUpdate(tpf);

		camDir.set(cam.getDirection());
		camDir.y = 0;
		camLeft.set(cam.getLeft());

		walkDirection.set(0, 0, 0);
		if (leftStrafe) {
			walkDirection.addLocal(camLeft.mult(moveSpeed));
		}
		if (rightStrafe) {
			walkDirection.addLocal(camLeft.negate().multLocal(moveSpeed));
		}
		if (forward) {
			walkDirection.addLocal(camDir.mult(moveSpeed));
		}
		if (backward) {
			walkDirection.addLocal(camDir.negate().multLocal(moveSpeed));
		}
		if (up) {
			walkDirection.y += riseSpeed;

		}
		if (down) {
			walkDirection.y -= riseSpeed;
		}

		player.setWalkDirection(walkDirection);
		cam.setLocation(player.getPhysicsLocation());

		ray.setOrigin(cam.getLocation());
		ray.setDirection(cam.getDirection());
		CollisionResults results = new CollisionResults();
		nodoScena.collideWith(ray, results);

		// Use the results
		if (results.size() > 0) {
			// how to react when a collision was detected
			CollisionResult closest = results.getClosestCollision();
			cursorPos = closest.getContactPoint();
			position();
			nodoModel.setLocalTranslation(cursorPos);

			Long currentTime = GregorianCalendar.getInstance()
					.getTimeInMillis();

			if (currentTime - 190 > coolDown) {
				coolDown = currentTime;
				if (insert) {
					myClone();
				}
				if (delete)
					remove();
			}
		}

		float[] angles = new float[3];
		cam.getRotation().toAngles(angles);

		if (angles[0] > FastMath.HALF_PI) {
			angles[0] = FastMath.HALF_PI;
			cam.setRotation(tmpQuat.fromAngles(angles));
		} else if (angles[0] < -FastMath.HALF_PI) {
			angles[0] = -FastMath.HALF_PI;
			cam.setRotation(tmpQuat.fromAngles(angles));
		}
	}

	public void position() {

		int x = (int) cursorPos.x;

		int z = (int) cursorPos.z;

		int y = (int) cursorPos.y;

		if (x % 2 == 0) {

			if (Math.abs(x + 1 - cursorPos.x) == Math
					.abs(cursorPos.x - (x - 1))) {
				if (cam.getLocation().x < x)
					x--;
				else
					x++;

			}

			else if (Math.abs(x + 1 - cursorPos.x) > Math.abs(cursorPos.x
					- (x - 1)))

				x--;
			else
				x++;
		}

		if (z % 2 == 0) {

			if (Math.abs(z + 1 - cursorPos.z) == Math
					.abs(cursorPos.z - (z - 1))) {
				if (cam.getLocation().z < z)
					z--;
				else
					z++;

			}

			else if (Math.abs(z + 1 - cursorPos.z) > Math.abs(cursorPos.z
					- (z - 1)))
				z--;
			else
				z++;
		}

		if (y % 2 == 0) {
			if (cam.getLocation().y < y)
				y--;
			else
				y++;
		}
		cursorPos.y = y;

		cursorPos.x = x;
		cursorPos.z = z;

		if (cursorPos.z > SIZEMAP - 1)
			cursorPos.z = SIZEMAP - 1;
		if (cursorPos.x > SIZEMAP - 1)
			cursorPos.x = SIZEMAP - 1;
		if (cursorPos.x < 1)
			cursorPos.x = 1;
		if (cursorPos.z < 1)
			cursorPos.z = 1;
		if (cursorPos.y < 1)
			cursorPos.y = 1;

	}

	@Override
	public void simpleRender(RenderManager rm) {

	}

	private void initBulletAppState() {

		bulletAppState = new BulletAppState();
		stateManager.attach(bulletAppState);
		bulletAppState.setDebugEnabled(true);
	}

	private void initPhysicsSpace() {

		getPhysicsSpace().addAll(rootNode);
		getPhysicsSpace().add(player);
	}

	public void initScene() {
		float s[] = new float[1];
		terrainQuad = new TerrainQuad("Terrain", SIZEMAP, SIZEMAP + 1, s);
		Material material = new Material(assetManager,
				"Common/MatDefs/Terrain/Terrain.j3md");
		material.setTexture("Alpha",
				assetManager.loadTexture("Textures/alpha.png"));
		Texture texture = componentLoader.getGrassTexture();
		texture.setWrap(WrapMode.Repeat);

		material.setTexture("Tex1", texture);
		material.setFloat("Tex1Scale", 128f);
		terrainQuad.setMaterial(material);

		nodoScena = new BatchNode();

		RigidBodyControl terrainControl = new RigidBodyControl(0);
		terrainQuad.setLocalTranslation(SIZEMAP / 2, 0, SIZEMAP / 2);
		terrainQuad.addControl(terrainControl);
		nodoScena.attachChild(terrainQuad);
		rootNode.attachChild(nodoScena);

		// getPhysicsSpace().setGravity(new Vector3f(0, -9.8f, 0));

	}

	public void remove() {
		ray.setOrigin(cam.getLocation());
		ray.setDirection(cam.getDirection());
		CollisionResults results = new CollisionResults();
		nodoScena.collideWith(ray, results);

		// Use the results
		if (results.size() > 0) {
			// how to react when a collision was detected
			cursorPos = results.getClosestCollision().getGeometry()
					.getLocalTranslation();
			Geometry geometry = results.getClosestCollision().getGeometry();

			// positionRemove();
			int x = (int) cursorPos.x;
			int y = (int) cursorPos.y;
			int z = (int) cursorPos.z;

			if (IsPositionValide(x, y, z)) {
				if (spatialMap.containsKey(geometry.getLocalTranslation()
						.toString())) {
					spatialMap.get(geometry.getLocalTranslation().toString())
							.activeControl(false);

					nodoScena.detachChild(geometry);
					spatialMap
							.remove(geometry.getLocalTranslation().toString());

				}
			}
		}

	}

	public void onAction(String name, boolean isPressed, float tpf) {

		if (!activeMouse) {
			flyCam.setRotationSpeed(rotateSpeed);
			if (name.equals("StrafeLeft")) {
				leftStrafe = isPressed;
			}
			if (name.equals("StrafeRight")) {
				rightStrafe = isPressed;
			}
			if (name.equals("MoveForward")) {
				forward = isPressed;

			}
			if (name.equals("MoveBackward")) {
				backward = isPressed;
			}
			if (name.equals("MoveUp")) {
				up = isPressed;
			}
			if (name.equals("MoveDown")) {
				down = isPressed;
			}

			if (name.equals("Insert") && !delete) {
				if (isPressed) {
					insert = true;
					myClone();
				} else
					insert = false;
			}

			if (name.equals("Delete") && !insert) {
				if (isPressed) {
					delete = true;
					remove();

				} else
					delete = false;
			}
			if (name.equals("rotate") && isPressed) {
				wedge.rotate(0, FastMath.HALF_PI, 0);
			}

		}

		if (name.equals("activeMouse")
				&& isPressed
				&& myEditorScreen.getCurrentScreen().getScreenId()
						.equals("CubeScreen")) {

			activeMouse = !activeMouse;
			flyCam.setDragToRotate(activeMouse);
			flyCam.setRotationSpeed(0);
			insert = false;
			delete = false;
		}

		if (name.equals("Exit"))
			stop();

		coolDown = GregorianCalendar.getInstance().getTimeInMillis();

	}

	private void optimize() {
		// TODO Auto-generated method stub
		// GeometryBatchFactory.optimize(nodoScena);
		for (Map.Entry<String, Cube> entry : spatialMap.entrySet()) {
			entry.getValue().activeControl(false);
		}
		spatialMap.clear();
		nodoScena.addControl(new RigidBodyControl(CollisionShapeFactory
				.createMeshShape(nodoScena)));
		nodoScena.updateModelBound();
		nodoScena.batch();

	}

	private void initListener() {

		inputManager.addMapping(InputMap.StrafeLeft.name(), new KeyTrigger(
				KeyInput.KEY_A));
		inputManager.addMapping(InputMap.StrafeRight.name(), new KeyTrigger(
				KeyInput.KEY_D));
		inputManager.addMapping(InputMap.MoveForward.name(), new KeyTrigger(
				KeyInput.KEY_W));
		inputManager.addMapping(InputMap.MoveBackward.name(), new KeyTrigger(
				KeyInput.KEY_S));
		inputManager.addMapping(InputMap.MoveUp.name(), new KeyTrigger(
				KeyInput.KEY_SPACE));
		inputManager.addMapping(InputMap.MoveDown.name(), new KeyTrigger(
				KeyInput.KEY_LCONTROL));
		inputManager.addMapping(InputMap.Exit.name(), new KeyTrigger(
				KeyInput.KEY_ESCAPE));
		inputManager.addMapping(InputMap.Insert.name(), new MouseButtonTrigger(
				MouseInput.BUTTON_LEFT));
		inputManager.addMapping(InputMap.Delete.name(), new MouseButtonTrigger(
				MouseInput.BUTTON_RIGHT));
		inputManager.addMapping(InputMap.activeMouse.name(), new KeyTrigger(
				KeyInput.KEY_E));
		inputManager.addMapping(InputMap.rotate.name(), new KeyTrigger(
				KeyInput.KEY_R));

		for (InputMap i : InputMap.values()) {
			inputManager.addListener(this, i.name());
		}

	}

	private PhysicsSpace getPhysicsSpace() {
		return bulletAppState.getPhysicsSpace();
	}

	public void changeScreen(String screen) {

		myEditorScreen.gotoScreen(screen);

	}

	public void loadMap(String nameSelected) {

		File file = new File(CubikArenaPath.getMappath(), nameSelected);

		if (!file.exists())
			return;
		FileReader fileReader = null;
		try {
			fileReader = new FileReader(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		clearScene();

		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String string;
		try {
			while ((string = bufferedReader.readLine()) != null) {
				StringTokenizer stringTokenizer = new StringTokenizer(string,
						" ");
				while (stringTokenizer.hasMoreTokens()) {
					String token = stringTokenizer.nextToken();

					if (token.equals("WALL"))
						currentType = CubeType.WALL;
					if (token.equals("STONE"))
						currentType = CubeType.STONE;
					if (token.equals("GRASS"))
						currentType = CubeType.GRASS;
					if (token.equals("WOOD"))
						currentType = CubeType.WOOD;
					if (stringTokenizer.equals(" "))
						stringTokenizer.nextToken();
					stringTokenizer.nextToken();
					cursorPos.x = Integer.parseInt(stringTokenizer.nextToken());
					stringTokenizer.nextToken();
					cursorPos.y = Integer.parseInt(stringTokenizer.nextToken());
					stringTokenizer.nextToken();
					cursorPos.z = Integer.parseInt(stringTokenizer.nextToken());
					myClone();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {

				fileReader.close();
				bufferedReader.close();

			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	public void saveMap() {

		try {
			// nodoScena.batch();
			StoreMapFile storeMapFile = new StoreMapFile(spatialMap, nameMap);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
