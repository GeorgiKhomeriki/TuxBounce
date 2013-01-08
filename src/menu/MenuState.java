package menu;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import menu.MenuModel.MENU;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.openal.SoundStore;

import util.Graphics;
import assets.Sounds;
import assets.Textures;
import engine.IGameState;

public class MenuState implements IGameState {
	public static final String name = "MENU_STATE";

	private static final MenuModel currentMenu = new MenuModel(MENU.MAIN);

	private MainMenu mainMenu;
	private LevelChoiceMenu levelChoiceMenu;
	private OptionsMenu optionsMenu;
	private HighscoreMenu highscoreMenu;

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void init() {
		mainMenu = new MainMenu() {
			@Override
			public void showLevelChoice() {
				currentMenu.set(MENU.LEVEL_CHOICE);
			}

			@Override
			public void showOptions() {
				currentMenu.set(MENU.OPTIONS);
			}

			@Override
			public void showHighscore() {
				HighscoreMenu.loadHighscores();
				currentMenu.set(MENU.HIGHSCORE);
			}
		};

		levelChoiceMenu = new LevelChoiceMenu() {
			@Override
			public void backToMainMenu() {
				currentMenu.set(MENU.MAIN);
			}
		};

		optionsMenu = new OptionsMenu() {
			@Override
			public void backToMainMenu() {
				currentMenu.set(MENU.MAIN);
			}
		};

		highscoreMenu = new HighscoreMenu() {
			@Override
			public void backToMainMenu() {
				currentMenu.set(MENU.MAIN);
			}
		};
	}

	@Override
	public void start() {
		Sounds.get().playMenuMusic();
	}

	@Override
	public void stop() {
		Sounds.get().stopMenuMusic();
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
		case LEVEL_CHOICE:
			levelChoiceMenu.render(delta);
			break;
		case OPTIONS:
			optionsMenu.render(delta);
			break;
		case HIGHSCORE:
			highscoreMenu.render(delta);
			break;
		default:
			break;
		}
	}

	private void renderBg() {
		Graphics.drawQuad(0.0f, 0.0f, Display.getWidth(), Display.getHeight(),
				Textures.get().getBgMenu(), true);
	}

	public void renderLogo() {
		float x = Display.getWidth() * 0.02f;
		float y = Display.getHeight() * 0.88f;
		float width = Display.getWidth() / 2;
		float height = Display.getHeight() / 10;
		Graphics.drawQuad(x, y, width, height, Textures.get().getLogo(), true);
	}

	@Override
	public void update(int delta) {
		switch (currentMenu.get()) {
		case MAIN:
			mainMenu.update(delta);
			break;
		case LEVEL_CHOICE:
			levelChoiceMenu.update(delta);
			break;
		case OPTIONS:
			optionsMenu.update(delta);
			break;
		case HIGHSCORE:
			highscoreMenu.update(delta);
			break;
		default:
			break;
		}

		// polling is required to allow streaming to get a chance to queue
		// buffers.
		SoundStore.get().poll(0);
	}

	public static void activateHighscoreMenu() {
		HighscoreMenu.loadHighscores();
		currentMenu.set(MENU.HIGHSCORE);
	}

}
