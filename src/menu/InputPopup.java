package menu;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import assets.Fonts;

public abstract class InputPopup extends Popup {
	private String input;

	public InputPopup(final String message, final float width,
			final float height) {
		super("", message, width, height);
		this.input = "";
	}

	@Override
	public void renderContent() {
		Fonts.get()
				.small()
				.renderText(super.getMessage(), getX() + 0.05f * getWidth(),
						getY() + 0.7f * getHeight());
		Fonts.get()
				.large()
				.renderText(getInputWithPoints(), getX() + 0.05f * getWidth(),
						getY() + 0.4f * getHeight());

		highlightSelection(SELECTION.YES);
		Fonts.get()
				.large()
				.renderText("OK", getX() + 0.25f * getWidth(),
						getY() + 0.1f * getHeight());
		highlightSelection(SELECTION.NO);
		Fonts.get()
				.large()
				.renderText("CANCEL", getX() + 0.5f * getWidth(),
						getY() + 0.1f * getHeight());
	}

	private final String getInputWithPoints() {
		String input = this.input;
		for (int i = input.length(); i < 13; i++) {
			input += ".";
		}
		return input;
	}

	@Override
	protected final boolean isMouseOnSelection(final SELECTION selection) {
		final float x = Mouse.getX();
		final float y = Mouse.getY();
		float loc;
		float size;
		if (selection.equals(SELECTION.YES)) {
			loc = 0.25f;
			size = 2.0f;
		} else if (selection.equals(SELECTION.NO)) {
			loc = 0.5f;
			size = 6.0f;
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

	@Override
	public void updateContent(final float delta) {
		super.updateContent(delta);
		updateInput();
	}

	private void updateInput() {
		if (!Commons.get().isKeyPressed()) {
			while (Keyboard.next()) {
				final char c = Keyboard.getEventCharacter();
				if (c >= 48 && c <= 57 || c >= 65 && c <= 90 || c >= 97
						&& c <= 122 && input.length() < 13) {
					input += String.valueOf(c).toUpperCase();
				} else if (c == 8 && input.length() > 0) {
					input = input.substring(0, input.length() - 1);
				}
			}
		}
	}

	public final String getInput() {
		return input;
	}

}
