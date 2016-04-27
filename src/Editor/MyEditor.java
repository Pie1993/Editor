package Editor;

import java.io.File;
import java.io.IOException;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.export.binary.BinaryExporter;
import com.jme3.font.BitmapText;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;

import de.lessvoid.nifty.Nifty;

public class MyEditor extends SimpleApplication {

	MyActionListener actionListener;

	@Override
	public void stop() {
		saveMap();

		super.stop();

	}

	private void load() {

		try {
			Node loadedNode = (Node) assetManager.loadModel("Salvataggio/"
					+ "savedgame.j3o");
			rootNode.attachChild(loadedNode);
		} catch (com.jme3.asset.AssetNotFoundException e) {
			System.out.println("cazzo");
		}

	}

	private void saveMap() {

		File file = new File("Salvataggio/" + "savedgame.j3o");
		BinaryExporter exporter = BinaryExporter.getInstance();
		try {
			exporter.save(rootNode, file);
		} catch (IOException ex) {
			System.out.println("vaffanculo");
		}
	}

	public static void main(String[] args) {

		MyEditor app = new MyEditor();
		app.start();

	}

	@Override
	public void simpleInitApp() {

		assetManager.registerLocator("./assets/", FileLocator.class); // /cambia
																		// percorso
		init();
		ComponentLoader.getIstance().init(assetManager);
		EditorManager.getIstance().init(cam, actionListener, assetManager,
				stateManager, rootNode);
		initCrossHairs();
		AppSettings newSetting = new AppSettings(true);
		newSetting.setFrameRate(60);
		setSettings(newSetting);
		load();
	}

	private void init() {

		NiftyJmeDisplay display = new NiftyJmeDisplay(getAssetManager(),
				inputManager, getAudioRenderer(), viewPort);
		Nifty nifty = display.getNifty();
		actionListener = new MyActionListener(inputManager, flyCam, nifty);
		MyScreenController myScreenController = new MyScreenController(display);
		ScreenNiftyCube screenNiftyCube = new ScreenNiftyCube(nifty, display,
				guiViewPort, myScreenController);
		ScreenSaveMap screenSaveMap = new ScreenSaveMap(nifty, display,
				guiViewPort, myScreenController);
		ScreenLoadMap screenLoadMap = new ScreenLoadMap(nifty, display,
				guiViewPort, myScreenController);

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

	@Override
	public void simpleUpdate(float tpf) {
		super.simpleUpdate(tpf);
		EditorManager.getIstance().update();
		if (EditorManager.getIstance().stop)
			stop();
	}

}
