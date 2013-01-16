package particles;

import engine.Texts;
import game.Hud;
import game.Paddle;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.opengl.Texture;

import util.Random;
import assets.Sounds;

public class Particles {
	private final List<Particle> particles;
	private final Texture texture;
	private final float startIntensity;
	private final float endIntensity;

	public Particles(final int numParticles, final Texture texture,
			final float x, final float y, final float minDx, final float maxDx,
			final float minDy, final float maxDy, final float minAy,
			final float maxAy, final float size, final int minLife,
			final int maxLife, final float startIntensity,
			final float endIntensity) {
		this.texture = texture;
		this.startIntensity = startIntensity;
		this.endIntensity = endIntensity;

		particles = new ArrayList<Particle>();

		for (int i = 0; i < numParticles; i++) {
			final float dx = minDx + Random.get().nextFloat() * (maxDx - minDx);
			final float dy = minDy + Random.get().nextFloat() * (maxDy - minDy);
			final float ay = minAy + Random.get().nextFloat() * (maxAy - minAy);
			final int life = minLife + Random.get().nextInt(maxLife - minLife);
			particles.add(new Particle(this.texture, x, y, dx, dy, ay, size,
					life, startIntensity));
		}
	}

	public void update(final float delta, final Paddle paddle, final Texts texts) {
		for (int i = 0; i < particles.size(); i++) {
			final Particle particle = particles.get(i);
			if (particle.hasHitPaddle(paddle)) {
				particles.remove(i);
				i--;
				texts.add("+1", particle.getX(), particle.getY(), 30, true);
				Hud.get().addPoints(1);
				Sounds.get().playPoint();
			} else if (particle.isAlive()) {
				particle.update(delta);
				final float lifeRatio = (float) (particle.getLife() - particle
						.getAge()) / (float) particle.getLife();
				final float intensity = startIntensity + (1.0f - lifeRatio)
						* (endIntensity - startIntensity);
				particle.setIntensity(intensity);
			} else {
				particles.remove(i);
				i--;
			}
		}
	}

	public void render() {
		for (final Particle particle : particles) {
			particle.render();
		}
	}

	public final boolean isAlive() {
		return !particles.isEmpty();
	}

}
