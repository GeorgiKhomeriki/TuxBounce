package breakout;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.io.IOException;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import engine.Font;
import engine.Game;
import engine.IGameState;

public class MenuState implements IGameState {
	public static final String name = "MENU_STATE";

	private Texture cursorTexture;
	private Font font;

	private enum MENU {
		MAIN, OPTIONS, CREDITS, LEVEL_LIST
	};

	private enum SELECTION {
		START, OPTIONS, CREDITS, EXIT
	}

	private MENU currentMenu;
	private SELECTION currentSelection;
	private boolean isKeyPressed;
	private float highlightColor;
	private float highLightColorDelta;

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void init() {
		try {
			cursorTexture = TextureLoader.getTexture("PNG", ResourceLoader
					.getResourceAsStream("resources/images/tux.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		font = new Font("resources/fonts/kromasky_16x16.png", 59, 16);
		currentMenu = MENU.MAIN;
		currentSelection = SELECTION.START;
		isKeyPressed = false;
		highlightColor = 1.0f;
		highLightColorDelta = -1.0f;
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
		renderCursor();
	}

	public void renderCursor() {
		float width = Display.getWidth() / 24;
		float height = Display.getHeight() / 18;
		float x = Display.getWidth() * 0.5f - 1.2f * width;
		float y = getSelectionY(currentSelection);
		cursorTexture.bind();
		glColor3f(1.0f, 1.0f, 1.0f);
		glBegin(GL_QUADS);
		glTexCoord2f(0.0f, cursorTexture.getHeight());
		glVertex2f(x, y);
		glTexCoord2f(cursorTexture.getWidth(), cursorTexture.getHeight());
		glVertex2f(x + width, y);
		glTexCoord2f(cursorTexture.getWidth(), 0.0f);
		glVertex2f(x + width, y + height);
		glTexCoord2f(0.0f, 0.0f);
		glVertex2f(x, y + height);
		glEnd();
	}

	private float getSelectionY(SELECTION selection) {
		float y = Display.getHeight() * 0.5f;
		switch (selection) {
		case OPTIONS:
			y -= Display.getHeight() / 16.0f;
			break;
		case CREDITS:
			y -= Display.getHeight() / 8.0f;
			break;
		case EXIT:
			y -= Display.getHeight() / 5.2f;
			break;
		default:
			break;
		}
		return y;
	}

	private void renderMain() {
		highlightSelection(SELECTION.START);
		font.drawText("START", Display.getWidth() / 2.0f,
				getSelectionY(SELECTION.START));
		highlightSelection(SELECTION.OPTIONS);
		font.drawText("OPTIONS", Display.getWidth() / 2.0f,
				getSelectionY(SELECTION.OPTIONS));
		highlightSelection(SELECTION.CREDITS);
		font.drawText("CREDITS", Display.getWidth() / 2.0f,
				getSelectionY(SELECTION.CREDITS));
		highlightSelection(SELECTION.EXIT);
		font.drawText("EXIT", Display.getWidth() / 2.0f,
				getSelectionY(SELECTION.EXIT));
	}

	private void highlightSelection(SELECTION selection) {
		if (currentSelection.equals(selection))
			glColor3f(1.0f, highlightColor, 0.0f);
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
		updateHighlightColor(delta);
		switch (currentMenu) {
		case MAIN:
			doMainSelectionAction();
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
		boolean downPressed = Keyboard.isKeyDown(Keyboard.KEY_DOWN);
		boolean upPressed = Keyboard.isKeyDown(Keyboard.KEY_UP);
		if (!isKeyPressed) {
			if (downPressed || upPressed) {
				currentSelection = getNextMainSelection(currentSelection,
						upPressed);
				isKeyPressed = true;
			}
		} else if (!downPressed && !upPressed) {
			isKeyPressed = false;
		}
	}

	private SELECTION getNextMainSelection(SELECTION selection, boolean up) {
		switch (selection) {
		case START:
			return up ? SELECTION.EXIT : SELECTION.OPTIONS;
		case OPTIONS:
			return up ? SELECTION.START : SELECTION.CREDITS;
		case CREDITS:
			return up ? SELECTION.OPTIONS : SELECTION.EXIT;
		case EXIT:
			return up ? SELECTION.CREDITS : SELECTION.START;
		default:
			return SELECTION.START;
		}
	}

	private void doMainSelectionAction() {
		if (Keyboard.isKeyDown(Keyboard.KEY_RETURN)) {
			switch (currentSelection) {
			case START:
				Game.get().setCurrentState(GameState.name);
				break;
			case OPTIONS:
				break;
			case CREDITS:
				break;
			case EXIT:
				Game.get().requestShutdown();
				break;
			default:
				break;
			}
		}
	}

	private void updateOptions(float delta) {

	}

	private void updateCredits(float delta) {

	}

	private void updateLevelList(float delta) {

	}

	private void updateHighlightColor(float delta) {
		float newColor = highlightColor + highLightColorDelta * delta / 200;
		if (newColor < 0.0f || newColor > 1.0f) {
			highLightColorDelta = -highLightColorDelta;
		} else {
			highlightColor = newColor;
		}
	}

}
