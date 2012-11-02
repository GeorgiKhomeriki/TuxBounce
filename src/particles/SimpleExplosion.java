package particles;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.opengl.Texture;

public class SimpleExplosion extends Particles {
	private static final float minDx = -Display.getWidth() / 36.0f;
	private static final float maxDx = Display.getWidth() / 36.0f;
	private static final float minDy = Display.getHeight() / 22.0f;
	private static final float maxDy = Display.getHeight() / 15.0f;
	private static final float minAy = -Display.getHeight() / 1000.0f;
	private static final float maxAy = -Display.getHeight() / 300.0f;
	private static final float size = Display.getWidth() / 80.0f;
	private static final int minLife = 50;
	private static final int maxLife = 150;

	public SimpleExplosion(int numParticles, Texture texture, float x, float y,
			float startIntensity, float endIntensity) {
		super(numParticles, texture, x, x, y, y, minDx, maxDx, minDy, maxDy,
				minAy, maxAy, size, minLife, maxLife, startIntensity,
				endIntensity);
	}

}
