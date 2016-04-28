package Editor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.StringTokenizer;

import com.jme3.math.Vector3f;

public class MapFile {

	public static void storeMap(String nameMap) throws IOException {
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(
				new File(CubikArenaPath.getMappath(), nameMap + ".txt")));

		for (Map.Entry<String, Cube> entry : EditorManager.getIstance()
				.getSpatialMap()) {

			Cube cube = entry.getValue();
			if (cube != null) {
				System.out.println(cube.getType().toString());
				bufferedWriter.write(cube.getType().toString() + " x "
						+ (int) cube.geometry.getLocalTranslation().x + " y "
						+ (int) cube.geometry.getLocalTranslation().y + " z "
						+ (int) cube.geometry.getLocalTranslation().z);
				bufferedWriter.newLine();
			}
		}
		bufferedWriter.flush();
		bufferedWriter.close();

	}

	public static void loadMap(String nameSelected) {

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
		CubeType currentType = EditorManager.getIstance().currentType;
		Vector3f cursorPos = EditorManager.getIstance().cursorPos;

		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String string;
		try {
			while ((string = bufferedReader.readLine()) != null) {
				StringTokenizer stringTokenizer = new StringTokenizer(string,
						" ");
				while (stringTokenizer.hasMoreTokens()) {
					String token = stringTokenizer.nextToken();
					currentType = CubeType.valueOf(token);
					if (stringTokenizer.equals(" "))
						stringTokenizer.nextToken();
					stringTokenizer.nextToken();
					cursorPos.x = Integer.parseInt(stringTokenizer.nextToken());
					stringTokenizer.nextToken();
					cursorPos.y = Integer.parseInt(stringTokenizer.nextToken());
					stringTokenizer.nextToken();
					cursorPos.z = Integer.parseInt(stringTokenizer.nextToken());
					EditorManager.getIstance().insert();
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

};
