package engine;

public class Text {
	private final String text;
	private final float x;
	private float y;
	private final int life;
	private float age;
	private final boolean infinite;
	private final boolean doFade;

	public Text(final String text, final float x, final float y) {
		this.text = text;
		this.x = x;
		this.y = y;
		this.life = 0;
		this.infinite = true;
		this.doFade = false;
	}

	public Text(final String text, final float x, final float y,
			final int life, final boolean doFade) {
		this.text = text;
		this.x = x;
		this.y = y;
		this.life = life;
		this.age = 0;
		this.infinite = false;
		this.doFade = doFade;
	}

	public void update(final float delta) {
		age += delta / 25.0f;
		y += delta / 20.0f;
	}

	public final boolean isAlive() {
		return infinite || age < life;
	}

	public final String getText() {
		return text;
	}

	public final float getX() {
		return x;
	}

	public final float getY() {
		return y;
	}

	public final int getLife() {
		return life;
	}

	public final float getAge() {
		return age;
	}

	public final boolean isDoFade() {
		return doFade;
	}

}
