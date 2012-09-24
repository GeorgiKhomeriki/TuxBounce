package sound;

import java.io.IOException;
import java.util.Random;

import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Sound {
	private static Sound instance;
	private Random random;
	private Audio acceptSound;
	private Audio declineSound;
	private Audio cursorSound;
	private Audio boingSound;
	private Audio pointSound;
	private Audio[] hitSounds;
	private Audio pointsPowerupSound;
	private Audio deathSound;
	private Audio music;
	private Audio menuMusic;
	private boolean isEnabled;
	
	public Sound() {
		isEnabled = true;
		random = new Random();
		try {
			acceptSound = AudioLoader.getAudio("WAV",
					ResourceLoader.getResourceAsStream("resources/sounds/menu-validate.wav"));
			declineSound = AudioLoader.getAudio("WAV",
					ResourceLoader.getResourceAsStream("resources/sounds/menu-back.wav"));
			cursorSound = AudioLoader.getAudio("WAV",
					ResourceLoader.getResourceAsStream("resources/sounds/menu-nav.wav"));
			boingSound = AudioLoader.getAudio("WAV",
					ResourceLoader.getResourceAsStream("resources/sounds/boing.wav"));
			pointSound = AudioLoader.getAudio("WAV",
					ResourceLoader.getResourceAsStream("resources/sounds/mouthpop.wav"));
			
			hitSounds = new Audio[3];
			hitSounds[0] = AudioLoader.getAudio("WAV",
					ResourceLoader.getResourceAsStream("resources/sounds/djembe-mid-4.wav"));
			hitSounds[1] = AudioLoader.getAudio("WAV",
					ResourceLoader.getResourceAsStream("resources/sounds/djembe-hi-3.wav"));
			hitSounds[2] = AudioLoader.getAudio("WAV",
					ResourceLoader.getResourceAsStream("resources/sounds/djembe-mid-2.wav"));
			
			pointsPowerupSound = AudioLoader.getAudio("WAV",
					ResourceLoader.getResourceAsStream("resources/sounds/goblet-g-medium.wav"));
			
			deathSound = AudioLoader.getAudio("WAV",
					ResourceLoader.getResourceAsStream("resources/sounds/stone-on-stone-impact.wav"));
			
			music = AudioLoader.getAudio("WAV",
					ResourceLoader.getResourceAsStream("resources/sounds/loop003-jungle.wav"));
			menuMusic = AudioLoader.getAudio("WAV",
					ResourceLoader.getResourceAsStream("resources/sounds/przeszkadzajki.wav"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void playAccept() {
		if(isEnabled)
			acceptSound.playAsSoundEffect(1.0f, 1.0f, false);
	}
	
	public void playDecline() {
		if(isEnabled)
			declineSound.playAsSoundEffect(1.0f, 1.0f, false);
	}
	
	public void playCursor() {
		if(isEnabled)
			cursorSound.playAsSoundEffect(1.0f, 1.0f, false);
	}
	
	public void playBoing() {
		if(isEnabled)
			boingSound.playAsSoundEffect(0.9f + 0.2f * random.nextFloat(), 0.5f, false);
	}
	
	public void playPoint() {
		if(isEnabled)
			pointSound.playAsSoundEffect(0.5f + 0.5f * random.nextFloat(),  0.5f, false);
	}
	
	public void playHit() {
		if(isEnabled) {
			int i = random.nextInt(3);
			hitSounds[i].playAsSoundEffect(1.0f,  1.0f,  false);
		}
	}
	
	public void playPointsPowerup() {
		if(isEnabled)
			pointsPowerupSound.playAsSoundEffect(1.0f, 0.5f, false);
	}
	
	public void playDeath() {
		if(isEnabled)
			deathSound.playAsSoundEffect(1.0f, 1.0f, false);
	}
	
	public void playMusic() {
		if(isEnabled)
			music.playAsMusic(1.0f, 1.0f, true);
	}
	
	public void stopMusic() {
		music.stop();
	}
	
	public void playMenuMusic() {
		if(isEnabled)
			menuMusic.playAsMusic(1.f, 1.0f, true);
	}
	
	public void stopMenuMusic() {
		menuMusic.stop();
	}
	
	public void setEnabled(boolean enable) {
		isEnabled = enable;
	}
	
	public static Sound get() {
		if(instance == null) {
			instance = new Sound();
		}
		return instance;
	}
}
