package particles;

public class SimpleExplosion extends Particles {
	private static final String texture = "resources/images/ball.png";
	private static final float minDx = -40.0f;
	private static final float maxDx = 40.0f;
	private static final float minDy = 40.0f;
	private static final float maxDy = 60.0f;
	private static final float minAx = 0.0f;
	private static final float maxAx = 0.0f;
	private static final float minAy = -0.5f;
	private static final float maxAy = -3.0f;
	private static final float minWidth = 10.0f;
	private static final float maxWidth = 10.0f;
	private static final float minHeight = 10.0f;
	private static final float maxHeight = 10.0f;
	private static final int minLife = 50;
	private static final int maxLife = 150;
	private static final boolean doFade = false;

	public SimpleExplosion(int numParticles, float x, float y, float startR,
			float startG, float startB, float endR, float endG, float endB) {
		super(numParticles, texture, x, x, y, y, minDx, maxDx, minDy, maxDy,
				minAx, maxAx, minAy, maxAy, minWidth, maxWidth, minHeight,
				maxHeight, minLife, maxLife, doFade, startR, startG, startB,
				endR, endG, endB);
	}

	/*
	 * private static final int numParticles = 100; private static final String
	 * texture = "resources/images/ball.png"; private static final float minDx =
	 * -30.0f; private static final float maxDx = 30.0f; private static final
	 * float minDy = -30.0f; private static final float maxDy = 30.0f; private
	 * static final float minAx = -3.0f; private static final float maxAx =
	 * 3.0f; private static final float minAy = -3.0f; private static final
	 * float maxAy = 3.0f; private static final float minWidth = 10.0f; private
	 * static final float maxWidth = 10.0f; private static final float minHeight
	 * = 10.0f; private static final float maxHeight = 10.0f; private static
	 * final int minLife = 50; private static final int maxLife = 100; private
	 * static final boolean doFade = true;
	 */
}
