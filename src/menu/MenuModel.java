package menu;

public class MenuModel {
	public enum MENU {
		MAIN, LEVEL_CHOICE, OPTIONS, HIGHSCORE, CREDITS
	};

	private MENU menu;

	public MenuModel(MENU menu) {
		this.menu = menu;
	}

	public MENU get() {
		return menu;
	}

	public void set(MENU menu) {
		Commons.get().setKeyPressed(true);
		this.menu = menu;
	}

}
