package engine;

import static org.lwjgl.opengl.GL11.GL_AMBIENT_AND_DIFFUSE;
import static org.lwjgl.opengl.GL11.GL_COLOR_MATERIAL;
import static org.lwjgl.opengl.GL11.GL_DIFFUSE;
import static org.lwjgl.opengl.GL11.GL_FRONT;
import static org.lwjgl.opengl.GL11.GL_LIGHT0;
import static org.lwjgl.opengl.GL11.GL_LIGHT1;
import static org.lwjgl.opengl.GL11.GL_LIGHTING;
import static org.lwjgl.opengl.GL11.GL_LIGHT_MODEL_AMBIENT;
import static org.lwjgl.opengl.GL11.GL_POSITION;
import static org.lwjgl.opengl.GL11.GL_SHININESS;
import static org.lwjgl.opengl.GL11.GL_SMOOTH;
import static org.lwjgl.opengl.GL11.GL_SPECULAR;
import static org.lwjgl.opengl.GL11.glColorMaterial;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glLight;
import static org.lwjgl.opengl.GL11.glLightModel;
import static org.lwjgl.opengl.GL11.glMaterial;
import static org.lwjgl.opengl.GL11.glMaterialf;
import static org.lwjgl.opengl.GL11.glShadeModel;

import java.nio.FloatBuffer;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;

import breakout.Ball;
import breakout.Paddle;

public class Light {
	private FloatBuffer matSpecular;
	private FloatBuffer lightPosition;
	private FloatBuffer whiteLight;
	private FloatBuffer lModelAmbient;

	public Light() {
		initLightArrays();
		glShadeModel(GL_SMOOTH);
		glMaterial(GL_FRONT, GL_SPECULAR, matSpecular);
		glMaterialf(GL_FRONT, GL_SHININESS, 50.0f);
		glLight(GL_LIGHT0, GL_POSITION, lightPosition);
		glLight(GL_LIGHT0, GL_SPECULAR, whiteLight);
		glLight(GL_LIGHT0, GL_DIFFUSE, whiteLight);
		glLight(GL_LIGHT1, GL_POSITION, lightPosition);
		glLight(GL_LIGHT1, GL_SPECULAR, whiteLight);
		glLight(GL_LIGHT1, GL_DIFFUSE, whiteLight);
		glLightModel(GL_LIGHT_MODEL_AMBIENT, lModelAmbient);
		glEnable(GL_LIGHTING);
		glEnable(GL_LIGHT0);
		glEnable(GL_LIGHT1);
		glEnable(GL_COLOR_MATERIAL);
		glColorMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE);
	}

	private void initLightArrays() {
		matSpecular = BufferUtils.createFloatBuffer(4);
		matSpecular.put(1.0f).put(1.0f).put(1.0f).put(1.0f).flip();
		lightPosition = BufferUtils.createFloatBuffer(4);
		lightPosition.put(Display.getWidth() / 2.0f)
				.put(Display.getHeight() / 2.0f).put(50.0f).put(1.0f).flip();
		whiteLight = BufferUtils.createFloatBuffer(4);
		whiteLight.put(1.0f).put(1.0f).put(1.0f).put(1.0f).flip();
		lModelAmbient = BufferUtils.createFloatBuffer(4);
		lModelAmbient.put(0.0f).put(0.0f).put(0.0f).put(1.0f).flip();
	}

	public void update(Paddle paddle, List<Ball> balls) {
		lightPosition.put(0, paddle.getX() + paddle.getWidth() / 2.0f).put(1,
				paddle.getY() + paddle.getHeight() / 2.0f);
		glLight(GL_LIGHT0, GL_POSITION, lightPosition);

		Ball b = balls.get(0);
		lightPosition.put(0, b.getX() + b.getR() / 2.0f).put(1,
				b.getY() + b.getR() / 2.0f);
		glLight(GL_LIGHT1, GL_POSITION, lightPosition);
	}
}
