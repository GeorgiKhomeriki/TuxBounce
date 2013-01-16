package assets;

import java.io.IOException;

import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

import util.Random;

public class Sounds {
	private static Sounds instance;

	private final Audio acceptSound;
	private final Audio declineSound;
	private final Audio cursorSound;
	private final Audio boingSound;
	private final Audio pointSound;
	private final Audio[] hitSounds;
	private final Audio pointsPowerupSound;
	private final Audio deathSound;
	private final Audio winSound;
	private final Audio loseSound;
	private final Audio music;
	private final Audio menuMusic;

	private boolean soundEnabled;
	private boolean musicEnabled;

	public Sounds() throws IOException {
		soundEnabled = true;
		musicEnabled = true;

		acceptSound = loadAudio("resources/sounds/menu-validate.wav");
		declineSound = loadAudio("resources/sounds/menu-back.wav");
		cursorSound = loadAudio("resources/sounds/menu-nav.wav");
		boingSound = loadAudio("resources/sounds/boing.wav");
		pointSound = loadAudio("resources/sounds/mouthpop.wav");

		hitSounds = new Audio[3];
		hitSounds[0] = loadAudio("resources/sounds/djembe-mid-4.wav");
		hitSounds[1] = loadAudio("resources/sounds/djembe-hi-3.wav");
		hitSounds[2] = loadAudio("resources/sounds/djembe-mid-2.wav");

		pointsPowerupSound = loadAudio("resources/sounds/goblet-g-medium.wav");
		deathSound = loadAudio("resources/sounds/stone-on-stone-impact.wav");
		winSound = loadAudio("resources/sounds/win.wav");
		loseSound = loadAudio("resources/sounds/lose.wav");

		music = loadAudio("resources/sounds/loop003-jungle.wav");
		menuMusic = loadAudio("resources/sounds/przeszkadzajki.wav");
	}

	private final Audio loadAudio(final String file) throws IOException {
		return AudioLoader.getAudio("WAV",
				ResourceLoader.getResourceAsStream(file));
	}

	public void playAccept() {
		if (soundEnabled)
			acceptSound.playAsSoundEffect(1.0f, 1.0f, false);
	}

	public void playDecline() {
		if (soundEnabled)
			declineSound.playAsSoundEffect(1.0f, 1.0f, false);
	}

	public void playCursor() {
		if (soundEnabled)
			cursorSound.playAsSoundEffect(1.0f, 1.0f, false);
	}

	public void playBoing() {
		if (soundEnabled)
			boingSound.playAsSoundEffect(
					0.9f + 0.2f * Random.get().nextFloat(), 0.5f, false);
	}

	public void playPoint() {
		if (soundEnabled)
			pointSound.playAsSoundEffect(
					0.5f + 0.5f * Random.get().nextFloat(), 0.5f, false);
	}

	public void playHit() {
		if (soundEnabled) {
			int i = Random.get().nextInt(3);
			hitSounds[i].playAsSoundEffect(1.0f, 1.0f, false);
		}
	}

	public void playPointsPowerup() {
		if (soundEnabled)
			pointsPowerupSound.playAsSoundEffect(1.0f, 0.5f, false);
	}

	public void playDeath() {
		if (soundEnabled)
			deathSound.playAsSoundEffect(1.0f, 1.0f, false);
	}

	public void playWin() {
		if (soundEnabled)
			winSound.playAsSoundEffect(1.0f, 1.0f, false);
	}

	public void playLose() {
		if (soundEnabled)
			loseSound.playAsSoundEffect(1.0f, 1.0f, false);
	}

	public void playMusic() {
		if (musicEnabled)
			music.playAsMusic(1.0f, 1.0f, true);
	}

	public void stopMusic() {
		music.stop();
	}

	public void playMenuMusic() {
		if (musicEnabled)
			menuMusic.playAsMusic(1.f, 1.0f, true);
	}

	public void stopMenuMusic() {
		menuMusic.stop();
	}

	public final boolean isSoundEnabled() {
		return soundEnabled;
	}

	public void setSoundEnabled(final boolean enabled) {
		soundEnabled = enabled;
	}

	public final boolean isMusicEnabled() {
		return musicEnabled;
	}

	public void setMusicEnabled(final boolean enabled) {
		musicEnabled = enabled;
		if (enabled) {
			playMenuMusic();
		} else {
			stopAllMusic();
		}
	}

	private void stopAllMusic() {
		music.stop();
		menuMusic.stop();
	}

	public final static Sounds get() {
		if (instance == null) {
			try {
				instance = new Sounds();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return instance;
	}
}
