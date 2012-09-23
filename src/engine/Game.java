package engine;

import java.util.ArrayList;
import java.util.List;


public class Game extends AbstractGame {

	private static Game instance; 
	
	private List<IGameState> states;
	private IGameState currentState;

	public Game(String title, int screenWidth, int screenHeight,
			boolean fullscreen) {
		super(title, screenWidth, screenHeight, fullscreen);
		states = new ArrayList<IGameState>();
	}

	@Override
	protected void init() {
		for (IGameState state : states) {
			state.init();
		}
	}

	@Override
	protected void update(int delta) {
		currentState.update(delta);
		currentState.render(delta);
	}

	@Override
	protected void shutdown() {
		currentState.stop();
	}

	public void addState(IGameState state) {
		states.add(state);
	}
	
	public void setCurrentState(String name) {
		for(IGameState state : states) {
			if(name.equals(state.getName())) {
				if(currentState != null) {
					currentState.stop();
				}
				state.start();
				currentState = state;
				return;
			}
		}
		System.err.println("State not found : " + name);
	}
	
	public static Game get() {
		if(instance == null) {
			//instance = new Game("Awesome BreakOut", 1440, 900, true);
			//instance = new Game("Awesome BreakOut", 1024, 768, false);
			instance = new Game("Awesome BreakOut", 800, 600, false);
			//instance = new Game("Awesome BreakOut", 640, 480, false);
		}
		return instance;
	}
}
