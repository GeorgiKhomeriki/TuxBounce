package main;

import org.lwjgl.LWJGLException;

import breakout.GameState;
import breakout.MenuState;
import engine.Game;

public class Main {

	public static void main(String[] args) throws LWJGLException {
		Game.get().addState(new GameState());
		Game.get().addState(new MenuState());
		Game.get().setCurrentState(MenuState.name);
		Game.get().start();
	}

}
