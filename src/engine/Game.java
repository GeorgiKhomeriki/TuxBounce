package engine;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.openal.AL;

import util.Config;

public class Game extends AbstractGame {
	private static Game instance;

	private final List<IGameState> states;
	private IGameState currentState;

	public Game(final String title, final int screenWidth,
			final int screenHeight, final boolean fullscreen) {
		super(title, screenWidth, screenHeight, fullscreen);
		states = new ArrayList<IGameState>();
	}

	@Override
	protected void init() {
		for (final IGameState state : states) {
			state.init();
		}
	}

	@Override
	protected void update(final int delta) {
		currentState.update(delta);
		currentState.render(delta);
	}

	@Override
	protected void shutdown() {
		currentState.stop();
		AL.destroy();
	}

	public void addState(final IGameState state) {
		states.add(state);
	}

	public void setCurrentState(final String name) {
		for (final IGameState state : states) {
			if (name.equals(state.getName())) {
				if (currentState != null) {
					currentState.stop();
				}
				state.start();
				currentState = state;
				return;
			}
		}
		System.err.println("State not found : " + name);
	}

	public void reinit(final String name) {
		for (final IGameState state : states) {
			if (name.equals(state.getName())) {
				state.init();
				return;
			}
		}
		System.err.println("State not found : " + name);
	}

	public final static Game get() {
		if (instance == null) {
			Config.createConfig();
			final List<Object> options = Config.loadOptions();
			if (options.size() > 0) {
				instance = new Game("Tux Bounce", (Integer) options.get(0),
						(Integer) options.get(1), (Boolean) options.get(2));
			} else {
				instance = new Game("Tux Bounce", 800, 600, false);
			}
		}
		return instance;
	}
}
