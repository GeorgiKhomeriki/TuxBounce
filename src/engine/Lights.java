package engine;

import static org.lwjgl.opengl.GL11.GL_AMBIENT_AND_DIFFUSE;
import static org.lwjgl.opengl.GL11.GL_COLOR_MATERIAL;
import static org.lwjgl.opengl.GL11.GL_DIFFUSE;
import static org.lwjgl.opengl.GL11.GL_FRONT;
import static org.lwjgl.opengl.GL11.GL_LIGHT0;
import static org.lwjgl.opengl.GL11.GL_LIGHT1;
import static org.lwjgl.opengl.GL11.GL_LIGHT2;
import static org.lwjgl.opengl.GL11.GL_LIGHT3;
import static org.lwjgl.opengl.GL11.GL_LIGHT4;
import static org.lwjgl.opengl.GL11.GL_LIGHT5;
import static org.lwjgl.opengl.GL11.GL_LIGHT6;
import static org.lwjgl.opengl.GL11.GL_LIGHT7;
import static org.lwjgl.opengl.GL11.GL_LIGHTING;
import static org.lwjgl.opengl.GL11.GL_LIGHT_MODEL_AMBIENT;
import static org.lwjgl.opengl.GL11.GL_POSITION;
import static org.lwjgl.opengl.GL11.GL_SHININESS;
import static org.lwjgl.opengl.GL11.GL_SMOOTH;
import static org.lwjgl.opengl.GL11.GL_SPECULAR;
import static org.lwjgl.opengl.GL11.glColorMaterial;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glIsEnabled;
import static org.lwjgl.opengl.GL11.glLight;
import static org.lwjgl.opengl.GL11.glLightModel;
import static org.lwjgl.opengl.GL11.glMaterial;
import static org.lwjgl.opengl.GL11.glMaterialf;
import static org.lwjgl.opengl.GL11.glShadeModel;

import game.Ball;
import game.Paddle;

import java.nio.FloatBuffer;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;


public class Lights {
	private FloatBuffer matSpecular;
	private FloatBuffer lightPosition;
	private FloatBuffer whiteLight;
	private FloatBuffer lModelAmbient;

	public Lights() {
		initLightArrays();
		glShadeModel(GL_SMOOTH);
		glMaterial(GL_FRONT, GL_SPECULAR, matSpecular);
		glMaterialf(GL_FRONT, GL_SHININESS, 50.0f);
		enableLight(GL_LIGHT0);
		enableLight(GL_LIGHT1);
		glLightModel(GL_LIGHT_MODEL_AMBIENT, lModelAmbient);
		glEnable(GL_LIGHTING);
		glEnable(GL_COLOR_MATERIAL);
		glColorMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE);
	}
	
	private void enableLight(int GL_LIGHT) {
		glLight(GL_LIGHT, GL_POSITION, lightPosition);
		glLight(GL_LIGHT, GL_SPECULAR, whiteLight);
		glLight(GL_LIGHT, GL_DIFFUSE, whiteLight);
		glEnable(GL_LIGHT);
	}

	private void initLightArrays() {
		float z = 50.0f;
		matSpecular = BufferUtils.createFloatBuffer(4);
		matSpecular.put(1.0f).put(1.0f).put(1.0f).put(1.0f).flip();
		lightPosition = BufferUtils.createFloatBuffer(4);
		lightPosition.put(Display.getWidth() / 2.0f)
				.put(Display.getHeight() / 2.0f).put(z).put(1.0f).flip();
		whiteLight = BufferUtils.createFloatBuffer(4);
		whiteLight.put(1.0f).put(1.0f).put(1.0f).put(1.0f).flip();
		lModelAmbient = BufferUtils.createFloatBuffer(4);
		lModelAmbient.put(0.0f).put(0.0f).put(0.0f).put(1.0f).flip();
	}

	public void update(Paddle paddle, List<Ball> balls) {
		lightPosition.put(0, paddle.getX() + paddle.getWidth() / 2.0f).put(1,
				paddle.getY() + paddle.getHeight() / 2.0f);
		glLight(GL_LIGHT0, GL_POSITION, lightPosition);

		int i=1;
		for(; i < balls.size() + 1; i++) {
			if(!glIsEnabled(getLight(i))) {
				enableLight(getLight(i));
			}
			Ball b = balls.get(i - 1);
			lightPosition.put(0, b.getX() + b.getR() / 2.0f).put(1,
					b.getY() + b.getR() / 2.0f);
			glLight(getLight(i), GL_POSITION, lightPosition);
		}
		for(; i < 8; i++) {
			if(glIsEnabled(getLight(i))) {
				glDisable(getLight(i));
			}
		}
	}
	
	public int getLight(int i) {
		switch(i) {
		case 0:
			return GL_LIGHT0;
		case 1:
			return GL_LIGHT1;
		case 2:
			return GL_LIGHT2;
		case 3:
			return GL_LIGHT3;
		case 4:
			return GL_LIGHT4;
		case 5:
			return GL_LIGHT5;
		case 6:
			return GL_LIGHT6;
		case 7:
			return GL_LIGHT7;
		default:
			return -1;
		}
	}
}
