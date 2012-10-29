package menu;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;
import engine.Game;
import game.GameState;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.opengl.Texture;

import assets.Fonts;
import assets.Sounds;
import assets.Textures;

public abstract class MainMenu {
	private enum SELECTION {
		CONTINUE, START, OPTIONS, HIGHSCORE, CREDITS, EXIT
	}

	private SELECTION currentSelection;
	private float highlightColor;
	private float highlightColorDelta;

	public MainMenu() {
		this.currentSelection = SELECTION.START;
		this.highlightColor = 1.0f;
		this.highlightColorDelta = -1.0f;
	}

	public abstract void showLevelChoice();

	public abstract void showOptions();

	public abstract void showHighscore();

	public abstract void showCredits();

	public void render(int delta) {
		renderCursor();
		if (GameState.paused) {
			highlightSelection(SELECTION.CONTINUE);
			Fonts.get()
					.large()
					.renderText("CONTINUE", Display.getWidth() / 2.0f,
							getSelectionY(SELECTION.CONTINUE));
		}
		highlightSelection(SELECTION.START);
		Fonts.get()
				.large()
				.renderText("START", Display.getWidth() / 2.0f,
						getSelectionY(SELECTION.START));
		highlightSelection(SELECTION.OPTIONS);
		Fonts.get()
				.large()
				.renderText("OPTIONS", Display.getWidth() / 2.0f,
						getSelectionY(SELECTION.OPTIONS));
		highlightSelection(SELECTION.HIGHSCORE);
		Fonts.get()
				.large()
				.renderText("HIGHSCORE", Display.getWidth() / 2.0f,
						getSelectionY(SELECTION.HIGHSCORE));
		highlightSelection(SELECTION.CREDITS);
		Fonts.get()
				.large()
				.renderText("CREDITS", Display.getWidth() / 2.0f,
						getSelectionY(SELECTION.CREDITS));
		highlightSelection(SELECTION.EXIT);
		Fonts.get()
				.large()
				.renderText("EXIT", Display.getWidth() / 2.0f,
						getSelectionY(SELECTION.EXIT));
	}

	private void highlightSelection(SELECTION selection) {
		if (currentSelection.equals(selection))
			glColor3f(1.0f, highlightColor, 0.0f);
		else
			glColor3f(1.0f, 1.0f, 1.0f);
	}

	private void renderCursor() {
		float width = Display.getWidth() / 24;
		float height = Display.getHeight() / 18;
		float x = Display.getWidth() * 0.5f - 1.2f * width;
		float y = getSelectionY(currentSelection);
		Texture cursorTexture = Textures.get().getBall();
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
		case CONTINUE:
			y += Display.getHeight() / 16.0f;
			break;
		case OPTIONS:
			y -= Display.getHeight() / 16.0f;
			break;
		case HIGHSCORE:
			y -= Display.getHeight() / 8.0f;
			break;
		case CREDITS:
			y -= Display.getHeight() / 5.2f;
			break;
		case EXIT:
			y -= Display.getHeight() / 3.9f;
			break;
		default:
			break;
		}
		return y;
	}

	public void update(int delta) {
		updateHighlightColor(delta);

		handleKeyboardInput();

		handleMouseInput();

		handleSelectedAction();
	}

	private void handleKeyboardInput() {
		boolean downPressed = Keyboard.isKeyDown(Keyboard.KEY_DOWN);
		boolean upPressed = Keyboard.isKeyDown(Keyboard.KEY_UP);
		boolean returnPressed = Keyboard.isKeyDown(Keyboard.KEY_RETURN);
		boolean mouseClicked = Mouse.isButtonDown(0);
		if (!Commons.get().isKeyPressed()) {
			if (downPressed || upPressed) {
				currentSelection = getNextSelection(currentSelection, upPressed);
				Commons.get().setKeyPressed(true);
				Sounds.get().playCursor();
			}
		} else if (!downPressed && !upPressed && !returnPressed
				&& !mouseClicked) {
			Commons.get().setKeyPressed(false);
		}
	}

	private void handleMouseInput() {
		if (Mouse.getDX() != 0 || Mouse.getDY() != 0) {
			for (SELECTION selection : SELECTION.values()) {
				if (!currentSelection.equals(selection)
						&& isMouseOnSelection(selection)
						&& (!selection.equals(SELECTION.CONTINUE) || GameState.paused)) {
					Sounds.get().playCursor();
					currentSelection = selection;
					break;
				}
			}
		}
	}

	private boolean isMouseOnSelection(SELECTION selection) {
		float x = Mouse.getX();
		float y = Mouse.getY();
		return x > Display.getWidth() / 2.0f
				&& x < Display.getWidth() / 2.0f + 7
						* Fonts.get().large().getCharacterWidth()
				&& y > getSelectionY(selection)
				&& y < getSelectionY(selection)
						+ Fonts.get().large().getCharacterHeight();
	}

	private void handleSelectedAction() {
		if (!Commons.get().isKeyPressed()
				&& ((Keyboard.isKeyDown(Keyboard.KEY_RETURN) || Mouse
						.isButtonDown(0)
						&& isMouseOnSelection(currentSelection)))) {
			Commons.get().setKeyPressed(true);
			Sounds.get().playAccept();
			switch (currentSelection) {
			case CONTINUE:
				Game.get().setCurrentState(GameState.name);
				break;
			case START:
				showLevelChoice();
				break;
			case OPTIONS:
				showOptions();
				break;
			case HIGHSCORE:
				showHighscore();
				break;
			case CREDITS:
				showCredits();
				break;
			case EXIT:
				Game.get().requestShutdown();
				break;
			default:
				break;
			}
		}
	}

	private SELECTION getNextSelection(SELECTION selection, boolean up) {
		switch (selection) {
		case CONTINUE:
			return up ? SELECTION.EXIT : SELECTION.START;
		case START:
			return up ? GameState.paused ? SELECTION.CONTINUE : SELECTION.EXIT
					: SELECTION.OPTIONS;
		case OPTIONS:
			return up ? SELECTION.START : SELECTION.HIGHSCORE;
		case HIGHSCORE:
			return up ? SELECTION.OPTIONS : SELECTION.CREDITS;
		case CREDITS:
			return up ? SELECTION.HIGHSCORE : SELECTION.EXIT;
		case EXIT:
			return up ? SELECTION.CREDITS
					: GameState.paused ? SELECTION.CONTINUE : SELECTION.START;
		default:
			return SELECTION.START;
		}
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
