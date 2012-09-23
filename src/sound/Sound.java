package sound;

import java.io.IOException;
import java.util.Random;

import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Sound {
	private static Sound instance;
	private static Random random;
	private Audio acceptSound;
	private Audio declineSound;
	private Audio cursorSound;
	private Audio boingSound;
	private Audio pointSound;
	private Audio[] hitSounds;
	private Audio music;
	private Audio menuMusic;
	
	public Sound() {
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
			
			music = AudioLoader.getAudio("WAV",
					ResourceLoader.getResourceAsStream("resources/sounds/loop003-jungle.wav"));
			menuMusic = AudioLoader.getAudio("WAV",
					ResourceLoader.getResourceAsStream("resources/sounds/przeszkadzajki.wav"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void playAccept() {
		acceptSound.playAsSoundEffect(1.0f, 1.0f, false);
	}
	
	public void playDecline() {
		declineSound.playAsSoundEffect(1.0f, 1.0f, false);
	}
	
	public void playCursor() {
		cursorSound.playAsSoundEffect(1.0f, 1.0f, false);
	}
	
	public void playBoing() {
		boingSound.playAsSoundEffect(1.0f, 0.5f, false);
	}
	
	public void playPoint() {
		pointSound.playAsSoundEffect(0.5f + 0.5f * random.nextFloat(),  0.5f, false);
	}
	
	public void playHit() {
		int i = random.nextInt(3);
		hitSounds[i].playAsSoundEffect(1.0f,  1.0f,  false);
	}
	
	public void playMusic() {
		music.playAsMusic(1.0f, 1.0f, true);
	}
	
	public void stopMusic() {
		music.stop();
	}
	
	public void playMenuMusic() {
		menuMusic.playAsMusic(1.f, 1.0f, false);
	}
	
	public void stopMenuMusic() {
		menuMusic.stop();
	}
	
	public static Sound get() {
		if(instance == null) {
			instance = new Sound();
		}
		return instance;
	}
}
