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

	private final static String modelPath = "Models/";
	private final static String mapPath = "assets/Map/";
	private final static String texturesPath = "Textures/";
	private final static String soundsPath = "Sounds/";
	private final static String healthPath = "health/health.obj";
	private final static String hastePath = "haste/haste.obj";
	private final static String ammoPath = "ammo/ammo.obj";
	private final static String regenerationPath = "regeneration/regeneration.obj";
	private final static String armorPath = "armor/armor.obj";
	private final static String ultradamagePath = "ultradamage/ultradamage.obj";
	private final static String untouchablePath = "untouchable/untouchable.obj";
	private final static String rocketLauncherPath = "Rocket/Rocket.mesh.j3o";
	private final static String chainsawPath = "chainsaw/chainsaw.obj";
	private final static String laserPath = "laser/laser_rifle.obj";
	private final static String shotgunPath = "Shotgun/Shotgun.mesh.j3o";
	private final static String sniperPath = "sniper/sniper.obj";
	private final static String riflePath = "Rifle/Rifle.mesh.j3o";

}
