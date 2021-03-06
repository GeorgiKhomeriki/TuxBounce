package menu;

import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import engine.Game;
import game.GameState;
import game.Hud;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import util.Config;
import util.Graphics;
import assets.Fonts;
import assets.Sounds;
import assets.Textures;

public abstract class OptionsMenu {
	private enum SELECTION {
		RESOLUTION, FULLSCREEN, SOUND, MUSIC, BACK
	}

	private SELECTION currentSelection;
	private float highlightColor;
	private float highlightColorDelta;

	public OptionsMenu() {
		this.currentSelection = SELECTION.RESOLUTION;
		this.highlightColor = 1.0f;
		this.highlightColorDelta = -1.0f;
	}

	public abstract void backToMainMenu();

	public void render(final int delta) {
		renderOptions();
		renderValues();
		renderCursor();
	}

	private void renderOptions() {
		final float x = 0.1f * Display.getWidth();
		highlightSelection(SELECTION.RESOLUTION);
		Fonts.get()
				.large()
				.renderText("RESOLUTION:", x,
						getSelectionY(SELECTION.RESOLUTION));
		highlightSelection(SELECTION.FULLSCREEN);
		Fonts.get()
				.large()
				.renderText("FULLSCREEN:", x,
						getSelectionY(SELECTION.FULLSCREEN));
		highlightSelection(SELECTION.SOUND);
		Fonts.get().large()
				.renderText("SOUND:", x, getSelectionY(SELECTION.SOUND));
		highlightSelection(SELECTION.MUSIC);
		Fonts.get().large()
				.renderText("MUSIC:", x, getSelectionY(SELECTION.MUSIC));
		highlightSelection(SELECTION.BACK);
		Fonts.get().large()
				.renderText("BACK", x, getSelectionY(SELECTION.BACK));
	}

	private void highlightSelection(final SELECTION selection) {
		if (currentSelection.equals(selection))
			glColor3f(1.0f, highlightColor, 0.0f);
		else
			glColor3f(1.0f, 1.0f, 1.0f);
	}

	private void renderValues() {
		glColor3f(1.0f, 1.0f, 1.0f);
		final float x = 0.5f * Display.getWidth();
		String resolution = Display.getWidth() + "X" + Display.getHeight();
		Fonts.get().large()
				.renderText(resolution, x, getSelectionY(SELECTION.RESOLUTION));
		String fullscreen = booleanToString(Display.isFullscreen());
		Fonts.get().large()
				.renderText(fullscreen, x, getSelectionY(SELECTION.FULLSCREEN));
		String sound = booleanToString(Sounds.get().isSoundEnabled());
		Fonts.get().large()
				.renderText(sound, x, getSelectionY(SELECTION.SOUND));
		String music = booleanToString(Sounds.get().isMusicEnabled());
		Fonts.get().large()
				.renderText(music, x, getSelectionY(SELECTION.MUSIC));
	}

	private String booleanToString(final boolean b) {
		return b ? "ON" : "OFF";
	}

	private void renderCursor() {
		final float width = Display.getWidth() / 24;
		final float height = Display.getHeight() / 18;
		final float x = Display.getWidth() * 0.1f - 1.2f * width;
		final float y = getSelectionY(currentSelection);
		Graphics.drawQuad(x, y, width, height, Textures.get().getBall(), true);
	}

	private float getSelectionY(final SELECTION selection) {
		float y = Display.getHeight() * 0.6f;
		switch (selection) {
		case FULLSCREEN:
			y -= Display.getHeight() / 16.0f;
			break;
		case SOUND:
			y -= Display.getHeight() / 8.0f;
			break;
		case MUSIC:
			y -= Display.getHeight() / 5.2f;
			break;
		case BACK:
			y -= Display.getHeight() / 3.4f;
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
						&& isMouseOnSelection(selection)) {
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
		return x > 0.1f * Display.getWidth()
				&& x < 0.1f * Display.getWidth() + 10
						* Fonts.get().large().getCharacterWidth()
				&& y > getSelectionY(selection)
				&& y < getSelectionY(selection)
						+ Fonts.get().large().getCharacterHeight();
	}

	private void handleSelectedAction() {
		if (!Commons.get().isKeyPressed()
				&& (Keyboard.isKeyDown(Keyboard.KEY_RETURN) || Mouse
						.isButtonDown(0)
						&& isMouseOnSelection(currentSelection))) {
			Commons.get().setKeyPressed(true);
			switch (currentSelection) {
			case RESOLUTION:
				try {
					DisplayMode[] modes = Display.getAvailableDisplayModes();
					for (int i = 0; i < modes.length; i++) {
						if (Graphics.compareDisplayModes(modes[i],
								Display.getDisplayMode())) {
							final DisplayMode newMode = i + 1 < modes.length ? modes[i + 1]
									: modes[0];
							Graphics.setDisplayMode(newMode.getWidth(),
									newMode.getHeight(), Display.isFullscreen());
							glMatrixMode(GL_PROJECTION);
							glLoadIdentity();
							glOrtho(0, newMode.getWidth(), 0,
									newMode.getHeight(), 1, -1);
							glMatrixMode(GL_MODELVIEW);
							Hud.get().resize();
							Game.get().reinit(MenuState.name);
							Game.get().reinit(GameState.name);
							break;
						}
					}
				} catch (LWJGLException e) {
					e.printStackTrace();
				}
				Sounds.get().playAccept();
				break;
			case FULLSCREEN:
				Graphics.setDisplayMode(Display.getDisplayMode().getWidth(),
						Display.getDisplayMode().getHeight(),
						!Display.isFullscreen());
				Sounds.get().playAccept();
				break;
			case MUSIC:
				Sounds.get().setMusicEnabled(!Sounds.get().isMusicEnabled());
				Sounds.get().playAccept();
				break;
			case SOUND:
				Sounds.get().setSoundEnabled(!Sounds.get().isSoundEnabled());
				Sounds.get().playAccept();
				break;
			case BACK:
				Config.saveOptions(Display.getDisplayMode().toString(),
						Display.isFullscreen(), Sounds.get().isSoundEnabled(),
						Sounds.get().isMusicEnabled());
				backToMainMenu();
				Sounds.get().playDecline();
				break;
			default:
				break;
			}
		}
	}

	private final SELECTION getNextSelection(final SELECTION selection,
			final boolean up) {
		switch (selection) {
		case RESOLUTION:
			return up ? SELECTION.BACK : SELECTION.FULLSCREEN;
		case FULLSCREEN:
			return up ? SELECTION.RESOLUTION : SELECTION.SOUND;
		case SOUND:
			return up ? SELECTION.FULLSCREEN : SELECTION.MUSIC;
		case MUSIC:
			return up ? SELECTION.SOUND : SELECTION.BACK;
		case BACK:
			return up ? SELECTION.MUSIC : SELECTION.RESOLUTION;
		default:
			return SELECTION.RESOLUTION;
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
