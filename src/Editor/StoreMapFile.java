package Editor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class StoreMapFile {

	public StoreMapFile(

	HashMap<String, Cube> spatialMap, String nameMap) throws IOException {
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(
				new File(CubikArenaPath.getMappath(), nameMap + ".txt")));

		for (Map.Entry<String, Cube> entry : spatialMap.entrySet()) {

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

};
