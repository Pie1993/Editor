package Editor;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

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
		nifty = display.getNifty();

	}

	public void setType(String string) {

		CubeType type = CubeType.valueOf(string.toUpperCase());

		EditorManager.getIstance().setCurrentType(type);

	}

	public void loadMap() {

		String selection = (String) EditorManager.getIstance().listBox
				.getSelection().get(0);
		EditorManager.getIstance().clearScene();
		MapFile.loadMap(selection);
		changeScreen("GScreen0");
		nifty.setIgnoreKeyboardEvents(true);

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
		nifty.setIgnoreKeyboardEvents(true);
	}

	public void clearMap() {
		EditorManager.getIstance().clearScene();
	}

	public void backToMenu() {

	}

	public void saveScreen() {

		changeScreen("SaveScreen");
		nifty.setIgnoreKeyboardEvents(false);

	}

	public void loadScreen() {
		fillMyListBox();
		changeScreen("LoadScreen");
		nifty.setIgnoreKeyboardEvents(true);

	}

	public void Return() {

		changeScreen("GScreen0");
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
