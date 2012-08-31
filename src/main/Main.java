package main;

import org.lwjgl.LWJGLException;

import breakout.GameState;
import breakout.MenuState;
import engine.Game;

public class Main {

	public static void main(String[] args) throws LWJGLException {
		Game game = new Game("Awesome BreakOut", 1440, 900, true);
		//Game game = new Game("Awesome BreakOut", 800, 600, false);
		game.addState(new GameState());
		game.addState(new MenuState());
		game.setCurrentState(GameState.name);
		game.start();
	}

}
