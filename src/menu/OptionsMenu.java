package menu;

import static org.lwjgl.opengl.GL11.glColor3f;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.opengl.Texture;

import engine.Font;

public class OptionsMenu implements IMenu {
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
		this.isKeyPressed = false;
		this.highlightColor = 1.0f;
		this.highlightColorDelta = -1.0f;
	}

	@Override
	public void start() {

	}

	@Override
	public void stop() {

	}

	@Override
	public void render(int delta) {
		highlightSelection(SELECTION.RESOLUTION);
		font.drawText("RESOLUTION", Display.getWidth() / 2.0f,
				getSelectionY(SELECTION.RESOLUTION));
		highlightSelection(SELECTION.FULLSCREEN);
		font.drawText("FULLSCREEN", Display.getWidth() / 2.0f,
				getSelectionY(SELECTION.FULLSCREEN));
		highlightSelection(SELECTION.SOUND);
		font.drawText("SOUND", Display.getWidth() / 2.0f,
				getSelectionY(SELECTION.SOUND));
		highlightSelection(SELECTION.BACK);
		font.drawText("BACK", Display.getWidth() / 2.0f,
				getSelectionY(SELECTION.BACK));
	}

	private void highlightSelection(SELECTION selection) {
		if (currentSelection.equals(selection))
			glColor3f(1.0f, highlightColor, 0.0f);
		else
			glColor3f(1.0f, 1.0f, 1.0f);
	}

	private float getSelectionY(SELECTION selection) {
		float y = Display.getHeight() * 0.5f;
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
