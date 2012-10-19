package menu;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.opengl.Texture;

import sound.Sound;
import util.Graphics;
import engine.Font;

public abstract class OptionsMenu implements IMenu {
	private enum SELECTION {
		RESOLUTION, FULLSCREEN, SOUND, BACK
	}

	private Texture cursorTexture;
	private Font font;
	private SELECTION currentSelection;
	private boolean isKeyPressed;
	private float highlightColor;
	private float highlightColorDelta;

	public OptionsMenu(Font font, Texture cursorTexture) {
		this.font = font;
		this.cursorTexture = cursorTexture;
		this.currentSelection = SELECTION.RESOLUTION;
		this.isKeyPressed = true;
		this.highlightColor = 1.0f;
		this.highlightColorDelta = -1.0f;
	}

	public abstract void backToMainMenu();

	@Override
	public void render(int delta) {
		renderOptions();
		renderValues();
		renderCursor();
	}

	private void renderOptions() {
		float x = 0.1f * Display.getWidth();
		highlightSelection(SELECTION.RESOLUTION);
		font.drawText("RESOLUTION:", x, getSelectionY(SELECTION.RESOLUTION));
		highlightSelection(SELECTION.FULLSCREEN);
		font.drawText("FULLSCREEN:", x, getSelectionY(SELECTION.FULLSCREEN));
		highlightSelection(SELECTION.SOUND);
		font.drawText("SOUND:", x, getSelectionY(SELECTION.SOUND));
		highlightSelection(SELECTION.BACK);
		font.drawText("BACK", x, getSelectionY(SELECTION.BACK));
	}

	private void highlightSelection(SELECTION selection) {
		if (currentSelection.equals(selection))
			glColor3f(1.0f, highlightColor, 0.0f);
		else
			glColor3f(1.0f, 1.0f, 1.0f);
	}

	private void renderValues() {
		glColor3f(1.0f, 1.0f, 1.0f);
		float x = 0.5f * Display.getWidth();
		String resolution = Display.getWidth() + "X" + Display.getHeight();
		font.drawText(resolution, x, getSelectionY(SELECTION.RESOLUTION));
		String fullscreen = booleanToString(Display.isFullscreen());
		if (!Display.getDisplayMode().isFullscreenCapable()) {
			fullscreen = "CHANGE RES.";
		}
		font.drawText(fullscreen, x, getSelectionY(SELECTION.FULLSCREEN));
		String sound = booleanToString(Sound.get().isEnabled());
		font.drawText(sound, x, getSelectionY(SELECTION.SOUND));
	}

	private String booleanToString(boolean b) {
		return b ? "ON" : "OFF";
	}

	private void renderCursor() {
		float width = Display.getWidth() / 24;
		float height = Display.getHeight() / 18;
		float x = Display.getWidth() * 0.1f - 1.2f * width;
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
		float y = Display.getHeight() * 0.6f;
		switch (selection) {
		case FULLSCREEN:
			y -= Display.getHeight() / 16.0f;
			break;
		case SOUND:
			y -= Display.getHeight() / 8.0f;
			break;
		case BACK:
			y -= Display.getHeight() / 5.2f;
			break;
		default:
			break;
		}
		return y;
	}

	@Override
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
		if (!isKeyPressed) {
			if (downPressed || upPressed) {
				currentSelection = getNextSelection(currentSelection, upPressed);
				isKeyPressed = true;
				Sound.get().playCursor();
			}
		} else if (!downPressed && !upPressed && !returnPressed
				&& !mouseClicked) {
			isKeyPressed = false;
		}
	}

	private void handleMouseInput() {
		if (Mouse.getDX() != 0 || Mouse.getDY() != 0) {
			for (SELECTION selection : SELECTION.values()) {
				if (!currentSelection.equals(selection)
						&& isMouseOnSelection(selection)) {
					Sound.get().playCursor();
					currentSelection = selection;
					break;
				}
			}
		}
	}

	private boolean isMouseOnSelection(SELECTION selection) {
		float x = Mouse.getX();
		float y = Mouse.getY();
		return x > 0.1f * Display.getWidth()
				&& x < 0.1f * Display.getWidth() + 10
						* font.getCharacterWidth()
				&& y > getSelectionY(selection)
				&& y < getSelectionY(selection) + font.getCharacterHeight();
	}

	private void handleSelectedAction() {
		if (!isKeyPressed
				&& (Keyboard.isKeyDown(Keyboard.KEY_RETURN) || Mouse
						.isButtonDown(0)
						&& isMouseOnSelection(currentSelection))) {
			isKeyPressed = true;
			switch (currentSelection) {
			case RESOLUTION:
				try {
					DisplayMode[] modes = Display.getAvailableDisplayModes();
					for(int i = 0; i < modes.length; i++) {
						if(Graphics.compareDisplayModes(modes[i], Display.getDisplayMode())) {
							if(i+1 < modes.length)
								Display.setDisplayMode(modes[i+1]);
							else
								Display.setDisplayMode(modes[0]);
							break;
						}
					}
				} catch (LWJGLException e1) {
					e1.printStackTrace();
				}
				Sound.get().playAccept();
				break;
			case FULLSCREEN:
				try {
					if (Display.getDisplayMode().isFullscreenCapable())
						Display.setFullscreen(!Display.isFullscreen());
				} catch (LWJGLException e) {
					e.printStackTrace();
				}
				Sound.get().playAccept();
				break;
			case SOUND:
				Sound.get().setEnabled(!Sound.get().isEnabled());
				Sound.get().playAccept();
				break;
			case BACK:
				backToMainMenu();
				Sound.get().playDecline();
				break;
			default:
				break;
			}
		}
	}

	private SELECTION getNextSelection(SELECTION selection, boolean up) {
		switch (selection) {
		case RESOLUTION:
			return up ? SELECTION.BACK : SELECTION.FULLSCREEN;
		case FULLSCREEN:
			return up ? SELECTION.RESOLUTION : SELECTION.SOUND;
		case SOUND:
			return up ? SELECTION.FULLSCREEN : SELECTION.BACK;
		case BACK:
			return up ? SELECTION.SOUND : SELECTION.RESOLUTION;
		default:
			return SELECTION.RESOLUTION;
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
