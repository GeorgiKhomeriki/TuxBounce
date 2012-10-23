package textures;

import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Textures {
	private static Textures instance;
	
	private Texture paddle;
	private Texture ball;
	
	private Texture blockRed;
	private Texture blockPurple;
	private Texture blockOrange;
	private Texture blockGreyFace;
	private Texture blockGreenFace;
	private Texture blockBlueFace;
	private Texture blockRedFace;
	private Texture blockWall;
	private Texture blockWallBroken;
	private Texture blockBrownFace;
	private Texture blockBrownFaceBroken;
	
	private Texture coinBlue;
	private Texture coinGreen;
	private Texture coinGrey;
	private Texture coinRed;
	private Texture coinPotion;
	
	private Texture logo;
	private Texture bgMenu;
	private Texture bgGame1;
	private Texture bgGame2;
	private Texture bgGame3;
	private Texture bgGame4;
	
	private Texture popup;
	
	
	public Textures() {
		try {
			paddle = TextureLoader.getTexture("PNG", ResourceLoader
					.getResourceAsStream("resources/images/paddle.png"));
			ball = TextureLoader.getTexture("PNG", ResourceLoader
					.getResourceAsStream("resources/images/tux.png"));
			
			blockRed = TextureLoader.getTexture("PNG", ResourceLoader
					.getResourceAsStream("resources/images/blockRed.png"));
			blockPurple = TextureLoader.getTexture("PNG", ResourceLoader
					.getResourceAsStream("resources/images/blockPurple.png"));
			blockOrange = TextureLoader.getTexture("PNG", ResourceLoader
					.getResourceAsStream("resources/images/blockOrange.png"));
			blockGreyFace = TextureLoader.getTexture("PNG", ResourceLoader
					.getResourceAsStream("resources/images/blockFaceGrey.png"));
			blockGreenFace = TextureLoader.getTexture("PNG", ResourceLoader
					.getResourceAsStream("resources/images/blockFaceGreen.png"));
			blockBlueFace = TextureLoader.getTexture("PNG", ResourceLoader
					.getResourceAsStream("resources/images/blockFaceBlue.png"));
			blockRedFace = TextureLoader.getTexture("PNG", ResourceLoader
					.getResourceAsStream("resources/images/blockFaceRed.png"));
			blockWall = TextureLoader.getTexture("PNG", ResourceLoader
					.getResourceAsStream("resources/images/blockWall1.png"));
			blockWallBroken = TextureLoader.getTexture("PNG", ResourceLoader
					.getResourceAsStream("resources/images/blockWall2.png"));
			blockBrownFace = TextureLoader.getTexture("PNG", ResourceLoader
					.getResourceAsStream("resources/images/blockFaceBrown1.png"));
			blockBrownFaceBroken = TextureLoader.getTexture("PNG", ResourceLoader
					.getResourceAsStream("resources/images/blockFaceBrown2.png"));
			
			coinBlue = TextureLoader.getTexture("PNG", ResourceLoader
					.getResourceAsStream("resources/images/coinBlue.png"));
			coinGreen = TextureLoader.getTexture("PNG", ResourceLoader
					.getResourceAsStream("resources/images/coinGreen.png"));
			coinGrey = TextureLoader.getTexture("PNG", ResourceLoader
					.getResourceAsStream("resources/images/coinGrey.png"));
			coinRed = TextureLoader.getTexture("PNG", ResourceLoader
					.getResourceAsStream("resources/images/coinRed.png"));
			coinPotion = TextureLoader.getTexture("PNG", ResourceLoader
					.getResourceAsStream("resources/images/coinPotion.png"));
			
			logo = TextureLoader.getTexture("PNG", ResourceLoader
					.getResourceAsStream("resources/images/logo.png"));
			bgMenu = TextureLoader.getTexture("JPG", ResourceLoader
					.getResourceAsStream("resources/images/menu-bg.jpg"));
			bgGame1 = TextureLoader.getTexture("PNG", ResourceLoader
					.getResourceAsStream("resources/images/coldmountain.png"));
			bgGame2 = TextureLoader.getTexture("PNG", ResourceLoader
					.getResourceAsStream("resources/images/sunsetintheswamp.png"));
			bgGame3 = TextureLoader.getTexture("PNG", ResourceLoader
					.getResourceAsStream("resources/images/cloudsinthedesert.png"));
			bgGame4 = TextureLoader.getTexture("PNG", ResourceLoader
					.getResourceAsStream("resources/images/wizardtower.png"));
			
			popup = TextureLoader.getTexture("PNG", ResourceLoader
					.getResourceAsStream("resources/images/futureui.png"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	public Texture getPaddle() {
		return paddle;
	}

	public Texture getBall() {
		return ball;
	}

	public Texture getBlockRed() {
		return blockRed;
	}

	public Texture getBlockPurple() {
		return blockPurple;
	}

	public Texture getBlockOrange() {
		return blockOrange;
	}

	public Texture getBlockGreyFace() {
		return blockGreyFace;
	}

	public Texture getBlockGreenFace() {
		return blockGreenFace;
	}

	public Texture getBlockBlueFace() {
		return blockBlueFace;
	}

	public Texture getBlockRedFace() {
		return blockRedFace;
	}

	public Texture getBlockWall() {
		return blockWall;
	}

	public Texture getBlockWallBroken() {
		return blockWallBroken;
	}

	public Texture getBlockBrownFace() {
		return blockBrownFace;
	}

	public Texture getBlockBrownFaceBroken() {
		return blockBrownFaceBroken;
	}

	public Texture getCoinBlue() {
		return coinBlue;
	}

	public Texture getCoinGreen() {
		return coinGreen;
	}

	public Texture getCoinGrey() {
		return coinGrey;
	}

	public Texture getCoinRed() {
		return coinRed;
	}

	public Texture getCoinPotion() {
		return coinPotion;
	}

	public Texture getLogo() {
		return logo;
	}

	public Texture getBgMenu() {
		return bgMenu;
	}

	public Texture getBgGame1() {
		return bgGame1;
	}

	public Texture getBgGame2() {
		return bgGame2;
	}

	public Texture getBgGame3() {
		return bgGame3;
	}

	public Texture getBgGame4() {
		return bgGame4;
	}

	public Texture getPopup() {
		return popup;
	}

	public static Textures get() {
		if(instance==null) {
			instance = new Textures();
		}
		return instance;
	}
}
