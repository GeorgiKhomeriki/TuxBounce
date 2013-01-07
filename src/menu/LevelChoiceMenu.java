package menu;

import static org.lwjgl.opengl.GL11.glColor3f;
import engine.Game;
import game.GameState;
import game.Hud;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import util.Graphics;
import assets.Fonts;
import assets.Sounds;
import assets.Textures;

public abstract class LevelChoiceMenu {
	private float highlightColor;
	private float highlightColorDelta;
	private int selection;

	public LevelChoiceMenu() {
		this.highlightColor = 1.0f;
		this.highlightColorDelta = -1.0f;
		this.selection = 1;
	}

	public abstract void backToMainMenu();

	public void render(int delta) {
		renderChoices();
		renderBack();
		renderCursor();
	}

	private void renderChoices() {
		float blockWidth = getBlockWidth();
		float blockHeight = getBlockHeight();
		for (int i = 0; i < 10; i++) {
			float blockX = 0.05f * Display.getWidth() + i * blockWidth * 1.1f;
			for (int j = 0; j < 5; j++) {
				float blockY = 0.6f * Display.getHeight() - j * blockHeight
						* 1.1f;
				int num = i + j * 10 + 1;
				float alpha = num <= Commons.get().getLevelProgress() ? 0.8f
						: 0.4f;
				Graphics.drawQuad(blockX, blockY, blockWidth, blockHeight,
						Textures.get().getBlockRed(), 1.0f, alpha, true);
				highlightSelection(num);
				float textX = num > 9 ? blockX - 0.01f * blockWidth : blockX
						+ 0.2f * blockWidth;
				float textY = blockY + 0.3f * blockHeight;
				Fonts.get().large()
						.renderText(String.valueOf(num), textX, textY);
			}
		}
	}

	private void highlightSelection(int n) {
		if (selection == n) {
			glColor3f(1.0f, highlightColor, 0.0f);
		} else {
			glColor3f(1.0f, 1.0f, 1.0f);
		}
	}

	private void renderBack() {
		highlightSelection(0);
		Fonts.get()
				.large()
				.renderText("BACK", Display.getWidth() * 0.45f,
						0.03f * Display.getHeight());
	}

	private void renderCursor() {
		float width = Display.getWidth() / 24;
		float height = Display.getHeight() / 18;
		float x = Display.getWidth() * 0.45f - 1.2f * width;
		float y = 0.03f * Display.getHeight();
		Graphics.drawQuad(x, y, width, height, Textures.get().getBall(), true);
	}

	public void update(int delta) {
		if (!Commons.get().isKeyPressed()) {
			boolean isMouseOnBack = isMouseOnBack();
			if (Keyboard.isKeyDown(Keyboard.KEY_RETURN)
					|| Mouse.isButtonDown(0) && isMouseOnBack) {
				if (isMouseOnBack || selection == 0) {
					backToMainMenu();
					Sounds.get().playDecline();
				} else {
					startGame();
				}
				Commons.get().setKeyPressed(true);
			} else if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
				Commons.get().setKeyPressed(true);
				selection = selection == 0 ? 1 : selection - 1;
			} else if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
				Commons.get().setKeyPressed(true);
				selection = selection == 0 ? 1 : selection + 1;
			} else if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
				Commons.get().setKeyPressed(true);
				selection = selection == 0 ? 1 : selection - 10;
			} else if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
				Commons.get().setKeyPressed(true);
				selection = selection == 0 ? 1 : selection + 10;
			}
		}
		if (selection < 0 || selection > 50) {
			selection = 0;
		}
		if (!Keyboard.isKeyDown(Keyboard.KEY_RETURN) && !Mouse.isButtonDown(0)
				&& !Keyboard.isKeyDown(Keyboard.KEY_LEFT)
				&& !Keyboard.isKeyDown(Keyboard.KEY_RIGHT)
				&& !Keyboard.isKeyDown(Keyboard.KEY_UP)
				&& !Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			Commons.get().setKeyPressed(false);
		}
		handleMouse();
		updateHighlightColor(delta);
	}

	private void handleMouse() {
		boolean startGame = Mouse.isButtonDown(0)
				&& !Commons.get().isKeyPressed();
		if (startGame || Mouse.getDX() != 0 && Mouse.getDY() != 0) {
			if (isMouseOnBack()) {
				selection = 0;
			} else {
				float blockWidth = getBlockWidth();
				float blockHeight = getBlockHeight();
				float mouseX = Mouse.getX();
				float mouseY = Mouse.getY();
				for (int i = 0; i < 10; i++) {
					float blockX = 0.05f * Display.getWidth() + i * blockWidth
							* 1.1f;
					for (int j = 0; j < 5; j++) {
						float blockY = 0.6f * Display.getHeight() - j
								* blockHeight * 1.1f;
						if (mouseX >= blockX && mouseY >= blockY
								&& mouseX <= blockX + blockWidth
								&& mouseY <= blockY + blockHeight) {
							selection = i + j * 10 + 1;
							if (startGame) {
								startGame();
							}
							return;
						}
					}
				}
			}
		}
	}

	private void startGame() {
		if (selection <= Commons.get().getLevelProgress()) {
			backToMainMenu();
			Sounds.get().playAccept();
			Hud.get().reset();
			GameState.level = selection;
			Game.get().reinit(GameState.name);
			Game.get().setCurrentState(GameState.name);
		} else if (!Commons.get().isKeyPressed()) {
			Sounds.get().playDecline();
			Commons.get().setKeyPressed(true);
		}
	}

	private boolean isMouseOnBack() {
		float x = Mouse.getX();
		float y = Mouse.getY();
		return x > Display.getWidth() * 0.45f
				&& x < Display.getWidth() * 0.45f + 4
						* Fonts.get().large().getCharacterWidth()
				&& y > 0.03f * Display.getHeight()
				&& y < 0.03f * Display.getHeight()
						+ Fonts.get().large().getCharacterHeight();
	}

	private void updateHighlightColor(float delta) {
		float newColor = highlightColor + highlightColorDelta * delta / 200;
		if (newColor < 0.0f || newColor > 1.0f) {
			highlightColorDelta = -highlightColorDelta;
		} else {
			highlightColor = newColor;
		}
	}

	private float getBlockWidth() {
		return Display.getWidth() / 12.0f;
	}

	private float getBlockHeight() {
		return Display.getHeight() / 10.0f;
	}

}
