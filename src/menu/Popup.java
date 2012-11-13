package menu;

import static org.lwjgl.opengl.GL11.glColor3f;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import assets.Fonts;
import assets.Sounds;

public abstract class Popup extends AbstractPopup {
	public enum SELECTION {
		YES, NO
	};

	private SELECTION currentSelection;
	private float highlightColor;
	private float highlightColorDelta;
	private String title;
	private String message;

	public Popup(String title, String message, float width, float height) {
		super(width, height);
		this.title = title;
		this.message = message;
		this.currentSelection = SELECTION.YES;
		this.highlightColor = 1.0f;
		this.highlightColorDelta = -1.0f;
	}

	public abstract void doYes();
	public abstract void doNo();

	@Override
	public void renderContent() {
		Fonts.get()
				.large()
				.renderText(title, getX() + 0.05f * getWidth(),
						getY() + 0.7f * getHeight());
		Fonts.get()
				.small()
				.renderText(message, getX() + 0.05f * getWidth(),
						getY() + 0.5f * getHeight());
		highlightSelection(SELECTION.YES);
		Fonts.get()
				.large()
				.renderText("YES", getX() + 0.55f * getWidth(),
						getY() + 0.1f * getHeight());
		highlightSelection(SELECTION.NO);
		Fonts.get()
				.large()
				.renderText("NO", getX() + 0.8f * getWidth(),
						getY() + 0.1f * getHeight());
	}

	private void highlightSelection(SELECTION selection) {
		if (currentSelection.equals(selection))
			glColor3f(1.0f, highlightColor, 0.0f);
		else
			glColor3f(1.0f, 1.0f, 1.0f);
	}

	@Override
	public void updateContent(float delta) {
		updateHighlightColor(delta);
		handleKeyboardInput();
		handleMouseInput();
		handleSelectedAction();
	}

	private void handleKeyboardInput() {
		boolean leftPressed = Keyboard.isKeyDown(Keyboard.KEY_LEFT);
		boolean rightPressed = Keyboard.isKeyDown(Keyboard.KEY_RIGHT);
		boolean returnPressed = Keyboard.isKeyDown(Keyboard.KEY_RETURN);
		if (!Commons.get().isKeyPressed()) {
			if (leftPressed || rightPressed) {
				for (SELECTION s : SELECTION.values()) {
					if (!s.equals(currentSelection)) {
						currentSelection = s;
						break;
					}
				}
				Commons.get().setKeyPressed(true);
				Sounds.get().playCursor();
			}
		} else if (!leftPressed && !rightPressed && !returnPressed) {
			Commons.get().setKeyPressed(false);
		}
	}

	private void handleMouseInput() {
		if (Mouse.getDX() != 0 || Mouse.getDY() != 0) {
			if (isMouseOnSelection(SELECTION.YES)
					&& !currentSelection.equals(SELECTION.YES)) {
				Sounds.get().playCursor();
				currentSelection = SELECTION.YES;
			} else if (isMouseOnSelection(SELECTION.NO)
					&& !currentSelection.equals(SELECTION.NO)) {
				Sounds.get().playCursor();
				currentSelection = SELECTION.NO;
			}
		}
	}

	private boolean isMouseOnSelection(SELECTION selection) {
		float x = Mouse.getX();
		float y = Mouse.getY();
		float loc;
		float size;
		if (selection.equals(SELECTION.YES)) {
			loc = 0.55f;
			size = 3.0f;
		} else if (selection.equals(SELECTION.NO)) {
			loc = 0.8f;
			size = 2.0f;
		} else {
			return false;
		}
		return x > getX() + loc * getWidth()
				&& x < getX() + loc * getWidth() + size
						* Fonts.get().large().getCharacterWidth()
				&& y > getY() + 0.1f * getHeight()
				&& y < getY() + 0.1f * getHeight()
						+ Fonts.get().large().getCharacterHeight();
	}

	private void handleSelectedAction() {
		if (!Commons.get().isKeyPressed()
				&& (Keyboard.isKeyDown(Keyboard.KEY_RETURN) || Mouse
						.isButtonDown(0)
						&& isMouseOnSelection(currentSelection))) {
			Commons.get().setKeyPressed(true);
			switch (currentSelection) {
			case YES:
				Sounds.get().playAccept();
				doYes();
				break;
			case NO:
				Sounds.get().playDecline();
				doNo();
				break;
			}
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
