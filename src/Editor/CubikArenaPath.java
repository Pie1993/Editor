package Editor;

public class CubikArenaPath {

	public static String getModelpath() {
		return modelPath;
	}

	public static String getMappath() {
		return mapPath;
	}

	public static String getTexturespath() {
		return texturesPath;
	}

	public static String getSoundspath() {
		return soundsPath;
	}

	public static String getHealthpath() {
		return modelPath + healthPath;
	}

	public static String getHastepath() {
		return modelPath + hastePath;
	}

	public static String getAmmopath() {
		return modelPath + ammoPath;
	}

	public static String getRegenerationpath() {
		return modelPath + regenerationPath;
	}

	public static String getArmorpath() {
		return modelPath + armorPath;
	}

	public static String getUltradamagepath() {
		return modelPath + ultradamagePath;
	}

	public static String getUntouchablepath() {
		return modelPath + untouchablePath;
	}

	public static String getRocketlauncherpath() {
		return modelPath + rocketLauncherPath;
	}

	public static String getChainsawpath() {
		return modelPath + chainsawPath;
	}

	public static String getLaserpath() {
		return modelPath + laserPath;
	}

	public static String getShotgunpath() {
		return modelPath + shotgunPath;
	}

	public static String getSniperpath() {

		return modelPath + sniperPath;
	}

	public static String getRiflepath() {
		return modelPath + riflePath;
	}

	public static String getSavePath() {
		return savePath;
	}

	public static String getXmlpath() {
		return xmlPath;
	}

	private final static String modelPath = "Models/";
	private final static String mapPath = "Map/";
	private final static String texturesPath = "Textures/";
	private final static String soundsPath = "Sounds/";
	private final static String healthPath = "health/health.obj";
	private final static String hastePath = "haste/haste.obj";
	private final static String ammoPath = "ammo/Ammo.mesh.j3o";
	private final static String regenerationPath = "regeneration/regeneration.obj";
	private final static String armorPath = "armor/armor.obj";
	private final static String ultradamagePath = "ultradamage/ultradamage.mesh.j3o";
	private final static String untouchablePath = "untouchable/untouchable.obj";
	private final static String rocketLauncherPath = "Rocket Laucher/RocketLauncher.mesh.j3o";
	private final static String chainsawPath = "chainsaw/chainsaw.obj";
	private final static String laserPath = "laser/laser_rifle.obj";
	private final static String shotgunPath = "NewShotgun/Shotgun.mesh.j3o";
	private final static String sniperPath = "sniper/Sniper.mesh.j3o";
	private final static String riflePath = "NewRifle/Rifle.mesh.j3o";
	private final static String savePath = "Salvataggio/";
	private final static String xmlPath = "Xml/";

}
