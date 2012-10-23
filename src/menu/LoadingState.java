package menu;

import sound.Sound;
import textures.Textures;
import engine.Game;
import engine.IGameState;

public class LoadingState implements IGameState {
	public static final String name = "STATE_LOADING";

	private boolean startLoading;

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void init() {
		startLoading = false;
	}

	@Override
	public void start() {

	}

	@Override
	public void stop() {

	}

	@Override
	public void render(int delta) {
		System.out.println("loading...");
	}

	@Override
	public void update(int delta) {
		if (startLoading) {
			Sound.get();
			Textures.get();
			Game.get().setCurrentState(MenuState.name);
		}
		startLoading = true;
	}

}
