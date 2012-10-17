package menu;

public class MenuModel {
	public enum MENU {
		MAIN, OPTIONS, CREDITS, LEVEL_LIST
	};

	private MENU menu;

	public MenuModel(MENU menu) {
		this.menu = menu;
	}

	public MENU get() {
		return menu;
	}

	public void set(MENU menu) {
		this.menu = menu;
	}

}
