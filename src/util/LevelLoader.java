package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.lwjgl.opengl.Display;

import breakout.IBlock;
import breakout.SimpleBlock;
import breakout.IBlock.BlockType;


public class LevelLoader {

	public static List<IBlock> load(String file) {
		List<IBlock> blocks = new ArrayList<IBlock>();

		try {
			Scanner s = new Scanner(new File(file));
			for (int y = 0; s.hasNextLine(); y++) {
				String line = s.nextLine();
				String[] parts = line.split(" ");
				for (int x = 0; x < parts.length; x++) {
					int value = Integer.parseInt(parts[x]);
					if (value != 0) {
						BlockType type = getType(value);
						IBlock block = new SimpleBlock(type, x * IBlock.width,
								Display.getHeight() - y * IBlock.height);
						blocks.add(block);
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return blocks;
	}

	private static BlockType getType(int value) {
		switch (value) {
		case 1:
			return BlockType.RED;
		case 2:
			return BlockType.PURPLE;
		case 3:
			return BlockType.ORANGE;
		case 4:
			return BlockType.GREY_FACE;
		case 5:
			return BlockType.GREEN_FACE;
		case 6:
			return BlockType.BLUE_FACE;
		case 7:
			return BlockType.RED_FACE;
		default:
			return null;
		}
	}

	public static String getTexture(BlockType type) {
		String baseDir = "resources/images/";
		switch (type) {
		case RED:
			return baseDir + "blockRed.png";
		case PURPLE:
			return baseDir + "blockPurple.png";
		case ORANGE:
			return baseDir + "blockOrange.png";
		case GREY_FACE:
			return baseDir + "blockFaceGrey.png";
		case GREEN_FACE:
			return baseDir + "blockFaceGreen.png";
		case BLUE_FACE:
			return baseDir + "blockFaceBlue.png";
		case RED_FACE:
			return baseDir + "blockFaceRed.png";
		default:
			return null;
		}
	}

}
