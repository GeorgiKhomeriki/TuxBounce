package breakout;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glLoadIdentity;

import org.lwjgl.opengl.Display;

import engine.Font;
import engine.IGameState;

public class MenuState implements IGameState {
	public static final String name = "MENU_STATE";

	private Font font;

	private enum MENU {
		MAIN, OPTIONS, CREDITS, LEVEL_LIST
	};
	
	private enum SELECTION {
		START, OPTIONS, CREDITS, EXIT
	}

	private MENU currentMenu;
	private SELECTION currentSelection; 

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void init() {
		font = new Font("resources/fonts/kromasky_16x16.png", 59, 16);
		currentMenu = MENU.MAIN;
		currentSelection = SELECTION.START;
		
	}

	@Override
	public void start() {

	}

	@Override
	public void stop() {

	}

	@Override
	public void render(int delta) {
		glLoadIdentity();
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		switch (currentMenu) {
		case MAIN:
			renderMain();
			break;
		case OPTIONS:
			renderOptions();
			break;
		case CREDITS:
			renderCredits();
			break;
		case LEVEL_LIST:
			renderLevelList();
			break;
		default:
			break;
		}
	}

	public void renderMain() {
		glColor3f(1.0f, 1.0f, 1.0f);
		highlightSelection(SELECTION.START);
		font.drawText("START", Display.getWidth() / 2.0f,
				Display.getHeight() / 2.0f);
		highlightSelection(SELECTION.OPTIONS);
		font.drawText("OPTIONS", Display.getWidth() / 2.0f,
				Display.getHeight() / 2.0f - Display.getHeight()/16.0f);
		highlightSelection(SELECTION.CREDITS);
		font.drawText("CREDITS", Display.getWidth() / 2.0f,
				Display.getHeight() / 2.0f - Display.getHeight()/8.0f);
		highlightSelection(SELECTION.EXIT);
		font.drawText("EXIT", Display.getWidth() / 2.0f,
				Display.getHeight() / 2.0f - Display.getHeight()/5.0f);

	}
	
	private void highlightSelection(SELECTION selection) {
		if(currentSelection.equals(selection))
			glColor3f(1.0f, 0.0f, 0.0f);
		else
			glColor3f(1.0f, 1.0f, 1.0f);
	}

	public void renderOptions() {
		
	}

	public void renderCredits() {
		
	}

	public void renderLevelList() {
		
	}

	@Override
	public void update(int delta) {
		switch (currentMenu) {
		case MAIN:
			updateMain(delta);
			break;
		case OPTIONS:
			updateOptions(delta);
			break;
		case CREDITS:
			updateCredits(delta);
			break;
		case LEVEL_LIST:
			updateLevelList(delta);
			break;
		default:
			break;
		}
	}

	private void updateMain(float delta) {
		
	}

	private void updateOptions(float delta) {
		
	}

	private void updateCredits(float delta) {
		
	}

	private void updateLevelList(float delta) {
		
	}

}
