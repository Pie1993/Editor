package Editor;

import java.io.File;
import java.io.FilenameFilter;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.ListBox;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

public class MyScreenController implements ScreenController {
	private MyEditor myEditor;

	public MyScreenController(MyEditor myEditor) {
		this.myEditor = myEditor;

	}

	public void GrassCube() {
		myEditor.currentType = CubeType.GRASS;
		myEditor.nodoModel.detachAllChildren();
		myEditor.nodoModel.attachChild(myEditor.modelCube);

	}

	public void WoodCube() {
		myEditor.currentType = CubeType.WOOD;

	}

	public void StoneCube() {
		myEditor.currentType = CubeType.STONE;
		myEditor.nodoModel.detachAllChildren();
		myEditor.nodoModel.attachChild(myEditor.wedge);
	}

	public void WallCube() {
		myEditor.currentType = CubeType.WALL;

	}

	public void LoadMap() {

		String selection = (String) myEditor.listBox.getSelection().get(0);
		myEditor.loadMap(selection);
		myEditor.changeScreen("CubeScreen");
		myEditor.myEditorScreen.setIgnoreKeyboardEvents(true);

	}

	public void SaveMap() {

		Screen screen = myEditor.myEditorScreen.getCurrentScreen();

		TextField txt = screen.findNiftyControl("nameMap", TextField.class);
		myEditor.nameMap = txt.getText();
		myEditor.saveMap();
		myEditor.myEditorScreen.gotoScreen("CubeScreen");
		myEditor.myEditorScreen.setIgnoreKeyboardEvents(true);
	}

	public void SaveScreen() {

		myEditor.changeScreen("SaveScreen");
		myEditor.myEditorScreen.setIgnoreKeyboardEvents(false);

	}

	public void LoadScreen() {
		fillMyListBox();
		myEditor.changeScreen("LoadScreen");
		myEditor.myEditorScreen.setIgnoreKeyboardEvents(true);

	}

	public void Return() {

		myEditor.changeScreen("CubeScreen");
		myEditor.myEditorScreen.setIgnoreKeyboardEvents(true);
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

		Screen screen = myEditor.myEditorScreen.getScreen("LoadScreen");
		FilenameFilter filenameFilter = new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".txt");
			}
		};

		myEditor.listBox = screen.findNiftyControl("listBox", ListBox.class);
		myEditor.listBox.clear();
		File folder = new File(CubikArenaPath.getMappath());
		File[] listOfFiles = folder.listFiles(filenameFilter);

		for (File file : listOfFiles) {
			myEditor.listBox.addItem(file.getName());

		}

	}

}
