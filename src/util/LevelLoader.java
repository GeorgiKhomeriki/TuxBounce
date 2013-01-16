package util;

import game.Block;
import game.Block.BlockType;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.lwjgl.opengl.Display;

public class LevelLoader {

	public static final List<Block> load(final String file, final float offset) {
		final List<Block> blocks = new ArrayList<Block>();

		try {
			final Scanner s = new Scanner(new File(file));
			for (int y = 0; s.hasNextLine(); y++) {
				final String line = s.nextLine();
				final String[] parts = line.split(" ");
				for (int x = 0; x < parts.length; x++) {
					final int value = Integer.parseInt(parts[x]);
					if (value != 0) {
						final BlockType type = getType(value);
						final Block block = new Block(type, x * Block.getWidth(),
								Display.getHeight() - offset - y * Block.getHeight());
						blocks.add(block);
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return blocks;
	}

	private static final BlockType getType(final int value) {
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
		case 8:
			return BlockType.WALL;
		case 9:
			return BlockType.BROWN_FACE;
		default:
			return null;
		}
	}

}
