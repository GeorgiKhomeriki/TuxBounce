package main;

import menu.LoadingState;
import menu.MenuState;

import org.lwjgl.LWJGLException;

import sound.Sound;
import engine.Game;
import game.GameState;

public class Main {

	public static void main(String[] args) throws LWJGLException {
		Game.get().addState(new LoadingState());
		Game.get().addState(new MenuState());
		Game.get().addState(new GameState());
		Game.get().setCurrentState(LoadingState.name);
		Game.get().start();
	}

}
