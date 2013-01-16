package assets;

import game.Block.BlockType;

import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Textures {
	private static Textures instance;

	private final Texture paddle;
	private final Texture ball;

	private final Texture blockRed;
	private final Texture blockPurple;
	private final Texture blockOrange;
	private final Texture blockGreyFace;
	private final Texture blockGreenFace;
	private final Texture blockBlueFace;
	private final Texture blockRedFace;
	private final Texture blockWall;
	private final Texture blockWallBroken;
	private final Texture blockBrownFace;
	private final Texture blockBrownFaceBroken;

	private final Texture coinBlue;
	private final Texture coinGreen;
	private final Texture coinGrey;
	private final Texture coinRed;
	private final Texture coinPotion;

	private final Texture logo;
	private final Texture bgMenu;
	private final Texture bgGame1;
	private final Texture bgGame2;
	private final Texture bgGame3;
	private final Texture bgGame4;

	private final Texture popup;

	public Textures() throws IOException {
		paddle = loadTexture("resources/images/paddle.png");
		ball = loadTexture("resources/images/tux.png");

		blockRed = loadTexture("resources/images/blockRed.png");
		blockPurple = loadTexture("resources/images/blockPurple.png");
		blockOrange = loadTexture("resources/images/blockOrange.png");
		blockGreyFace = loadTexture("resources/images/blockFaceGrey.png");
		blockGreenFace = loadTexture("resources/images/blockFaceGreen.png");
		blockBlueFace = loadTexture("resources/images/blockFaceBlue.png");
		blockRedFace = loadTexture("resources/images/blockFaceRed.png");
		blockWall = loadTexture("resources/images/blockWall1.png");
		blockWallBroken = loadTexture("resources/images/blockWall2.png");
		blockBrownFace = loadTexture("resources/images/blockFaceBrown1.png");
		blockBrownFaceBroken = loadTexture("resources/images/blockFaceBrown2.png");

		coinBlue = loadTexture("resources/images/coinBlue.png");
		coinGreen = loadTexture("resources/images/coinGreen.png");
		coinGrey = loadTexture("resources/images/coinGrey.png");
		coinRed = loadTexture("resources/images/coinRed.png");
		coinPotion = loadTexture("resources/images/coinPotion.png");

		logo = loadTexture("resources/images/logo.png");
		bgMenu = loadTexture("resources/images/forest.png");
		bgGame1 = loadTexture("resources/images/coldmountain.png");
		bgGame2 = loadTexture("resources/images/sunsetintheswamp.png");
		bgGame3 = loadTexture("resources/images/cloudsinthedesert.png");
		bgGame4 = loadTexture("resources/images/wizardtower.png");

		popup = loadTexture("resources/images/futureui.png");
	}

	private final Texture loadTexture(final String file) throws IOException {
		return TextureLoader.getTexture("PNG",
				ResourceLoader.getResourceAsStream(file));
	}

	public final Texture getBlockTexture(final BlockType type) {
		switch (type) {
		case RED:
			return blockRed;
		case PURPLE:
			return blockPurple;
		case ORANGE:
			return blockOrange;
		case GREY_FACE:
			return blockGreyFace;
		case GREEN_FACE:
			return blockGreenFace;
		case BLUE_FACE:
			return blockBlueFace;
		case RED_FACE:
			return blockRedFace;
		case WALL:
			return blockWall;
		case WALL_BROKEN:
			return blockWallBroken;
		case BROWN_FACE:
			return blockBrownFace;
		case BROWN_FACE_BROKEN:
			return blockBrownFaceBroken;
		default:
			return null;
		}
	}

	public final Texture getCoinTexture(final BlockType type) {
		switch (type) {
		case BLUE_FACE:
			return coinBlue;
		case RED_FACE:
			return coinRed;
		case GREEN_FACE:
			return coinGreen;
		case GREY_FACE:
			return coinGrey;
		case BROWN_FACE_BROKEN:
			return coinPotion;
		default:
			return null;
		}
	}

	public final Texture getPaddle() {
		return paddle;
	}

	public final Texture getBall() {
		return ball;
	}

	public final Texture getBlockRed() {
		return blockRed;
	}

	public final Texture getBlockPurple() {
		return blockPurple;
	}

	public final Texture getBlockOrange() {
		return blockOrange;
	}

	public final Texture getBlockGreyFace() {
		return blockGreyFace;
	}

	public final Texture getBlockGreenFace() {
		return blockGreenFace;
	}

	public final Texture getBlockBlueFace() {
		return blockBlueFace;
	}

	public final Texture getBlockRedFace() {
		return blockRedFace;
	}

	public final Texture getBlockWall() {
		return blockWall;
	}

	public final Texture getBlockWallBroken() {
		return blockWallBroken;
	}

	public final Texture getBlockBrownFace() {
		return blockBrownFace;
	}

	public final Texture getBlockBrownFaceBroken() {
		return blockBrownFaceBroken;
	}

	public final Texture getCoinBlue() {
		return coinBlue;
	}

	public final Texture getCoinGreen() {
		return coinGreen;
	}

	public final Texture getCoinGrey() {
		return coinGrey;
	}

	public final Texture getCoinRed() {
		return coinRed;
	}

	public final Texture getCoinPotion() {
		return coinPotion;
	}

	public final Texture getLogo() {
		return logo;
	}

	public final Texture getBgMenu() {
		return bgMenu;
	}

	public final Texture getBgGame1() {
		return bgGame1;
	}

	public final Texture getBgGame2() {
		return bgGame2;
	}

	public final Texture getBgGame3() {
		return bgGame3;
	}

	public final Texture getBgGame4() {
		return bgGame4;
	}

	public final Texture getPopup() {
		return popup;
	}

	public final static Textures get() {
		if (instance == null) {
			try {
				instance = new Textures();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return instance;
	}
}
