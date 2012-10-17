package breakout;

import sound.Sound;
import menu.MainMenu;
import engine.IGameState;

public class MenuState implements IGameState {
	public static final String name = "MENU_STATE";
	
	private enum MENU {
		MAIN, OPTIONS, CREDITS, LEVEL_LIST
	};

	private MENU currentMenu;
	private MainMenu mainMenu;

	@Override
	public String getName() {
		return "MENU_STATE";
	}

	@Override
	public void init() {
		currentMenu = MENU.MAIN;
		mainMenu = new MainMenu();
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
		switch (currentMenu) {
		case MAIN:
			mainMenu.render(delta);
			break;
		case OPTIONS:
			break;
		case CREDITS:
			break;
		default:
			break;
		}
	}

	@Override
	public void update(int delta) {
		switch (currentMenu) {
		case MAIN:
			mainMenu.update(delta);
			break;
		case OPTIONS:
			break;
		case CREDITS:
			break;
		default:
			break;
		}
	}

}
