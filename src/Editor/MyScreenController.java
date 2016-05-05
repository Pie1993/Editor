package Editor;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import com.jme3.niftygui.NiftyJmeDisplay;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.ListBox;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.ImageRenderer;
import de.lessvoid.nifty.render.NiftyImage;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

public class MyScreenController implements ScreenController {
	Nifty nifty;
	NiftyJmeDisplay display;
	String name = "subpanel1";
	Element popupClearElement;
	Element popupBackElement;

	public MyScreenController(NiftyJmeDisplay display) {

		this.display = display;
		nifty = display.getNifty();

	}

	public void setName(String name) {
		NiftyImage image = nifty.getRenderEngine().createImage(
				nifty.getCurrentScreen(), "Textures/weaponsgrid.png", false);
		Element element = nifty.getCurrentScreen().findElementByName(this.name);
		element.getRenderer(ImageRenderer.class).setImage(image);
		this.name = name;
	}

	public void changeImage() {
		NiftyImage image = nifty.getRenderEngine().createImage(
				nifty.getCurrentScreen(), "Textures/selectedgrid.png", false);
		Element element = nifty.getCurrentScreen().findElementByName(name);
		element.getRenderer(ImageRenderer.class).setImage(image);
	}

	public void setType(String string, String name) {
		setName(name);
		changeImage();

		CubeType type = CubeType.valueOf(string.toUpperCase());
		EditorManager.getIstance().setCurrentType(type);
		if (EditorManager.getIstance().isWedgeActive()) {
			EditorManager.getIstance().nodoModel.detachAllChildren();
			EditorManager.getIstance().nodoModel.attachChild(EditorManager
					.getIstance().wedge);
		} else {
			EditorManager.getIstance().nodoModel.detachAllChildren();
			EditorManager.getIstance().nodoModel.attachChild(EditorManager
					.getIstance().modelCube);
		}
	}

	public void loadMap() {

		String selection = (String) EditorManager.getIstance().listBox
				.getSelection().get(0);
		EditorManager.getIstance().clearScene();
		MapFile.loadMap(selection);
		changeScreen("GScreen0");
		lockEvents(true);

	}

	private void changeScreen(String string) {

		nifty.gotoScreen(string);

	}

	public void saveMap() {

		Screen screen = nifty.getCurrentScreen();

		TextField txt = screen.findNiftyControl("nameMap", TextField.class);
		try {
			MapFile.storeMap(txt.getText());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		changeScreen("GScreen0");
		lockEvents(true);
	}

	public void backToMenu() {
		EditorManager.getIstance().stop = true;
	}

	public void saveScreen() {

		changeScreen("SaveScreen");
		lockEvents(false);

	}

	public void loadScreen() {

		fillMyListBox();

		changeScreen("LoadScreen");

		lockEvents(false);

	}

	public void lockEvents(boolean value) {
		nifty.setIgnoreKeyboardEvents(value);
		EditorManager.getIstance().events = value;

	}

	public void Return() {

		changeScreen("GScreen0");
		lockEvents(true);
	}

	@Override
	public void bind(Nifty arg0, Screen arg1) {
		// TODO Auto-generated method stub

	}

	public void clearMap() {
		EditorManager.getIstance().clearScene();
		closePopup("popupClearMap");
	}

	private void init() {
		this.popupClearElement = nifty.createPopup("popupClearMap");
		this.popupBackElement = nifty.createPopup("popupBackToMenu");
	}

	public void popup(String name) {
		switch (name) {
		case "popupClearMap":
			nifty.showPopup(nifty.getCurrentScreen(),
					popupClearElement.getId(), null);
			lockEvents(false);

			break;
		case "popupBackToMenu":
			nifty.showPopup(nifty.getCurrentScreen(), popupBackElement.getId(),
					null);
			lockEvents(false);
			break;
		default:
			break;
		}
	}

	public void closePopup(String name) {
		switch (name) {
		case "popupClearMap":
			nifty.closePopup(popupClearElement.getId());
			lockEvents(true);
			break;
		case "popupBackToMenu":
			nifty.closePopup(popupBackElement.getId());
			lockEvents(true);
			break;
		default:
			break;
		}
	}

	@Override
	public void onEndScreen() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStartScreen() {
		init();

	}

	public void fillMyListBox() {

		Screen screen = nifty.getScreen("LoadScreen");
		FilenameFilter filenameFilter = new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".j3o");
			}
		};

		EditorManager.getIstance().listBox = screen.findNiftyControl("listBox",
				ListBox.class);

		EditorManager.getIstance().listBox.clear();
		File folder = new File("assets/" + CubikArenaPath.getSavePath());

		File[] listOfFiles = folder.listFiles(filenameFilter);
		for (File file : listOfFiles) {

			EditorManager.getIstance().listBox.addItem(file.getName());

		}

	}

}
