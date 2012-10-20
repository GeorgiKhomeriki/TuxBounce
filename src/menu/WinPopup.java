package menu;

import static org.lwjgl.opengl.GL11.glColor3f;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import breakout.GameState;

import engine.Game;

import sound.Sound;

public abstract class WinPopup extends Popup {
	public enum SELECTION {
		YES, NO
	};

	private SELECTION currentSelection;
	private boolean isKeyPressed;
	private float highlightColor;
	private float highlightColorDelta;

	public WinPopup(float width, float height) {
		super(width, height);
		this.currentSelection = SELECTION.YES;
		this.isKeyPressed = true;
		this.highlightColor = 1.0f;
		this.highlightColorDelta = -1.0f;
	}
	
	public abstract void doYes();

	@Override
	public void renderContent() {
		getFontLarge().drawText("AWESOME!", getX() + 0.05f * getWidth(),
				getY() + 0.7f * getHeight());
		getFontSmall().drawText("ARE YOU READY FOR THE NEXT LEVEL?",
				getX() + 0.05f * getWidth(), getY() + 0.5f * getHeight());
		highlightSelection(SELECTION.YES);
		getFontLarge().drawText("YES", getX() + 0.55f * getWidth(),
				getY() + 0.1f * getHeight());
		highlightSelection(SELECTION.NO);
		getFontLarge().drawText("NO", getX() + 0.8f * getWidth(),
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
		if (!isKeyPressed) {
			if (leftPressed || rightPressed) {
				for (SELECTION s : SELECTION.values()) {
					if (!s.equals(currentSelection)) {
						currentSelection = s;
						break;
					}
				}
				isKeyPressed = true;
				Sound.get().playCursor();
			}
		} else if (!leftPressed && !rightPressed && !returnPressed) {
			isKeyPressed = false;
		}
	}
	
	private void handleMouseInput() {
		if(isMouseOnSelection(SELECTION.YES)) {
			currentSelection = SELECTION.YES;
		} else if(isMouseOnSelection(SELECTION.NO)) {
			currentSelection = SELECTION.NO;
		}
	}
	
	private boolean isMouseOnSelection(SELECTION selection) {
		float x = Mouse.getX();
		float y = Mouse.getY();
		switch (selection) {
		case YES:
			return x > getX() + 0.55f * getWidth()
					&& x < getX() + 0.55f * getWidth() + 3 * getFontLarge().getCharacterWidth()
					&& y > getY() + 0.1f * getHeight()
					&& y < getY() + 0.1f * getHeight() + getFontLarge().getCharacterHeight();
		case NO:
			return x > getX() + 0.8f * getWidth()
					&& x < getX() + 0.8f * getWidth() + 2 * getFontLarge().getCharacterWidth()
					&& y > getY() + 0.1f * getHeight()
					&& y < getY() + 0.1f * getHeight() + getFontLarge().getCharacterHeight();
		default:
			return false;
		}
	}
	
	private void handleSelectedAction() {
		if (!isKeyPressed
				&& (Keyboard.isKeyDown(Keyboard.KEY_RETURN) || Mouse
						.isButtonDown(0)
						&& isMouseOnSelection(currentSelection))) {
			isKeyPressed = true;
			Sound.get().playAccept();
			switch (currentSelection) {
			case YES:
				doYes();
				break;
			case NO:
				doNo();
				break;
			}
		}
	}
	
	private void doNo() {
		GameState.paused = true;
		Sound.get().playDecline();
		Game.get().setCurrentState(MenuState.name);
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
