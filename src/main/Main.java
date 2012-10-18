package main;

import org.lwjgl.LWJGLException;

import sound.Sound;
import breakout.GameState;
import breakout.MenuState;
import engine.Game;

public class Main {

	public static void main(String[] args) throws LWJGLException {
		Sound.get().setEnabled(false);
		Game.get().addState(new GameState());
		Game.get().addState(new MenuState());
		Game.get().setCurrentState(MenuState.name);
		Game.get().start();
	}

}
