package Editor;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.scene.Spatial;

public class Cube implements PrototypeInterface {
	Spatial geometry;
	CubeType type;
	RigidBodyControl control;

	public Cube(Spatial geometry, int posx, int posy, int posz, CubeType type) {

		this.geometry = geometry.clone();
		this.geometry.setLocalTranslation(posx, posy, posz);
		this.control = new RigidBodyControl(
				CollisionShapeFactory.createMeshShape(this.geometry), 0);
		this.geometry.addControl(control);
		this.type = type;

	}

	public void activeControl(boolean value) {
		control.setEnabled(value);

	}

	@Override
	public CubeType getType() {
		// TODO Auto-generated method stub
		return type;
	}
}
