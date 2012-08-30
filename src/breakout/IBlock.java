package breakout;

import org.lwjgl.opengl.Display;



public interface IBlock {
	public static final float width = Display.getWidth() / 20;
	public static final float height = Display.getHeight() / 15;
	
	public enum BlockType {
		RED,
		PURPLE,
		ORANGE,
		GREY_FACE,
		GREEN_FACE,
		BLUE_FACE,
		RED_FACE;
	}
	
	public enum BlockState {
		ALIVE,
		DEAD,
		DYING;
	}
	
	public void render();
	
	public void update(float delta);
	
	public void onHit();
	
	public boolean isHit(Ball ball);
	
	public float getX();
	
	public void setX(float x);
	
	public float getY();
	
	public void setY(float y);
	
	public BlockState getState();
	
}
