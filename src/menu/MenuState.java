package menu;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.io.IOException;

import menu.MenuModel.MENU;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.openal.SoundStore;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import sound.Sound;
import engine.Font;
import engine.IGameState;

public class MenuState implements IGameState {
	public static final String name = "MENU_STATE";

	private final MenuModel currentMenu = new MenuModel(MENU.MAIN);
	private MainMenu mainMenu;
	private OptionsMenu optionsMenu;
	private HighscoreMenu highscoreMenu;
	private CreditsMenu creditsMenu;
	private Font font;
	private Texture bgTexture;
	private Texture logoTexture;
	private Texture cursorTexture;

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void init() {
		try {
			cursorTexture = TextureLoader.getTexture("PNG", ResourceLoader
					.getResourceAsStream("resources/images/tux.png"));
			bgTexture = TextureLoader.getTexture("JPG", ResourceLoader
					.getResourceAsStream("resources/images/menu-bg.jpg"));
			logoTexture = TextureLoader.getTexture("PNG", ResourceLoader
					.getResourceAsStream("resources/images/logo.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		font = new Font("resources/fonts/kromasky_16x16.png", 59, 16);

		initMenus();
	}

	private void initMenus() {
		mainMenu = new MainMenu(font, cursorTexture) {
			@Override
			public void showOptions() {
				currentMenu.set(MENU.OPTIONS);
			}

			@Override
			public void showHighscore() {
				currentMenu.set(MENU.HIGHSCORE);
			}

			@Override
			public void showCredits() {
				currentMenu.set(MENU.CREDITS);
			}

		};

		optionsMenu = new OptionsMenu(font, cursorTexture) {
			@Override
			public void backToMainMenu() {
				currentMenu.set(MENU.MAIN);
			}
		};

		highscoreMenu = new HighscoreMenu(font, cursorTexture) {
			@Override
			public void backToMainMenu() {
				currentMenu.set(MENU.MAIN);
			}
		};

		creditsMenu = new CreditsMenu(font, cursorTexture) {
			@Override
			public void backToMainMenu() {
				currentMenu.set(MENU.MAIN);
			}
		};
	}

	@Override
	public void start() {
		Sound.get().playMenuMusic();
	}

	@Override
	public void stop() {
		Sound.get().stopMenuMusic();
	}

	@Override
	public void render(int delta) {
		glLoadIdentity();
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		renderBg();
		renderLogo();
		switch (currentMenu.get()) {
		case MAIN:
			mainMenu.render(delta);
			break;
		case OPTIONS:
			optionsMenu.render(delta);
			break;
		case HIGHSCORE:
			highscoreMenu.render(delta);
			break;
		case CREDITS:
			creditsMenu.render(delta);
			break;
		default:
			break;
		}
	}

	private void renderBg() {
		bgTexture.bind();
		glColor3f(1.0f, 1.0f, 1.0f);
		glBegin(GL_QUADS);
		glTexCoord2f(0.0f, bgTexture.getHeight());
		glVertex2f(0.0f, 0.0f);
		glTexCoord2f(bgTexture.getWidth(), bgTexture.getHeight());
		glVertex2f(Display.getWidth(), 0.0f);
		glTexCoord2f(bgTexture.getWidth(), 0.0f);
		glVertex2f(Display.getWidth(), Display.getHeight());
		glTexCoord2f(0.0f, 0.0f);
		glVertex2f(0.0f, Display.getHeight());
		glEnd();
	}

	public void renderLogo() {
		float width = Display.getWidth() / 2;
		float height = Display.getHeight() / 10;
		float x = Display.getWidth() * 0.01f;
		float y = Display.getHeight() * 0.85f;
		logoTexture.bind();
		glColor3f(1.0f, 1.0f, 1.0f);
		glBegin(GL_QUADS);
		glTexCoord2f(0.0f, logoTexture.getHeight());
		glVertex2f(x, y);
		glTexCoord2f(logoTexture.getWidth(), logoTexture.getHeight());
		glVertex2f(x + width, y);
		glTexCoord2f(logoTexture.getWidth(), 0.0f);
		glVertex2f(x + width, y + height);
		glTexCoord2f(0.0f, 0.0f);
		glVertex2f(x, y + height);
		glEnd();
	}

	@Override
	public void update(int delta) {
		switch (currentMenu.get()) {
		case MAIN:
			mainMenu.update(delta);
			break;
		case OPTIONS:
			optionsMenu.update(delta);
			break;
		case HIGHSCORE:
			highscoreMenu.update(delta);
			break;
		case CREDITS:
			creditsMenu.update(delta);
			break;
		default:
			break;
		}

		// polling is required to allow streaming to get a chance to queue
		// buffers.
		SoundStore.get().poll(0);
	}

}
