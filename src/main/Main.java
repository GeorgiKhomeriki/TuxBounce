package main;

import menu.MenuState;

import org.lwjgl.LWJGLException;

import sound.Sound;
import engine.Game;
import game.GameState;

public class Main {

	public static void main(String[] args) throws LWJGLException {
		Sound.get().setEnabled(false);
		Game.get().addState(new GameState());
		Game.get().addState(new MenuState());
		Game.get().setCurrentState(MenuState.name);
		Game.get().start();
	}

}
