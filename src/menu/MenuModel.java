package menu;

public class MenuModel {
	public enum MENU {
		MAIN, LEVEL_CHOICE, OPTIONS, HIGHSCORE
	};

	private MENU menu;

	public MenuModel(final MENU menu) {
		this.menu = menu;
	}

	public final MENU get() {
		return menu;
	}

	public void set(final MENU menu) {
		Commons.get().setKeyPressed(true);
		this.menu = menu;
	}

}
