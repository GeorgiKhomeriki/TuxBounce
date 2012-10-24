package particles;

import engine.Texts;
import game.Hud;
import game.Paddle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.newdawn.slick.opengl.Texture;

import assets.Sounds;

public class Particles {
	private List<Particle> particles;
	private Random random;
	private Texture texture;
	private int numParticles;
	private float minX;
	private float maxX;
	private float minY;
	private float maxY;
	private float minDx;
	private float maxDx;
	private float minDy;
	private float maxDy;
	private float minAx;
	private float maxAx;
	private float minAy;
	private float maxAy;
	private float minWidth;
	private float maxWidth;
	private float minHeight;
	private float maxHeight;
	private int minLife;
	private int maxLife;
	private boolean doFade;
	private float startR;
	private float endR;
	private float startG;
	private float endG;
	private float startB;
	private float endB;

	public Particles(int numParticles, Texture texture, float minX, float maxX,
			float minY, float maxY, float minDx, float maxDx, float minDy,
			float maxDy, float minAx, float maxAx, float minAy, float maxAy,
			float minWidth, float maxWidth, float minHeight, float maxHeight,
			int minLife, int maxLife, boolean doFade, float startR,
			float startG, float startB, float endR, float endG, float endB) {
		this.texture = texture;
		this.numParticles = numParticles;
		this.minX = minX;
		this.maxX = maxX;
		this.minY = minY;
		this.maxY = maxY;
		this.minDx = minDx;
		this.maxDx = maxDx;
		this.minDy = minDy;
		this.maxDy = maxDy;
		this.minAx = minAx;
		this.maxAx = maxAx;
		this.minAy = minAy;
		this.maxAy = maxAy;
		this.minWidth = minWidth;
		this.maxWidth = maxWidth;
		this.minHeight = minHeight;
		this.maxHeight = maxHeight;
		this.minLife = minLife;
		this.maxLife = maxLife;
		this.doFade = doFade;
		this.startR = startR;
		this.startG = startG;
		this.startB = startB;
		this.endR = endR;
		this.endG = endG;
		this.endB = endB;
		this.random = new Random();

		reset();
	}

	public void reset() {
		particles = new ArrayList<Particle>();

		for (int i = 0; i < numParticles; i++) {
			float x = minX + random.nextFloat() * (maxX - minX);
			float y = minY + random.nextFloat() * (maxY - minY);
			float dx = minDx + random.nextFloat() * (maxDx - minDx);
			float dy = minDy + random.nextFloat() * (maxDy - minDy);
			float ax = minAx + random.nextFloat() * (maxAx - minAx);
			float ay = minAy + random.nextFloat() * (maxAy - minAy);
			float width = minWidth + random.nextFloat() * (maxWidth - minWidth);
			float height = minHeight + random.nextFloat()
					* (maxHeight - minHeight);
			int life = minLife + random.nextInt(maxLife - minLife);
			particles.add(new Particle(this.texture, x, y, dx, dy, ax, ay,
					width, height, life, startR, startG, startB));
		}
	}

	public void update(float delta, Paddle paddle, Texts texts) {
		for (int i = 0; i < particles.size(); i++) {
			Particle particle = particles.get(i);
			if (particle.hasHitPaddle(paddle)) {
				particles.remove(i);
				i--;
				texts.add("+1", particle.getX(), particle.getY(), 30, true);
				Hud.get().addPoints(1);
				Sounds.get().playPoint();
			} else if (particle.isAlive()) {
				particle.update(delta);
				float lifeRatio = (float) (particle.getLife() - particle
						.getAge()) / (float) particle.getLife();
				float R = startR + (1.0f - lifeRatio) * (endR - startR);
				float G = startG + (1.0f - lifeRatio) * (endG - startG);
				float B = startB + (1.0f - lifeRatio) * (endB - startB);
				if (doFade) {
					particle.setColor(R, G, B, lifeRatio);
				} else {
					particle.setColor(R, G, B, 1.0f);
				}
			} else {
				particles.remove(i);
				i--;
			}
		}
	}

	public void render() {
		for (Particle particle : particles) {
			particle.render();
		}
	}

	public boolean isAlive() {
		return !particles.isEmpty();
	}

}
