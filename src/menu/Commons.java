package menu;

public class Commons {
	private static Commons instance;
	private boolean keyPressed;
	
	public Commons() {
		this.keyPressed = true;
	}
	
	public boolean isKeyPressed() {
		return keyPressed;
	}
	
	public void setKeyPressed(boolean keyPressed) {
		this.keyPressed = keyPressed;
	}
	
	public static Commons get() {
		if(instance == null) {
			instance = new Commons();
		}
		return instance;
	}
}
