package menu;

import util.Config;

public class Commons {
	private static Commons instance;
	private boolean keyPressed;
	private int levelProgress;
	
	public Commons() {
		this.keyPressed = true;
		this.levelProgress = Config.loadProgress();
	}
	
	public boolean isKeyPressed() {
		return keyPressed;
	}
	
	public void setKeyPressed(final boolean keyPressed) {
		this.keyPressed = keyPressed;
	}
	
	public final int getLevelProgress() {
		return levelProgress;
	}

	public void saveLevelProgress(final int level) {
		Config.saveProgress(level);
		this.levelProgress = level;
	}

	public final static Commons get() {
		if(instance == null) {
			instance = new Commons();
		}
		return instance;
	}
}
