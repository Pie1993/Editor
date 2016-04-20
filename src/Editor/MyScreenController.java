package Editor;

import java.io.File;
import java.io.FilenameFilter;

import com.jme3.niftygui.NiftyJmeDisplay;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.ListBox;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

public class MyScreenController implements ScreenController {
	Nifty nifty;
	NiftyJmeDisplay display;

	public MyScreenController(NiftyJmeDisplay display) {

		this.display = display;

	}

	public void GrassCube() {
		EditorManager.getIstance().currentType = CubeType.GRASS;

		EditorManager.getIstance().nodoModel.detachAllChildren();
		EditorManager.getIstance().nodoModel.attachChild(EditorManager
				.getIstance().modelCube);

	}

	public void WoodCube() {
		EditorManager.getIstance().currentType = CubeType.WOOD;

	}

	public void StoneCube() {
		EditorManager.getIstance().currentType = CubeType.STONE;
		EditorManager.getIstance().nodoModel.detachAllChildren();
		EditorManager.getIstance().nodoModel.attachChild(EditorManager
				.getIstance().wedge);
	}

	public void WallCube() {
		EditorManager.getIstance().currentType = CubeType.WALL;

	}

	public void LoadMap() {

		String selection = (String) EditorManager.getIstance().listBox
				.getSelection().get(0);
		EditorManager.getIstance().loadMap(selection);

		changeScreen("CubeScreen");
		nifty.setIgnoreKeyboardEvents(true);

	}

	private void changeScreen(String string) {
		nifty.gotoScreen(string);

	}

	public void SaveMap() {

		Screen screen = nifty.getCurrentScreen();

		TextField txt = screen.findNiftyControl("nameMap", TextField.class);
		EditorManager.getIstance().nameMap = txt.getText();
		EditorManager.getIstance().saveMap();
		changeScreen("CubeScreen");
		nifty.setIgnoreKeyboardEvents(true);
	}

	public void SaveScreen() {

		changeScreen("SaveScreen");
		nifty.setIgnoreKeyboardEvents(false);

	}

	public void LoadScreen() {
		fillMyListBox();
		changeScreen("LoadScreen");
		nifty.setIgnoreKeyboardEvents(true);

	}

	public void Return() {

		changeScreen("CubeScreen");
		nifty.setIgnoreKeyboardEvents(true);
	}

	@Override
	public void bind(Nifty arg0, Screen arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onEndScreen() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStartScreen() {
		// TODO Auto-generated method stub

	}

	public void fillMyListBox() {

		Screen screen = nifty.getScreen("LoadScreen");
		FilenameFilter filenameFilter = new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".txt");
			}
		};

		EditorManager.getIstance().listBox = screen.findNiftyControl("listBox",
				ListBox.class);
		EditorManager.getIstance().listBox.clear();
		File folder = new File(CubikArenaPath.getMappath());
		File[] listOfFiles = folder.listFiles(filenameFilter);

		for (File file : listOfFiles) {
			EditorManager.getIstance().listBox.addItem(file.getName());

		}

	}

}
