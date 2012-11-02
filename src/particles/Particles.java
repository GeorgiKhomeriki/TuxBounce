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
	private List<Particle> particles;
	private Texture texture;
	private float startIntensity;
	private float endIntensity;

	public Particles(int numParticles, Texture texture, float minX, float maxX,
			float minY, float maxY, float minDx, float maxDx, float minDy,
			float maxDy, float minAy, float maxAy, float size, int minLife,
			int maxLife, float startIntensity, float endIntensity) {
		this.texture = texture;
		this.startIntensity = startIntensity;
		this.endIntensity = endIntensity;

		particles = new ArrayList<Particle>();
		
		for (int i = 0; i < numParticles; i++) {
			float x = minX + Random.get().nextFloat() * (maxX - minX);
			float y = minY + Random.get().nextFloat() * (maxY - minY);
			float dx = minDx + Random.get().nextFloat() * (maxDx - minDx);
			float dy = minDy + Random.get().nextFloat() * (maxDy - minDy);
			float ay = minAy + Random.get().nextFloat() * (maxAy - minAy);
			int life = minLife + Random.get().nextInt(maxLife - minLife);
			particles.add(new Particle(this.texture, x, y, dx, dy, ay, size,
					life, startIntensity));
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
				float intensity = startIntensity + (1.0f - lifeRatio)
						* (endIntensity - startIntensity);
				particle.setIntensity(intensity);
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
