package engine;

import java.util.ArrayList;
import java.util.List;


public class Game extends AbstractGame {

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
}
