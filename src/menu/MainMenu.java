package menu;

import static org.lwjgl.opengl.GL11.glColor3f;
import engine.Game;
import game.GameState;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import util.Graphics;
import assets.Fonts;
import assets.Sounds;
import assets.Textures;

public abstract class MainMenu {
	private enum SELECTION {
		CONTINUE, START, OPTIONS, HIGHSCORE, EXIT
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

	public void render(final int delta) {
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
		highlightSelection(SELECTION.EXIT);
		Fonts.get()
				.large()
				.renderText("EXIT", Display.getWidth() / 2.0f,
						getSelectionY(SELECTION.EXIT));
	}

	private void highlightSelection(final SELECTION selection) {
		if (currentSelection.equals(selection))
			glColor3f(1.0f, highlightColor, 0.0f);
		else
			glColor3f(1.0f, 1.0f, 1.0f);
	}

	private void renderCursor() {
		final float width = Display.getWidth() / 24;
		final float height = Display.getHeight() / 18;
		final float x = Display.getWidth() * 0.5f - 1.2f * width;
		final float y = getSelectionY(currentSelection);
		Graphics.drawQuad(x, y, width, height, Textures.get().getBall(), true);
	}

	private final float getSelectionY(final SELECTION selection) {
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
		case EXIT:
			y -= Display.getHeight() / 5.2f;
			break;
		default:
			break;
		}
		return y;
	}

	public void update(final int delta) {
		updateHighlightColor(delta);

		handleKeyboardInput();

		handleMouseInput();

		handleSelectedAction();
	}

	private void handleKeyboardInput() {
		final boolean downPressed = Keyboard.isKeyDown(Keyboard.KEY_DOWN);
		final boolean upPressed = Keyboard.isKeyDown(Keyboard.KEY_UP);
		final boolean returnPressed = Keyboard.isKeyDown(Keyboard.KEY_RETURN);
		final boolean mouseClicked = Mouse.isButtonDown(0);
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
			for (final SELECTION selection : SELECTION.values()) {
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

	private final boolean isMouseOnSelection(final SELECTION selection) {
		final float x = Mouse.getX();
		final float y = Mouse.getY();
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
			case EXIT:
				Game.get().requestShutdown();
				break;
			default:
				break;
			}
		}
	}

	private final SELECTION getNextSelection(final SELECTION selection,
			final boolean up) {
		switch (selection) {
		case CONTINUE:
			return up ? SELECTION.EXIT : SELECTION.START;
		case START:
			return up ? GameState.paused ? SELECTION.CONTINUE : SELECTION.EXIT
					: SELECTION.OPTIONS;
		case OPTIONS:
			return up ? SELECTION.START : SELECTION.HIGHSCORE;
		case HIGHSCORE:
			return up ? SELECTION.OPTIONS : SELECTION.EXIT;
		case EXIT:
			return up ? SELECTION.HIGHSCORE
					: GameState.paused ? SELECTION.CONTINUE : SELECTION.START;
		default:
			return SELECTION.START;
		}
	}

	private void updateHighlightColor(final float delta) {
		final float newColor = highlightColor + highlightColorDelta * delta
				/ 200;
		if (newColor < 0.0f || newColor > 1.0f) {
			highlightColorDelta = -highlightColorDelta;
		} else {
			highlightColor = newColor;
		}
	}

}
