package assets;

import java.io.IOException;
import java.util.Random;

import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Sounds {
	private static Sounds instance;
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
	private boolean soundEnabled;
	private boolean musicEnabled;
	
	public Sounds() {
		soundEnabled = true;
		musicEnabled = true;
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
		if(soundEnabled)
			acceptSound.playAsSoundEffect(1.0f, 1.0f, false);
	}
	
	public void playDecline() {
		if(soundEnabled)
			declineSound.playAsSoundEffect(1.0f, 1.0f, false);
	}
	
	public void playCursor() {
		if(soundEnabled)
			cursorSound.playAsSoundEffect(1.0f, 1.0f, false);
	}
	
	public void playBoing() {
		if(soundEnabled)
			boingSound.playAsSoundEffect(0.9f + 0.2f * random.nextFloat(), 0.5f, false);
	}
	
	public void playPoint() {
		if(soundEnabled)
			pointSound.playAsSoundEffect(0.5f + 0.5f * random.nextFloat(),  0.5f, false);
	}
	
	public void playHit() {
		if(soundEnabled) {
			int i = random.nextInt(3);
			hitSounds[i].playAsSoundEffect(1.0f,  1.0f,  false);
		}
	}
	
	public void playPointsPowerup() {
		if(soundEnabled)
			pointsPowerupSound.playAsSoundEffect(1.0f, 0.5f, false);
	}
	
	public void playDeath() {
		if(soundEnabled)
			deathSound.playAsSoundEffect(1.0f, 1.0f, false);
	}
	
	public void playMusic() {
		if(musicEnabled)
			music.playAsMusic(1.0f, 1.0f, true);
	}
	
	public void stopMusic() {
		music.stop();
	}
	
	public void playMenuMusic() {
		if(musicEnabled)
			menuMusic.playAsMusic(1.f, 1.0f, true);
	}
	
	public void stopMenuMusic() {
		menuMusic.stop();
	}
	
	public boolean isSoundEnabled() {
		return soundEnabled;
	}
	
	public void setSoundEnabled(boolean enabled) {
		soundEnabled = enabled;
	}
	
	public boolean isMusicEnabled() {
		return musicEnabled;
	}
	
	public void setMusicEnabled(boolean enabled) {
		musicEnabled = enabled;
		if(enabled) {
			playMenuMusic();
		} else {
			stopAllMusic();
		}
	}
	
	private void stopAllMusic() {
		music.stop();
		menuMusic.stop();
	}
	
	public static Sounds get() {
		if(instance == null) {
			instance = new Sounds();
		}
		return instance;
	}
}
