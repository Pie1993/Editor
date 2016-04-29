package Editor;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.BatchNode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.terrain.geomipmap.TerrainQuad;

import de.lessvoid.nifty.controls.ListBox;

public class EditorManager {

	CubeType currentType = CubeType.CHAINSAW;

	Vector3f walkDirection = new Vector3f(0, 0, 0);
	Vector3f viewDirection = new Vector3f(1, 0, 1);
	private Vector3f camDir = new Vector3f();
	private Vector3f camLeft = new Vector3f();
	ListBox<String> listBox;
	public boolean stop = false;
	BulletAppState bulletAppState;
	private final static int SIZEMAP = 256;
	private HashMap<String, Cube> spatialMap;

	private Ray ray = new Ray();
	Spatial wedge;
	private Camera cam;
	private float moveSpeed = 0.3f;
	private float riseSpeed = 0.2f;
	Long coolDown = (long) 0;
	private Quaternion tmpQuat = new Quaternion();
	private MyActionListener actionListener;

	private CharacterControl player;
	BatchNode nodoScena, nodoModel;
	Vector3f cursorPos;

	Geometry modelCube, modelTriangle;

	private AppStateManager stateManager;
	private Node rootNode;

	public void update() {
		camDir.set(cam.getDirection());
		camDir.y = 0;
		camLeft.set(cam.getLeft());

		walkDirection.set(0, 0, 0);
		if (actionListener.leftStrafe) {
			walkDirection.addLocal(camLeft.mult(moveSpeed));
		}
		if (actionListener.rightStrafe) {
			walkDirection.addLocal(camLeft.negate().multLocal(moveSpeed));
		}
		if (actionListener.forward) {
			walkDirection.addLocal(camDir.mult(moveSpeed));
		}
		if (actionListener.backward) {
			walkDirection.addLocal(camDir.negate().multLocal(moveSpeed));
		}
		if (actionListener.up) {
			walkDirection.y += riseSpeed;
		}
		if (actionListener.down) {
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
			if (closest.getGeometry().getName().startsWith("Terrain"))
				nodoModel.setLocalTranslation(cursorPos);
			else
				nodoModel.setLocalTranslation(closest.getGeometry()
						.getWorldTranslation());
			Long currentTime = GregorianCalendar.getInstance()
					.getTimeInMillis();

			if (currentTime - 190 > coolDown) {
				coolDown = currentTime;
				if (actionListener.insert) {
					insert();
				}
				if (actionListener.delete)
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

	public static EditorManager getIstance() {
		if (manager == null)
			manager = new EditorManager();

		return manager;
	}

	private EditorManager() {

		spatialMap = new HashMap<String, Cube>();

	}

	private static EditorManager manager;

	public void init(Camera cam, MyActionListener actionListener,
			AssetManager assetManager, AppStateManager stateManager,
			Node rootNode) {
		this.cursorPos = new Vector3f(SIZEMAP / 2 - 1, 0, SIZEMAP / 2 - 1);
		this.cam = cam;
		this.actionListener = actionListener;
		this.stateManager = stateManager;
		this.rootNode = rootNode;
		Creator.getIstance().init(assetManager);

		modelCube = Creator.getIstance().createModelCube();
		wedge = Creator.getIstance().createWedge();
		nodoModel = new BatchNode();
		nodoModel.attachChild(modelCube);
		rootNode.attachChild(nodoModel);
		player = Creator.getIstance().createPlayer();
		cam.setLocation(new Vector3f(0, 0, 0));
		initScene();
		initBulletAppState();
		initPhysicsSpace();
		bulletAppState.setDebugEnabled(false);
	}

	private void initScene() {
		TerrainQuad terrainQuad = Creator.getIstance().createScene(SIZEMAP);
		nodoScena = new BatchNode();
		nodoScena.attachChild(terrainQuad);
		rootNode.attachChild(nodoScena);
	}

	private void initBulletAppState() {

		bulletAppState = new BulletAppState();
		stateManager.attach(bulletAppState);
	}

	private void initPhysicsSpace() {

		bulletAppState.getPhysicsSpace().addAll(rootNode);
		bulletAppState.getPhysicsSpace().add(player);
	}

	private boolean IsPositionValide(int posx, int posy, int posz) {
		if (posx < 1 || posz < 1 || posx >= SIZEMAP || posz >= SIZEMAP
				|| posx % 2 == 0 || posz % 2 == 0 || posy % 2 == 0 || posy < 1) {
			return false;
		}
		return true;

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
					.getWorldTranslation();
			Geometry geometry = results.getClosestCollision().getGeometry();

			// positionRemove();
			int x = (int) cursorPos.x;
			int y = (int) cursorPos.y;
			int z = (int) cursorPos.z;
			if (IsPositionValide(x, y, z)) {
				if (spatialMap.containsKey(geometry.getWorldTranslation()
						.toString())) {

					spatialMap.get(geometry.getWorldTranslation().toString())
							.activeControl(false);

					nodoScena.detachChild(geometry.getParent());
					nodoScena.detachChild(geometry);
					spatialMap
							.remove(geometry.getWorldTranslation().toString());

				}
			}
		}

	}

	public void insert() {

		int posx = (int) cursorPos.x;
		int posz = (int) cursorPos.z;
		int posy = (int) cursorPos.y;
		String key = "(" + posx + ".0, " + posy + ".0, " + posz + ".0)";
		if (!IsPositionValide(posx, posy, posz) || spatialMap.containsKey(key)) {
			return;
		}
		Cube tmp = Creator.getIstance().createCube(posx, posy, posz,
				currentType);
		tmp.geometry.rotate(wedge.getLocalRotation());
		tmp.geometry.getControl(RigidBodyControl.class).setPhysicsRotation(
				wedge.getLocalRotation());
		spatialMap.put(tmp.geometry.getWorldTranslation().toString(), tmp);
		bulletAppState.getPhysicsSpace().add(tmp.geometry);
		nodoScena.attachChild(tmp.geometry);

	}

	public void clearScene() {
		for (Map.Entry<String, Cube> entry : spatialMap.entrySet()) {
			nodoScena.detachChild(entry.getValue().geometry);
			entry.getValue().activeControl(false);

		}

		spatialMap.clear();

	}

	private void position() {

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

	public Set<Entry<String, Cube>> getSpatialMap() {
		return spatialMap.entrySet();
	}

	// private void optimize() {
	// // TODO Auto-generated method stub
	// // GeometryBatchFactory.optimize(nodoScena);
	// for (Map.Entry<String, Cube> entry : spatialMap.entrySet()) {
	// entry.getValue().activeControl(false);
	// }
	// spatialMap.clear();
	// nodoScena.addControl(new RigidBodyControl(CollisionShapeFactory
	// .createMeshShape(nodoScena)));
	// nodoScena.updateModelBound();
	// nodoScena.batch();
	//
	// }

	public void setCurrentType(CubeType type) {
		this.currentType = type;

	}
}