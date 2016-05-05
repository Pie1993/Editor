package Editor;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import com.jme3.export.binary.BinaryExporter;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class MapFile {

	public static void storeMap(String nameMap) throws IOException {

		EditorManager.getIstance().nodoScena.detachChild(EditorManager
				.getIstance().terrainQuad);
		File file = new File("assets/" + CubikArenaPath.getSavePath() + nameMap
				+ ".j3o");
		BinaryExporter exporter = BinaryExporter.getInstance();
		try {
			exporter.save(EditorManager.getIstance().nodoScena, file);
		} catch (IOException ex) {

		}
		EditorManager.getIstance().nodoScena.attachChild(EditorManager
				.getIstance().terrainQuad);

	}

	public static void loadMap(String nameSelected) {

		try {
			Node node = (Node) Creator.getIstance().getAssetManager()
					.loadModel(CubikArenaPath.getSavePath() + nameSelected);

			List<Spatial> list = node.getChildren();
			Iterator<Spatial> it = list.iterator();

			while (it.hasNext()) {
				Spatial p = it.next();
				EditorManager.getIstance().nodoScena.attachChild(p);
				EditorManager.getIstance().spatialMap.put(p
						.getWorldTranslation().toString(), new Cube(p));

				EditorManager.getIstance().bulletAppState.getPhysicsSpace()
						.addAll(p);
				Creator.getIstance().updateInformation(
						CubeType.valueOf(p.getName()));

			}

		} catch (NullPointerException e) {
			// TODO: handle exception
		} catch (NoSuchElementException e) {
			// TODO: handle exception
		}

	}

};

// File file = new File(CubikArenaPath.getMappath(), nameSelected);
//
// if (!file.exists())
// return;
// FileReader fileReader = null;
// try {
// fileReader = new FileReader(file);
// } catch (FileNotFoundException e) {
// // TODO Auto-generated catch block
// e.printStackTrace();
// }
// Vector3f cursorPos = EditorManager.getIstance().cursorPos;
// int x, y, z, w;
// CubeType type = EditorManager.getIstance().currentType;
// BufferedReader bufferedReader = new BufferedReader(fileReader);
// String string;
// try {
// while ((string = bufferedReader.readLine()) != null) {
// StringTokenizer stringTokenizer = new StringTokenizer(string,
// " ");
// while (stringTokenizer.hasMoreTokens()) {
// String token = stringTokenizer.nextToken();
// EditorManager.getIstance().currentType = CubeType
// .valueOf(token.toUpperCase());
// if (stringTokenizer.equals(" "))
// stringTokenizer.nextToken();
// stringTokenizer.nextToken();
// cursorPos.x = Integer.parseInt(stringTokenizer.nextToken());
// stringTokenizer.nextToken();
// cursorPos.y = Integer.parseInt(stringTokenizer.nextToken());
// stringTokenizer.nextToken();
// cursorPos.z = Integer.parseInt(stringTokenizer.nextToken());
//
// if (EditorManager.getIstance().isWedgeActive()) {
//
// x = Integer.parseInt(stringTokenizer.nextToken());
// y = Integer.parseInt(stringTokenizer.nextToken());
// z = Integer.parseInt(stringTokenizer.nextToken());
// w = Integer.parseInt(stringTokenizer.nextToken());
//
// if (x == 1)
// EditorManager.getIstance().wedge.rotate(0,
// FastMath.HALF_PI, 0);
// else if (w == 1)
// EditorManager.getIstance().wedge.rotate(0, 0, 0);
// else if (y == 1)
// EditorManager.getIstance().wedge.rotate(0,
// FastMath.PI, 0);
// else if (z == 1)
// EditorManager.getIstance().wedge.rotate(0, 0, 0);
//
// }
// EditorManager.getIstance().insert();
// }
// }
// } catch (IOException e) {
// // TODO Auto-generated catch block
// e.printStackTrace();
// } finally {
// try {
//
// EditorManager.getIstance().currentType = type;
// fileReader.close();
// bufferedReader.close();
//
// } catch (IOException e) {
// e.printStackTrace();
// }
//
// }

// ////////////////////////////////////////////////////////////////////////////////////////////////////////////

// BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(
// new File(CubikArenaPath.getMappath(), nameMap + ".txt")));
//
// for (Map.Entry<String, Cube> entry : EditorManager.getIstance()
// .getSpatialMap()) {
//
// Cube cube = entry.getValue();
// if (cube != null) {
//
// bufferedWriter.write(cube.getType().toString() + " x "
// + (int) cube.geometry.getLocalTranslation().x + " y "
// + (int) cube.geometry.getLocalTranslation().y + " z "
// + (int) cube.geometry.getLocalTranslation().z);
// if (cube.getType().name().startsWith("W_"))
// bufferedWriter.write(" "
// + (int) cube.geometry.getWorldRotation().getX()
// + " "
// + (int) cube.geometry.getWorldRotation().getY()
// + " "
// + (int) cube.geometry.getWorldRotation().getZ()
// + " "
// + (int) cube.geometry.getWorldRotation().getW());
// bufferedWriter.newLine();
// }
// }
// bufferedWriter.flush();
// bufferedWriter.close();
//
//
//

