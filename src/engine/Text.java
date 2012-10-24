package engine;

public class Text {
	private String text;
	private float x;
	private float y;
	private int life;
	private float age;
	private boolean infinite;
	private boolean doFade;

	public Text(String text, float x, float y) {
		this.text = text;
		this.x = x;
		this.y = y;
		this.infinite = true;
	}

	public Text(String text, float x, float y, int life, boolean doFade) {
		this.text = text;
		this.x = x;
		this.y = y;
		this.life = life;
		this.age = 0;
		this.infinite = false;
		this.doFade = doFade;
	}

	public void update(float delta) {
		age += delta / 25.0f;
		y += delta / 20.0f;
	}

	public boolean isAlive() {
		return infinite || age < life;
	}

	public String getText() {
		return text;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public int getLife() {
		return life;
	}
	
	public float getAge() {
		return age;
	}

	public boolean isDoFade() {
		return doFade;
	}

}
