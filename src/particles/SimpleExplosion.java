package particles;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.opengl.Texture;

public class SimpleExplosion extends Particles {
	private static final float minDx = -40.0f;
	private static final float maxDx = 40.0f;
	private static final float minDy = 40.0f;
	private static final float maxDy = 60.0f;
	private static final float minAx = 0.0f;
	private static final float maxAx = 0.0f;
	private static final float minAy = -0.5f;
	private static final float maxAy = -3.0f;
	private static final float minWidth = Display.getWidth() / 100.0f;
	private static final float maxWidth = minWidth;
	private static final float minHeight = minWidth;
	private static final float maxHeight = minWidth;
	private static final int minLife = 50;
	private static final int maxLife = 150;
	private static final boolean doFade = false;

	public SimpleExplosion(int numParticles, Texture texture, float x, float y) {
		super(numParticles, texture, x, x, y, y, minDx, maxDx, minDy, maxDy,
				minAx, maxAx, minAy, maxAy, minWidth, maxWidth, minHeight,
				maxHeight, minLife, maxLife, doFade, 1.0f, 1.0f, 1.0f, 1.0f,
				1.0f, 1.0f);
	}

	public SimpleExplosion(int numParticles, Texture texture, float x, float y,
			float startR, float startG, float startB, float endR, float endG,
			float endB) {
		super(numParticles, texture, x, x, y, y, minDx, maxDx, minDy, maxDy,
				minAx, maxAx, minAy, maxAy, minWidth, maxWidth, minHeight,
				maxHeight, minLife, maxLife, doFade, startR, startG, startB,
				endR, endG, endB);
	}

	public SimpleExplosion(int numParticles, String texture, float x, float y,
			float startR, float startG, float startB, float endR, float endG,
			float endB) {
		super(numParticles, texture, x, x, y, y, minDx, maxDx, minDy, maxDy,
				minAx, maxAx, minAy, maxAy, minWidth, maxWidth, minHeight,
				maxHeight, minLife, maxLife, doFade, startR, startG, startB,
				endR, endG, endB);
	}

}
