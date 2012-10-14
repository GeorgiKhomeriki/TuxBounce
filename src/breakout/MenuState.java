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
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.openal.SoundStore;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import sound.Sound;
import engine.Font;
import engine.Game;
import engine.IGameState;

public class MenuState implements IGameState {
	public static final String name = "MENU_STATE";

	private Texture bgTexture;
	private Texture logoTexture;
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
	private float highlightColorDelta;

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void init() {
		try {
			bgTexture = TextureLoader.getTexture("JPG", ResourceLoader
					.getResourceAsStream("resources/images/menu-bg.jpg"));
			logoTexture = TextureLoader.getTexture("PNG", ResourceLoader
					.getResourceAsStream("resources/images/logo.png"));
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
		highlightColorDelta = -1.0f;
	}

	@Override
	public void start() {
		Sound.get().playMenuMusic();
	}

	@Override
	public void stop() {
		Sound.get().stopMenuMusic();
	}

	@Override
	public void render(int delta) {
		glLoadIdentity();
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		renderBg();
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
		renderLogo();
	}

	public void renderBg() {
		bgTexture.bind();
		glColor3f(1.0f, 1.0f, 1.0f);
		glBegin(GL_QUADS);
		glTexCoord2f(0.0f, bgTexture.getHeight());
		glVertex2f(0.0f, 0.0f);
		glTexCoord2f(bgTexture.getWidth(), bgTexture.getHeight());
		glVertex2f(Display.getWidth(), 0.0f);
		glTexCoord2f(bgTexture.getWidth(), 0.0f);
		glVertex2f(Display.getWidth(), Display.getHeight());
		glTexCoord2f(0.0f, 0.0f);
		glVertex2f(0.0f, Display.getHeight());
		glEnd();
	}

	public void renderLogo() {
		float width = Display.getWidth() / 2;
		float height = Display.getHeight() / 10;
		float x = Display.getWidth() * 0.01f;
		float y = Display.getHeight() * 0.85f;
		logoTexture.bind();
		glColor3f(1.0f, 1.0f, 1.0f);
		glBegin(GL_QUADS);
		glTexCoord2f(0.0f, logoTexture.getHeight());
		glVertex2f(x, y);
		glTexCoord2f(logoTexture.getWidth(), logoTexture.getHeight());
		glVertex2f(x + width, y);
		glTexCoord2f(logoTexture.getWidth(), 0.0f);
		glVertex2f(x + width, y + height);
		glTexCoord2f(0.0f, 0.0f);
		glVertex2f(x, y + height);
		glEnd();
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
		SoundStore.get().poll(0);
	}

	private void updateMain(float delta) {
		// check keyboard input
		boolean downPressed = Keyboard.isKeyDown(Keyboard.KEY_DOWN);
		boolean upPressed = Keyboard.isKeyDown(Keyboard.KEY_UP);
		if (!isKeyPressed) {
			if (downPressed || upPressed) {
				currentSelection = getNextMainSelection(currentSelection,
						upPressed);
				isKeyPressed = true;
				Sound.get().playCursor();
			}
		} else if (!downPressed && !upPressed) {
			isKeyPressed = false;
		}
		
		// check mouse input
		checkMouseSelection();
	}

	private void checkMouseSelection() {
		if (Mouse.getDX() != 0 || Mouse.getDY() != 0) {
			float x = Mouse.getX();
			if (x > Display.getWidth() / 2.0f
					&& x < Display.getWidth() / 2.0f + 7
							* font.getCharacterWidth()) {
				float y = Mouse.getY();
				for (SELECTION selection : SELECTION.values()) {
					if (y > getSelectionY(selection)
							&& y < getSelectionY(selection) + font.getCharacterHeight()) {
						currentSelection = selection;
						break;
					}
				}
			}
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
		if (Keyboard.isKeyDown(Keyboard.KEY_RETURN) || Mouse.isButtonDown(0)) {
			Sound.get().playAccept();
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
		float newColor = highlightColor + highlightColorDelta * delta / 200;
		if (newColor < 0.0f || newColor > 1.0f) {
			highlightColorDelta = -highlightColorDelta;
		} else {
			highlightColor = newColor;
		}
	}

}
