package Editor;

import java.util.GregorianCalendar;

import com.jme3.input.FlyByCamera;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.math.FastMath;

import de.lessvoid.nifty.Nifty;

public class MyActionListener implements ActionListener {
	public enum InputMap {
		StrafeLeft, StrafeRight, MoveForward, MoveBackward, MoveUp, MoveDown, Insert, Delete, Exit, activeMouse, rotate;
	}

	InputManager inputManager;
	FlyByCamera flyCam;
	boolean forward = false, backward = false, xRotate = false,
			yRotate = false, leftStrafe = false, rightStrafe = false,
			up = false, down = false, insert = false, delete = false,
			activeMouse = false;
	float xRotation, yRotation;
	private float rotateSpeed = 0.8f;

	private Nifty nifty;

	public MyActionListener(InputManager inputManager, FlyByCamera flyCam,
			Nifty nifty) {
		this.inputManager = inputManager;
		this.flyCam = flyCam;
		flyCam.setEnabled(true);
		flyCam.setMoveSpeed(0);
		flyCam.setRotationSpeed(rotateSpeed);
		flyCam.setDragToRotate(false);
		flyCam.setZoomSpeed(0);
		this.nifty = nifty;
		initListener();
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
					// for (int i = 1; i < 256; i += 2)
					// for (int j = 1; j < 256; j += 2) {
					// EditorManager.getIstance().cursorPos.x = i;
					// EditorManager.getIstance().cursorPos.z = j;

					EditorManager.getIstance().insert();
					// }
				} else
					insert = false;
			}

			if (name.equals("Delete") && !insert) {
				if (isPressed) {
					delete = true;
					EditorManager.getIstance().remove();

				} else
					delete = false;
			}
			if (name.equals("rotate") && isPressed) {
				EditorManager.getIstance().wedge.rotate(0, FastMath.HALF_PI, 0);
			}

		}

		if (name.equals("activeMouse") && isPressed
				&& nifty.getCurrentScreen().getScreenId().equals("CubeScreen")) {

			activeMouse = !activeMouse;
			flyCam.setDragToRotate(activeMouse);
			flyCam.setRotationSpeed(0);
			insert = delete = forward = backward = leftStrafe = rightStrafe = up = down = false;

		}

		if (name.equals("Exit")) {
			EditorManager.getIstance().clearScene();
			EditorManager.getIstance().stop = true;
		}
		EditorManager.getIstance().coolDown = GregorianCalendar.getInstance()
				.getTimeInMillis();

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

}
