package breakout;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.Display;

public class Hud {
	public static final float width = Display.getWidth();
	public static final float height = Display.getHeight() / 15.0f;
	public static final float displayHeight = Display.getHeight();
	
	public void render() {	
		drawBackground();
	}
	
	private void drawBackground() {
		glDisable(GL_TEXTURE_2D);
		glColor4f(0.0f, 0.0f, 0.0f, 0.5f);
		glBegin(GL_QUADS);
		glVertex2f(0.0f, displayHeight);
		glVertex2f(width, displayHeight);
		glVertex2f(width, displayHeight - height);
		glVertex2f(0.0f, displayHeight - height);
		glEnd();
		
		
		glBegin(GL_LINES);
//		glColor3f(1.0f, 0.0f, 0.0f);
//		glVertex2f(0.0f, displayHeight);
//		glColor3f(0.0f, 0.0f, 1.0f);
//		glVertex2f(width, displayHeight);
		
		glColor3f(1.0f, 0.0f, 0.0f);
		glVertex2f(0.0f, displayHeight - height);
		glColor3f(0.0f, 0.0f, 1.0f);
		glVertex2f(width, displayHeight - height);
		glEnd();
		
		glEnable(GL_TEXTURE_2D);
	}

	public void update(float delta) {

	}
}
